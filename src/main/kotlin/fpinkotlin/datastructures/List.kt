package fpinkotlin.datastructures

sealed class List<out T> {
    override fun equals(other: Any?): Boolean =
        if (this is Nil && other is Nil) true
        else if (this is Cons && other is Cons<*>) this.head == other.head && this.tail == other.tail
        else false

    companion object {
        fun <T> create(vararg xs: T): List<T> =
            if (xs.isEmpty()) Nil else Cons(xs[0], create(*xs.sliceArray(1 until xs.size)))
    }
}

data class Cons<out T>(val head: T, val tail: List<T>) : List<T>()
object Nil : List<Nothing>()

// 3.2
fun <T> tail(list: List<T>) = when (list) {
    is Nil -> throw IllegalArgumentException()
    is Cons -> list.tail
}

// 3.3
fun <T> setHead(list: List<T>, h: T) = when (list) {
    is Nil -> Cons(h, Nil)
    is Cons -> Cons(h, list.tail)
}

// 3.4
tailrec fun <T> drop(list: List<T>, n: Int): List<T> = when {
    n > 0 && list is Nil -> throw IllegalArgumentException("Can't drop more elements")
    n > 0 && list is Cons -> drop(list.tail, n - 1)
    // n <= 0
    else -> list
}

// 3.5
tailrec fun <T> dropWhile(list: List<T>, f: (T) -> Boolean): List<T> = when {
    list is Nil -> Nil
    list is Cons && f(list.head) -> dropWhile(list.tail, f)
    else -> list

}

// 3.6
fun <T> init(list: List<T>): List<T> = when (list) {
    is Nil -> Nil
    is Cons -> if (list.tail == Nil) Nil else Cons(list.head, init(list.tail))
}

fun <T, R> foldRight(list: List<T>, z: R, f: (T, R) -> R): R = when (list) {
    is Nil -> z
    is Cons -> f(list.head, foldRight(list.tail, z, f))
}

fun sumViaFoldRight(list: List<Int>): Int =
    foldRight(list, 0) { x, y -> x + y }

fun productViaFoldRight(list: List<Int>): Int =
    foldRight(list, 1) { x, y -> x * y }

// 3.9
fun lengthViaFoldRight(list: List<Int>): Int =
    foldRight(list, 0) { _, total -> total + 1 }

// 3.10
tailrec fun <T, R> foldLeft(list: List<T>, z: R, f: (R, T) -> R): R = when (list) {
    is Nil -> z
    is Cons -> foldLeft(list.tail, f(z, list.head), f)
}

// 3.11
fun sumViaFoldLeft(list: List<Int>): Int =
    foldLeft(list, 0) { x, y -> x + y }

fun productViaFoldLeft(list: List<Int>): Int =
    foldLeft(list, 1) { x, y -> x * y }

fun lengthViaFoldLeft(list: List<Int>): Int =
    foldLeft(list, 0) { total, _ -> total + 1 }

// 3.12
fun <T> reverseViaFoldLeft(list: List<T>): List<T> =
    foldLeft(list, Nil as List<T>) { acc, x -> Cons(x, acc) }

//3.13
fun <T, R> foldRightViaFoldLeft(list: List<T>, z: R, f: (T, R) -> R): R {
    val rev = reverseViaFoldLeft(list)
    return foldLeft(rev, z) { acc, x -> f(x, acc) }
}

//3.14
fun <T> appendViaFoldRight(list1: List<T>, list2: List<T>): List<T> =
    foldRight(list1, list2) { x, acc -> Cons(x, acc) }

//3.15
fun <T> concat(lists: List<List<T>>): List<T> =
    foldRight(lists, Nil as List<T>, ::appendViaFoldRight)

fun add1(list: List<Int>) =
    foldRight(list, Nil as List<Int>) { x, acc -> Cons(x + 1, acc) }

fun doubleToString(list: List<Double>) =
    foldRight(list, Nil as List<String>) { x, acc -> Cons(x.toString(), acc) }

// 3.18
fun <A, B> map(list: List<A>, f: (A) -> B): List<B> =
    foldRight(list, Nil as List<B>) { x, acc -> Cons(f(x), acc) }

// 3.19
fun <A> filter(list: List<A>, pred: (A) -> Boolean): List<A> =
    foldRight(list, Nil as List<A>) { x, acc -> if (pred(x)) Cons(x, acc) else acc }

// 3.20
fun <A> flatMap(list: List<A>, f: (A) -> List<A>): List<A> =
    concat(map(list, f))

// 3.21
fun <A> filterViaFlatMap(list: List<A>, pred: (A) -> Boolean): List<A> =
    flatMap(list) { if (pred(it)) Cons(it, Nil) else Nil }

// 3.22
fun addPairwise(list1: List<Int>, list2: List<Int>): List<Int> = when {
    list1 is Cons && list2 is Cons -> Cons(list1.head + list2.head, addPairwise(list1.tail, list2.tail))
    else -> Nil
}

// 3.23
fun <A, B, C> zipWith(list1: List<A>, list2: List<B>, f: (A, B) -> C): List<C> = when {
    list1 is Cons && list2 is Cons -> Cons(f(list1.head, list2.head), zipWith(list1.tail, list2.tail, f))
    else -> Nil
}

// 3.24
fun <A> hasSubsequence(sup: List<A>, sub: List<A>): Boolean = when {
    sub is Nil -> true
    sup is Nil -> false
    else -> {
        val supCons = sup as Cons
        val subCons = sub as Cons
        supCons.head == subCons.head && hasSubsequence(supCons.tail, subCons.tail) || hasSubsequence(
            supCons.tail,
            subCons
        )
    }
}

tailrec fun <A> startsWith(sup: List<A>, sub: List<A>): Boolean = when {
    sub is Nil -> true
    sup is Nil -> false
    else -> {
        val supCons = sup as Cons
        val subCons = sub as Cons
        if (supCons.head != subCons.head) false else startsWith(supCons.tail, subCons.tail)
    }
}

tailrec fun <A> hasSubsequenceViaStartsWith(sup: List<A>, sub: List<A>): Boolean = when {
    startsWith(sup, sub) -> true
    sup is Nil -> false
    else -> {
        val supCons = sup as Cons
        hasSubsequenceViaStartsWith(supCons.tail, sub)
    }
}
