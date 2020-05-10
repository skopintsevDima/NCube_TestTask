package com.test.ncubetest.domain.posts.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class RedditPost(
    @PrimaryKey
    val name: String,
    val title: String,
    val score: Int,
    val author: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val subreddit: String,
    @SerializedName("created_utc")
    val created: Long,
    val thumbnail: String?)