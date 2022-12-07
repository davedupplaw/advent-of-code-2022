interface Item {
    val name: String
    fun size(): Int
}

data class File(override val name: String, private val size: Int) : Item {
    override fun size() = size
}

class Directory(
    override val name: String,
    val parent: Directory? = null,
    private val files: MutableList<Item> = mutableListOf()
) : Item {
    override fun size() = files.sumOf { it.size() }
    fun addDir(name: String): Directory = files
        .filterIsInstance<Directory>()
        .find { it.name == name }
        ?: run {
            Directory(name, this).let { files.add(it); it }
        }

    fun addFile(name: String, size: Int) = files.add(File(name, size))
    fun dirs() = files.filterIsInstance<Directory>()
    fun fullName(): String = (parent?.fullName() ?: "") + name + "/"
    override fun toString(): String {
        return """${fullName()}($files)"""
    }
}

fun main() {
    fun parseOutput(input: List<String>): Directory {
        val root = Directory("")
        var currentDir = root
        input.forEach { output ->
            val bits = output.split(" ")

            if (bits[0] == "$") {
                when (bits[1]) {
                    "cd" -> currentDir = when (bits[2]) {
                        ".." -> currentDir.parent!!
                        "/" -> root
                        else -> currentDir.addDir(bits[2])
                    }
                }
            } else {
                when (bits[0]) {
                    "dir" -> currentDir.addDir(bits[1])
                    else -> currentDir.addFile(bits[1], bits[0].toInt())
                }
            }
        }
        return root
    }

    fun dirSizes(root: Directory): List<Pair<String, Int>> {
        return root.dirs().map { dirSizes(it) }.flatten() + listOf(root.fullName() to root.size())
    }

    fun part1(input: List<String>): Int {
        val root = parseOutput(input)
        val dirSizes = dirSizes(root)
        return dirSizes.filter { it.second <= 100000 }.sumOf { it.second }
    }

    fun part2(input: List<String>): Int {
        val root = parseOutput(input)
        val total = 70000000
        val freeSpace = total - root.size()
        val dirSizes = dirSizes(root).sortedBy { it.second }
        val toRemove = dirSizes.find { freeSpace + it.second >= 30000000 }
        return toRemove!!.second
    }

    val test = readInput("Day07.test")

    val part1test = part1(test)
    println("part1 test: $part1test")
    check(part1test == 95437)

    val part2test = part2(test)
    println("part2 test: $part2test")
    check(part2test == 24933642)

    val input = readInput("Day07")

    val part1 = part1(input)
    println("Part1: $part1")

    val part2 = part2(input)
    println("Part2: $part2")
}
