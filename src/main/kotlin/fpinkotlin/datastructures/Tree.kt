package fpinkotlin.datastructures

import kotlin.math.max

sealed class Tree<out T> {
    override fun equals(other: Any?): Boolean = when {
        this is Leaf && other is Leaf<*> -> this.value == other.value
        this is Branch && other is Branch<*> -> this.left == other.left && this.right == other.right
        else -> false
    }
}

data class Leaf<out T>(val value: T): Tree<T>()
data class Branch<out T>(val left: Tree<T>, val right: Tree<T>): Tree<T>()

// 3.25
fun <A> size(tree: Tree<A>): Int = when (tree) {
    is Leaf -> 1
    is Branch -> size(tree.left) + size(tree.right) + 1
}

// 3.26
fun max(tree: Tree<Int>): Int = when (tree) {
    is Leaf -> tree.value
    is Branch -> max(max(tree.left), max(tree.right))
}

// 3.27
fun depth(tree: Tree<Int>): Int = when (tree) {
    is Leaf -> 1
    is Branch -> max(depth(tree.left), depth(tree.right)) + 1
}

// 3.28
fun <A,B> map(tree: Tree<A>, f: (A) -> B): Tree<B> = when (tree) {
    is Leaf -> Leaf(f(tree.value))
    is Branch -> Branch(map(tree.left,f), map(tree.right,f))
}

// 3.29
fun <A,B> fold(tree: Tree<A>, fl: (A) -> B, fb: (B, B) -> B): B = when (tree) {
    is Leaf -> fl(tree.value)
    is Branch -> fb(fold(tree.left, fl, fb), fold(tree.right, fl, fb))
}

fun <A> sizeViaFold(tree: Tree<A>): Int =
    fold(tree, { 1 }, { x, y -> x + y + 1 })

fun maxViaFold(tree: Tree<Int>): Int =
    fold(tree, { it },  { x, y -> max(x, y) } )


fun depthViaFold(tree: Tree<Int>): Int =
    fold(tree, { 1 },  { x, y -> max(x, y) + 1 } )

fun <A,B> mapViaFold(tree: Tree<A>, f: (A) -> B): Tree<B> =
    fold(tree, { Leaf(f(it)) as Tree<B> }, { l, r -> Branch(l, r) })
