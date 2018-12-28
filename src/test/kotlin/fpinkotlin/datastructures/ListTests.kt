package fpinkotlin.datastructures

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ListTests {

    @Test
    fun createList() {
        assertTrue(List.create<Int>() == Nil)
        assertTrue((List.create(1, 2, 3) as Cons).head == 1)
    }

    @Test
    fun tail() {
        assertThrows(IllegalArgumentException::class.java) {
            tail(Nil)
        }

        assertTrue(tail(tail(List.create(1, 2))) == Nil)
    }

    @Test
    fun setHead() {
        assertEquals(2, setHead(Cons(1, Nil), 2).head)
    }

    @Test
    fun drop() {
        assertThrows(IllegalArgumentException::class.java) {
            drop(Nil, 1)
        }

        assertEquals(3, (drop((List.create(1, 2, 3)), 2) as Cons).head)
    }

    @Test
    fun dropWhile() {
        assertEquals(Nil, dropWhile(Nil) { true })

        assertEquals(3, (dropWhile((List.create(1, 2, 3))) { it < 3 } as Cons).head)
    }

    @Test
    fun init() {
        assertEquals(Nil, init(Nil))

        assertEquals(List.create(1, 2, 3), init(List.create(1, 2, 3, 4)));
    }

    @Test
    fun foldRight() {
        assertEquals(15, sumViaFoldRight(List.create(1, 2, 3, 4, 5)))
        assertEquals(120, productViaFoldRight(List.create(1, 2, 3, 4, 5)))
        assertEquals(5, lengthViaFoldRight(List.create(1, 2, 3, 4, 5)))
    }

    @Test
    fun foldLeft() {
        assertEquals(15, sumViaFoldLeft(List.create(1, 2, 3, 4, 5)))
        assertEquals(120, productViaFoldLeft(List.create(1, 2, 3, 4, 5)))
        assertEquals(5, lengthViaFoldLeft(List.create(1, 2, 3, 4, 5)))
    }

    @Test
    fun addPairwise() {
        assertEquals(List.create(4, 6, 8), addPairwise(List.create(1, 2, 3), List.create(3, 4, 5, 6)))
    }

    @Test
    fun zipWith() {
        assertEquals(List.create(4, 6, 8), zipWith(List.create(1, 2, 3), List.create(3, 4, 5, 6)) { x, y -> x + y })
    }

    @Test
    fun hasSubsequence() {
        assertTrue(hasSubsequence(List.create(4, 6, 8, 9), List.create(6, 8)))
        assertFalse(hasSubsequence(List.create(4, 6, 8, 9), List.create(6, 7)))
        assertTrue(hasSubsequence(List.create(4, 6, 8, 9), List.create()))
        assertTrue(hasSubsequence(List.create<Int>(), List.create()))
        assertFalse(hasSubsequence(List.create(), List.create(1, 2)))
    }

    @Test
    fun hasSubsequenceViaStartsWith() {
        assertTrue(hasSubsequenceViaStartsWith(List.create(4, 6, 8, 9), List.create(6, 8)))
        assertFalse(hasSubsequenceViaStartsWith(List.create(4, 6, 8, 9), List.create(6, 7)))
        assertTrue(hasSubsequenceViaStartsWith(List.create(4, 6, 8, 9), List.create()))
        assertTrue(hasSubsequenceViaStartsWith(List.create<Int>(), List.create()))
        assertFalse(hasSubsequenceViaStartsWith(List.create(), List.create(1, 2)))
    }


}