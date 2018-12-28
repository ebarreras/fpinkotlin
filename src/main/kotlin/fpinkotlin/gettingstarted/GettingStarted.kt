package fpinkotlin.gettingstarted

fun fib(n: Int): Int {
    tailrec fun go(i: Int, f1: Int, f2: Int): Int {
        return if (i == n) f2
        else go(i + 1, f2, f1 + f2)
    }

    require(n > 0)
    return when (n) {
        1 -> 0
        2 -> 1
        else -> go(1, 0, 1)
    }
}

fun <A> isSorted(xs: Array<A>, gt: (A, A) -> Boolean): Boolean {
    tailrec fun go(i: Int): Boolean =
        if (i > xs.size - 2) true
        else if (!gt(xs[i + 1], xs[i])) false
        else go(i + 1)

    return go(0)
}

fun <A, B, C> curry(f: (A, B) -> C): (A) -> ((B) -> C) = { a -> { b -> f(a, b) } }

fun <A, B, C> uncurry(f: (A) -> ((B) -> C)): (A, B) -> C = { a, b -> f(a)(b) }

fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C = { a -> f(g(a)) }

