package pl.michal.olszewski.aoc.day08

import readInput

fun main() {

    fun part1(input: List<String>): Int {
       return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day08", "Day08_test")
    check(part1(testInput) == 1)

    val input = readInput("day08", "Day08")
    //println(part1(input))

    check(part2(testInput) == 1)

    //println(part2(input))
}
