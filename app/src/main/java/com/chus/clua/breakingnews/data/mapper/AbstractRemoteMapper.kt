package com.chus.clua.breakingnews.data.mapper

abstract class AbstractRemoteMapper<in R, out D> : RemoteMapper<R, D> {
    override fun mapFromRemoteList(remoteList: List<R>): List<D> {
        return remoteList.map { mapFromRemote(it) }
    }
}