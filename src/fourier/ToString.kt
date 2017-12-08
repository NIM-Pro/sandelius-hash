package fourier

fun Array<Int>.toRadixString(radix: Int = 16): String {
    val builder = StringBuilder(size * 2)
    for (int in this) {
        var number = int
        if (number < 0)
            number += Int.MAX_VALUE
        while (number >= radix) {
            val rest = number % radix
            builder.append(Character.forDigit(rest, radix))
            number -= rest
            number /= radix
        }
        builder.append(Character.forDigit(number, radix))
    }
    builder.reverse()
    return builder.toString()
}

fun Array<Int>.toHexString() = toRadixString(16)

fun <T> Array<T>.toArrayString(toString: (T) -> String = { it.toString() }): String {
    val builder = StringBuilder(size * 2 + 1)
    builder.append('[')
    val it = iterator()
    if (it.hasNext())
        builder.append(toString(it.next()))
    while (it.hasNext()) {
        builder.append(", ")
        builder.append(toString(it.next()))
    }
    builder.append(']')
    return builder.toString()
}
