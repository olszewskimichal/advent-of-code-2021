package pl.michal.olszewski.aoc.day09

import readInput

fun main() {
    data class PositionWithValue(val x: Int, val y: Int, val value: Int)

    fun getAdjacents(
        columnIndex: Int,
        parsedInput: List<List<Int>>,
        rowIndex: Int,
        height: Int,
        width: Int
    ): MutableList<PositionWithValue> {
        val positionWithValue = mutableListOf<PositionWithValue>()
        if (columnIndex > 0) {
            positionWithValue.add(PositionWithValue(columnIndex - 1, rowIndex, parsedInput.get(columnIndex - 1).get(rowIndex)))
        }
        if (rowIndex > 0) {
            positionWithValue.add(PositionWithValue(columnIndex, rowIndex - 1, parsedInput.get(columnIndex).get(rowIndex - 1)))
        }
        if (columnIndex < height - 1) {
            positionWithValue.add(PositionWithValue(columnIndex + 1, rowIndex, parsedInput.get(columnIndex + 1).get(rowIndex)))
        }
        if (rowIndex < width - 1) {
            positionWithValue.add(PositionWithValue(columnIndex, rowIndex + 1, parsedInput.get(columnIndex).get(rowIndex + 1)))
        }
        return positionWithValue
    }


    fun getLowPoints(parsedInput: List<List<Int>>, input: List<String>): MutableList<PositionWithValue> {
        val result = mutableListOf<PositionWithValue>()
        for ((columnIndex, row) in parsedInput.withIndex()) {
            for (rowIndex in row.indices) {
                val checkedValue = parsedInput.get(columnIndex).get(rowIndex)
                val adjacent = getAdjacents(columnIndex, parsedInput, rowIndex, input.size, row.size)
                if (adjacent.all { it.value > checkedValue }) {
                    result.add(PositionWithValue(columnIndex, rowIndex, checkedValue))
                }
            }
        }
        return result
    }

    fun basinSize(start: PositionWithValue, parsedInput: List<List<Int>>, input: List<String>): Int {
        val seen = mutableSetOf(start)
        val todo = mutableListOf(start)
        while (todo.isNotEmpty()) {
            val p = todo.removeLast()
            val adj = getAdjacents(p.x, parsedInput, p.y, input.size, parsedInput.first().size)
            for (a in adj) {
                if (a.value != 9 && !seen.contains(a)) {
                    seen.add(a)
                    todo.add(a)
                }
            }
        }
        return seen.size
    }

    fun part1(input: List<String>): Int {
        val parsedInput = input.map { it.toCharArray().map { it.code - 48 } }
        val result = getLowPoints(parsedInput, input)
        return result.sumOf { it.value + 1 }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.map { it.toCharArray().map { it.code - 48 } }
        val result = getLowPoints(parsedInput, input).map { basinSize(it, parsedInput, input) }
        return result.sortedDescending().take(3).reduce { a, b -> a * b }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day09", "Day09_test")
    check(part1(testInput) == 15)

    val input = readInput("day09", "Day09")
    println(part1(input))

    check(part2(testInput) == 1134)

    println(part2(input))
}
