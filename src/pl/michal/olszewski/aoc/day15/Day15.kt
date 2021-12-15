package pl.michal.olszewski.aoc.day15

import readInput
import readTestInput
import kotlin.Int.Companion.MAX_VALUE


data class Point(val x: Int, val y: Int)

fun main() {
    val startingPoint = Point(0, 0)

    fun neighbours(element: Point, height: Int, width: Int): List<Point> {
        val x = element.x
        val y = element.y
        val result = mutableListOf<Point>()
        if (x + 1 <= width) {
            result.add(Point(x + 1, y))
        }
        if (x - 1 >= 0) {
            result.add(Point(x - 1, y))
        }

        if (y + 1 <= height) {
            result.add(Point(x, y + 1))
        }
        if (y - 1 >= 0) {
            result.add(Point(x, y - 1))
        }

        return result
    }

    fun dijkstra(input: List<List<Int>>, pair: Point, startingPoint: Point): Map<Point, Int> {
        val distances = mutableMapOf<Point, Int>()

        val height = input.lastIndex
        val width = input[height].lastIndex

        val queue = mutableMapOf<Point, Int>()
        distances[startingPoint] = 0
        queue[startingPoint] = distances[startingPoint]!!

        while (queue.isNotEmpty()) {
            val element = queue.minByOrNull { it.value }!!.key
            if (element == pair) break
            queue.remove(element)
            val value = distances[element]!!
            neighbours(element, height, width).forEach { neighbour ->
                val newValue = value + input[neighbour.x][neighbour.y]
                if (newValue < distances.getOrDefault(neighbour, MAX_VALUE)) {
                    distances[neighbour] = newValue
                    queue[neighbour] = newValue
                }
            }
        }

        return distances
    }

    fun part1(input: List<String>): Int {
        val parsedInput = input.map { line -> line.map { it.digitToInt() } }

        val targetY = parsedInput.lastIndex
        val targetX = parsedInput[targetY].lastIndex
        val endingPoint = Point(targetX, targetY)

        val distances = dijkstra(parsedInput, endingPoint, startingPoint)
        return distances[endingPoint]!!
    }


    fun extendLine(line: List<Int>): List<Int> {
        val extended = line.toMutableList()
        var increased = line
        repeat(4) {
            increased = increased.map { if ((it + 1) > 9) (it + 1) - 9 else it + 1 }
            extended.addAll(increased)
        }
        return extended
    }

    fun extendLines(input: List<List<Int>>): List<List<Int>> {
        val extended = input.toMutableList()
        var increased = input
        repeat(4) {
            increased = increased.map { line -> line.map { num -> if ((num + 1) > 9) (num + 1) - 9 else num + 1 } }
            extended.addAll(increased)
        }
        return extended
    }

    fun extend(input: List<List<Int>>): List<List<Int>> {
        return extendLines(input).map { line -> extendLine(line) }
    }

    fun part2(input: List<String>): Int {
        val extendedInput = extend(input.map { line -> line.map { it.digitToInt() } })
        val targetY = extendedInput.lastIndex
        val targetX = extendedInput[targetY].lastIndex
        val distances = dijkstra(extendedInput, Point(targetX, targetY), startingPoint)
        return distances[Point(targetX, targetY)]!!
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day15")
    check(part1(testInput) == 40)

    val input = readInput("day15")
    println(part1(input))

    check(part2(testInput) == 315)

    println(part2(input))
}
