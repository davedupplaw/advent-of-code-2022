fun main() {
    fun makeMap(inputs: List<String>): Triple<List<List<Int>>, Int, Int> {
        val h = inputs.size
        val w = inputs[0].length
        val trees = inputs
            .map {
                it.split("")
                    .filter { x -> x.isNotBlank() }
                    .map { x -> x.toInt() }
            }
        return Triple(trees, w, h)
    }

    fun List<List<Int>>.column(y: Int) = this.map { it[y] }
    fun List<List<Int>>.row(x: Int) = this[x]
    fun List<List<Int>>.isVisibleFromAnywhere(x: Int, y: Int): Boolean {
        val col = this.column(x)
        val row = this.row(y)
        val value = this[y][x]

        val visibleFromAbove = col.take(y).all { v -> v < value }
        val visibleFromBelow = col.reversed().take(col.size - y - 1).all { v -> v < value }
        val visibleFromLeftSide = row.take(x).all { v -> v < value }
        val visibleFromRightSide = row.reversed().take(row.size - x - 1).all { v -> v < value }

        return visibleFromAbove || visibleFromBelow || visibleFromLeftSide || visibleFromRightSide
    }

    fun Sequence<Int>.chase(value: Int): List<Int> {
        var blocked = 0
        return this.map {
            if (it >= value || blocked > 0) blocked++
            it
        }.takeWhile { blocked < 2 }.toList()
    }

    fun List<List<Int>>.scenicScore(x: Int, y: Int): Int {
        val col = this.column(x)
        val row = this.row(y)
        val value = this[y][x]

        val toTopEdge = col.take(y).reversed().asSequence().chase(value).count()
        val toLeftEdge = row.take(x).reversed().asSequence().chase(value).count()
        val toBottomEdge = col.takeLast(col.size - y - 1).asSequence().chase(value).count()
        val toRightEdge = row.takeLast(row.size - x - 1).asSequence().chase(value).count()

        return toTopEdge * toLeftEdge * toBottomEdge * toRightEdge
    }

    fun part1(input: List<String>): Int {
        val (trees, w, h) = makeMap(input)
        val sum = (1 until w - 1).sumOf { y ->
            (1 until h - 1).sumOf { x ->
                if (trees.isVisibleFromAnywhere(x, y)) 1 else 0 as Int
            }
        }
        return sum + 2 * h + 2 * (w - 2)
    }

    fun part2(input: List<String>): Int {
        val (trees, w, h) = makeMap(input)
        return (0 until w).maxOf { y ->
            (0 until h).maxOf { x ->
                trees.scenicScore(x, y)
            }
        }
    }

    val test = readInput("Day08.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == 21)

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == 8)

    val input = readInput("Day08")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
