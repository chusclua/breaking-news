package com.chus.clua.breakingnews.data.mapper

interface RemoteMapper<in R, out D> {
    fun mapFromRemote(remoteModel: R): D
    fun mapFromRemoteList(remoteList: List<R>): List<D>
}