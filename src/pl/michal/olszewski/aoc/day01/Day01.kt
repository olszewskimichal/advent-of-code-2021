package pl.michal.olszewski.aoc.day01

import readInput

fun main() {
    fun part1(input: List<Int>): Int {
        var previousValue = input.first()
        var result = 0;
        for (value in input.subList(1, input.size)) {
            if (value > previousValue) {
                result++
            }
            previousValue = value;
        }
        return result;
    }

    fun part2(input: List<Int>): Int {
        var result = 0;
        var firstSum = input.subList(0, 3).sum()
        val lastIndex = input.size - 3
        for (i in 1..lastIndex) {
            val secondSum = input.subList(i, i + 3).sum()
            if (secondSum > firstSum) {
                result++
            }
            firstSum = secondSum
        }
        return result;
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day01", "Day01_test").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("day01", "Day01").map { it.toInt() }
    println(part1(input))

    check(part2(testInput) == 5)

    println(part2(input))
}
