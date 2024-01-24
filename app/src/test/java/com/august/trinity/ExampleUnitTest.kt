package com.august.trinity

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // Unconfined Dispatcher
    // tasks that it executes are not confined to any particular thread and for an event loop
    // it is different in that it skips delays, as all TestDispatchers do.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testEagerlyEnteringChildCoroutines() = runTest(UnconfinedTestDispatcher()) {
        // this : Test Scope
        var entered = false
        val deferred = CompletableDeferred<Unit>()
        var completed = false

        launch {
            entered = true
            deferred.await()
            completed = true
        }

        assertTrue(entered)
        assertFalse(completed)
        deferred.complete(Unit) // resume the coroutine
        assertTrue(completed)
    }

    //Using this TestDispatcher can greatly simplify writing tests where it's not important which
    // thread is used when and in which order the queued coroutines are executed. Another typical
    // use case for this dispatcher is launching child coroutines that are resumed immediately,
    // without going through a dispatch; this can be helpful for testing Channel and StateFlow usages.
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testUnconfinedDispatcher() = runTest {
        val values = mutableListOf<Int>()
        val stateFlow = MutableStateFlow(0)
        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            stateFlow.collect { value ->
                values.add(value)
            }
        }

        // 값을 넣는다
        stateFlow.value = 1
        stateFlow.value = 2
        stateFlow.value = 3

        job.cancel()
        // 잘 collect 되었는지 확인한다
        // UnconfinedDispatcher 사용 안하면 values 는 empty 이다
        assertEquals(listOf(0, 1, 2, 3), values)
    }
}