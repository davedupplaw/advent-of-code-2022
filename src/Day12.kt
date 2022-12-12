fun main() {
    fun bestSteps(map: List<List<Int>>, x: Int, y: Int, current: Int): List<Pair<Int, Int>> {
        val output = mutableListOf<Pair<Int, Int>>()
        val allowedStep = listOf(current, current + 1, 'E'.code)

        if (x > 0 && y > 0 && map[y - 1][x - 1] in allowedStep) {
            output.add(Pair(x - 1, y - 1))
        }
        if (x < map[0].size - 1 && y > 0 && map[y - 1][x + 1] in allowedStep) {
            output.add(Pair(x + 1, y - 1))
        }
        if (x < map[0].size - 1 && y < map.size - 1 && map[y + 1][x + 1] in allowedStep) {
            output.add(Pair(x + 1, y + 1))
        }
        if (x > 0 && y < map.size - 1 && map[y + 1][x - 1] in allowedStep) {
            output.add(Pair(x - 1, y + 1))
        }
        if (y > 0 && map[y - 1][x] in allowedStep) {
            output.add(Pair(x, y - 1))
        }
        if (x > 0 && map[y][x - 1] in allowedStep) {
            output.add(Pair(x - 1, y))
        }
        if (y < map.size - 1 && map[y + 1][x] in allowedStep) {
            output.add(Pair(x, y + 1))
        }
        if (x < map[0].size - 1 && map[y][x + 1] in allowedStep) {
            output.add(Pair(x + 1, y))
        }
        return output
    }

    fun followPath(map: List<List<Int>>, x: Int, y: Int, currentPath: ArrayDeque<Pair<Int, Int>>): Pair<Int, Int>? {
        println("Current position: ${map[y][x].toChar()} @ (${x},${y})")
        println(currentPath.map { """(${it.first},${it.second})""" })

        val possibleSteps = bestSteps(map, x, y, if (map[y][x] == 'S'.code) 'a'.code else map[y][x])
        val stepsToTry = possibleSteps - currentPath

        println("  -> Possible steps $possibleSteps")
        println("  -> Steps to try $stepsToTry")

        var cx = x
        var cy = y
        stepsToTry.forEach { nextStep ->
            cx = nextStep.first
            cy = nextStep.second
            if (map[y][x] == 'E'.code) {
                return nextStep
            } else {
                return followPath(map, cx, cy, ArrayDeque(currentPath + nextStep))
            }
        }

        return null
    }

    fun part1(input: List<String>): Int {
        val map = input.map { line -> line.map { it.code } }
        val startY = map.indexOfFirst { it.contains('S'.code) }
        val startX = map[startY].indexOfFirst { it == 'S'.code }

        var x = startX
        var y = startY
        var ended = false
        var currentPath = ArrayDeque(listOf(x to y))
        followPath(map, x, y, currentPath)

        return currentPath.size
    }

    fun part2(input: List<String>): Int {
        return 2
    }

    val test = readInput("Day12.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == 31)

//    val part2test = part2(test)
//    println("part2 test: $part2test")
//    check(part2test == 70)

    val input = readInput("Day12")

    val part1 = part1(input)
    println("Part1: $part1")

//    val part2 = part2(input)
//    println("Part2: $part2")
}
