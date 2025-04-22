package io.craigmiller160.kotlin.result

/**
 * Returns the Result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] or the
 * original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * Note that this function rethrows any [Throwable] exception thrown by [transform] function.
 * See [flatMapCatching] for an alternative that encapsulates exceptions.
 */
inline fun <R, T> Result<T>.flatMap(transform: (value: T) -> Result<R>): Result<R> = fold(
    onSuccess = { value -> transform(value) },
    onFailure = { exception -> Result.failure(exception) }
)

/** Returns the internally wrapped Result, if the outer Result represents [success][Result.isSuccess]
 * or the outer Result if it is [failure][Result.isFailure]. */
fun <T> Result<Result<T>>.flatten(): Result<T> = flatMap { it }

/**
 * Returns the Result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] or the
 * original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
 * See [flatMap] for an alternative that rethrows exceptions from `transform` function.
 */
inline fun <R, T> Result<T>.flatMapCatching(transform: (value: T) -> Result<R>): Result<R> =
    flatMap { kotlin.runCatching { transform(it) }.flatten() }

/**
 * Returns the Result of the given [transform] function applied to the encapsulated [Throwable] exception
 * if this instance represents [failure][Result.isFailure] or the
 * original encapsulated value if it is [success][Result.isSuccess].
 *
 * Note that this function rethrows any [Throwable] exception thrown by [transform] function.
 * See [flatRecoverCatching] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.flatRecover(transform: (exception: Throwable) -> Result<R>): Result<R> =
    fold(
        onSuccess = { this },
        onFailure = { exception -> transform(exception) }
    )

/**
 * Returns the result of the given [transform] function applied to the encapsulated [Throwable] exception
 * if this instance represents [failure][Result.isFailure] or the
 * original encapsulated value if it is [success][Result.isSuccess].
 *
 * This function catches any [Throwable] exception thrown by [transform] function and encapsulates it as a failure.
 * See [flatRecover] for an alternative that rethrows exceptions.
 */
inline fun <R, T : R> Result<T>.flatRecoverCatching(transform: (exception: Throwable) -> Result<R>): Result<R> =
    flatRecover { kotlin.runCatching { transform(it) }.flatten() }