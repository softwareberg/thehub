package com.softwareberg.companies

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.HttpClient
import com.softwareberg.JsonMapper
import javax.inject.Singleton

class HubModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideHubFetcher(httpClient: HttpClient, jsonMapper: JsonMapper): CompaniesFetcher {
        return CompaniesFetcher(httpClient, jsonMapper)
    }

    @Singleton
    @Provides
    private fun provideHubController(jsonMapper: JsonMapper, companiesFetcher: CompaniesFetcher): HubController {
        return HubController(jsonMapper, companiesFetcher)
    }
}
