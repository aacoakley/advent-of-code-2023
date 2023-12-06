fun main() {
    fun part1(input: List<String>): Int {
        return input.map { s -> "${s.first { it.isDigit() }}${s.last { it.isDigit() }}" }.sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val numberMap = mapOf(
            "one" to '1',
            "two" to '2',
            "three" to '3',
            "four" to '4',
            "five" to '5',
            "six" to '6',
            "seven" to '7',
            "eight" to '8',
            "nine" to '9'
        )
        val nums = numberMap.keys

        fun StringBuilder.notMatching(c: Char, nums: Set<String>, add: StringBuilder.() -> StringBuilder): Boolean {
            if (c.isDigit()) clear()
            add()
            return firstOrNull()?.isDigit() == false && !nums.any { number ->
                this.toString().contains(number).also {
                    if (it) {
                        clear()
                        append(numberMap[number])
                    }
                }
            }
        }

        return input.sumOf { s ->
            val first = buildString { s.takeWhile { notMatching(it, nums) { append(it) } } }
            val second = buildString { s.takeLastWhile { notMatching(it, nums) { insert(0, it) } } }

            "$first$second".toInt()
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
