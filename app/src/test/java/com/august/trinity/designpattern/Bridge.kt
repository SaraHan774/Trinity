package com.august.trinity.designpattern

import org.junit.Test


class Bridge {
    @Test
    fun test() {
        val stormTrooper = StormTrooper2(Rifle(), RegularLegs())
        val flameTrooper = StormTrooper2(Flamethrower(), RegularLegs())
        val scoutTrooper = StormTrooper2(Rifle(), AthleticLegs())
    }
}

interface Trooper {
    fun move(x: Long, y: Long)
    fun attackRebel(x: Long, y: Long)
}

open class StormTrooper : Trooper {
    override fun move(x: Long, y: Long) {
        TODO("Not yet implemented")
    }

    override fun attackRebel(x: Long, y: Long) {
        TODO("Not yet implemented")
    }
}

open class ShockTrooper : Trooper {
    override fun move(x: Long, y: Long) {
        TODO("Not yet implemented")
    }

    override fun attackRebel(x: Long, y: Long) {
        TODO("Not yet implemented")
    }
}

class RiotControlTrooper : StormTrooper() {
    override fun move(x: Long, y: Long) {
        super.move(x, y)
    }

    override fun attackRebel(x: Long, y: Long) {
        super.attackRebel(x, y)
    }
}

class FlameTrooper : ShockTrooper() {
    override fun move(x: Long, y: Long) {
        super.move(x, y)
    }

    override fun attackRebel(x: Long, y: Long) {
        super.attackRebel(x, y)
    }
}

class ScoutTrooper : ShockTrooper() {
    override fun move(x: Long, y: Long) {
        super.move(x, y)
    }
}

interface Infantry {
    fun move(x: Long, y: Long)
    fun attackRebel(x: Long, y: Long)
    fun shout(): String // interface 변경사항 추가 -> 구현하는 모든 클래스들을 수정해야 함
}


///////////////////////////////////////////////////////////////////////////////
data class StormTrooper2(
    private val weapon: Weapon,
    private val legs: Legs
) : Trooper {

    override fun move(x: Long, y: Long) {
        legs.move(x, y)
    }

    override fun attackRebel(x: Long, y: Long) {
        weapon.attack(x, y)
    }
}

typealias PointsOfDamage = Long
typealias Meters = Int

interface Weapon {
    fun attack(x: Long, y: Long): PointsOfDamage
}

interface Legs {
    fun move(x: Long, y: Long) : Meters
}


const val RIFLE_DAMAGE = 3L
const val REGULAR_SPEED: Meters = 1

class Rifle: Weapon {
    override fun attack(x: Long, y: Long): PointsOfDamage {
        return RIFLE_DAMAGE
    }
}

class Flamethrower: Weapon {
    override fun attack(x: Long, y: Long): PointsOfDamage {
        return RIFLE_DAMAGE * 2
    }
}

class Batton: Weapon {
    override fun attack(x: Long, y: Long): PointsOfDamage {
        return RIFLE_DAMAGE * 3
    }
}

class RegularLegs: Legs {
    override fun move(x: Long, y: Long): Meters {
        return REGULAR_SPEED
    }
}

class AthleticLegs: Legs {
    override fun move(x: Long, y: Long): Meters {
        return REGULAR_SPEED * 2
    }
}