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

    fun findCommonItems(rucksack1: Set<Char>, rucksack2: String): Set<Char> {
        return rucksack1.mapNotNull { item ->
            if (rucksack2.contains(item)) item else null
        }.toSet()
    }

    fun findCommonItemInGroup(group: List<String>): Char {
        val firstRucksack = group[0]
        var foundItems = firstRucksack.toCharArray().toSet()

        for (rucksack in group.drop(1)) {
            foundItems = findCommonItems(foundItems, rucksack)
        }

        return foundItems.toList()[0]
    }

    fun part2(input: List<String>): Int {
        return input
            .chunked(3)
            .map { findCommonItemInGroup(it) }
            .sumOf { convertToPriorities(it) }
    }

    val test = readInput("Day03.test")
    val part1 = part1(test)
    println("part1: $part1")
    check(part1 == 157)
    val part2 = part2(test)
    println("part2: $part2")
    check(part2 == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
