fun main() {
    fun gameResults(input: List<String>): Map<Int, Map<String, List<Int>>> {
        return input.associate { game ->
            val id = game.drop(5).substringBefore(':').toInt()
            val results = game.substringAfter(':')
                .split(';')
                .flatMap { it.split(',') }
                .map { cubes -> cubes.trim().split(' ').let { it[0] to it[1] } }
                .groupBy({ it.second }, { it.first.toInt() })

            id to results
        }
    }

    fun part1(input: List<String>): Int {
        val cubes = listOf("red" to 12, "green" to 13, "blue" to 14)

        return gameResults(input).filter { (_, resultMap) ->
            cubes.all { (color, number) ->
                (resultMap[color]?.max() ?: 0) <= number
            }
        }.keys.sum()
    }

    fun part2(input: List<String>): Int {
        return gameResults(input).map { (_, resultMap) ->
            resultMap.values.map { it.max()}.fold(1, Int::times)
        }.sum()

    }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
