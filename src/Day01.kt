fun main() {
    fun getElvesWithCalories(input: List<String>): Map<Int, Int> {
        var elfNumber = 0
        return input.mapNotNull { i ->
            if (i.isBlank()) {
                elfNumber++; null
            } else elfNumber to i.toInt()
        }
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }
    }

    fun part1(input: List<String>): Int {
        return getElvesWithCalories(input)
                .values
                .max()
    }

    fun part2(input: List<String>): Int {
        return getElvesWithCalories(input)
                .values
                .sortedDescending()
                .take(3)
                .sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
