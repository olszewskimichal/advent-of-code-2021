package pl.michal.olszewski.aoc.day03

import readInput

fun isMoreZeroThanOnes(position: List<Char>) = position.count { it == '0' } > position.count { it == '1' }

fun isLessZerosThanOnes(position: List<Char>) = position.count { it == '0' } <= position.count { it == '1' }

private fun calculateOxygenRating(input: List<String>): String {
    val length = input.first().length
    var oxygenInput = input
    for (i in 0 until length) {
        oxygenInput = when {
            isLessZerosThanOnes(oxygenInput.map { it[i] }) -> {
                oxygenInput.filter { it[i] == '1' }
            }
            else -> {
                oxygenInput.filter { it[i] == '0' }
            }
        }

        if (oxygenInput.size == 1) {
            return oxygenInput.first()
        }
    }
    throw IllegalStateException()
}

private fun calculateScrubberRating(input: List<String>): String {
    val length = input.first().length
    var scrubberRatingInput = input
    for (i in 0 until length) {
        scrubberRatingInput = when {
            isMoreZeroThanOnes(scrubberRatingInput.map { it[i] }) -> {
                scrubberRatingInput.filter { it[i] == '1' }
            }
            else -> {
                scrubberRatingInput.filter { it[i] == '0' }
            }
        }

        if (scrubberRatingInput.size == 1) {
            return scrubberRatingInput.first()
        }
    }
    throw IllegalStateException()
}

fun main() {

    fun part1(input: List<String>): Int {
        val correspondingPositions = (0 until input.first().length)
            .map { i -> input.map { it[i] } }
        val gammaRate = correspondingPositions.map { position -> if (isMoreZeroThanOnes(position)) '0' else '1' }.joinToString(separator = "").toInt(2)
        val epsilonRate = correspondingPositions.map { position -> if (isMoreZeroThanOnes(position)) '1' else '0' }.joinToString(separator = "").toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val oxygenGeneratorRatingAsString = calculateOxygenRating(input)
        val scrubberRatingAsString = calculateScrubberRating(input)
        return oxygenGeneratorRatingAsString.toInt(2) * scrubberRatingAsString.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03", "Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("day03", "Day03")
    println(part1(input))

    check(part2(testInput) == 230)

    println(part2(input))
}
