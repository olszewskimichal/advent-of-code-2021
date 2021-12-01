package pl.michal.olszewski.aoc.template

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("template", "Day01_test")
    check(part1(testInput) == 1)

    val input = readInput("template", "Day01")
    //println(part1(input))

    check(part2(testInput) == 1)

    //println(part2(input))
}
