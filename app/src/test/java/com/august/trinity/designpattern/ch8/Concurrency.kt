package com.august.trinity.designpattern.ch8

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis


fun main() = runBlocking {
    val time = measureTimeMillis {
        getEverything()
    }
    println(time)
}

suspend fun getEverything() = coroutineScope {
//    val hello = HelloTrinity(
//        one = getSomething().await(),
//        two = getSomethingBetter().await()
//    )
//
//    println(hello)
//    val one = getSomething().await()
//    val two = getSomethingBetter().await()
//    println(HelloTrinity(
//        one, two
//    ))
}

fun CoroutineScope.getSomething(): Deferred<String> = async {
    delay(300)
    "Hello"
}

fun CoroutineScope.getSomethingBetter(): Deferred<String> = async {
    delay(2000)
    "World"
}

data class HelloTrinity(
    val one: String,
    val two: String,
)