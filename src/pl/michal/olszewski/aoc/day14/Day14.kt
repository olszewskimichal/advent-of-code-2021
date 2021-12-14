package pl.michal.olszewski.aoc.day14

import readInput
import readTestInput

data class InsertionPair(val rule: String, val intersectedElement: String)

fun main() {

    fun increment(polymerPairsFrequency: MutableMap<String, Long>, key: String, value: Long) {
        polymerPairsFrequency[key] = polymerPairsFrequency.getOrDefault(key, 0) + value
    }

    fun countEachElementFrequency(polymerPairsFrequency: MutableMap<String, Long>, polymerTemplate: String): MutableMap<String, Long> {
        val result = mutableMapOf<String, Long>()

        polymerPairsFrequency.entries.forEach { (key, value) ->
            increment(result, "${key[0]}", value)
            increment(result, "${key[1]}", value)
        }
        increment(result, "${polymerTemplate.first()}", 1)
        increment(result, "${polymerTemplate.last()}", 1)
        return result
    }

    fun nextStep(polymerPairsFrequency: MutableMap<String, Long>, insertionPairs: List<InsertionPair>): MutableMap<String, Long> {
        val newPolymerPairsFrequency = mutableMapOf<String, Long>()
        polymerPairsFrequency.entries.forEach { (rule, value) ->
            insertionPairs.firstOrNull() { it.rule == rule }?.let {
                val intersectedElement = it.intersectedElement
                increment(newPolymerPairsFrequency, "${rule[0]}$intersectedElement", value)
                increment(newPolymerPairsFrequency, "$intersectedElement${rule[1]}", value)
            }
        }
        return newPolymerPairsFrequency
    }

    fun readInsertionPairs(input: List<String>) = input.drop(2).map {
        val (rule, element) = it.split(" -> ")
        InsertionPair(rule, element)
    }

    fun calculateInitialPairFrequency(polymerTemplate: String, polymerPairsFrequency: MutableMap<String, Long>) {
        for (i in 0..polymerTemplate.length - 2) {
            increment(polymerPairsFrequency, polymerTemplate[i] + "" + polymerTemplate[i + 1], 1)
        }
    }

    fun part1(input: List<String>): Long {
        val polymerTemplate = input.first()

        val insertionPairs = readInsertionPairs(input)

        var polymerPairsFrequency = mutableMapOf<String, Long>()
        calculateInitialPairFrequency(polymerTemplate, polymerPairsFrequency)

        repeat(10) {
            polymerPairsFrequency = nextStep(polymerPairsFrequency, insertionPairs)
        }
        val result = countEachElementFrequency(polymerPairsFrequency, polymerTemplate)
        return (result.maxOf { it.value } - result.minOf { it.value }) / 2
    }

    fun part2(input: List<String>): Long {
        val polymerTemplate = input.first()
        val insertionPairs = readInsertionPairs(input)

        var polymerPairsFrequency = mutableMapOf<String, Long>()
        calculateInitialPairFrequency(polymerTemplate, polymerPairsFrequency)

        repeat(40) {
            polymerPairsFrequency = nextStep(polymerPairsFrequency, insertionPairs)
        }
        val result = countEachElementFrequency(polymerPairsFrequency, polymerTemplate)
        return (result.maxOf { it.value } - result.minOf { it.value }) / 2
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day14")
    check(part1(testInput) == 1588L)

    val input = readInput("day14")
    println(part1(input))

    check(part2(testInput) == 2188189693529L)

    println(part2(input))
}

