package com.softwareberg.base.modules

import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.softwareberg.JsonMapper
import com.softwareberg.XmlMapper
import javax.inject.Singleton

class JsonXmlModule : AbstractModule() {

    override fun configure() {}

    @Singleton
    @Provides
    private fun provideJsonMapper(): JsonMapper {
        return JsonMapper.create()
    }

    @Singleton
    @Provides
    private fun provideXmlMapper(): XmlMapper {
        return XmlMapper.create()
    }
}
