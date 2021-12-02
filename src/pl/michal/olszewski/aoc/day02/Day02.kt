package pl.michal.olszewski.aoc.day02

import pl.michal.olszewski.aoc.day02.Direction.DOWN
import pl.michal.olszewski.aoc.day02.Direction.FORWARD
import pl.michal.olszewski.aoc.day02.Direction.UP
import pl.michal.olszewski.aoc.day02.Direction.valueOf
import pl.michal.olszewski.aoc.day02.Position.Companion.INITIAL_POSITION
import readInput

enum class Direction {
    FORWARD,
    DOWN,
    UP
}

data class Operation(
    val direction: Direction,
    val unit: Int
)

data class Position(
    val horizontal: Int,
    val depth: Int,
    val aim: Int
) {
    companion object {
        val INITIAL_POSITION = Position(0, 0, 0)
    }

    fun changeByV1(operation: Operation): Position {
        return when (operation.direction) {
            FORWARD -> this.increaseHorizontal(operation.unit)
            DOWN -> this.increaseDepth(operation.unit)
            UP -> this.decreaseDepth(operation.unit)
        }
    }

    fun changeByV2(operation: Operation): Position {
        return when (operation.direction) {
            FORWARD -> this
                .increaseHorizontal(operation.unit)
                .increaseDepthMultipliedByAim(operation.unit)
            DOWN -> this.increaseAim(operation.unit)
            UP -> this.decreaseAim(operation.unit)
        }
    }

    private fun increaseHorizontal(value: Int): Position {
        return this.copy(horizontal = horizontal + value)
    }

    private fun increaseDepth(value: Int): Position {
        return this.copy(depth = depth + value)
    }

    private fun decreaseDepth(value: Int): Position {
        return this.copy(depth = depth - value)
    }

    private fun increaseDepthMultipliedByAim(value: Int): Position {
        return this.copy(depth = depth + (value * aim))
    }

    private fun increaseAim(value: Int): Position {
        return this.copy(aim = aim + value)
    }

    private fun decreaseAim(value: Int): Position {
        return this.copy(aim = aim - value)
    }
}

fun main() {

    fun mapOperationFromString(it: String): Operation {
        val (directionAsString, unitAsString) = it.split(" ")
        return Operation(direction = valueOf(directionAsString.uppercase()), unit = unitAsString.toInt())
    }

    fun part1(input: List<String>): Int {
        val result = input.map { mapOperationFromString(it) }
            .fold(INITIAL_POSITION) { position: Position, operation: Operation ->
                position.changeByV1(operation)
            }
        return result.horizontal * result.depth
    }

    fun part2(input: List<String>): Int {
        val result = input.map { mapOperationFromString(it) }
            .fold(INITIAL_POSITION) { position: Position, operation: Operation ->
                position.changeByV2(operation)
            }
        return result.horizontal * result.depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02", "Day02_test")
    check(part1(testInput) == 150)

    val input = readInput("day02", "Day02")
    println(part1(input))

    check(part2(testInput) == 900)

    println(part2(input))
}
