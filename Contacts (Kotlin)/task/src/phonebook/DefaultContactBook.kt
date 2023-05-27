package models.phonebook

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import models.Contact
import models.OrganizationContact
import models.PersonContact
import java.io.File

interface ContactBook {
    val size: Int

    fun search(query: String): List<Contact>
    fun getByIndex(index: Int): Contact?
    fun printContactList()
    fun printContactInfo(index: Int)
    fun addContact()
    fun printCount()
    fun remove(contact: Contact)
    fun editContact(contact: Contact)
    fun saveContactsToFile()
    fun loadContactsFromFile()
}

class DefaultContactBook(private val file: File?) : ContactBook {
    private val contacts = mutableListOf<Contact>()
    override val size: Int get() = contacts.size

    override fun search(query: String): List<Contact> = contacts.filter { it.matchesQuery(query) }

    override fun getByIndex(index: Int) = if (index in 0 until size) contacts[index] else null

    override fun printContactList() {
        contacts.forEachIndexed { index, contact -> println("${index + 1} ${contact.getDisplayName()}") }
    }

    override fun printContactInfo(index: Int) {
        val contact = getByIndex(index)
        if (contact != null) {
            println(contact)
        } else {
            println("There is no contact with that index!")
        }
    }

    override fun addContact() {
        println("Enter the type (person, organization):")
        val contact = when (readln().lowercase()) {
            "person" -> PersonContact.createInstance()
            "organization" -> OrganizationContact.createInstance()
            else -> return println("Invalid contact type")
        }
        contacts.add(contact)
        println("The record added!")
    }

    override fun printCount() = println("The Phone Book has $size records.")

    override fun remove(contact: Contact) {
        val isRemoved = contacts.remove(contact)
        if (!isRemoved) println("There is no such contact to remove!")
    }

    override fun editContact(contact: Contact) {
        contact.changeProperty()
        println("Saved")
    }

    fun listContactsAction() {
        printContactList()
        println("\n[list] Enter action ([number], back):")
        val action = readln().lowercase()
        if (action == "back") return

        val index = action.toIntOrNull()?.minus(1)
        val contact = getByIndex(index ?: -1)
        if (contact != null) {
            printContactInfo(contact)
            recordAction(contact)
        } else {
            println("Invalid contact number!")
        }
    }

    private fun printContactInfo(contact: Contact) {
        println(contact.toString())
    }

    private fun recordAction(contact: Contact) {
        while (true) {
            println("\n[record] Enter action (edit, delete, menu):")
            when (readln().lowercase()) {
                "edit" -> editContact(contact)
                "delete" -> remove(contact)
                "menu" -> return
                else -> println("Invalid action!")
            }
            saveContactsToFile()
            printContactInfo(contact)
        }
    }

    fun searchContactsAction() {
        if (size == 0) {
            println("No records to search!")
            return
        }

        val query = getSearchQuery()
        if (query.isBlank()) {
            println("Invalid search query!")
            return
        }

        val matchingContacts = search(query)
        if (matchingContacts.isEmpty()) {
            println("No matching contacts found!")
            return
        }

        displaySearchResults(matchingContacts)
        handleSearchAction(matchingContacts)
    }

    private fun getSearchQuery(): String {
        println("Enter search query:")
        return readln()
    }

    private fun displaySearchResults(contacts: List<Contact>) {
        println("Found ${contacts.size} results:")
        contacts.forEachIndexed { index, contact ->
            println("${index + 1}. ${contact.getDisplayName()}")
        }
    }

    private fun handleSearchAction(contacts: List<Contact>) {
        while (true) {
            println("\n[search] Enter action ([number], back, again]):")
            when (val input = readln().lowercase()) {
                "again" -> searchContactsAction()
                "back" -> return
                else -> {
                    val contactIndex = input.toIntOrNull()?.minus(1)
                    if (contactIndex != null && contactIndex in contacts.indices) {
                        val contact = contacts[contactIndex]
                        println("$contact\n")
                        recordAction(contact)
                        break
                    } else {
                        println("Invalid action!")
                    }
                }
            }
        }
    }

    private val json = Json { prettyPrint = true }

    override fun saveContactsToFile() {
        if (file == null) return
        if (!file.exists()) file.createNewFile()
        file.writeText(json.encodeToString(contacts))
    }

    override fun loadContactsFromFile() {
        if (file == null || !file.exists()) return
        val text = file.readText()
        contacts.clear()
        contacts.addAll(Json.decodeFromString<List<Contact>>(text))
    }

    private object ContactType {
        const val PERSON = "person"
        const val ORGANIZATION = "organization"
    }
}
