package com.test.ncubetest.data.posts.api.model

class ListingData(
    val children: List<ListingChildrenResponse>,
    val after: String?,
    val before: String?
)