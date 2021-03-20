package dev.aleixmorgadas.dddhome

import java.time.LocalDate

class Home(private val leaseholder: Person, private val lessor: Person, private val today: LocalDate = LocalDate.now()) {
    private var people = setOf<Person>()

    init {
        if (isPayDay()) {
            leaseholder.makeTransferTo(lessor, rent())
        }
    }

    fun peopleInside(): Int = people.size

    fun letPeopleIn(peopleComingIn: Set<Person>) {
        if (leaseholder !in people && leaseholder !in peopleComingIn) {
            throw IllegalStateException("Leaseholder isn't at home to open")
        }
        people = people + peopleComingIn
    }

    fun rent(): Float = 500.0F

    fun isPayDay(): Boolean {
        return today.dayOfMonth in 1..5
    }
}
