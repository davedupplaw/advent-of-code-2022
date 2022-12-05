fun main() {
    fun readCrateStack(input: List<String>, indexOfBlankLine: Int): MutableMap<Int, List<String>> {
        val stack = input
                .take(indexOfBlankLine)
                .map {
                    it.chunked(4)
                            .mapIndexed { index, crate ->
                                index to crate.replace("[", "").replace("]", "").trim()
                            }
                            .filter { p -> p.second.isNotBlank() }
                }
                .dropLast(1)
                .flatten()
                .groupBy { it.first + 1 }
                .mapValues { it.value.reversed().map { v -> v.second } }
                .toMutableMap()
        return stack
    }

    fun readMoves(input: List<String>, indexOfBlankLine: Int): List<Triple<Int, Int, Int>> {
        val regex = """move (\d+) from (\d+) to (\d+)""".toRegex()
        return input
                .drop(indexOfBlankLine)
                .filter { it.isNotBlank() }
                .map {
                    val values = regex.find(it)!!.groupValues
                    Triple(values[1].toInt(), values[2].toInt(), values[3].toInt())
                }
    }


    fun processMoves(stack: MutableMap<Int, List<String>>, moves: List<Triple<Int, Int, Int>>) {
        moves.forEach { move ->
            repeat(move.first) {
                val topOfStack = stack[move.second]!!.last()
                stack[move.third] = stack[move.third]!!.plus(topOfStack)
                stack[move.second] = stack[move.second]!!.dropLast(1)
            }
        }
    }

    fun processMovesWith9001(stack: MutableMap<Int, List<String>>, moves: List<Triple<Int, Int, Int>>) {
        moves.forEach { move ->
            val topOfStack = stack[move.second]!!.takeLast(move.first)
            stack[move.third] = stack[move.third]!!.plus(topOfStack)
            stack[move.second] = stack[move.second]!!.dropLast(move.first)
        }
    }

    fun getTopOfStacks(stack: MutableMap<Int, List<String>>): List<String> {
        println("At end: $stack")
        return stack.entries.sortedBy { it.key }.map { it.value.lastOrNull() ?: " " }
    }

    fun part1(input: List<String>): String {
        val indexOfBlankLine = input.indexOfFirst { it.isBlank() }
        val stack = readCrateStack(input, indexOfBlankLine)
        val moves = readMoves(input, indexOfBlankLine)
        processMoves(stack, moves)

        return getTopOfStacks(stack).joinToString("") { it }
    }

    fun part2(input: List<String>): String {
        val indexOfBlankLine = input.indexOfFirst { it.isBlank() }
        val stack = readCrateStack(input, indexOfBlankLine)
        val moves = readMoves(input, indexOfBlankLine)
        processMovesWith9001(stack, moves)

        return getTopOfStacks(stack).joinToString("") { it }
    }

    val test = readInput("Day05.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == "CMZ")

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == "MCD")

    val input = readInput("Day05")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
