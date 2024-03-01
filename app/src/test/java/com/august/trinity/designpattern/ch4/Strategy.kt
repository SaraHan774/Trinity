package com.august.trinity.designpattern.ch4

import org.junit.Test

enum class Direction {
    LEFT, RIGHT
}

data class Projectile(
    private var x: Int,
    private var y: Int,
    private var direction: Direction
)

class OurHero {
    private var direction = Direction.LEFT
    private var x: Int = 42
    private var y: Int = 173
    private var weapon: Weapon2 = Peashooter()

    fun shoot(): Projectile {
        return weapon.shoot(x, y, direction)
    }

    fun equip(weapon: Weapon2) {
        this.weapon = weapon
    }
}

interface Weapon2 {
    fun shoot(
        x: Int,
        y: Int,
        direction: Direction
    ): Projectile
}

/// Strategy pattern
object Weapons {
    fun peashooter(x: Int, y: Int, direction: Direction): Projectile {
        return Projectile(x, y, direction)
    }

    fun banana(x: Int, y: Int, direction: Direction): Projectile {
        return Projectile(x, y, direction)
    }
}

class OurHero2 {
    private var direction = Direction.LEFT
    private var x: Int = 42
    private var y: Int = 173
    var currentWeapon = Weapons::peashooter

    val shoot = fun() {
        currentWeapon(x, y, direction)
    }
}

class Peashooter: Weapon2 {
    override fun shoot(x: Int, y: Int, direction: Direction): Projectile {
        return Projectile(x, y, direction)
    }
}

class Banana: Weapon2 {
    override fun shoot(x: Int, y: Int, direction: Direction): Projectile {
        return Projectile(x, y, direction)
    }
}

class Strategy {
    @Test
    fun test() {
        val ourHero = OurHero2()
        ourHero.shoot()
        ourHero.currentWeapon = Weapons::banana
        ourHero.shoot()
    }
}