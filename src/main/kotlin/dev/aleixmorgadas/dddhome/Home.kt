package dev.aleixmorgadas.dddhome

class Home(private val leaseholder: Person) {
    private var people = setOf<Person>()

    fun peopleInside(): Int = people.size

    fun letPeopleIn(peopleComingIn: Set<Person>) {
        if (leaseholder !in people && leaseholder !in peopleComingIn) {
            throw IllegalStateException("Leaseholder isn't at home to open")
        }
        people = people + peopleComingIn
    }
}
