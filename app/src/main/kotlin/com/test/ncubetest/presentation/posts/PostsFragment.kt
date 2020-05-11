package com.test.ncubetest.presentation.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.test.ncubetest.R
import com.test.ncubetest.app.App
import com.test.ncubetest.data.posts.repository.model.NetworkState
import com.test.ncubetest.presentation.posts.list.RedditPostsAdapter
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment: Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostsViewModel
    private val adapterDataObserver = object : AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            loadingView.visibility = GONE
            retryLayout.visibility = GONE
        }
    }
    private val postsAdapter: RedditPostsAdapter by lazy {
        RedditPostsAdapter().also {
            it.registerAdapterDataObserver(adapterDataObserver)
            postsView.adapter = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_posts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDependencies()
        initList()
        initSwipeToRefresh()
        initRetry()
    }

    private fun initDependencies() {
        App.appComponent.fragmentComponent().create().inject(this)
        viewModel = viewModelFactory.create(PostsViewModel::class.java)
    }

    private fun initList() {
        val llm = LinearLayoutManager(activity)
        llm.orientation = RecyclerView.VERTICAL
        postsView.layoutManager = llm
        postsView.addItemDecoration(DividerItemDecoration(activity, llm.orientation))
        postsView.adapter = postsAdapter
        viewModel.posts.observe(this, Observer {
            postsAdapter.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    loadingView.visibility = VISIBLE
                    retryLayout.visibility = GONE
                }
                NetworkState.LOADED -> {
                    retryLayout.visibility = GONE
                }
                else -> {
                    loadingView.visibility = GONE
                    retryLayout.visibility = VISIBLE
                }
            }
        })
    }

    private fun initSwipeToRefresh() {
        viewModel.refreshState.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it == NetworkState.LOADING
        })
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun initRetry() {
        retryBtn.setOnClickListener { viewModel.retry(postsAdapter.currentList?.last()?.name) }
    }

    override fun onDestroyView() {
        postsAdapter.unregisterAdapterDataObserver(adapterDataObserver)
        super.onDestroyView()
    }
}