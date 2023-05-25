package models.phonebook

import models.Contact
import models.OrganizationContact
import models.PersonContact
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class PhoneBook(private val file: File?) {
    private val contacts = mutableListOf<Contact>()

    enum class ContactType {
        PERSON, ORGANIZATION;

        companion object {
            fun of(str: String): ContactType? {
                return if (str.uppercase() !in ContactType.values().map(ContactType::name)) null
                else valueOf(str.uppercase())
            }
        }
    }

    private fun printContactList() {
        contacts.forEachIndexed { index, contact ->
            when (contact) {
                is PersonContact -> println("${index + 1}. ${contact.name} ${contact.surname}")
                is OrganizationContact -> println("${index + 1}. ${contact.name}")
            }
        }
    }

    fun printContactInfo() {
        if (contacts.isEmpty()) return println("There is no records!")
        printContactList()
        println("Enter index to show info:")
        val selectedIndex = getSelectedIndex()
        if (selectedIndex !in contacts.indices) return println("Invalid input!")
        val contact = contacts[selectedIndex]
        println(contact.toString())
    }

    fun addContact() {
        println("Enter the type (person, organization):")
        val contactType = ContactType.of(readln()) ?: return println("There is no such type!")
        val contact: Contact = when (contactType) {
            ContactType.PERSON -> PersonContact.createInstance()
            ContactType.ORGANIZATION -> OrganizationContact.createInstance()
        }
        contacts.add(contact)
        println("The record added.")
    }

    fun printCount() = println("The Phone Book has ${contacts.size} records.")

    fun removeContact() {
        if (contacts.isEmpty()) return println("No records to remove!")
        println("Select record to remove:")
        printContactList()
        val index = getSelectedIndex()
        contacts.removeAt(index)
        println("The record removed!")
    }

    fun editContact() {
        if (contacts.isEmpty()) return println("No records to edit!")
        printContactList()
        println("Select a record:")
        val index = getSelectedIndex()
        val contact = contacts[index]

        contact.changeProperty()
        println("The record updated!")
    }

    private fun getSelectedIndex(): Int {
        var input = readln()
        while (input.toIntOrNull() == null) {
            println("Wrong input format (only numbers are allowed!) Try again:")
            input = readln()
        }
        return input.toInt() - 1
    }

    private val json = Json { prettyPrint = true }

    fun saveContactsToFile() {
        if (file == null) return
        if (!file.exists()) file.createNewFile()
        file.writeText(json.encodeToString(contacts))
    }

    fun loadContactsFromFile() {
        if (file == null || !file.exists()) return
        val text = file.readText()
//        println("decoded: $text")
        contacts.clear()
        contacts.addAll(Json.decodeFromString<List<Contact>>(text))
    }
}
