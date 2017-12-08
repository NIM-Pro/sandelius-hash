package sandelius

class ByteGenerator(
        private val source: ByteArray
) : Generator<Byte> {
    private var index = 0

    init {
        if (source.isEmpty())
            throw IllegalArgumentException()
    }

    override operator fun next(): Byte {
        val result = source[index]
        index = (index + 1) % source.size
        return result
    }
}
