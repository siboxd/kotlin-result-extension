package io.craigmiller160.kotlin.result

import org.junit.Assert.assertEquals
import org.junit.Test

class ResultExtensionsTest {

    /** Exception used in tests */
    data class MyTestException(override val message: String? = null) : RuntimeException(message)

    private val mySuccessResult = Result.success("Hello World")
    private val myOtherSuccessResult = Result.success(123)

    private val myFailureResult = Result.failure<String>(MyTestException("First"))
    private val myOtherFailureResult = Result.failure<String>(MyTestException("Second"))

    @Test
    fun flattenAnOuterSuccessfulResultWithAnInnerSuccessfulResult() {
        val actualResult = Result.success(mySuccessResult)
            .flatten()

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flattenAnOuterSuccessfulResultWithAndInnerFailureResult() {
        val actualResult = Result.success(myFailureResult)
            .flatten()

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flattenThreeLevelsOfResults() {
        val actualResult = Result.success(Result.success(mySuccessResult))
            .flatten()
            .flatten()

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapSuccessfulResultIntoSuccessfulResult() {
        val actualResult = mySuccessResult
            .flatMap { myOtherSuccessResult }

        assertEquals(myOtherSuccessResult, actualResult)
    }

    @Test
    fun flatMapSuccessfulResultIntoFailureResult() {
        val actualResult = mySuccessResult
            .flatMap { myFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun flatMapSuccessfulResultIntoThrowingException() {
        mySuccessResult
            .flatMap<String, String> { throw MyTestException("RuntimeException") }
    }

    @Test
    fun flatMapFailureResultIntoSuccessResult() {
        val actualResult = myFailureResult
            .flatMap { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapFailureResultIntoFailureResult() {
        val actualResult = myFailureResult
            .flatMap { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapFailureResultIntoThrowingException() {
        val actualResult = myFailureResult
            .flatMap<String, String> { throw MyTestException("RuntimeException") }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingSuccessfulResultIntoSuccessfulResult() {
        val actualResult = mySuccessResult
            .flatMapCatching { myOtherSuccessResult }

        assertEquals(myOtherSuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingSuccessfulResultIntoFailureResult() {
        val actualResult = mySuccessResult
            .flatMapCatching { myFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingSuccessfulResultIntoThrowingException() {
        val testException = MyTestException("RuntimeException")

        val actualResult = mySuccessResult
            .flatMapCatching<String, String> { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatMapCatchingFailureResultIntoSuccessResult() {
        val actualResult = myFailureResult
            .flatMapCatching { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingFailureResultIntoFailureResult() {
        val actualResult = myFailureResult
            .flatMapCatching { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingFailureResultIntoThrowingException() {
        val actualResult = myFailureResult
            .flatMapCatching<String, String> { throw MyTestException("RuntimeException") }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverSuccessfulResultIntoSuccessfulResult() {
        val actualResult = mySuccessResult
            .flatRecover { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverSuccessfulResultIntoFailureResult() {
        val actualResult = mySuccessResult
            .flatRecover { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverSuccessfulResultIntoThrowingException() {
        val actualResult = mySuccessResult
            .flatRecover { throw MyTestException("RuntimeException") }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverFailureResultIntoSuccessfulResult() {
        val actualResult = myFailureResult
            .flatRecover { mySuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverFailureResultIntoFailureResult() {
        val actualResult = myFailureResult
            .flatRecover { myOtherFailureResult }

        assertEquals(myOtherFailureResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun flatRecoverFailureResultIntoThrowingException() {
        myFailureResult
            .flatRecover { throw MyTestException("RuntimeException") }
    }

    @Test
    fun flatRecoverCatchingSuccessfulResultIntoSuccessfulResult() {
        val actualResult = mySuccessResult
            .flatRecoverCatching { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingSuccessfulResultIntoFailureResult() {
        val actualResult = mySuccessResult
            .flatRecoverCatching { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingSuccessfulResultIntoThrowingException() {
        val actualResult = mySuccessResult
            .flatRecoverCatching { throw MyTestException("RuntimeException") }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingFailureResultIntoSuccessfulResult() {
        val actualResult = myFailureResult
            .flatRecoverCatching { mySuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingFailureResultIntoFailureResult() {
        val actualResult = myFailureResult
            .flatRecoverCatching { myOtherFailureResult }

        assertEquals(myOtherFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingFailureResultIntoThrowingException() {
        val testException = MyTestException("RuntimeException")

        val actualResult = myFailureResult
            .flatRecoverCatching { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }
}