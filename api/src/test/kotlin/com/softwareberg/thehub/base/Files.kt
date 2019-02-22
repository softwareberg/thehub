package com.softwareberg.thehub.base

import java.io.FileNotFoundException

object Files {
    fun getResourceAsText(path: String): String {
        val url = Files.javaClass.getResource(path) ?: throw FileNotFoundException()
        return url.readText()
    }
}
