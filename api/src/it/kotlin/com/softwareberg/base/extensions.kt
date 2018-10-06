package com.softwareberg.base

import com.despegar.http.client.HttpResponse
import com.despegar.sparkjava.test.SparkServer

fun SparkServer<*>.get(path: String): HttpResponse = execute(get(path, false))

fun HttpResponse.bodyAsString(): String = body().toString(Charsets.UTF_8)
