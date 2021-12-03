package pl.michal.olszewski.aoc.day03

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val length = input.first().length
        var gammaRateAsString = ""
        var epsilonRateAsString = ""
        for (i in 0 until length) {
            val zeros = input.count { it[i] == '0' }
            val ones = input.count { it[i] == '1' }
            if (zeros > ones) {
                gammaRateAsString += '0'
                epsilonRateAsString += '1'
            } else {
                gammaRateAsString += '1'
                epsilonRateAsString += '0'
            }
        }
        return gammaRateAsString.toInt(2) * epsilonRateAsString.toInt(2)
    }

    fun part2(input: List<String>): Int {
        var oxygenInput: List<String> = input
        var scrubberRatingInput: List<String> = input
        val length = input.first().length
        var oxygenGeneratorRatingAsString = ""
        var srubberRatingAsString = ""
        for (i in 0 until length) {
            val zeros = oxygenInput.count { it[i] == '0' }
            val ones = oxygenInput.count { it[i] == '1' }
            if (oxygenInput.size == 1) {
                break
            }
            when {
                zeros == ones || ones > zeros -> {
                    oxygenInput = oxygenInput.filter { it[i] == '1' }
                }
                else -> {
                    oxygenInput = oxygenInput.filter { it[i] == '0' }
                }
            }
        }
        oxygenGeneratorRatingAsString = oxygenInput[0]

        for (i in 0 until length) {
            val zeros = scrubberRatingInput.count { it[i] == '0' }
            val ones = scrubberRatingInput.count { it[i] == '1' }
            if (scrubberRatingInput.size == 1) {
                break
            }
            when {
                zeros <= ones -> {
                    scrubberRatingInput = scrubberRatingInput.filter { it[i] == '0' }
                }
                else -> {
                    scrubberRatingInput = scrubberRatingInput.filter { it[i] == '1' }
                }
            }
        }
        srubberRatingAsString = scrubberRatingInput[0]

        return oxygenGeneratorRatingAsString.toInt(2) * srubberRatingAsString.toInt(2)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03", "Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("day03", "Day03")
    println(part1(input))

    check(part2(testInput) == 230)

    println(part2(input))
}
