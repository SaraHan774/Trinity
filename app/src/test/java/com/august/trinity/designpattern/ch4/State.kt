package com.august.trinity.designpattern.ch4

interface WhatCanHappen {
    fun seeHero()
    fun getHit(pointsOfDamage: Int)
    fun calmAgain()
}

class Snail : WhatCanHappen {
    private var mood: Mood = Mood.Still
    private var healthPoints = 10
    override fun seeHero() {
        mood = when (mood) {
            is Mood.Still -> Mood.Aggressive
            else -> mood
        }
    }

    override fun getHit(pointsOfDamage: Int) {
        healthPoints -= pointsOfDamage
        mood = when {
            (healthPoints <= 0) -> Mood.Dead
            mood is Mood.Aggressive -> Mood.Retreating
            else -> mood
        }
    }

    override fun calmAgain() {
        mood = Mood.Still
    }
}

sealed class Mood {
    object Still : Mood()
    object Aggressive : Mood()
    object Retreating : Mood()
    object Dead : Mood()
}

sealed class BigMood : WhatCanHappen {
    class Still(private val bigSnail: BigSnail) : BigMood() {
        override fun seeHero() {
            // bigSnail.mood = BigMood.Aggressive
        }

        override fun getHit(pointsOfDamage: Int) {
            TODO("Not yet implemented")
        }

        override fun calmAgain() {
            TODO("Not yet implemented")
        }

    }

}

class BigSnail {
    internal var mood: BigMood = BigMood.Still(this)
    private var healthPoints = 10
}