fun main() {
    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 2
    }

    val test = readInput("Day04.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == 157)

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == 70)

    val input = readInput("Day04")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
