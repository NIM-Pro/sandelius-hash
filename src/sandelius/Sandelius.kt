package sandelius

import kotlin.experimental.xor

private fun <T> MutableList<T>.copyFrom(source: List<T>, begin: Int = 0) {
    val range = begin..(Math.min(begin + source.size, size) - 1)
    for (i in range)
        this[i] = source[i - begin]
}

private fun <T> shuffle(target: MutableList<T>, bitSequence: Generator<Boolean>) {
    val n = target.size
    if (n < 2) return
    val left = MutableList(0) { target[0] }
    val right = MutableList(0) { target[0] }
    for (element in target) {
        val list = if (bitSequence.next()) right else left
        list.add(element)
    }
    shuffle(left, bitSequence)
    shuffle(right, bitSequence)
    target.copyFrom(left)
    target.copyFrom(right, left.size)
}

fun MutableList<Byte>.updateHash(
        data: ByteArray,
        roundCount: Int = 64
) {
    val dataList = data.toMutableList()
    dataList.add(0, data.size.toByte())
    val byteGenerator = ByteGenerator(data)
    val bitGenerator = BitGenerator(data)
    (1..roundCount).forEach {
        shuffle(this, bitGenerator)
        this.forEachIndexed { index, element ->
            if (bitGenerator.next())
                this[index] = element xor byteGenerator.next()
        }
    }
}

fun MutableList<Byte>.defuseHash(newSize: Int) {
    if (newSize < 0) throw IllegalArgumentException()
    if (newSize >= size) return
    val bitGenerator = BitGenerator(this.toByteArray())
    while (size > newSize) {
        var index = 0
        while (index < (size - 1)) {
            if ((size > newSize) && bitGenerator.next()) {
                val temp = removeAt(index)
                this[index] = this[index] xor temp
            } else
                index++
        }
    }
}

fun hash(
        data: ByteArray,
        roundCount: Int = 64,
        size: Int = 16
): MutableList<Byte> {
    val result = mutableListOf(
            0x6A.toByte(), 0x09.toByte(), 0xE6.toByte(), 0x67.toByte(),
            0xBB.toByte(), 0x67.toByte(), 0xAE.toByte(), 0x85.toByte(),
            0x3C.toByte(), 0x6E.toByte(), 0xF3.toByte(), 0x72.toByte(),
            0xA5.toByte(), 0x4F.toByte(), 0xF5.toByte(), 0x3A.toByte(),
            0x51.toByte(), 0x0E.toByte(), 0x52.toByte(), 0x7F.toByte(),
            0x9B.toByte(), 0x05.toByte(), 0x68.toByte(), 0x8C.toByte(),
            0x1F.toByte(), 0x83.toByte(), 0xD9.toByte(), 0xAB.toByte(),
            0x5B.toByte(), 0xE0.toByte(), 0xCD.toByte(), 0x19.toByte()
    )
    result.updateHash(data, roundCount)
    result.defuseHash(size)
    return result
}
