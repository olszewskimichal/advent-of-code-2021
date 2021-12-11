package pl.michal.olszewski.aoc.day05

import readInput
import readTestInput
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int)

fun main() {
    fun part1(input: List<String>): Int {
        val intersections = mutableMapOf<Point, Int>()

        input.forEach { line ->
            val (firstCoordinate, secondCoordinate) = line.split(" -> ")
            val (x1, y1) = firstCoordinate.split(",").map { it.toInt() }
            val (x2, y2) = secondCoordinate.split(",").map { it.toInt() }

            when {
                x1 == x2 -> {
                    for (y in y1.coerceAtMost(y2)..y1.coerceAtLeast(y2)) {
                        intersections[Point(x1, y)] = intersections.getOrDefault(Point(x1, y), 0) + 1
                    }
                }
                y1 == y2 -> {
                    for (x in x1.coerceAtMost(x2)..x1.coerceAtLeast(x2)) {
                        intersections[Point(x, y1)] = intersections.getOrDefault(Point(x, y1), 0) + 1
                    }
                }
            }
        }

        return intersections.values.count { it > 1 }
    }

    fun part2(input: List<String>): Int {
        val intersections = mutableMapOf<Point, Int>()

        input.forEach { line ->
            val (firstCoordinate, secondCoordinate) = line.split(" -> ")
            val (x1, y1) = firstCoordinate.split(",").map { it.toInt() }
            val (x2, y2) = secondCoordinate.split(",").map { it.toInt() }

            when {
                x1 == x2 -> {
                    for (y in y1.coerceAtMost(y2)..y1.coerceAtLeast(y2)) {
                        intersections[Point(x1, y)] = intersections.getOrDefault(Point(x1, y), 0) + 1
                    }
                }
                y1 == y2 -> {
                    for (x in x1.coerceAtMost(x2)..x1.coerceAtLeast(x2)) {
                        intersections[Point(x, y1)] = intersections.getOrDefault(Point(x, y1), 0) + 1
                    }
                }
                (x1 - x2).absoluteValue == (y1 - y2).absoluteValue -> {
                    val diffX = if (x2 > x1) 1 else -1
                    val diffY = if (y2 > y1) 1 else -1
                    for (i in 0..(x2 - x1).absoluteValue) {
                        val x = x1 + (i * diffX)
                        val y = y1 + (i * diffY)
                        intersections[Point(x, y)] = intersections.getOrDefault(Point(x, y), 0) + 1
                    }
                }
            }
        }
        return intersections.values.count { it > 1 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day05")
    check(part1(testInput) == 5)

    val input = readInput("day05")
    println(part1(input))

    check(part2(testInput) == 12)

    println(part2(input))
}
