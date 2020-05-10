package com.test.ncubetest.domain.posts.usecase.posts

typealias PendingCallFunc = () -> Unit

interface GetPostsCallback {
    fun onError(t: Throwable)

    fun setupPendingLoading(pendingCallFunc: PendingCallFunc)
}