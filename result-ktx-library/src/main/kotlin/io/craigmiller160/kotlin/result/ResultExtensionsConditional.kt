package io.craigmiller160.kotlin.result

/**
 * 1) Returns the encapsulated result of the given [transform] function applied to the encapsulated
 * value if this instance represents [success][Result.isSuccess] and [predicate] applied to the
 * encapsulated value returns `true`.
 * 2) Returns the original encapsulated value if this instance represents [success][Result.isSuccess]
 * and [predicate] applied to the encapsulated value returns `false`.
 * 3) Returns the original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * Note that this function rethrows any [Throwable] exception thrown by [transform] and [predicate]
 * functions.
 * See [mapCatchingIf] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.mapIf(
    predicate: (value: T) -> Boolean,
    transform: (value: T) -> R
): Result<R> = map {
    if (predicate(it)) transform(it)
    else it
}

/**
 * 1) Returns the encapsulated result of the given [transform] function applied to the encapsulated
 * value if this instance represents [success][Result.isSuccess] and [predicate] applied to the
 * encapsulated value returns `true`.
 * 2) Returns the original encapsulated value if this instance represents [success][Result.isSuccess]
 * and [predicate] applied to the encapsulated value returns `false`.
 * 3) Returns the original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * This function catches any [Throwable] exception thrown by [transform] and [predicate] functions
 * and encapsulates it as a failure.
 * See [mapIf] for an alternative that rethrows exceptions from `transform` and `predicate` functions.
 */
inline fun <R, T : R> Result<T>.mapCatchingIf(
    predicate: (value: T) -> Boolean,
    transform: (value: T) -> R
): Result<R> = mapCatching {
    if (predicate(it)) transform(it)
    else it
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated [Throwable]
 * exception if this instance represents [failure][Result.isFailure] and [predicate] applied to
 * the encapsulated [Throwable] exception returns `true`.
 * 2) Returns the original encapsulated [Throwable] exception if this instance represents
 * [failure][Result.isFailure] and [predicate] applied to the encapsulated [Throwable] exception
 * returns `false`.
 * 3) Returns the original encapsulated value if it is [success][Result.isSuccess].
 *
 * Note that this function rethrows any [Throwable] exception thrown by [transform] and [predicate]
 * functions.
 * See [recoverCatchingIf] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.recoverIf(
    predicate: (exception: Throwable) -> Boolean,
    transform: (exception: Throwable) -> R
): Result<R> {
    val exception = exceptionOrNull()
    return when {
        exception == null || !predicate(exception) -> this
        else -> Result.success(transform(exception))
    }
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated [Throwable]
 * exception if this instance represents [failure][Result.isFailure] and [predicate] applied to
 * the encapsulated [Throwable] exception returns `true`.
 * 2) Returns the original encapsulated [Throwable] exception if this instance represents
 * [failure][Result.isFailure] and [predicate] applied to the encapsulated [Throwable] exception
 * returns `false`.
 * 3) Returns the original encapsulated value if it is [success][Result.isSuccess].
 *
 * This function catches any [Throwable] exception thrown by [transform] and [predicate] functions
 * and encapsulates it as a failure.
 * See [recoverIf] for an alternative that rethrows exceptions.
 */
inline fun <R, T : R> Result<T>.recoverCatchingIf(
    predicate: (exception: Throwable) -> Boolean,
    transform: (exception: Throwable) -> R
): Result<R> {
    val exception = exceptionOrNull() ?: return this
    val predicateResult = kotlin.runCatching { predicate(exception) }
    return predicateResult.fold(
        onSuccess = { condition ->
            if (condition) kotlin.runCatching { transform(exception) }
            else this
        },
        onFailure = { Result.failure(it) }
    )
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] and [predicate] applied to the
 * encapsulated value returns `true`.
 * 2) Returns the original encapsulated value if this instance represents [success][Result.isSuccess]
 * and [predicate] applied to the encapsulated value returns `false`.
 * 3) Returns the original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * Note that this function rethrows any [Throwable] exception thrown by [transform] or [predicate]
 * functions.
 * See [flatMapCatchingIf] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.flatMapIf(
    predicate: (value: T) -> Boolean,
    transform: (value: T) -> Result<R>
): Result<R> = flatMap {
    if (predicate(it)) transform(it)
    else Result.success(it)
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated value
 * if this instance represents [success][Result.isSuccess] and [predicate] applied to the
 * encapsulated value returns `true`.
 * 2) Returns the original encapsulated value if this instance represents [success][Result.isSuccess]
 * and [predicate] applied to the encapsulated value returns `false`.
 * 3) Returns the original encapsulated [Throwable] exception if it is [failure][Result.isFailure].
 *
 * This function catches any [Throwable] exception thrown by [transform] and [predicate] functions
 * and encapsulates it as a failure.
 * See [flatMapIf] for an alternative that rethrows exceptions from `transform` and `predicate` function.
 */
inline fun <R, T : R> Result<T>.flatMapCatchingIf(
    predicate: (value: T) -> Boolean,
    transform: (value: T) -> Result<R>
): Result<R> = flatMapCatching {
    if (predicate(it)) transform(it)
    else Result.success(it)
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated [Throwable]
 * exception if this instance represents [failure][Result.isFailure] and [predicate] applied to
 * the encapsulated [Throwable] exception returns `true`.
 * 2) Returns the original encapsulated [Throwable] exception if this instance represents
 * [failure][Result.isFailure] and [predicate] applied to the encapsulated [Throwable] exception
 * returns `false`.
 * 3) Returns the original encapsulated value if it is [success][Result.isSuccess].
 *
 * Note that this function rethrows any [Throwable] exception thrown by [transform] and [predicate]
 * functions.
 * See [flatRecoverCatchingIf] for an alternative that encapsulates exceptions.
 */
inline fun <R, T : R> Result<T>.flatRecoverIf(
    predicate: (exception: Throwable) -> Boolean,
    transform: (exception: Throwable) -> Result<R>
): Result<R> = flatRecover {
    if (predicate(it)) transform(it)
    else Result.failure(it)
}

/**
 * 1) Returns the Result of the given [transform] function applied to the encapsulated [Throwable]
 * exception if this instance represents [failure][Result.isFailure] and [predicate] applied to
 * the encapsulated [Throwable] exception returns `true`.
 * 2) Returns the original encapsulated [Throwable] exception if this instance represents
 * [failure][Result.isFailure] and [predicate] applied to the encapsulated [Throwable] exception
 * returns `false`.
 * 3) Returns the original encapsulated value if it is [success][Result.isSuccess].
 *
 * This function catches any [Throwable] exception thrown by [transform] and [predicate] functions
 * and encapsulates it as a failure.
 * See [flatRecoverIf] for an alternative that rethrows exceptions.
 */
inline fun <R, T : R> Result<T>.flatRecoverCatchingIf(
    predicate: (exception: Throwable) -> Boolean,
    transform: (exception: Throwable) -> Result<R>
): Result<R> = flatRecoverCatching {
    if (predicate(it)) transform(it)
    else Result.failure(it)
}