package com.august.trinity.state

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


private val myViewModelScope = CoroutineScope(
    Dispatchers.Default +
            SupervisorJob() +
            CoroutineExceptionHandler { _, throwable ->
                Log.d("=== error", "throwable = ${throwable.localizedMessage}")
            }
)

/**
 * https://medium.com/@theAndroidDeveloper/yet-another-pitfall-in-jetpack-compose-you-must-be-aware-of-225a1d07d033
 */
class TestViewModel(
    viewModelScope: CoroutineScope = myViewModelScope
) : ViewModel(viewModelScope) {

    var currentCounterValue by mutableIntStateOf(0)
        private set

    private val stateFlow = MutableStateFlow(
        TestUIM(
            isHello = false,
            list = ImmutableList.of(
                TestData("one", "two"),
                TestData("three", "four"),
                TestData("five", "six")
            )
        )
    )
    private val flow = stateFlow.map { it.list }

    init {
        viewModelScope.launch {
            flow.collect {
                Log.d("===", "collected $it")
            }
        }
    }

    fun sortList() {
        val list = stateFlow.value.list
        val sortedList = list.sortedBy { it.one }
        stateFlow.value = stateFlow.value.copy(list = ImmutableList.copyOf(sortedList))
    }

    fun incrementCounter() {
        currentCounterValue++
    }


    private val defaultCoroutineErrorHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("===", "error!!!! ${throwable.localizedMessage}")
    }

    val viewModelDefaultScope by lazy { viewModelScope + Dispatchers.Default + defaultCoroutineErrorHandler }

    suspend fun testAsync() {
        try {
            coroutineScope {
                val a = async {
                    delay(2000)
                    println("=== starting one")
                    1
                }

                val b = async {
                    delay(3000)
                    println("=== starting two")
                    2
                }

                val c = async {
                    delay(1000)
                    throw Exception("exception thrown on 1s delay")
                    3
                }
                sum(a.await(), b.await(), c.await())
            }
        } catch (e: Exception) {
            Log.d("===", "error!!!! ${e.localizedMessage}")
        }
    }

    private fun sum(a: Int, b: Int, c: Int) {
        println("=== total ${a + b + c}")
    }
}

data class TestUIM(
    val isHello: Boolean,
    val list: ImmutableList<TestData>
)

class TestData {
    val one: String
    val two: String
    val hello: String
    private val mutableStateFlow = MutableStateFlow("")
    val stateFlow = mutableStateFlow.asStateFlow()

    constructor(one: String, two: String) {
        this.one = one
        this.two = two
        this.hello = "hello $one $two"
    }

    fun updateFlow(s: String) {
        mutableStateFlow.update { s }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TestData

        if (one != other.one) return false
        if (two != other.two) return false
        println("=== equals")
        return true
    }

    override fun hashCode(): Int {
        var result = one.hashCode()
        result = 31 * result + two.hashCode()
        return result
    }
}