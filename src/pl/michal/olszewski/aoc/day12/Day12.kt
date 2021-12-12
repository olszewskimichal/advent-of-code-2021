package pl.michal.olszewski.aoc.day12

import readInput
import readTestInput


data class Point(val name: String, val isSmall: Boolean) {
    fun isStart(): Boolean {
        return name == "start"
    }

    fun isEnd(): Boolean {
        return name == "end"
    }
}

fun main() {

    fun readInput(input: List<String>): MutableMap<Point, Set<Point>> {
        val pointsWithNeighbours = mutableMapOf<Point, Set<Point>>()

        input.map {
            val (point, neighbour) = it.split("-")
                .map { pointName -> if (pointName.all { sign -> sign.isLowerCase() }) Point(pointName, true) else Point(pointName, false) }

            pointsWithNeighbours[point] = pointsWithNeighbours.getOrDefault(point, emptySet()) + neighbour
            pointsWithNeighbours[neighbour] = pointsWithNeighbours.getOrDefault(neighbour, emptySet()) + point
        }
        return pointsWithNeighbours
    }

    fun findPaths(
        pointsWithNeighbours: MutableMap<Point, Set<Point>>,
        pointsAlreadyVisited: List<Point>,
        pointsNameWhichCanBeVisitedTwice: List<String>
    ): List<List<Point>> {
        return pointsWithNeighbours[pointsAlreadyVisited.last()]?.flatMap { next ->
            val allowTwice = pointsNameWhichCanBeVisitedTwice.contains(next.name)
            when {
                next.isSmall && allowTwice && pointsAlreadyVisited.count { it == next } > 1 -> emptyList()
                next.isSmall && !allowTwice && pointsAlreadyVisited.count { it == next } > 0 -> emptyList()
                next.isStart() -> emptyList()
                next.isEnd() -> listOf(pointsAlreadyVisited + next)
                else -> findPaths(pointsWithNeighbours, pointsAlreadyVisited + next, pointsNameWhichCanBeVisitedTwice)
            }
        } ?: emptyList()
    }

    fun part1(input: List<String>): Int {
        val pointsWithNeighbours = readInput(input)
        val start = pointsWithNeighbours.filter { it.key.isStart() }.keys.first()

        return findPaths(pointsWithNeighbours, listOf(start), emptyList()).size
    }

    fun part2(input: List<String>): Int {
        val pointsWithNeighbours = readInput(input)
        val start = pointsWithNeighbours.filter { it.key.isStart() }.keys.first()

        return pointsWithNeighbours.keys
            .filter { it.isSmall }
            .flatMap { findPaths(pointsWithNeighbours, listOf(start), listOf(it.name)) }
            .distinct()
            .size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day12")
    check(part1(testInput) == 226)

    val input = readInput("day12")
    println(part1(input))

    check(part2(testInput) == 3509)

    println(part2(input))
}
