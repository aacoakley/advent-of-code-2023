fun main() {
    fun part1(input: List<String>): Int {
        val inputs = input.map { it.substringAfter(':').trim().split(Regex("\\s+")).map { it.toInt() } }
        return inputs.first().zip(inputs.last()).map { (time, distance) ->
            (0..time).map { calculateDistance(it.toLong(), time.toLong()) }.count { it > distance }
        }.reduce(Int::times)

    }

    fun part2(input: List<String>): Long {
        val (time, distance) = input.map { it.filter { c -> c.isDigit() }.toLong() }

        val shortHold = (0..time).first { calculateDistance(it, time) > distance }
        val longHold = (time downTo 0).first { calculateDistance(it, time) > distance }
        return longHold - shortHold + 1
    }

    val input = readInput("Day06")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

fun calculateDistance(hold: Long, time: Long) = ((time - hold) * hold)
