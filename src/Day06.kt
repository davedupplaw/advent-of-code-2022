fun main() {
    fun findFirstPositionAndCharsOfN(input: String, n: Int): Pair<Int, String> {
        return input.windowed(n, 1).asSequence()
                .mapIndexed { i, s -> (i + n) to s }
                .first { it.second.toHashSet().size == it.second.length && it.second.length == n }
    }

    fun part1(input: String): Int {
        return findFirstPositionAndCharsOfN(input, 4).first
    }

    fun part2(input: List<String>): Int {
        return 2
    }

    val test = readInput("Day06.test")

    val part1test1 = part1(test[0])
    println("part1 test (1): $part1test1")
    check(part1test1 == 7)
    val part1test2 = part1(test[1])
    println("part1 test (2): $part1test2")
    check(part1test2 == 5)
    val part1test3 = part1(test[2])
    println("part1 test (3): $part1test3")
    check(part1test3 == 6)
    val part1test4 = part1(test[3])
    println("part1 test (4): $part1test4")
    check(part1test4 == 10)
    val part1test5 = part1(test[4])
    println("part1 test (5): $part1test5")
    check(part1test5 ==11)

//    val part2test = part2(test)
//    println("part2 test: $part2test")
//    check(part2test == 70)

    val input = readInput("Day06")

    val part1 = part1(input[0])
    println("Part1: $part1")

//    val part2 = part2(input)
//    println("Part2: $part2")
}
