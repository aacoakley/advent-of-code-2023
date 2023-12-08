fun main() {
    fun buildAlmanac(input: List<String>): Almanac {
        return input.drop(2).fold(mutableListOf<MutableList<String>>()) { acc, line ->
            when {
                line == "" -> Unit
                line.contains(':') -> acc.add(mutableListOf())
                else -> acc.last().add(line)
            }
            acc
        }.map { group ->
            group.map { line ->
                val ranges = line.split(' ').map { it.toLong() }
                ranges[0]..<(ranges[0] + ranges[2]) to ranges[1]..<(ranges[1] + ranges[2])
            }
        }
    }

    fun Almanac.traverse(seed: Long) = fold(seed) { acc, pairs ->
        val (dest, source) = pairs.find { acc in it.second } ?: return@fold acc
        dest.first + (acc - source.first)
    }

    fun part1(input: List<String>): Long {
        val seeds = input.first().substringAfter(": ").split(' ').map { it.toLong() }
        val almanac = buildAlmanac(input)

        return seeds.minOf { almanac.traverse(it) }

    }

    fun part2(input: List<String>): Long {
        val seeds = input.first().substringAfter(": ")
            .split(' ')
            .chunked(2) { it[0].toLong()..<(it[0].toLong() + it[1].toLong()) }
        val reversedAlmanac = buildAlmanac(input).map { pairs -> pairs.map { it.second to it.first } }.reversed()

        return generateSequence(1, Long::inc).first { location ->
            seeds.any { reversedAlmanac.traverse(location) in it }
        }
    }

    val input = readInput("Day05")
    println("Part 1: ${part1(input)}")
    println("Part 2: ${part2(input)}")
}

typealias Almanac = List<List<Pair<LongRange, LongRange>>>