package models

import models.phonebook.PhoneBook
import java.io.File
import kotlin.system.exitProcess

class ContactsApp(file: File?) {
    private val phoneBook: PhoneBook = PhoneBook(file)

    private enum class Action {
        ADD, REMOVE, EDIT, COUNT, INFO, EXIT;

        companion object {
            fun of(str: String): Action? {
                return if (str.uppercase() !in Action.values().map(Action::name)) null
                else Action.valueOf(str.uppercase())
            }
        }
    }

    init {
        phoneBook.loadContactsFromFile()
    }

    fun start() {
        var isFirstRun = true
        while (true) {
            if (!isFirstRun) println()
            println("Enter action (${Action.values().joinToString { it.name.lowercase() }}):")
            val action = Action.of(readln())
            if (action == null) {
                println("There is no such action!")
                continue
            }
            doAction(action)
            isFirstRun = false
        }
    }

    private fun doAction(action: Action) = with(phoneBook) {
        when (action) {
            Action.ADD -> {
                addContact()
                saveContactsToFile()
            }

            Action.REMOVE -> {
                removeContact()
                saveContactsToFile()
            }

            Action.EDIT -> {
                editContact()
                saveContactsToFile()
            }

            Action.COUNT -> printCount()
            Action.INFO -> printContactInfo()
            Action.EXIT -> exitProcess(0)
        }
    }
}

