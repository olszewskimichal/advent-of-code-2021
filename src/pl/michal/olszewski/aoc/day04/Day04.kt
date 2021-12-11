package pl.michal.olszewski.aoc.day04

import readInput
import readTestInput

private const val BOARD_SIZE = 5

data class BingoField(val value: Int, val marked: Boolean = false)

data class BingoFields(val fields: List<BingoField>) {

    fun markNumber(numberDrawn: Int): BingoFields {
        return this.copy(
            fields = fields.map {
                if (it.value == numberDrawn)
                    it.copy(marked = true)
                else it
            }
        )
    }

    fun allFieldsAreMarked(): Boolean {
        return fields.all { it.marked }
    }
}

data class BingoBoard(var columns: List<BingoFields>, var rows: List<BingoFields>) {

    fun markNumber(numberDrawn: Int) {
        columns = columns.map {
            it.markNumber(numberDrawn)
        }
        rows = rows.map {
            it.markNumber(numberDrawn)
        }
    }

    fun isWinning(): Boolean {
        return anyColumnHasAllValuesMarked() || anyRowHasAllValuesMarked()
    }

    private fun anyRowHasAllValuesMarked() = rows.any { row -> row.allFieldsAreMarked() }

    private fun anyColumnHasAllValuesMarked() = columns.any { column -> column.allFieldsAreMarked() }
    fun sumOfUnmarkedFields(): Int {
        return rows.sumOf { it.fields.filter { field -> !field.marked }.sumOf { field -> field.value } }
    }
}

fun readBingoBoards(input: List<String>): List<BingoBoard> = input.drop(1)
    .filter { it.isNotEmpty() }
    .chunked(BOARD_SIZE)
    .map { board ->
        val rows = board.map { boardLine -> boardLine.split(" ").filter { it.isNotEmpty() }.map { value -> value.toInt() } }
        val cols = rows.indices.map { column -> rows.map { it[column] } }
        BingoBoard(
            columns = cols.map { BingoFields(it.map(::BingoField)) },
            rows = rows.map { BingoFields(it.map(::BingoField)) }
        )
    }

fun main() {

    fun calculateWinningScoresForBoards(input: List<String>, boards: List<BingoBoard>): MutableList<Int> {
        val winningScores = mutableListOf<Int>()
        val drawnNumbers = input.take(1).flatMap { it.split(",") }.map { it.toInt() }

        drawnNumbers.forEach { numberDrawn ->
            boards
                .filter { !it.isWinning() }
                .forEach { board ->
                    board.markNumber(numberDrawn)
                    if (board.isWinning()) {
                        winningScores.add(numberDrawn * board.sumOfUnmarkedFields())
                    }
                }
        }
        return winningScores
    }

    fun part1(input: List<String>): Int {
        val boards = readBingoBoards(input)
        val winningScores = calculateWinningScoresForBoards(input, boards)
        return winningScores.first()
    }

    fun part2(input: List<String>): Int {
        val boards = readBingoBoards(input)
        val winningScores = calculateWinningScoresForBoards(input, boards)
        return winningScores.last()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day04")
    check(part1(testInput) == 4512)

    val input = readInput("day04")
    println(part1(input))

    check(part2(testInput) == 1924)

    println(part2(input))
}
