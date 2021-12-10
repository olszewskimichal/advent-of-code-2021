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

    fun findIncompleteLineAndFill(line: String): List<Int> {
        val stack = mutableListOf<Char>()
        val result = mutableListOf<Int>()
        for (sign in line.toCharArray()) {
            when (sign) {
                '(', '[', '{', '<' -> {
                    stack.push(sign)
                }
                else -> {
                    val closeCharacter = stack.pop()

                    when (sign) {
                        ')' -> if (closeCharacter != '(') return emptyList()
                        ']' -> if (closeCharacter != '[') return emptyList()
                        '}' -> if (closeCharacter != '{') return emptyList()
                        '>' -> if (closeCharacter != '<') return emptyList()
                    }

                }
            }
        }
        stack.forEach {
            when (it) {
                '(' -> result.add(1)
                '{' -> result.add(3)
                '[' -> result.add(2)
                '<' -> result.add(4)
            }
        }
        return result.reversed()
    }

    fun part1(input: List<String>): Int {
        return input.map { line ->
            val findIncorrectSign = findIncorrectSign(line)
            findIncorrectSign.let {
                when (it) {
                    ')' -> 3
                    ']' -> 57
                    '}' -> 1197
                    '>' -> 25137
                    else -> 0
                }
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val sorted = input.map { line ->
            val result = findIncompleteLineAndFill(line)
            var sum = 0L
            result.forEach {
                sum *= 5
                sum += it
            }
            sum
        }.filter { it != 0L }
            .sorted()
        return sorted[sorted.size / 2]
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day10", "Day10_test")
    check(part1(testInput) == 26397)

    val input = readInput("day10", "Day10")
    println(part1(input))

    check(part2(testInput) == 288957L)

    println(part2(input))
}
