package eu.codeloop.thehub.base

import org.springframework.data.domain.Page

data class PageResponse<T>(
    val data: List<T>,
    val totalPages: Int,
    val totalElements: Long,
    val last: Boolean,
    val size: Int,
    val number: Int,
    val first: Boolean,
    val numberOfElements: Int
) {
    companion object {
        @SuppressWarnings("FunctionMinLength")
        fun <T> of(page: Page<T>): PageResponse<T> {
            return PageResponse(
                data = page.content,
                totalPages = page.totalPages,
                totalElements = page.totalElements,
                last = page.isLast,
                size = page.size,
                number = page.number,
                first = page.isFirst,
                numberOfElements = page.numberOfElements
            )
        }
    }
}
