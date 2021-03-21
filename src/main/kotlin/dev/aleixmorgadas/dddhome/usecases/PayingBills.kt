package dev.aleixmorgadas.dddhome.usecases

import dev.aleixmorgadas.dddhome.Home
import dev.aleixmorgadas.dddhome.Person

val leaseholder = Person(name = "Aleix")
val lessor = Person(name = "Monica")

fun main() {
    val home = Home(leaseholder = leaseholder, lessor = lessor)

    println()
}