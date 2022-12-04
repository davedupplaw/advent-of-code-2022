fun main() {
    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.split(",", "-").map { x -> x.toInt() } }
            .map { listOf(it[0]..it[1], it[2]..it[3]) }
            .filter {
                (it[1].first in it[0] && it[1].last in it[0]) ||
                        (it[0].first in it[1] && it[0].last in it[1])
            }
            .count()
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.split(",", "-").map { x -> x.toInt() } }
            .map { listOf(it[0]..it[1], it[2]..it[3]) }
            .filter {
                it[1].first in it[0] || it[1].last in it[0] ||
                        it[0].first in it[1] || it[0].last in it[1]
            }
            .count()
    }

    val test = readInput("Day04.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == 2)

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == 4)

    val input = readInput("Day04")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
