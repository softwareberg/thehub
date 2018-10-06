package com.softwareberg.base.web

data class PageResponse<T>(val data: List<T>, val page: Int, val pageSize: Int)
