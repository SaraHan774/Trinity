package com.august.trinity.designpattern.ch5

import org.junit.Test

fun mathInvoker(x: Int, y: Int, mathFunction: (Int, Int) -> Int) {
    println(mathFunction(x, y))
}

inline fun <T> Iterable<T>.forEachPrint(action: (T) -> Unit) {
}

enum class LogLevel {
    ERROR, WARNING, INFO
}

fun log(level: LogLevel, message: String) {
    println("level: $level, message : $message")
}

val errorLog = fun(message: String) {
    log(LogLevel.ERROR, message)
}

fun createLogger(level: LogLevel): (String) -> Unit {
    return { message ->
        log(level, message)
    }
}


fun summarizer(): (Set<Int>) -> Double {
    val resultsCache = mutableMapOf<Set<Int>, Double>()
    return { numbers ->
        resultsCache.computeIfAbsent(numbers, ::sum)
    }
}

fun sum(numbers: Set<Int>): Double {
    return numbers.sumOf {
        it.toDouble()
    }
}

class Math {
    @Test
    fun test() {
        mathInvoker(1, 2) { a, b ->
            a * b
        }

        mathInvoker(3, 4) { a, b ->
            a / b
        }

        val list = listOf("asdf", "asdfdsfsd")
        list.forEach(::println)

        createLogger(LogLevel.ERROR)("Hello World")

    }
}