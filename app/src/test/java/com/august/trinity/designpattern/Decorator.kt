package com.august.trinity.designpattern

import org.junit.Assert.assertThrows
import org.junit.Test

open class StarTrekRepository {
    private val starshipCaptains = mutableMapOf("USS Enterprise" to "John T. Harrison")

    open fun getCaptain(starshipName: String): String {
        return starshipCaptains[starshipName] ?: "Unknown"
    }

    open fun addCaptain(starshipName: String, captainName: String) {
        starshipCaptains[starshipName] = captainName
    }
}

// StarTrekRepository 를 수정하지 않고 로그를 남기는 기능을 추가하고 싶다
// 객체에 새로운 동작을 동적으로 추가

/*interface IStarTrekRepository {
    fun getCaptain(starshipName: String): String
    fun addCaptain(starshipName: String, captainName: String)
}*/

// operator overloading
interface IStarTrekRepository {
    operator fun get(starshipName: String): String
    operator fun set(starshipName: String, captainName: String)
}

class DefaultStarTrekRepository : IStarTrekRepository {
    private val starshipCaptains = mutableMapOf("USS Enterprise" to "Captain Hello")

    override fun get(starshipName: String): String {
        return starshipCaptains[starshipName] ?: "Unknown"
    }

    override fun set(starshipName: String, captainName: String) {
        starshipCaptains[starshipName] = captainName
    }
}

// 인터페이스의 구현을 다른 객체에 위임
class LoggingGetCaptain(private val repository: IStarTrekRepository) :
    IStarTrekRepository by repository {

    //데코레이터 패턴
    // 1. 데코레이션 (새로운 동작) 을 추가할 대상 객체를 입력으로 받는다.
    // 2. 대상 객체에 대한 참조를 계속 유지한다.
    // 3. 데코레이터 클래스의 메서드가 호출되면 들고있는 대상 객체의 처리를 변경할지 처리를 위임할지 결정한다.
    // 4. 대상 객체에서 인터페이스를 추출하거나 또는 해당 클래스가 이미 구현하고 있는 인터페이스를 사용한다.
    override fun get(starshipName: String): String {
        println("Getting captain for $starshipName")
        return repository[starshipName] // get operator
    }
}

class ValidatingAddCaptain(private val repository: IStarTrekRepository) : IStarTrekRepository by repository {
    private val maxNameLength = 7

    override fun set(starshipName: String, captainName: String) {
        require(captainName.length <= maxNameLength) {
            "Captain name must be less than $maxNameLength characters"
        }
        repository[starshipName] = captainName // set operator
    }
}

class Decorator {

    @Test
    fun test() {
        val repo = DefaultStarTrekRepository()
        val name: String = repo["USS Enterprise"]
        assert(name == "Captain Hello")

        val unknownName = repo["USS Voyager"]
        assert(unknownName == "Unknown")

        val logger = LoggingGetCaptain(DefaultStarTrekRepository())
        logger["USS Enterprise"] = "Captain Hello New"
        println(logger["USS Enterprise"])

        val validator = ValidatingAddCaptain(DefaultStarTrekRepository())
        assertThrows(IllegalArgumentException::class.java) {
            validator["USS Enterprise"] = "Captain Hello New"
        }
    }
}