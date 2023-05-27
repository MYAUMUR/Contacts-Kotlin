package models

import models.phonebook.DefaultContactBook
import java.io.File
import kotlin.system.exitProcess

class ContactManager(file: File?) {
    private val contactBook: DefaultContactBook = DefaultContactBook(file)

    init {
        contactBook.loadContactsFromFile()
    }

    fun run() {
        var isFirstRun = true
        while (true) {
            if (!isFirstRun) println()
            println("[menu] Enter action (add, list, search, count, exit):")
            when (readln()) {
                "add" -> {
                    contactBook.addContact()
                    contactBook.saveContactsToFile()
                }

                "list" -> contactBook.listContactsAction()
                "search" -> contactBook.searchContactsAction()
                "count" -> contactBook.printCount()
                "exit" -> {
                    contactBook.saveContactsToFile()
                    exitProcess(0)
                }

                else -> println("Invalid action!")
            }
            isFirstRun = false
        }
    }
}
