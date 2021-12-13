package pl.michal.olszewski.aoc.day13

import readInput
import readTestInput
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int)

enum class FoldType {
    Y,
    X
}

data class Fold(val type: FoldType, val value: Int)

fun main() {

    fun foldPoints(points: List<Point>, fold: Fold): List<Point> {
        return when (fold.type) {
            FoldType.Y -> {
                val pointsBeforeLine = points.filter {
                    it.x < fold.value
                }
                val pointsAfterLine = points.filter {
                    it.x > fold.value
                }
                val result = pointsBeforeLine + pointsAfterLine.map { Point(x = (2 * fold.value - it.x).absoluteValue, y = it.y) }
                result.distinct()
            }
            FoldType.X -> {
                val pointsBeforeLine = points.filter {
                    it.y < fold.value
                }
                val pointsAfterLine = points.filter {
                    it.y > fold.value
                }
                val result = pointsBeforeLine + pointsAfterLine.map { Point(y = (2 * fold.value - it.y).absoluteValue, x = it.x) }
                result.distinct()
            }
        }
    }


    fun readPoints(input: List<String>) = input.filter { it.isNotEmpty() }.filter { !it.startsWith("fold") }
        .map {
            val (x, y) = it.split(",").map { value -> value.toInt() }
            Point(y, x)
        }

    fun readFolds(input: List<String>) = input.filter { it.isNotEmpty() }.filter { it.startsWith("fold") }
        .map {
            val (type, value) = it.replace("fold along ", "").split("=")
            when (type) {
                "y" -> Fold(FoldType.Y, value.toInt())
                else -> Fold(FoldType.X, value.toInt())
            }
        }

    fun part1(input: List<String>): Int {
        val points = readPoints(input)
        val folds = readFolds(input)
        return foldPoints(points, folds.first()).size
    }


    fun drawResult(result: List<Point>): String {
        val height = result.maxOf { it.x } + 1
        val width = result.maxOf { it.y } + 1
        val array = Array(height) { CharArray(width) { ' ' } }
        result.forEach { array[it.x][it.y] = '█' }
        return array.joinToString("\n") { it.joinToString("") }
    }

    fun part2(input: List<String>): String {
        val points = readPoints(input)
        val folds = readFolds(input)
        val result = folds.fold(points) { acc: List<Point>, fold: Fold ->
            foldPoints(acc, fold)
        }
        return drawResult(result)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day13")
    check(part1(testInput) == 17)

    val input = readInput("day13")
    println(part1(input))

    println(part2(testInput))
    println(part2(input))
}
