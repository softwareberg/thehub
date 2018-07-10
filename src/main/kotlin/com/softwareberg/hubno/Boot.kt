package com.softwareberg.hubno

fun main(args: Array<String>) {
    Boot().start()
}

class Boot {

    fun start() {
        val keywords = listOf("dev", "soft", "eng", "front", "full", "stack", "backend")
        val companies = HubNoFetcher().fetchCompaniesWithPositions(170)
        val filtered = filter(companies, keywords)
        println(asCsv(filtered).joinToString("\n"))
    }

    fun filter(companies: List<CompanyWithPositions>, keywords: List<String>): List<CompanyWithPositions> {
        return companies.filter { company ->
            company.positions.any { pos ->
                keywords.any { keyword -> pos.contains(keyword, ignoreCase = true) }
            }
        }
    }

    private fun asCsv(companiesWithPositions: List<CompanyWithPositions>): List<String> {
        return companiesWithPositions.map {
            "${it.name}\t${it.key}\thttps://thehub.dk/jobs/company/${it.key}\t${it.positions.joinToString("\t")}"
        }
    }
}
