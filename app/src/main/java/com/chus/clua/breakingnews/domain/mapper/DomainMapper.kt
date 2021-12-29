package com.chus.clua.breakingnews.domain.mapper

interface DomainMapper<in D, out U> {
    fun mapFromDomain(domainModel: D): U
    fun mapFromDomainList(domainList: List<D>): List<U>
}