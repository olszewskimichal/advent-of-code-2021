package pl.michal.olszewski.aoc.day07

import readInput
import readTestInput
import kotlin.math.absoluteValue

fun main() {

    fun part1(input: List<String>): Int {
        val crabPositions = input.first().split(",").map { it.toInt() }
        return (crabPositions.minOf { it }..crabPositions.maxOf { it }).minOf {
            crabPositions.sumOf { position -> (position - it).absoluteValue }
        }
    }

    fun part2(input: List<String>): Int {
        val crabPositions = input.first().split(",").map { it.toInt() }
        return (crabPositions.minOf { it }..crabPositions.maxOf { it }).minOf {
            crabPositions.sumOf { position ->
                val n = it.coerceAtLeast(position) - it.coerceAtMost(position)
                (n * (n + 1)) / 2
            }
        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day07")
    check(part1(testInput) == 37)

    val input = readInput("day07")
    println(part1(input))

    check(part2(testInput) == 168)

    println(part2(input))
}
