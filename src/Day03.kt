fun main() {
    fun findCommonItem(rucksackContents: String): Char {
        val numberOfItems = rucksackContents.length / 2
        val firstCompartmentContents = rucksackContents.substring(0, numberOfItems)
        val secondCompartmentContents = rucksackContents.substring(numberOfItems)
        return firstCompartmentContents.find { item ->
            secondCompartmentContents.contains(item)
        }!!
    }

    fun convertToPriorities(commonItem: Char): Int {
        val priority = commonItem.code - 96
        return if(priority > 0) priority else priority + 58
    }

    fun part1(input: List<String>): Int {
        return input
            .map { findCommonItem(it) }
            .sumOf { convertToPriorities(it) }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val test = readInput("Day03.test")
    val part1 = part1(test)
    println(part1)
    check(part1 == 157)
//    check(part2(test) == 12)

    val input = readInput("Day03")
    println(part1(input))
//    println(part2(input))
}
