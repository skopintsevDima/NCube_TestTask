package com.test.ncubetest.presentation.posts.list

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.ncubetest.R
import com.test.ncubetest.domain.posts.model.RedditPost
import java.text.SimpleDateFormat
import java.util.*

class RedditPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val scoreView = itemView.findViewById<AppCompatTextView>(R.id.scoreView)
    private val authorView = itemView.findViewById<AppCompatTextView>(R.id.authorView)
    private val subredditView = itemView.findViewById<AppCompatTextView>(R.id.subredditView)
    private val thumbView = itemView.findViewById<AppCompatImageView>(R.id.thumbView)
    // TODO: Make this view scrollable
    private val titleView = itemView.findViewById<AppCompatTextView>(R.id.titleView)
    private val createdView = itemView.findViewById<AppCompatTextView>(R.id.createdView)

    fun bind(post: RedditPost) {
        val context = itemView.context
        scoreView.text = context.getString(R.string.score, post.score.toString())
        authorView.text = context.getString(R.string.author, post.author)
        subredditView.text = post.subreddit
        post.thumbnail?.let {
            val resources = context.resources
            val thumbSizePx = resources.getDimensionPixelSize(R.dimen.item_thumb_size)
            Picasso.get()
                .load(it)
                .placeholder(R.drawable.ic_image_grey_24dp)
                .error(R.drawable.ic_broken_image_grey_24dp)
                .resize(thumbSizePx, thumbSizePx)
                .centerCrop()
                .into(thumbView)
        }
        titleView.text = post.title
        createdView.text = SimpleDateFormat(
            "h:mm a",
            Locale.getDefault()
        ).format(post.created)
    }
}
