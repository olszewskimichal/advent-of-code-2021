package pl.michal.olszewski.aoc.day01

import readInput

fun main() {
    fun part1(input: List<Int>): Int {
        return input.asSequence()
            .windowed(1)
            .map { it.sum() }
            .zipWithNext()
            .count {
                it.second > it.first
            }
    }

    fun part2(input: List<Int>): Int {
        return input.asSequence()
            .windowed(3)
            .map { it.sum() }
            .zipWithNext()
            .count {
                it.second > it.first
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01", "Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("day01", "Day01").map { it.toInt() }
    println(part1(input))

    check(part2(testInput) == 5)

    println(part2(input))
}
