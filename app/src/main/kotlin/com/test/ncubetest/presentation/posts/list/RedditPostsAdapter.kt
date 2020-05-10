package com.test.ncubetest.presentation.posts.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.test.ncubetest.R
import com.test.ncubetest.domain.posts.model.RedditPost

class RedditPostsAdapter : PagedListAdapter<RedditPost, RedditPostViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RedditPostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val redditPostItemView = layoutInflater.inflate(R.layout.item_reddit_post, parent, false)
        return RedditPostViewHolder(redditPostItemView)
    }

    override fun onBindViewHolder(holder: RedditPostViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<RedditPost>() {
            override fun areItemsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: RedditPost, newItem: RedditPost): Boolean =
                oldItem == newItem
        }
    }
}
