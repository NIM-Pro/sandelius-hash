import sandelius.*

fun main(args: Array<String>) {
    (0..9).forEach {
        val input = "Azaz$it"
        val hash = hash(input.toByteArray()).toHexString()
        println("input: $input, hash: $hash")
    }
}
