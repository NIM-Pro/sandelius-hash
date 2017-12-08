package fourier

fun Complex.calcSignalValue(time: Double, period: Double)
        = mod * Math.cos(2 * Math.PI * time / period + arg)

fun Array<Complex>.calcNthSignalValue(index: Int, time: Double)
        = this[index].calcSignalValue(time, size.toDouble())
