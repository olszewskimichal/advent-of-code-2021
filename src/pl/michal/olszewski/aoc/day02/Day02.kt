package pl.michal.olszewski.aoc.day02

import pl.michal.olszewski.aoc.day02.Operation.DOWN
import pl.michal.olszewski.aoc.day02.Operation.FORWARD
import pl.michal.olszewski.aoc.day02.Operation.UP
import readInput

enum class Operation {
    FORWARD,
    DOWN,
    UP
}

fun main() {

    fun part1(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        input.forEach {
            val value = it.substringAfter(" ").toInt()
            when (Operation.valueOf(it.substringBefore(" ").uppercase())) {
                FORWARD -> horizontal += value
                DOWN -> depth += value
                UP -> depth -= value
            }
        }
        return horizontal * depth
    }

    fun part2(input: List<String>): Int {
        var horizontal = 0
        var depth = 0
        var aim = 0
        input.forEach {
            val value = it.substringAfter(" ").toInt()
            when (Operation.valueOf(it.substringBefore(" ").uppercase())) {
                FORWARD -> {
                    horizontal += value
                    depth += (value * aim)
                }
                DOWN -> aim += value
                UP -> aim -= value
            }
        }
        return horizontal * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02", "Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("day02", "Day02")
    println(part1(input))

    check(part2(testInput) == 900)

    println(part2(input))
}
