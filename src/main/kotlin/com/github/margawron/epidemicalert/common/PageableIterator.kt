package com.github.margawron.epidemicalert.common

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class PageableIterator<T>(
    initPageable: Pageable,
    private val pageRequester: PageRequester<T>
): Iterable<T>, Iterator<T> {

    private var page = pageRequester.requestPage(initPageable)
    private var pageIterator = page.iterator()

    override fun iterator(): Iterator<T> {
        return this
    }

    override fun hasNext(): Boolean {
        val pageHasNext = pageIterator.hasNext()
        return if(pageHasNext){
            true
        } else {
            if(page.hasNext()){
                page = pageRequester.requestPage(page.nextPageable())
                pageIterator = page.iterator()
                pageIterator.hasNext()
            } else {
                false
            }
        }
    }

    override fun next(): T {
        return pageIterator.next()
    }

    fun getTotalElements(): Long = page.totalElements

    fun interface PageRequester<T>{
        fun requestPage(pageable: Pageable): Page<T>
    }
}