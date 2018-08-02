package com.softwareberg.hub

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.HttpClient
import com.softwareberg.JsonMapper
import com.softwareberg.hub.HubController
import com.softwareberg.hub.HubFetcher
import javax.inject.Singleton

class HubModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideHubFetcher(httpClient: HttpClient, jsonMapper: JsonMapper): HubFetcher {
        return HubFetcher(httpClient, jsonMapper)
    }

    @Singleton
    @Provides
    private fun provideHubController(jsonMapper: JsonMapper, hubFetcher: HubFetcher): HubController {
        return HubController(jsonMapper, hubFetcher)
    }
}
