package fpinkotlin.datastructures

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TreeTests {

    private val tree = Branch(
                Branch(
                    Leaf(1), Leaf(2)),
                Branch(
                    Leaf(3), Leaf(4)))

    @Test
    fun size() {
        assertEquals(1, size(Leaf(1)))

        assertEquals(7, size(tree))
    }

    @Test
    fun max() {
        assertEquals(1, max(Leaf(1)))

        assertEquals(4, max(tree))
    }

    @Test
    fun depth() {
        assertEquals(1, depth(Leaf(1)))

        assertEquals(3, depth(tree))
    }

    @Test
    fun map() {
        assertEquals(Leaf("a1"), map(Leaf(1)) { "a$it" })

        assertEquals(Branch(
            Branch(
                Leaf("a1"), Leaf("a2")),
            Branch(
                Leaf("a3"), Leaf("a4"))),
            map(tree) { "a$it" })
    }

    @Test
    fun sizeViaFold() {
        assertEquals(1, sizeViaFold(Leaf(1)))

        assertEquals(7, sizeViaFold(tree))
    }

    @Test
    fun maxViaFold() {
        assertEquals(1, maxViaFold(Leaf(1)))

        assertEquals(4, maxViaFold(tree))
    }

    @Test
    fun depthViaFold() {
        assertEquals(1, depthViaFold(Leaf(1)))

        assertEquals(3, depthViaFold(tree))
    }

    @Test
    fun mapViaFold() {
        assertEquals(Leaf("a1"), mapViaFold(Leaf(1)) { "a$it" })

        assertEquals(Branch(
            Branch(
                Leaf("a1"), Leaf("a2")),
            Branch(
                Leaf("a3"), Leaf("a4"))),
            mapViaFold(tree) { "a$it" })
    }
}