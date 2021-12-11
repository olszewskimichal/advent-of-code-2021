package pl.michal.olszewski.aoc.day01

import readInput
import readTestInput

fun countIncreases(input: List<Int>, window: Int = 1) = input.asSequence()
    .windowed(window)
    .map { it.sum() }
    .zipWithNext()
    .map { Measurements(previousSum = it.first, nextSum = it.second) }
    .count { it.nextIsLargerThanPrevious() }

data class Measurements(
    val previousSum: Int,
    val nextSum: Int
) {
    fun nextIsLargerThanPrevious(): Boolean {
        return nextSum > previousSum;
    }
}

fun main() {

    fun part1(input: List<Int>): Int {
        return countIncreases(input)
    }

    fun part2(input: List<Int>): Int {
        return countIncreases(input, window = 3)
    }

    val testInput = readTestInput("day01").map { it.toInt() }
    check(part1(testInput) == 7)

    val input = readInput("day01").map { it.toInt() }
    println(part1(input))

    check(part2(testInput) == 5)

    println(part2(input))
}
