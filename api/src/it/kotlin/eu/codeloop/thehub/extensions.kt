package eu.codeloop.thehub

import org.awaitility.core.ConditionFactory
import org.springframework.test.web.servlet.ResultActions

fun ConditionFactory.untilRequest(resultProvider: () -> ResultActions, resultChecker: (ResultActions) -> Unit): ResultActions {
    return this.until(resultProvider) { r ->
        try {
            resultChecker(r)
            true
        } catch (e: java.lang.AssertionError) {
            false
        }
    }
}