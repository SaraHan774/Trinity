package com.august.trinity.designpattern

import org.junit.Test
import java.util.stream.Stream

class AdapterTest{
    @Test
    fun test() {
        cellPhone(
            charger(
                krPowerOutlet().toPlugTypeA()
            ).toUsbTypeC()
        )

        // printStream(listOf(1, 2, 3))
        printStream(listOf(1, 2, 3).stream())

        val stream = Stream.generate { 42 }
        // stream.toList() runtime exception
    }
}

interface PlugTypeF {
    val hasPower: Int
}

interface PlugTypeA {
    val hasPower: String //"true" or "false"
}

interface UsbMini {
    val hasPower: Power
}

enum class Power {
    TRUE, FALSE
}

interface UsbTypeC {
    val hasPower: Boolean
}

fun cellPhone(chargeCable: UsbTypeC) {
    if (chargeCable.hasPower) {
        println("charging power ...")
    } else {
        println("power not attached")
    }
}

fun krPowerOutlet() : PlugTypeF {
    return object : PlugTypeF {
        override val hasPower: Int
            get() = 1
    }
}

// 휴대전화 충전기는 A 를 입력받아서 UsbMini 를 반환한다
fun charger(plug: PlugTypeA) : UsbMini {
    return object : UsbMini {
        override val hasPower: Power
            get() = Power.valueOf(plug.hasPower)
    }
}

fun PlugTypeF.toPlugTypeA(): PlugTypeA {
    val hasPower = if (this.hasPower == 1) "true" else "false"
    return object : PlugTypeA {
        override val hasPower: String = hasPower
    }
}

fun UsbMini.toUsbTypeC(): UsbTypeC {
    val hasPower = this.hasPower == Power.TRUE
    return object : UsbTypeC {
        override val hasPower: Boolean = hasPower
    }
}

fun printStream(stream: Stream<Int>) {
    stream.forEach {
        println(it)
    }
}

