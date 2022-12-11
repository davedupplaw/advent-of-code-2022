import java.math.BigInteger

fun main() {
    var lowestCommonModulus: Long

    data class Item(var worryLevel: Long)
    data class Monkey(
        val number: Int,
        val items: MutableList<Item> = mutableListOf(),
        val operation: String,
        val operationOperand: String,
        val testDivisibleBy: Long,
        val trueThrow: Int,
        val falseThrow: Int
    ) {
        var itemsInspected = BigInteger.ZERO

        fun test(item: Item) = (item.worryLevel % testDivisibleBy) == 0L

        fun updateWorryLevel(item: Item) {
            val operand = if (operationOperand == "old") item.worryLevel else operationOperand.toLong()
            return when (operation) {
                "*" -> item.worryLevel *= operand
                "+" -> item.worryLevel += operand
                else -> error("Not allowed")
            }
        }

        fun throwTo(item: Item, reduceWorry: Boolean, lowestCommonModulus: Long): Int {
            itemsInspected++
            updateWorryLevel(item)

            if (reduceWorry) item.worryLevel /= 3
            else item.worryLevel %= lowestCommonModulus

            val result = test(item)
            return if (result) trueThrow else falseThrow
        }
    }

    fun parseInput(input: String): List<Monkey> {
        val monkeys = input.split("\n\n").map { monkeyInput ->
            val lines = monkeyInput.lines()
            val monkeyN = lines[0].split(" ", ":")[1].toInt()
            val splitItems = lines[1].split(" ", ":", ",").filter { it.isNotBlank() }
            val items = (2 until splitItems.size).map { Item(splitItems[it].toLong()) }
            val operationSplit = lines[2].split(" ").filter { it.isNotBlank() }
            val operation = operationSplit[4]
            val operationOperand = operationSplit[5]
            val divBy = lines[3].split(" ").filter { it.isNotBlank() }[3].toLong()
            val onTrue = lines[4].split(" ").filter { it.isNotBlank() }[5].toInt()
            val onFalse = lines[5].split(" ").filter { it.isNotBlank() }[5].toInt()
            Monkey(monkeyN, items.toMutableList(), operation, operationOperand, divBy, onTrue, onFalse)
        }
        return monkeys
    }

    fun getThoseMonkeysTyping(input: String, nTimes: Int, reduceWorry: Boolean): BigInteger {
        val monkeys = parseInput(input)
        lowestCommonModulus = monkeys.map { it.testDivisibleBy }.reduce { a, b -> a * b }
        repeat(nTimes) {
            monkeys.forEach { monkey ->
                monkey.items.forEach { item ->
                    monkeys[monkey.throwTo(item, reduceWorry, lowestCommonModulus)].items += item
                }
                monkey.items.clear()
            }
        }

        return monkeys
            .map { it.itemsInspected }
            .sortedByDescending { it }
            .take(2).reduce { a, b -> a * b }
    }

    fun part1(input: String): BigInteger {
        return getThoseMonkeysTyping(input, 20, true)
    }

    fun part2(input: String): BigInteger {
        return getThoseMonkeysTyping(input, 10_000, false)
    }

    val test = readInputToString("Day11.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == BigInteger.valueOf(10605))

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == BigInteger.valueOf(2_713_310_158L))

    val input = readInputToString("Day11")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
