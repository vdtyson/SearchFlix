package com.versilistyson.searchflix.domain.common

import com.versilistyson.searchflix.data.remote.dto.StreamLookupResponseDto
import com.versilistyson.searchflix.data.util.NetworkResult
import com.versilistyson.searchflix.domain.entities.StreamLookupResponse

// https://github.com/android10/Android-CleanArchitectureKotlin/blob/master/app/src/main/kotlin/com/fernandocejas/sample/core/functional/Either.kt
/**
 * Represents a value of one of two possible types (a disjoint union).
 * Instances of [Either] are either an instance of [Left] or [Right].
 * FP Convention dictates that [Left] is used for "failure"
 * and [Right] is used for "success".
 *
 * @see Left
 * @see Right
 */
sealed class Either<out L, out R> {
    /** * Represents the left side of [Either] class which by convention is a "Failure". */
    data class Left<out L>(val left: L) : Either<L, Nothing>()

    /** * Represents the right side of [Either] class which by convention is a "Success". */
    data class Right<out R>(val right: R) : Either<Nothing, R>()

    /**
     * Returns true if this is a Right, false otherwise.
     * @see Right
     */
    val isRight get(): Boolean = this is Right<R>

    /**
     * Returns true if this is a Left, false otherwise.
     * @see Left
     */
    val isLeft get(): Boolean = this is Left<L>

    /**
     * Creates a Left type.
     * @see Left
     */
    fun <L> left(left: L): Left<L> =
        Left(left)

    /**
     * Creates a Left type.
     * @see Right
     */
    fun <R> right(right: R): Right<R> =
        Right(right)

    /**
     * Applies fnL if this is a Left or fnR if this is a Right.
     * @see Left
     * @see Right
     */

    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    fun <T> foldAndGet(fnL: (L) -> T, fnR: (R) -> T): T =
        when(this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    fun <A, B> foldAndMap (fnL: (L) -> A, fnR: (R) -> B): Either<A, B> =
        when(this) {
            is Left -> Either.Left(fnL(left))
            is Right -> Right(fnR(right))
        }

    suspend fun sFold(fnL: suspend (L) -> Any, fnR: suspend (R) -> Any): Any =
        when(this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }

    suspend fun <T> sFoldAndGet(fnL: suspend (L) -> T, fnR: suspend (R) -> T): T =
        when(this) {
            is Left -> fnL(left)
            is Right -> fnR(right)
        }


    /**
     * Composes 2 functions
     * See <a href="https://proandroiddev.com/kotlins-nothing-type-946de7d464fb">Credits to Alex Hart.</a>
     */
    fun <A, B, C> ((A) -> B).c(f: (B) -> C): (A) -> C = {
        f(this(it))
    }

    /**
     * Right-biased flatMap() FP convention which means that Right is assumed to be the default case
     * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
     */
    fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>): Either<L, T> =
        when (this) {
            is Left -> Left(
                left
            )
            is Right -> fn(right)
        }

    /**
     * Right-biased map() FP convention which means that Right is assumed to be the default case
     * to operate on. If it is Left, operations like map, flatMap, ... return the Left value unchanged.
     */
    fun <T, L, R> Either<L, R>.map(fn: (R) -> (T)): Either<L, T> = this.flatMap(fn.c(::right))

    /** Returns the value from this `Right` or the given argument if this is a `Left`.
     *  Right(12).getOrElse(17) RETURNS 12 and Left(12).getOrElse(17) RETURNS 17
     */
    fun <L, R> Either<L, R>.getOrElse(value: R): R =
        when (this) {
            is Left -> value
            is Right -> right
        }
}