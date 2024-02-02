package com.august.trinity

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.selects.whileSelect
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SelectTest {

    @OptIn(ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class,
        DelicateCoroutinesApi::class
    )
    @Test
    fun testWhileSelect() {
        val values = mutableListOf<Int>()
        val ticker = ticker(10000)
        try {
            runTest {
                whileSelect {
                    ticker.onReceive {
                        false
                    }

                    listOf(1, 2, 3, 4).forEach {
                        values.add(it)
                        true // continue whileSelect loop
                    }
                }
            }
        } finally {
            ticker.cancel()
        }
        assertTrue(values.size == 4)
        assertTrue(ticker.isClosedForReceive)
    }
}