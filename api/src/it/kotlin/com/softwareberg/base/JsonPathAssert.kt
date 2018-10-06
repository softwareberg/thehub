package com.softwareberg.base

import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.DocumentContext
import com.jayway.jsonpath.JsonPath
import com.jayway.jsonpath.JsonPathException
import com.jayway.jsonpath.TypeRef
import com.jayway.jsonpath.spi.json.JacksonJsonProvider
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider
import org.assertj.core.api.AbstractAssert
import org.assertj.core.api.BigDecimalAssert
import org.assertj.core.api.BooleanAssert
import org.assertj.core.api.DoubleAssert
import org.assertj.core.api.IntegerAssert
import org.assertj.core.api.ListAssert
import org.assertj.core.api.ObjectAssert
import org.assertj.core.api.StringAssert

import java.math.BigDecimal

class JsonPathAssert(documentContext: DocumentContext?) : AbstractAssert<JsonPathAssert, DocumentContext>(documentContext, JsonPathAssert::class.java) {

    fun jsonPathAsString(path: String?): StringAssert {
        hasJsonPath(path)
        return StringAssert(actual.read(path, String::class.java))
    }

    fun jsonPathAsInteger(path: String?): IntegerAssert {
        hasJsonPath(path)
        return IntegerAssert(actual.read(path, Int::class.java))
    }

    fun jsonPathAsBigDecimal(path: String?): BigDecimalAssert {
        hasJsonPath(path)
        return BigDecimalAssert(actual.read(path, BigDecimal::class.java))
    }

    fun jsonPathAsDouble(path: String?): DoubleAssert {
        hasJsonPath(path)
        return DoubleAssert(actual.read(path, Double::class.java))
    }

    fun jsonPathAsBoolean(path: String?): BooleanAssert {
        hasJsonPath(path)
        return BooleanAssert(actual.read(path, Boolean::class.java))
    }

    fun <T> jsonPathAs(path: String?, clazz: Class<T>): ObjectAssert<T> {
        hasJsonPath(path)
        return ObjectAssert(actual.read(path, clazz))
    }

    fun jsonPathAsListOfStrings(path: String?): ListAssert<String> {
        return jsonPathAsListOf(path, object : TypeRef<List<String>>() {
        })
    }

    fun <T> jsonPathAsListOf(path: String?, typeRef: TypeRef<List<T>>): ListAssert<T> {
        hasJsonPath(path)
        return ListAssert(actual.read(path, typeRef))
    }

    fun hasJsonPath(path: String?) {
        if (!isValidJsonPathAndHasJsonPath(path)) {
            failWithMessage("[%s] does not have json path [%s]", asJsonString(), path)
        }
    }

    private fun asJsonString(): String? {
        return actual?.jsonString()
    }

    fun hasNoJsonPath(path: String?) {
        if (isValidJsonPathAndHasJsonPath(path)) {
            failWithMessage("[%s] has json path [%s]", asJsonString(), path)
        }
    }

    private fun isValidJsonPathAndHasJsonPath(path: String?): Boolean {
        if (actual == null || path == null) {
            return false
        }
        return try {
            actual.read<Any>(path)
            true
        } catch (exception: JsonPathException) {
            false
        }
    }

    fun isEqualToJson(jsonText: String?) {
        if (jsonText == null && this.actual == null) {
            return
        }
        if (jsonText == null) {
            failWithMessage("[%s] is not equal to [null]", asJsonString())
        }
        val documentContext = parseJson(jsonText)
        if (documentContext.json<Any>() != this.actual.json<Any>()) {
            failWithMessage("[%s] is not equal to [%s]", asJsonString(), jsonText)
        }
    }

    companion object {

        fun assertThat(json: DocumentContext?): JsonPathAssert {
            return JsonPathAssert(json)
        }

        fun assertJsonThat(json: String?): JsonPathAssert {
            if (json == null) {
                return JsonPathAssert(null)
            }
            val documentContext = parseJson(json)
            return assertThat(documentContext)
        }

        private fun parseJson(json: String?): DocumentContext {
            val configuration = Configuration.builder()
                    .jsonProvider(JacksonJsonProvider())
                    .mappingProvider(JacksonMappingProvider())
                    .build()
            return JsonPath.parse(json, configuration)
        }
    }
}
