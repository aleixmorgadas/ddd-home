package dev.aleixmorgadas.dddhome

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate.of

class HomeTest {
    val leaseholder = Person(name = "Aleix")

    @Test
    @DisplayName("Home knows about how many people are inside and the leaseholder")
    fun bootstrappingOurHome() {
        val home = Home(leaseholder = leaseholder)

        assertEquals(0, home.peopleInside())
    }


    @Test
    @DisplayName("Home allows always the Leaseholder to come in")
    fun alwaysAllowTheLeaseholderToComeIn() {
        val home = Home(leaseholder = leaseholder)
        home.letPeopleIn(setOf(leaseholder))

        assertEquals(1, home.peopleInside())
    }

    @Test
    @DisplayName("Home allows people to come in as long there is the Leaseholder inside")
    fun denyPeopleToComeInWhenTheLeaseholderIsNotAtHome() {
        val home = Home(leaseholder = leaseholder)

        val john = Person(name = "John")

        assertThrows(IllegalStateException::class.java) {
            home.letPeopleIn(setOf(john))
        }
    }

    @Test
    @DisplayName("Home allows people to come in when the Leaseholder is already inside")
    fun allowsPeopleToComeInWhenLeaseholderIsAlreadyInside() {
        val home = Home(leaseholder = leaseholder)
        home.letPeopleIn(setOf(leaseholder))

        val john = Person(name = "John")
        home.letPeopleIn(setOf(john))

        assertEquals(2, home.peopleInside())
    }

    @Test
    @DisplayName("Home allows people to come in when the Leaseholder is inside")
    fun allowsPeopleToComeInWhenTheLeaseholderIsJoiningWithThem() {
        val home = Home(leaseholder = leaseholder)

        val john = Person(name = "John")
        home.letPeopleIn(setOf(john, leaseholder))

        assertEquals(2, home.peopleInside())
    }

    @Nested
    inner class Rent {

        @Test
        @DisplayName("Home has a fix Rent")
        fun homeKnowsTheRent() {
            val home = Home(leaseholder = leaseholder)

            assertEquals(500, home.rent())
        }

        @Test
        @DisplayName("is paying day if it's between 1st and 5th of each month")
        fun isItPayDayWhenItIsBetween1stAnd5th() {
            for (day in 1..5) {
                val home = Home(leaseholder = leaseholder, today = of(2021, 1, day))

                assertTrue(home.isPayDay(), "Day $day is a Pay Day")
            }
        }

        @Test
        @DisplayName("is not paying day if it's not between 1st and 5th of each month")
        fun isNotItPayDayWhenItIsNotBetween1stAnd5th() {
            for (day in 6..31) {
                val home = Home(leaseholder = leaseholder, today = of(2021, 1, day))

                assertFalse(home.isPayDay(), "Day $day is not a Pay Day")
            }
        }
    }
}