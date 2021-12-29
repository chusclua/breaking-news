package com.chus.clua.breakingnews.domain.mapper

abstract class AbstractDomainMapper<in D, out U> : DomainMapper<D, U> {
    override fun mapFromDomainList(domainList: List<D>): List<U> {
        return domainList.map { mapFromDomain(it) }
    }
}