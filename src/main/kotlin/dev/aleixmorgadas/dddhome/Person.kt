package dev.aleixmorgadas.dddhome

open class Person(val name: String): BankAccount {
    open fun makeTransferTo(receiver: BankAccount, amount: Float) {

    }
}
