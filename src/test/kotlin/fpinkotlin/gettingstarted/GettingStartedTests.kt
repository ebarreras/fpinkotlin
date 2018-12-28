package fpinkotlin.gettingstarted

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class GettingStartedTests {

    @Test
    fun fib() {
        assertEquals(8, fib(6))
    }

    @Test
    fun fib_zero_is_not_defined() {
        assertThrows(IllegalArgumentException::class.java) {
            fib(0)
        }
    }

    @Test
    fun isSorted() {
        assertTrue(isSorted(arrayOf<Int>()) { a, b -> a > b })
        assertTrue(isSorted(arrayOf(4)) { a, b -> a > b })
        assertTrue(isSorted(arrayOf(4, 7, 9, 10)) { a, b -> a > b })
        assertTrue(isSorted(arrayOf(4, 5, 7, 8, 8)) { a, b -> a >= b })
        assertFalse(isSorted(arrayOf(4, 7, 10, 9)) { a, b -> a > b })
        assertFalse(isSorted(arrayOf(5, 4, 7, 8, 9)) { a, b -> a > b })
        assertFalse(isSorted(arrayOf(4, 5, 7, 8, 8)) { a, b -> a > b })
    }

    @Test
    fun curry() {
        val curried = curry { x: Int, y: Int -> x + y }

        assertEquals(8, curried(3)(5))
    }

    @Test
    fun uncurry() {
        val uncurried = uncurry { x: Int -> { y: Int -> x + y } }

        assertEquals(8, uncurried(3, 5))
    }

    @Test
    fun compose() {
        val f = { x: Int -> x + 3 }
        val g = { x: Int -> x * 2 }

        val fog = compose(f, g)
        val gof = compose(g, f)

        assertEquals(13, fog(5))
        assertEquals(16, gof(5))
    }
}