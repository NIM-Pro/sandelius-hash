package fourier

fun transform(input: Collection<Complex>, coefficient: Double): Array<Complex> {
    val n = input.size
    val argDeltaSimple = coefficient * 2 * Math.PI / n
    return Array(n) { k ->
        val argDelta = argDeltaSimple * k
        val sum = Complex(0.0)
        for (i in 0..(n-1))
            sum += Complex.fromEulerForm(1.0, argDelta * i.toDouble()) * input.elementAt(i)
        sum
    }
}

fun transform(input: Collection<Double>)
        = transform(input.map { Complex(it) }, -1.0)

fun reverseTransform(input: Collection<Complex>)
        = transform(input, 1.0).map { it.mod / input.size}
