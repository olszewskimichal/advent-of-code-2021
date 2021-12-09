package pl.michal.olszewski.aoc.day09

import readInput

fun main() {
    data class PositionWithValue(val x: Int, val y: Int, val value: Int)

    fun getAdjacentPositionsWithValue(
        columnIndex: Int,
        rowIndex: Int,
        parsedInput: List<List<Int>>,
        height: Int,
        width: Int
    ): MutableList<PositionWithValue> {
        val positionWithValue = mutableListOf<PositionWithValue>()
        if (columnIndex > 0) {
            positionWithValue.add(PositionWithValue(columnIndex - 1, rowIndex, parsedInput[columnIndex - 1][rowIndex]))
        }
        if (rowIndex > 0) {
            positionWithValue.add(PositionWithValue(columnIndex, rowIndex - 1, parsedInput[columnIndex][rowIndex - 1]))
        }
        if (columnIndex < height - 1) {
            positionWithValue.add(PositionWithValue(columnIndex + 1, rowIndex, parsedInput[columnIndex + 1][rowIndex]))
        }
        if (rowIndex < width - 1) {
            positionWithValue.add(PositionWithValue(columnIndex, rowIndex + 1, parsedInput[columnIndex][rowIndex + 1]))
        }
        return positionWithValue
    }


    fun getLowPoints(parsedInput: List<List<Int>>, input: List<String>): MutableList<PositionWithValue> {
        val result = mutableListOf<PositionWithValue>()
        for ((columnIndex, row) in parsedInput.withIndex()) {
            for (rowIndex in row.indices) {
                val checkedValue = parsedInput[columnIndex][rowIndex]
                val adjacentPositions = getAdjacentPositionsWithValue(columnIndex, rowIndex, parsedInput, input.size, row.size)
                if (adjacentPositions.all { it.value > checkedValue }) {
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
            val adj = getAdjacentPositionsWithValue(p.x, p.y, parsedInput, input.size, parsedInput.first().size)
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
        val parsedInput = input.map { line -> line.toCharArray().map { it.code - 48 } }
        val result = getLowPoints(parsedInput, input)
        return result.sumOf { it.value + 1 }
    }

    fun part2(input: List<String>): Int {
        val parsedInput = input.map { line -> line.toCharArray().map { it.code - 48 } }
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
