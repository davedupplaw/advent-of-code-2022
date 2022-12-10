fun main() {
    fun executeOperation(it: List<String>, value: Int): Int {
        val (operation, argument) = it
        return if (operation == "addx") value + argument.toInt() else value
    }

    fun runProgram(input: List<String>, hook: (Int, Int) -> Unit) {
        var value = 1
        val allInstructions = input.map { it.split(" ") }
        val opsToComplete = mutableMapOf<Int, MutableList<List<String>>>()
        val nThreads = 1
        var cycle = 1
        var programCounter = 0

        do {
            if (opsToComplete.size < nThreads) {
                val instruction = allInstructions[programCounter++]
                val (operation, _) = instruction
                if (operation == "addx") opsToComplete.getOrPut(cycle + 1) { mutableListOf() }.add(instruction)
            }

            hook(cycle, value)

            val completeThisCycle = opsToComplete.remove(cycle)
            completeThisCycle?.forEach {
                value = executeOperation(it, value)
            }

            cycle++
        } while (opsToComplete.isNotEmpty() || programCounter < input.size)
    }

    fun part1(input: List<String>): Int {
        val readingPoints = listOf(20, 60, 100, 140, 180, 220)
        val readings = mutableMapOf<Int, Int>()

        runProgram(input) { cycle: Int, value: Int ->
            if (cycle in readingPoints) {
                readings[cycle] = cycle * value
            }
        }

        return readings.values.sum()
    }

    data class CRT(val w: Int, val h: Int) {
        private val crt = List(h) { MutableList(w) { 0 } }
        fun set(x: Int, y: Int, v: Int) {
            crt[y][x] = v
        }

        override fun toString(): String {
            return crt.joinToString("\n") {
                it.joinToString("") { x ->
                    when (x) {
                        1 -> "#"
                        else -> "."
                    }
                }
            }
        }
    }

    fun part2(input: List<String>): String {
        val crt = CRT(40, 6)

        runProgram(input) { cycleOneBased, value ->
            val cycle = cycleOneBased - 1
            val x = cycle % 40
            val y = cycle / 40
            if (x in value - 1..value + 1) {
                crt.set(x, y, 1)
            }
        }

        return crt.toString()
    }

    val test = readInput("Day10.test")
    val part1test = part1(test)
    println("part1 test: $part1test")

    val test2 = readInput("Day10.test-2")
    val part12test = part1(test2)
    println("part1 test: $part12test")
    check(part12test == 13140)
    val part2test = part2(test2)
    println("part2 test: \n$part2test")
    check(
        part2test == """##..##..##..##..##..##..##..##..##..##..
###...###...###...###...###...###...###.
####....####....####....####....####....
#####.....#####.....#####.....#####.....
######......######......######......####
#######.......#######.......#######....."""
    )

    val input = readInput("Day10")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2:\n$part2")
}
