import kotlin.math.floor
import kotlin.math.pow

fun main() {
    val parseGame = { s: String ->
        s.substringAfter(':')
            .split('|')
            .flatMap { split -> split.trim().split("  ", " ") }
    }

    val wins = { nums: List<String> -> nums.size - nums.toSet().size }

    fun part1(input: List<String>): Int {
        return input.map(parseGame).sumOf { numbers: List<String> ->
            floor(2.0.pow(wins(numbers) - 1)).toInt()
        }
    }

    fun part2(input: List<String>): Int {
        val matches = input.map(parseGame).map(wins)
        val cards = IntArray(matches.size) { 1 }

        matches.forEachIndexed { index, count ->
            repeat(count) {
                cards[index + it + 1] += cards[index]
            }
        }
        return cards.sum()
    }

    val input = readInput("Day04")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}
