package sandelius

class ByteGenerator(
        private val source: ByteArray
) : Generator<Byte> {
    private var index = 0
    private var cycle = false

    init {
        if (source.isEmpty())
            throw IllegalArgumentException()
    }

    override operator fun next(): Byte {
        val result = source[index]
        index++
        if (index >= source.size) {
            cycle = true
            index %= source.size
        }
        return result
    }

    fun isCycled() = cycle
}
