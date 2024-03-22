package com.august.trinity.designpattern.ch7

import com.august.trinity.designpattern.ch5.forEachPrint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.system.measureTimeMillis

class DataStream {


    private fun sum100(): Int {
        return (1..100).reduce { acc, i ->
            acc + i
        }
    }

    private fun sum100For(): Int {
        var sum = 0
        for (i in 1..100) {
            sum += i
        }
        return sum
    }

    @Test
    fun test() {
        assert(sum100() == sum100For())
    }

    private val listOfLists = listOf(
        listOf(1, 2, 3),
        listOf(3, 4, 5),
        listOf(6, 7, 8)
    )

    private fun flatten(): Int {
        val list = mutableListOf<Int>()
        for (l in listOfLists) {
            list.addAll(l)
        }
        return list.size
    }

    private fun flatten2(): Int {
        return listOfLists.flatten().size
    }

    @Test
    fun test2() {
        assert(flatten() == flatten2())
    }

    private val sequence = generateSequence(1L) {
        it + 1
    }

    private val sequence2 = (1..100).asSequence()

    private val numbers = (1..100_000).toList()

    @Test
    fun test3() {
        println(
            measureTimeMillis {
                numbers.map { it * it }.take(1).forEach { it }
            }
        )

        println(
            measureTimeMillis {
                // sequence 가 더 빠르다
                numbers.asSequence().map {
                    it * it
                }.take(1).forEach { it }
            }
        )
    }

    private val channel = Channel<Int>()

    @Test
    fun test4() = runTest {
        // 순차 프로세스 통신
        // 채널을 사용해서 코루틴 간 통신 가능
        // 코틀린에는 wait, notify 등이 없다
        launch {
            for (c in channel) {
                println(c)
            }
        }

        (1..10).forEach {
            println("sending $it")
            channel.send(it)
        }
        channel.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test5() = runTest {
        // producer coroutine
        val channel = produce {
            (1..10).forEach {
                send(it)
            }
        }

        launch {
            for (i in channel) {
                println(i)
            }
        }
    }

    @Test
    fun test6() = runTest {
        val actor = actor<Int> {
            this.channel.consumeEach {
                println(it)
            }
        }

        (1..10).forEach {
            actor.send(it)
        }
        actor.close()
    }

    @Test
    fun test7() = runTest {
        // buffered channel
        val actor = actor<Long>(capacity = 10) {
            var prev = 0L
            this.channel.consumeEach {
                println(it - prev)
                prev = it
                delay(100)
            }
        }

        repeat(10) {
            println("sending ... ")
            actor.send(System.currentTimeMillis())
        }
        actor.close().also {
            println("전송 완료")
        }
    }
}


fun main() = runBlocking {
    val stock = flow {
        var i = 0
        while (true) {
            emit(++i)
            print(". ")
            delay(100)
        }
    }

    var seconds = 0
    // Requests a conflated channel in the Channel(...) factory function. This is a shortcut to creating a channel with onBufferOverflow = DROP_OLDEST.
    // conflated() 함수를 통해서 버퍼를 손쉽게 구현할 수 있음
    stock.buffer(capacity = 10, onBufferOverflow = BufferOverflow.DROP_OLDEST).collect {
        println("*")
        delay(1000)
        seconds++
        println("$seconds 초 -> $it 수신")
    }
//    stock.conflate().collect {
//        delay(1000)
//        seconds++
//        println("$seconds 초 -> $it 수신")
//    }
}