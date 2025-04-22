package io.craigmiller160.kotlin.result

import org.junit.Assert.assertEquals
import org.junit.Test

class ResultExtensionsConditionalTest {

    /** Exception used in tests */
    data class MyTestException(override val message: String? = null) : RuntimeException(message)

    /** Other exception used in tests */
    data class MyOtherTestException(override val message: String? = null) :
        RuntimeException(message)

    private val mySuccessResult = Result.success("Hello World")
    private val myOtherSuccessResult = Result.success(123)

    private val myFailureResult = Result.failure<String>(MyTestException("First"))
    private val myOtherFailureResult = Result.failure<String>(MyTestException("Second"))

    @Test
    fun mapIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .mapIf({ true }) { myOtherSuccessResult }

        assertEquals(Result.success(myOtherSuccessResult), actualResult)
    }

    @Test
    fun mapIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .mapIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun mapIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .mapIf({ true }) { myFailureResult }

        assertEquals(Result.success(myFailureResult), actualResult)
    }

    @Test
    fun mapIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .mapIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun mapIfOnSuccessfulResultIntoPredicateThrowingException() {
        mySuccessResult
            .mapIf({ throw MyTestException() }) { throw MyOtherTestException() }
    }

    @Test(expected = MyTestException::class)
    fun mapIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        mySuccessResult
            .mapIf({ true }) { throw MyTestException() }
    }

    @Test
    fun mapIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        mySuccessResult
            .mapIf({ false }) { throw MyTestException() }
    }

    @Test
    fun mapIfOnFailureResultIntoSuccessResultWithTrueCondition() {
        val actualResult = myFailureResult
            .mapIf({ true }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapIfOnFailureResultIntoSuccessResultWithFalseCondition() {
        val actualResult = myFailureResult
            .mapIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .mapIf({ true }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .mapIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapIfOnFailureResultIntoPredicateThrowingException() {
        val actualResult = myFailureResult
            .mapIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = myFailureResult
            .mapIf({ true }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapIfOnFailureResultIntoTransformsThrowingExceptionWithFalseCondition() {
        val actualResult = myFailureResult
            .mapIf({ false }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .mapCatchingIf({ true }) { myOtherSuccessResult }

        assertEquals(Result.success(myOtherSuccessResult), actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .mapCatchingIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .mapCatchingIf({ true }) { myFailureResult }

        assertEquals(Result.success(myFailureResult), actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .mapCatchingIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoPredicateThrowingException() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .mapCatchingIf({ throw testException }) { throw MyOtherTestException() }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .mapCatchingIf({ true }) { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun mapCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .mapCatchingIf({ false }) { throw testException }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoSuccessResultWithTrueCondition() {
        val actualResult = myFailureResult
            .mapCatchingIf({ true }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoSuccessResultWithFalseCondition() {
        val actualResult = myFailureResult
            .mapCatchingIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .mapCatchingIf({ true }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .mapCatchingIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoPredicateThrowingException() {
        val actualResult = myFailureResult
            .mapCatchingIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = myFailureResult
            .mapCatchingIf({ true }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun mapCatchingIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = myFailureResult
            .mapCatchingIf({ false }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .recoverIf({ true }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoSuccessfulResultWitFalseCondition() {
        val actualResult = mySuccessResult
            .recoverIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .recoverIf({ true }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .recoverIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoPredicateThrowingException() {
        val actualResult = mySuccessResult
            .recoverIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = mySuccessResult
            .recoverIf({ true }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = mySuccessResult
            .recoverIf({ false }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverIfOnFailureResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = myFailureResult
            .recoverIf({ true }) { mySuccessResult }

        assertEquals(Result.success(mySuccessResult), actualResult)
    }

    @Test
    fun recoverIfOnFailureResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = myFailureResult
            .recoverIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun recoverIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .recoverIf({ true }) { myOtherFailureResult }

        assertEquals(Result.success(myOtherFailureResult), actualResult)
    }

    @Test
    fun recoverIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .recoverIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun recoverIfOnFailureResultIntoPredicateThrowingException() {
        myFailureResult
            .recoverIf({ throw MyTestException() }) { throw MyOtherTestException() }
    }

    @Test(expected = MyTestException::class)
    fun recoverIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        myFailureResult
            .recoverIf({ true }) { throw MyTestException() }
    }

    @Test
    fun recoverIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        myFailureResult
            .recoverIf({ false }) { throw MyTestException() }
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ true }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ true }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoPredicateThrowingException() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ true }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = mySuccessResult
            .recoverCatchingIf({ false }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = myFailureResult
            .recoverCatchingIf({ true }) { mySuccessResult }

        assertEquals(Result.success(mySuccessResult), actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = myFailureResult
            .recoverCatchingIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .recoverCatchingIf({ true }) { myOtherFailureResult }

        assertEquals(Result.success(myOtherFailureResult), actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .recoverCatchingIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoPredicateThrowingException() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .recoverCatchingIf({ throw testException }) { throw MyOtherTestException() }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .recoverCatchingIf({ true }) { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun recoverCatchingIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .recoverCatchingIf({ false }) { throw testException }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ true }) { myOtherSuccessResult }

        assertEquals(myOtherSuccessResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ true }) { myFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun flatMapIfOnSuccessfulResultIntoPredicateThrowingException() {
        mySuccessResult
            .flatMapIf({ throw MyTestException() }) { throw MyOtherTestException() }
    }

    @Test(expected = MyTestException::class)
    fun flatMapIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        mySuccessResult
            .flatMapIf({ true }) { throw MyTestException() }
    }

    @Test
    fun flatMapIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        mySuccessResult
            .flatMapIf({ false }) { throw MyTestException() }
    }

    @Test
    fun flatMapIfOnFailureResultIntoSuccessResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ true }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoSuccessResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ true }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoPredicateThrowingException() {
        val actualResult = myFailureResult
            .flatMapIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ true }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapIfOnFailureResultIntoTransformsThrowingExceptionWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapIf({ false }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ true }) { myOtherSuccessResult }

        assertEquals(myOtherSuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ true }) { myFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatMapCatchingIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoPredicateThrowingException() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .flatMapCatchingIf({ throw testException }) { throw MyOtherTestException() }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .flatMapCatchingIf({ true }) { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatMapCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val testException = MyTestException()

        val actualResult = mySuccessResult
            .flatMapCatchingIf({ false }) { throw testException }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoSuccessResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ true }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoSuccessResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ true }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoPredicateThrowingException() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ true }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatMapCatchingIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = myFailureResult
            .flatMapCatchingIf({ false }) { throw MyTestException() }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ true }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoSuccessfulResultWitFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ true }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoPredicateThrowingException() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ true }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverIf({ false }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ true }) { mySuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ true }) { myOtherFailureResult }

        assertEquals(myOtherFailureResult, actualResult)
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test(expected = MyTestException::class)
    fun flatRecoverIfOnFailureResultIntoPredicateThrowingException() {
        myFailureResult
            .flatRecoverIf({ throw MyTestException() }) { throw MyOtherTestException() }
    }

    @Test(expected = MyTestException::class)
    fun flatRecoverIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        myFailureResult
            .flatRecoverIf({ true }) { throw MyTestException() }
    }

    @Test
    fun flatRecoverIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        myFailureResult
            .flatRecoverIf({ false }) { throw MyTestException() }
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ true }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ false }) { myOtherSuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoFailureResultWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ true }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoFailureResultWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ false }) { myFailureResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoPredicateThrowingException() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ throw MyTestException() }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithTrueCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ true }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnSuccessfulResultIntoTransformThrowingExceptionWithFalseCondition() {
        val actualResult = mySuccessResult
            .flatRecoverCatchingIf({ false }) { throw MyTestException() }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoSuccessfulResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ true }) { mySuccessResult }

        assertEquals(mySuccessResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoSuccessfulResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ false }) { mySuccessResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoFailureResultWithTrueCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ true }) { myOtherFailureResult }

        assertEquals(myOtherFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoFailureResultWithFalseCondition() {
        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ false }) { myOtherFailureResult }

        assertEquals(myFailureResult, actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoPredicateThrowingException() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ throw testException }) { throw MyOtherTestException() }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoTransformThrowingExceptionWithTrueCondition() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ true }) { throw testException }

        assertEquals(Result.failure<String>(testException), actualResult)
    }

    @Test
    fun flatRecoverCatchingIfOnFailureResultIntoTransformThrowingExceptionWithFalseCondition() {
        val testException = MyTestException()

        val actualResult = myFailureResult
            .flatRecoverCatchingIf({ false }) { throw testException }

        assertEquals(myFailureResult, actualResult)
    }
}