package pl.michal.olszewski.aoc.day07

import readInput
import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val inputAsInt = input.first().split(",").map { it.toInt() }
        val maxValue = inputAsInt.maxOf { it }
        val result = (1..maxValue)
            .map { i ->
                val result = i to inputAsInt.map {
                    (it - i).absoluteValue
                }.sum()
                result.second
            }.minOf { it }
        return result
    }

    fun part2(input: List<String>): Int {
        val inputAsInt = input.first().split(",").map { it.toInt() }
        val maxValue = inputAsInt.maxOf { it }
        val result = (1..maxValue)
            .map { i ->
                val result = i to inputAsInt.sumOf {
                    val value = it.coerceAtLeast(i) - it.coerceAtMost(i)
                    (value * (value + 1)) / 2
                }
                System.err.println(result)
                result.second
            }.minOf { it }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day07", "Day07_test")
    check(part1(testInput) == 37)

    val input = readInput("day07", "Day07")
    println(part1(input))

    check(part2(testInput) == 168)

    println(part2(input))
}
