package pl.michal.olszewski.aoc.day10

import readInput


fun <T> MutableList<T>.push(item: T) = add(item)
fun <T> MutableList<T>.pop(): T? = if (isNotEmpty()) removeAt(lastIndex) else null

fun main() {

    fun findIncorrectSign(line: String): Char? {
        val stack = mutableListOf<Char>()
        for (sign in line.toCharArray()) {
            when (sign) {
                '(', '[', '{', '<' -> {
                    stack.push(sign)
                }
                else -> {
                    val closeCharacter = stack.pop()

                    when (sign) {
                        ')' -> if (closeCharacter != '(') return sign
                        ']' -> if (closeCharacter != '[') return sign
                        '}' -> if (closeCharacter != '{') return sign
                        '>' -> if (closeCharacter != '<') return sign
                    }

                }
            }
        }
        return null
    }

    fun isIncorrectLine(line: String): Boolean {
        return findIncorrectSign(line) != null
    }

    fun findMissingSigns(line: String): List<Char> {
        val stack = mutableListOf<Char>()
        val result = mutableListOf<Char>()
        for (sign in line.toCharArray()) {
            when (sign) {
                '(', '[', '{', '<' -> {
                    stack.push(sign)
                }
                else -> {
                    stack.pop()
                }
            }
        }
        stack.forEach {
            when (it) {
                '(' -> result.add(')')
                '[' -> result.add(']')
                '{' -> result.add('}')
                '<' -> result.add('}')
            }
        }
        return result.reversed()
    }

    fun part1(input: List<String>): Int {
        return input
            .map { line -> findIncorrectSign(line) }
            .sumOf { incorrectSign -> incorrectSign.toPointsForIncorrect() }
    }

    fun sumOfPointsForMissingSigns(missingSigns: List<Char>): Long {
        var sum = 0L
        missingSigns
            .map { sign -> sign.toPointsForMissing() }
            .forEach {
                sum *= 5
                sum += it
            }
        return sum
    }

    fun part2(input: List<String>): Long {
        val sortedSumOfPoints = input
            .asSequence()
            .filterNot { isIncorrectLine(it) }
            .map { line -> findMissingSigns(line) }
            .filter { it.isNotEmpty() }
            .map { missingSigns -> sumOfPointsForMissingSigns(missingSigns) }
            .sorted()
            .toList()
        return sortedSumOfPoints[sortedSumOfPoints.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10", "Day10_test")
    check(part1(testInput) == 26397)

    val input = readInput("day10", "Day10")
    println(part1(input))

    check(part2(testInput) == 288957L)

    println(part2(input))
}

private fun Char?.toPointsForIncorrect(): Int {
    return when (this) {
        ')' -> 3
        ']' -> 57
        '}' -> 1197
        '>' -> 25137
        else -> 0
    }
}

private fun Char.toPointsForMissing(): Int {
    return when (this) {
        ')' -> 1
        ']' -> 2
        '}' -> 3
        '>' -> 4
        else -> 0
    }
}
