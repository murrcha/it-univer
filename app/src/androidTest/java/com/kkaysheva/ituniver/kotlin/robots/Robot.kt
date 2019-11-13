package com.kkaysheva.ituniver.kotlin.robots

open class Robot {

    fun action(func: Robot.() -> Unit) = apply { func() }

    fun check(func: Robot.() -> Unit) = apply { func() }
}
