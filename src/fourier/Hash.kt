package fourier

fun Double.intoIntSignalValue(times: Double) = (this * times).toInt()

fun <T> Array<T>.getRinged(index: Int): T {
    var ringedIndex = index % size
    if (ringedIndex < 0)
        ringedIndex += size
    return this[ringedIndex]
}

fun Array<Int>.blurring(deep: Int) {
    val dSize = size.toDouble()
    for (n in 1..deep) {
        val original = Array(size) { this[it] }
        original.withIndex().forEach { (i, original_value) ->
            val sum = (1..(size/2)).fold(original_value.toDouble()) { acc, j ->
                val coefficient = ((size - j).toDouble()) / (dSize * dSize)
                val value = original.getRinged(i - j) + original.getRinged(i + j)
                acc + value.toDouble() * coefficient * coefficient
            }
            this[i] = sum.toInt()
        }
    }
}

fun hash(
        input: Array<Byte>,
        resultSize: Int = 24,
        jumpSize: Int = 3,
        cycleCount: Int = 16,
        signalMultiplier: Double = Math.PI,
        blurringDeep: Int = 5
): Array<Int> {
    val fourier = transform(input.map(Byte::toDouble))
    val result = Array(resultSize) { 0 }
    for ((i) in fourier.withIndex()) {
        for (j in 0..resultSize * cycleCount) {
            val index = (i + j * jumpSize) % resultSize
            val value = fourier.calcNthSignalValue(i, j.toDouble()).intoIntSignalValue(signalMultiplier)
            result[index] += value
        }
    }
    result.blurring(blurringDeep)
    return result
}
