package dev.aleixmorgadas.dddhome

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.IllegalStateException

class HomeTest {

    @Test
    @DisplayName("Home knows about how many people are inside and the leaseholder")
    fun bootstrappingOurHome() {
        val aleix = Person(name = "Aleix")
        val home = Home(leaseholder = aleix)

        assertEquals(0, home.peopleInside())
    }


    @Test
    @DisplayName("Home allows always the Leaseholder to come in")
    fun alwaysAllowTheLeaseholderToComeIn() {
        val aleix = Person(name = "Aleix")
        val home = Home(leaseholder = aleix)
        home.letPeopleIn(setOf(aleix))

        assertEquals(1, home.peopleInside())
    }

    @Test
    @DisplayName("Home allows people to come in as long there is the Leaseholder inside")
    fun denyPeopleToComeInWhenTheLeaseholderIsNotAtHome() {
        val aleix = Person(name = "Aleix")
        val home = Home(leaseholder = aleix)

        val john = Person(name = "John")

        assertThrows(IllegalStateException::class.java) {
            home.letPeopleIn(setOf(john))
        }
    }

    @Test
    @DisplayName("Home allows people to come in when the Leaseholder is already inside")
    fun allowsPeopleToComeInWhenLeaseholderIsAlreadyInside() {
        val aleix = Person(name = "Aleix")
        val home = Home(leaseholder = aleix)
        home.letPeopleIn(setOf(aleix))

        val john = Person(name = "John")
        home.letPeopleIn(setOf(john))

        assertEquals(2, home.peopleInside())
    }

    @Test
    @DisplayName("Home allows people to come in when the Leaseholder is inside")
    fun allowsPeopleToComeInWhenTheLeaseholderIsJoiningWithThem() {
        val aleix = Person(name = "Aleix")
        val home = Home(leaseholder = aleix)

        val john = Person(name = "John")
        home.letPeopleIn(setOf(john, aleix))

        assertEquals(2, home.peopleInside())
    }
}