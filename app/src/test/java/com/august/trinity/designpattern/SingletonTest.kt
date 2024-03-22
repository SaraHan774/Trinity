package com.august.trinity.designpattern

import org.junit.Test

class SingletonTest {
    @Test
    fun singletonTest() {
        val list: List<String> = listOf()
        val list2: List<String> = listOf()
        assert(list == list2)
        assert(list === list2)
    }

    @Test
    fun propTest() {
        val portProperty = property("port: 8080")
        val environmentProperty = property("environment: production")
        val port = portProperty.value// value = Any

        val portProp2 = property2("port: 8080")
        val port2 = portProp2.value

        // is - smart casting
        if (port2 is IntProperty) {
            val port2 : Int = port2.value
        }
    }
}


object NoMoviesList : List<String> by emptyList() // 구현을 empty list 에 위임할수도

// abstract factory - 프레임워크나 라이브러리가 파일에서 구성 설정을 읽어들일때 자주 사용
interface Property {
    val name: String
    val value: Any // non-null object
}

interface ServerConfiguration {
    val properties: List<Property>
}

data class PropertyImpl(override val name: String, override val value: Any) : Property

data class  ServerConfigurationImpl(override val properties: List<Property>) : ServerConfiguration

fun property(prop: String): Property {
    val (name, value) = prop.split(":")
    return when (name) {
        "port" -> PropertyImpl(name.trim(), value.trim().toInt())
        "environment" -> PropertyImpl(name.trim(), value.trim())
        else -> throw IllegalArgumentException("Unknown property $name")
    }
}

data class IntProperty(override val name: String, override val value: Int) : Property

data class StringProperty(override val name: String, override val value: String) : Property

fun property2(prop: String): Property {
    val (name, value) = prop.split(":")
    return when (name) {
        "port" -> IntProperty(name.trim(), value.trim().toInt())
        "environment" -> StringProperty(name.trim(), value.trim())
        else -> throw IllegalArgumentException("Unknown property $name")
    }
}

fun server(propertyStrings: List<String>): ServerConfiguration {
    val parsed = mutableListOf<Property>()
    for (p in propertyStrings) {
        parsed += property2(p)
    }
    return ServerConfigurationImpl(parsed)
}