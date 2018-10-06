package com.softwareberg

import java.io.FileNotFoundException

fun loadFile(path: String) = Boot.javaClass.getResource(path) ?: throw FileNotFoundException("Cannot find file $path")
fun readText(path: String) = loadFile(path).readText()
