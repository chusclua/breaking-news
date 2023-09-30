package com.chus.clua.data.network.fake


open class BaseFakeApi {

    protected var isFailure = false

    fun forceFailure() {
        isFailure = true
    }
}