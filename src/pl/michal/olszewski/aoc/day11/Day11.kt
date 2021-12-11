package pl.michal.olszewski.aoc.day11

import readInput

fun main() {

    fun increaseAdjacentOctopuses(input: MutableList<MutableList<Int>>, x: Int, y: Int) {
        val height = input.size
        val width = input[0].size

        for (xPosition in x - 1..x + 1) {
            if (xPosition >= 0 && xPosition <= (width - 1)) {
                for (yPosition in y - 1..y + 1) {
                    if (yPosition >= 0 && yPosition <= (height - 1)) {
                        input[xPosition][yPosition] += 1
                    }
                }
            }
        }
    }

    fun getFlashes(octopusPositions: MutableList<MutableList<Int>>): MutableSet<Pair<Int, Int>> {
        val flashes = mutableSetOf<Pair<Int, Int>>()
        for (i in octopusPositions.indices) {
            for (j in octopusPositions[i].indices) {
                if (octopusPositions[i][j] > 9)
                    flashes.add(Pair(i, j))
            }
        }
        return flashes
    }

    fun replaceFlashedOctopusPositionsWithZeros(octopusPositions: MutableList<MutableList<Int>>) {
        getFlashes(octopusPositions).forEach {
            octopusPositions[it.first][it.second] = 0
        }
    }


    fun increaseAllOctopusByOne(octopusPositions: MutableList<MutableList<Int>>) {
        for (i in octopusPositions.indices) {
            for (j in octopusPositions[i].indices) {
                octopusPositions[i][j] += 1
            }
        }
    }

    fun nextStep(octopusPositions: MutableList<MutableList<Int>>) {
        increaseAllOctopusByOne(octopusPositions)
        val octopusPositionsThatAlreadyFlashed = mutableSetOf<Pair<Int, Int>>()
        while (getFlashes(octopusPositions) != octopusPositionsThatAlreadyFlashed) {
            val positionsToIterate = getFlashes(octopusPositions).minus(octopusPositionsThatAlreadyFlashed)
            positionsToIterate.forEach {
                increaseAdjacentOctopuses(octopusPositions, it.first, it.second)
                octopusPositionsThatAlreadyFlashed.add(it)
            }
        }
    }

    fun part1(input: List<String>): Int {
        val octopusPositions = input.map { it.map { ch -> ch.digitToInt() }.toMutableList() }.toMutableList()
        var result = 0
        repeat(100) {
            nextStep(octopusPositions)
            result += octopusPositions.sumOf {
                it.count { value -> value > 9 }
            }
            replaceFlashedOctopusPositionsWithZeros(octopusPositions)
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val octopusPositions = input.map { it.map { ch -> ch.digitToInt() }.toMutableList() }.toMutableList()
        var iters = 0
        while (octopusPositions.flatten().any { it != 0 }) {
            nextStep(octopusPositions)
            replaceFlashedOctopusPositionsWithZeros(octopusPositions)
            iters++
        }
        return iters
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day11", "Day11_test")
    check(part1(testInput) == 1656)
    val input = readInput("day11", "Day11")
    println(part1(input))

    check(part2(testInput) == 195)
    println(part2(input))
}
