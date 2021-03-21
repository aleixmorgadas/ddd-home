package dev.aleixmorgadas.dddhome

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.time.LocalDate.of

class HomeTest {
    val leaseholder = Person(name = "Aleix")
    val lessor = Person(name = "Monica")

    @Test
    @DisplayName("Home knows about how many people are inside and the leaseholder")
    fun bootstrappingOurHome() {
        val home = Home(leaseholder = leaseholder, lessor = lessor)

        assertEquals(0, home.peopleInside())
    }


    @Test
    @DisplayName("Home allows always the Leaseholder to come in")
    fun alwaysAllowTheLeaseholderToComeIn() {
        val home = Home(leaseholder = leaseholder, lessor = lessor)
        home.letPeopleIn(setOf(leaseholder))

        assertEquals(1, home.peopleInside())
    }

    @Test
    @DisplayName("Home allows people to come in as long there is the Leaseholder inside")
    fun denyPeopleToComeInWhenTheLeaseholderIsNotAtHome() {
        val home = Home(leaseholder = leaseholder, lessor = lessor)

        val john = Person(name = "John")

        assertThrows(IllegalStateException::class.java) {
            home.letPeopleIn(setOf(john))
        }
    }

    @Test
    @DisplayName("Home allows people to come in when the Leaseholder is already inside")
    fun allowsPeopleToComeInWhenLeaseholderIsAlreadyInside() {
        val home = Home(leaseholder = leaseholder, lessor = lessor)
        home.letPeopleIn(setOf(leaseholder))

        val john = Person(name = "John")
        home.letPeopleIn(setOf(john))

        assertEquals(2, home.peopleInside())
    }

    @Test
    @DisplayName("Home allows people to come in when the Leaseholder is inside")
    fun allowsPeopleToComeInWhenTheLeaseholderIsJoiningWithThem() {
        val home = Home(leaseholder = leaseholder, lessor = lessor)

        val john = Person(name = "John")
        home.letPeopleIn(setOf(john, leaseholder))

        assertEquals(2, home.peopleInside())
    }

    @Nested
    inner class Rent {

        @Test
        @DisplayName("Home has a fix Rent")
        fun homeKnowsTheRent() {
            val home = Home(leaseholder = leaseholder, lessor = lessor)

            assertEquals(500.0F, home.rent())
        }

        @Test
        @DisplayName("is paying day if it's between 1st and 5th of each month")
        fun isItPayDayWhenItIsBetween1stAnd5th() {
            for (day in 1..5) {
                val home = Home(leaseholder = leaseholder, lessor = lessor, today = of(2021, 1, day))

                assertTrue(home.isPayDay(), "Day $day is a Pay Day")
            }
        }

        @Test
        @DisplayName("is not paying day if it's not between 1st and 5th of each month")
        fun isNotItPayDayWhenItIsNotBetween1stAnd5th() {
            for (day in 6..31) {
                val home = Home(leaseholder = leaseholder, lessor = lessor, today = of(2021, 1, day))

                assertFalse(home.isPayDay(), "Day $day is not a Pay Day")
            }
        }

        @Test
        @DisplayName("when is pay day, home asks the Leaseholder to make the money transfer to Lessor")
        fun whenIsPayDayHomeAsksTheLeaseholderToMakeAMoneyTransferToLessor() {
            val leaseholderSpy = LeaseholderMock(name = "Aleix")
            val homeOnPayDay = Home(leaseholder = leaseholderSpy, lessor = lessor, today = of(2021, 1, 3))

            assertTrue(leaseholderSpy.makeTransferToBeenCalled)
            assertEquals(lessor, leaseholderSpy.makeTransferToBeenCalledWithReceiver)
            assertEquals(500.0F, leaseholderSpy.makeTransferToBeenCalledWithAmount)
        }
    }

    @Nested
    inner class Bills {

        @Test
        @DisplayName("does it have pending bills")
        fun doesItHavePendingBills() {
            val home = Home(leaseholder = leaseholder, lessor = lessor)

            assertFalse(home.pendingBills())
        }

        @Test
        @DisplayName("when a provider emits a bill, then there are pending bills")
        fun whenAProviderEmitsABillThenThereArePendingBills() {
            val home = Home(leaseholder = leaseholder, lessor = lessor)
            val electricitySupplier = object: BankAccount {}

            home.billEmitted(supplier = electricitySupplier, amount = 50.0F)

            assertTrue(home.pendingBills())
        }

        @Test
        @DisplayName("when there are pending bills, the leaseholder can pay them")
        fun whenThereArePendingBillsTheLeaseholderCanPayThem() {
            val leaseholderSpy = LeaseholderMock(name = "Aleix")
            val homeWithBills = Home(leaseholder = leaseholderSpy, lessor = lessor)
            val electricitySupplier = object: BankAccount {}
            homeWithBills.billEmitted(supplier = electricitySupplier, amount = 50.0F)

            homeWithBills.payBills()

            assertTrue(leaseholderSpy.makeTransferToBeenCalled)
            assertEquals(electricitySupplier, leaseholderSpy.makeTransferToBeenCalledWithReceiver)
            assertEquals(50.0F, leaseholderSpy.makeTransferToBeenCalledWithAmount)
        }
    }
}

class LeaseholderMock(name: String) : Person(name = name) {
    var makeTransferToBeenCalled = false
    var makeTransferToBeenCalledWithReceiver: BankAccount? = null
    var makeTransferToBeenCalledWithAmount: Float? = null

    override fun makeTransferTo(receiver: BankAccount, amount: Float) {
        makeTransferToBeenCalled = true
        makeTransferToBeenCalledWithReceiver = receiver
        makeTransferToBeenCalledWithAmount = amount
    }
}