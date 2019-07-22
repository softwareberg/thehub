package eu.codeloop.thehub.jobs

data class JobDto(
    val jobId: String,
    val title: String,
    val description: String,
    val hasStar: Boolean,
    val keywords: List<String>,
    val href: String
)
