package sandelius

fun MutableList<Byte>.toHexString(): String {
    val builder = StringBuilder(size * 2)
    forEach {
        val number = if (it < 0) it + 256 else it.toInt()
        val a = number % 16
        val b = (number - a) / 16
        builder.append(Character.forDigit(b, 16))
        builder.append(Character.forDigit(a, 16))
    }
    return builder.toString()
}

fun MutableList<*>.toArrString(): String {
    val builder = StringBuilder(size * 2 + 1)
    builder.append('[')
    val it = iterator()
    if (it.hasNext())
        builder.append(it.next().toString())
    while (it.hasNext()) {
        builder.append(", ")
        builder.append(it.next().toString())
    }
    builder.append(']')
    return builder.toString()
}
