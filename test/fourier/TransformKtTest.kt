package fourier

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TransformKtTest {
    @Test
    fun transform() {
        for (c in 0..32) {
            val size = (Math.floor(Math.random() * 128) + 128).toInt()
            val input = Array(size) { Math.random() }
            val fourier = transform(input.asList())
            val output = reverseTransform(fourier.asList())
            val outputIterator = output.iterator()
            for (n in input) {
                assertEquals(n, outputIterator.next(), 1e-10)
            }
        }
    }
}
