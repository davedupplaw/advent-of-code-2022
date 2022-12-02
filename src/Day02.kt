fun main() {
    val yourShapeMap = mapOf(
            "X" to "A",
            "Y" to "B",
            "Z" to "C"
    )
    val shapeScores = mapOf(
            "A" to 1,
            "B" to 2,
            "C" to 3
    )
    val beats = mapOf(
            "C" to "B",
            "B" to "A",
            "A" to "C"
    )

    val winOrLose = { yours: String, theirs: String ->
        if (yours == theirs) 3
        else if (beats[yours] == theirs) 6
        else 0
    }

    val forceOutcome = mapOf(
            "X" to { t: String -> beats[t] },
            "Y" to { t: String -> t },
            "Z" to { t: String -> beats.entries.find { it.value == t }!!.key },
    )

    fun part1(input: List<String>): Int {
        return input.asSequence()
                .map { it.split(" ") }
                .sumOf { winOrLose(yourShapeMap[it[1]]!!, it[0]) + shapeScores[yourShapeMap[it[1]]!!]!! }
    }

    fun part2(input: List<String>): Int {
        return input.asSequence()
                .map { it.split(" ") }
                .map { listOf(it[0], forceOutcome[it[1]]!!(it[0])) }
                .sumOf { winOrLose(it[1]!!, it[0]!!) + shapeScores[it[1]]!! }
    }

    val test = readInput("Day02.test")
    check(part1(test) == 15)
    check(part2(test) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}
