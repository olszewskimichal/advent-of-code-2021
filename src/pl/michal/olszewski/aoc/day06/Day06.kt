package pl.michal.olszewski.aoc.day06

import readInput
import java.util.Arrays

fun main() {


    fun initialFirstState(input: List<String>, counts: LongArray) {
        input.first().split(",").map { it.toInt() }
            .forEach {
                counts[it] += 1L
            }
    }

    fun calculateStateForDay(counts: LongArray, day: Int) {
        repeat(day) {
            val newBirthFish = counts[0]
            for (i in 0..7)
                counts[i] = counts[i + 1]
            counts[8] = newBirthFish
            counts[6] += newBirthFish
        }
    }

    fun part1(input: List<String>): Long {
        val counts = LongArray(9) { 0 }
        initialFirstState(input, counts)
        calculateStateForDay(counts, 80)
        return counts.sum()
    }

    fun part2(input: List<String>): Long {
        val counts = LongArray(9) { 0 }
        initialFirstState(input, counts)
        calculateStateForDay(counts, 256)
        return counts.sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day06", "Day06_test")
    check(part1(testInput) == 5934L)

    val input = readInput("day06", "Day06")
    println(part1(input))

    check(part2(testInput) == 26984457539L)

    println(part2(input))
}
