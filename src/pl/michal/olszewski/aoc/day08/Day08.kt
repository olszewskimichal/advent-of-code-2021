package pl.michal.olszewski.aoc.day08

import readInput
import readTestInput

fun main() {

    fun part1(input: List<String>): Int {
        val countingResponse = input.flatMap {
            it.substringAfter("|").split(" ").filter { value -> value.isNotEmpty() }
        }.groupingBy {
            it.length
        }.eachCount()
        return countingResponse.getValue(2) + countingResponse.getValue(3) + countingResponse.getValue(4) + countingResponse.getValue(7)
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val (segments, output) = line.split(" | ").map { it.split(" ") }

            val one = segments.single { it.length == 2 }
            val four = segments.single { it.length == 4 }
            val seven = segments.single { it.length == 3 }
            val eight = segments.single { it.length == 7 }
            val six = segments.single { it.length == 6 && it.count { char -> one.contains(char) } == 1 }
            val zero = segments.single { it.length == 6 && !it.toList().containsAll(six.toList()) && !it.toList().containsAll(four.toList()) }
            val nine = segments.single { it.length == 6 && !it.toList().containsAll(six.toList()) && !it.toList().containsAll(zero.toList()) }
            val topRight = segments.single { it.length == 7 }.filterNot { six.contains(it) }
            val five = segments.single { it.length == 5 && !it.contains(topRight) }
            val bottomLeft = segments.single { it.length == 7 }.filterNot { nine.contains(it) }
            val three = segments.single { it.length == 5 && !it.toList().containsAll(five.toList()) && !it.contains(bottomLeft) }
            val two = segments.single { it.length == 5 && !it.toList().containsAll(five.toList()) && !it.toList().containsAll(three.toList()) }

            val mapOfNumbers = mapOf(
                zero to "0",
                one to "1",
                two to "2",
                three to "3",
                four to "4",
                five to "5",
                six to "6",
                seven to "7",
                eight to "8",
                nine to "9"
            )

            var number = ""
            output.map {
                number += mapOfNumbers.entries.single { entry -> entry.key.toList().containsAll(it.toList()) && entry.key.length == it.length }.value
            }
            number.toInt()

        }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readTestInput("day08")
    check(part1(testInput) == 26)

    val input = readInput("day08")
    println(part1(input))

    check(part2(testInput) == 61229)

    println(part2(input))
}