package pl.michal.olszewski.aoc.day04

import readInput

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
        return columns.any { column -> column.fields.all { field -> field.marked } } || rows.any { row -> row.fields.all { field -> field.marked } }
    }

    fun sumOfUnmarkedFields(): Int {
        return rows.sumOf { it.fields.filter { field -> !field.marked }.sumOf { field -> field.value } }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val drawnNumbers = input.take(1).flatMap { it.split(",") }.map { it.toInt() }

        val boardsTMP = input.drop(1)
            .filter { it.isNotEmpty() }
            .chunked(5) //TODO const board size
            .map { board ->
                val rows = board.map { it.split(" ").filter { it.isNotEmpty() }.map { value -> value.toInt() } }
                val cols = rows.indices.map { column -> rows.map { it[column] } }
                BingoBoard(
                    columns = cols.map { BingoFields(it.map { value -> BingoField(value, false) }) },
                    rows = rows.map { BingoFields(it.map { value -> BingoField(value, false) }) }
                )
            }

        drawnNumbers.forEach { numberDrawn ->
            boardsTMP.forEach { board ->
                board.markNumber(numberDrawn)
                if (board.isWinning()) {
                    return numberDrawn * board.sumOfUnmarkedFields()
                }
            }
        }

        throw IllegalStateException()
    }

    fun part2(input: List<String>): Int {
        val winningScores = mutableListOf<Int>()
        val drawnNumbers = input.take(1).flatMap { it.split(",") }.map { it.toInt() }

        val boardsTMP = input.drop(1)
            .filter { it.isNotEmpty() }
            .chunked(5) //TODO const board size
            .map { board ->
                val rows = board.map { it.split(" ").filter { it.isNotEmpty() }.map { value -> value.toInt() } }
                val cols = rows.indices.map { column -> rows.map { it[column] } }
                BingoBoard(
                    columns = cols.map { BingoFields(it.map { value -> BingoField(value, false) }) },
                    rows = rows.map { BingoFields(it.map { value -> BingoField(value, false) }) }
                )
            }

        drawnNumbers.forEach { numberDrawn ->
            boardsTMP
                .filter { !it.isWinning() }
                .forEach { board ->
                    board.markNumber(numberDrawn)
                    if (board.isWinning()) {
                        winningScores.add(numberDrawn * board.sumOfUnmarkedFields())
                    }
                }
        }

        return winningScores.last()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04", "Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("day04", "Day04")
    println(part1(input))

    check(part2(testInput) == 1924)

    println(part2(input))
}
