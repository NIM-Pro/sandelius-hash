package fourier

data class Complex(var real: Double, var imag: Double = 0.0) {
    companion object {
        fun fromEulerForm(mod: Double, arg: Double) = Complex(mod * Math.cos(arg), mod * Math.sin(arg))
    }
    val modSqr get() = (real * real) + (imag * imag)
    val mod get() = Math.sqrt(modSqr)
    val arg get() = if (modSqr == 0.0) { 0.0 } else {
        val cos = real / mod
        when {
            cos > 1 -> 0.0
            cos < 1 -> Math.PI
            else -> {
                var arg = Math.acos(cos)
                if (imag < 0) arg = (Math.PI * 2) - arg
                arg
            }
        }
    }
    fun copy() = Complex(real, imag)
    fun copy(action: (Complex) -> Unit): Complex {
        val result = copy()
        action(result)
        return result
    }
    operator fun unaryPlus() = this
    operator fun unaryMinus() = Complex(-real, -imag)
    operator fun not() = Complex(real, -imag)
    operator fun plusAssign(rhs: Complex) { real += rhs.real; imag += rhs.imag }
    operator fun plusAssign(rhs: Double) { real += rhs }
    operator fun plus(rhs: Complex) = copy { it += rhs }
    operator fun plus(rhs: Double) = copy { it += rhs }
    operator fun minusAssign(rhs: Complex) { real -= rhs.real; imag -= rhs.imag }
    operator fun minusAssign(rhs: Double) { real -= rhs }
    operator fun minus(rhs: Complex) = copy { it -= rhs }
    operator fun minus(rhs: Double) = copy { it -= rhs }
    operator fun timesAssign(rhs: Complex) {
        val real = real
        val imag = imag
        this.real = (real * rhs.real) - (imag * rhs.imag)
        this.imag = (real * rhs.imag) + (imag * rhs.real)
    }
    operator fun timesAssign(rhs: Double) { real *= rhs; imag *= rhs }
    operator fun times(rhs: Complex) = copy { it *= rhs }
    operator fun times(rhs: Double) = copy { it *= rhs }
    operator fun divAssign(rhs: Complex) {
        val real = real
        val imag = imag
        val rhsModSqr = rhs.modSqr
        this.real = ((real * rhs.real) + (imag * rhs.imag)) / rhsModSqr
        this.imag = ((imag * rhs.real) - (real * rhs.imag)) / rhsModSqr
    }
    operator fun divAssign(rhs: Double) { real /= rhs; imag /= rhs }
    operator fun div(rhs: Complex) = copy { it /= rhs }
    operator fun div(rhs: Double) = copy { it /= rhs }
    override operator fun equals(other: Any?) = when(other) {
        other == null -> false
        is Complex -> (real == other.real) && (imag == other.imag)
        is Double -> (real == other) && (imag == 0.0)
        else -> false
    }

    override fun hashCode() = super.hashCode()
}
