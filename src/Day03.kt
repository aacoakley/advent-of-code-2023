fun main() {
    val symbols: MutableSet<Coordinate> = mutableSetOf()
    val numbers: MutableSet<Number> = mutableSetOf()
    val gears: MutableSet<Coordinate> = mutableSetOf()

    fun part1(input: List<String>): Int {
        input.forEachIndexed { i, s ->
            buildString {
                var first = 0
                val cleanUp = { x: IntRange, y: Int ->
                    if (this.isNotEmpty()) numbers.add(Number(this.toString().toInt(), x, y))
                    clear()
                    first = 0
                }

                val y = i + 1
                s.forEachIndexed { j, c ->
                    val x = j + 1
                    when {
                        c == '.' -> cleanUp(first..<x, y)
                        c == '*' -> {
                            cleanUp(first..<x, y)
                            gears.add(Coordinate(x, y))
                        }
                        !c.isDigit() -> {
                            cleanUp(first..<x, y)
                            symbols.add(Coordinate(x, y))
                        }
                        c.isDigit() && first == 0 -> {
                            append(c)
                            first = x
                        }
                        c.isDigit() -> append(c)
                    }
                }
                cleanUp(first..s.length, y)
            }

        }

        return numbers.filter { number ->
            number.surrounding().any {
                symbols.contains(it) || gears.contains(it)
            }
        }.sumOf { it.value }
    }

    fun part2(): Int {
        return gears.sumOf { gear ->
            val matched = numbers.filter { it.surrounding().contains(gear) }.map { it.value }
            if (matched.size == 2) matched[0] * matched[1] else 0
        }
    }

    val input = readInput("Day03")
    part1(input).println()
    part2().println()
}

data class Coordinate(val x: Int, val y: Int)

data class Number(val value: Int, val x: IntRange, val y: Int) {
    fun surrounding(): Set<Coordinate> {
        val build: MutableSet<Coordinate>.(Int) -> Boolean = {
            add(Coordinate(it, y))
            add(Coordinate(it, y + 1))
            add(Coordinate(it, y - 1))
        }
        return buildSet {
            build(x.first - 1)
            build(x.last + 1)
            x.forEach { add(Coordinate(it, y + 1)); add(Coordinate(it, y - 1)) }
        }
    }
}

