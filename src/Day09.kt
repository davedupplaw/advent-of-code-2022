import kotlin.math.abs
import kotlin.math.sign

fun main() {
    data class Position(var x: Int, var y: Int) {
        fun moveX(v: Int) {
            x += v
        }

        fun moveY(v: Int) {
            y += v
        }

        fun toPair() = Pair(x, y)
    }

    fun moveHead(command: String, headPosition: Position) {
        when (command) {
            "R" -> headPosition.moveX(1)
            "L" -> headPosition.moveX(-1)
            "U" -> headPosition.moveY(1)
            "D" -> headPosition.moveY(-1)
        }
    }

    fun moveKnotBasedOnOtherKnot(headPosition: Position, tailPosition: Position) {
        val headTailDiffX = headPosition.x - tailPosition.x
        val headTailDiffY = headPosition.y - tailPosition.y
        if (abs(headTailDiffX) > 1 || abs(headTailDiffY) > 1) {
            if (abs(headTailDiffX) > 1 || (abs(headTailDiffY) > 1 && abs(headTailDiffX) == 1)) {
                tailPosition.moveX(1 * headTailDiffX.sign)
            }
            if (abs(headTailDiffY) > 1 || (abs(headTailDiffX) > 1 && abs(headTailDiffY) == 1)) {
                tailPosition.moveY(1 * headTailDiffY.sign)
            }
        }
    }

    fun simulateRopeWithNKnots(input: List<String>, n: Int): Int {
        val positionsVisited = mutableSetOf<Pair<Int, Int>>()
        val knots = List(n) { Position(0, 0) }
        input.forEach { line ->
            val (command, amountStr) = line.split(" ")
            val amount = amountStr.toInt()
            repeat(amount) {
                moveHead(command, knots[0])
                knots.windowed(2) { (leader, follower) ->
                    moveKnotBasedOnOtherKnot(leader, follower)
                }

                positionsVisited += knots.last().toPair()
            }
        }
        return positionsVisited.count()
    }

    fun part1(input: List<String>): Int {
        return simulateRopeWithNKnots(input, 2)
    }

    fun part2(input: List<String>): Int {
        return simulateRopeWithNKnots(input, 10)
    }

    val test = readInput("Day09.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == 13)

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == 1)

    val test2 = readInput("Day09-2.test")
    val part22test = part2(test2)
    println("part22 test: $part22test")
    check(part22test == 36)

    val input = readInput("Day09")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
