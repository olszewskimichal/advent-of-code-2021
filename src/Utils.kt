import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(day: String) = File("src/pl/michal/olszewski/aoc/$day/", "$day.txt").readLines()

fun readTestInput(day: String) = File("src/pl/michal/olszewski/aoc/$day/", "${day}_test.txt").readLines()
