package contacts

import contacts.phonebook.PhoneBook
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

    private fun doAction(action: Action) = when (action) {
        Action.ADD -> {
            phoneBook.addContact()
            phoneBook.saveContactsToFile()
        }

        Action.REMOVE -> {
            phoneBook.removeContact()
            phoneBook.saveContactsToFile()
        }

        Action.EDIT -> {
            phoneBook.editContact()
            phoneBook.saveContactsToFile()
        }

        Action.COUNT -> phoneBook.printCount()
        Action.INFO -> phoneBook.printContactInfo()
        Action.EXIT -> exitProcess(0)
    }
}
