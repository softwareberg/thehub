package com.softwareberg

import java.io.FileNotFoundException

fun readFile(path: String) = Boot.javaClass.getResource(path)?.readText() ?: throw FileNotFoundException("Cannot find file $path")
