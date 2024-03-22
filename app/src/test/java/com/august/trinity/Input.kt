package com.august.trinity

import com.august.trinity.state.TestData
import com.google.common.collect.ImmutableList
import junit.framework.TestCase.assertFalse
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test

class Input {

    @Test
    fun test() = runTest {
        val flow = flowOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val start = System.currentTimeMillis()
        flow
            .debounce(200)
            .collect {
            println("collected $it ${System.currentTimeMillis() - start}")
        }
    }

    @Test
    fun test2() = runTest {
        coroutineScope {
            val start = System.currentTimeMillis()
            val a = async {
                delay(1000)
                1
            }

            val b = async {
                delay(2000)
                2
            }

            val c = async {
                delay(3000)
                3
            }
            hello(a.await(), b.await(), c.await())
            println("finish ${System.currentTimeMillis() - start}")
        }
    }

    fun hello(a: Int, b: Int, c: Int) {
        println("total ${a + b + c}")
    }

    @Test
    fun test3(){
        val list = ImmutableList.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val list2 = list.map { it * 2 }
        val list3 = list
        val list4 = ImmutableList.of(1, 2, 3, 4, 5, 6, 7, 9, 9, 10)

        assert(list != list2)
        assert(list == list3)
        assert(list != list4)


        val one = SomeClass()
        val two = SomeClass()
        val three = SomeClass()

        val someList = listOf(one, two, three)
        val someList2 = someList.map { it.someValue = 1 }
        assert(someList != someList2)
    }

    @Test
    fun test4() {
        val testData1 = TestData("one", "two")
        val testData2 = TestData("one", "two")
        val testData3 = TestData("one", "two")

        val list1 = listOf(testData1, testData2, testData3).toImmutableList()
        val list2 = listOf(testData1, testData2, testData3).toImmutableList()

        assert(list1.containsAll(list2))
        assert(list1 == list2)
        println(list1)
        println(list2)
    }

    class SomeClass {
        var someValue: Int = 0
    }
}