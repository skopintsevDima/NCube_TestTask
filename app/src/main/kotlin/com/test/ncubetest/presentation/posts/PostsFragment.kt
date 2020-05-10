package com.test.ncubetest.presentation.posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.test.ncubetest.R
import com.test.ncubetest.app.App
import com.test.ncubetest.domain.posts.model.RedditPost
import com.test.ncubetest.domain.posts.usecase.posts.GetPostsCallback
import com.test.ncubetest.domain.posts.usecase.posts.PendingCallFunc
import com.test.ncubetest.presentation.posts.list.RedditPostsAdapter
import kotlinx.android.synthetic.main.fragment_posts.*
import javax.inject.Inject

class PostsFragment: Fragment(), GetPostsCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: PostsViewModel
    private val adapterDataObserver = object : AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            loadingView.visibility = GONE
            errorLayout.visibility = GONE
        }
    }
    private val postsAdapter: RedditPostsAdapter by lazy {
        RedditPostsAdapter().also {
            it.registerAdapterDataObserver(adapterDataObserver)
            postsView.adapter = it
        }
    }
    private lateinit var connectionObserver: Observer<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_posts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDependencies()
        initUI()
    }

    private fun initDependencies() {
        App.appComponent.fragmentComponent().create().inject(this)
        viewModel = viewModelFactory.create(PostsViewModel::class.java)
    }

    private fun initUI() {
        activity?.let { activity ->
            val llm = LinearLayoutManager(activity)
            llm.orientation = RecyclerView.VERTICAL
            postsView.layoutManager = llm
            postsView.addItemDecoration(DividerItemDecoration(activity, llm.orientation))
            loadPosts(false)
        }
        swipeRefreshLayout.setOnRefreshListener { loadPosts(true) }
        retryBtn.setOnClickListener { loadPosts(false) }
    }

    private fun loadPosts(refresh: Boolean) {
        swipeRefreshLayout.isRefreshing = true
        loadingView.visibility = VISIBLE
        errorLayout.visibility = GONE
        viewModel.loadPosts(this, refresh).observe(viewLifecycleOwner, Observer {
            swipeRefreshLayout.isRefreshing = false
            handlePostsRes(it)
        })
    }

    private fun handlePostsRes(postsList: PagedList<RedditPost>) {
        postsAdapter.submitList(postsList)
    }

    override fun onError(t: Throwable) {
        activity?.let {
            it.runOnUiThread {
                loadingView.visibility = GONE
                if (postsAdapter.currentList.isNullOrEmpty()) {
                    errorLayout.visibility = VISIBLE
                }
            }
        }
    }

    /**
     * This [GetPostsCallback]'s method added to have possibility load more items if needed,
     * after Internet connection restored.
     */
    override fun setupPendingLoading(pendingCallFunc: PendingCallFunc) {
        connectionObserver = Observer {
            if (it) {
                pendingCallFunc.invoke()
                viewModel.getNetworkState().removeObserver(connectionObserver)
            }
        }
        viewModel.getNetworkState().observe(viewLifecycleOwner, connectionObserver)
    }

    override fun onDestroyView() {
        postsAdapter.unregisterAdapterDataObserver(adapterDataObserver)
        super.onDestroyView()
    }
}