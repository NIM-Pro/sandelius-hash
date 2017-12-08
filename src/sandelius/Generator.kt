package sandelius

interface Generator<out T>: Iterator<T> {
    override operator fun hasNext() = true
}
