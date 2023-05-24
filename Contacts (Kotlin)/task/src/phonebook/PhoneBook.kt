package contacts.phonebook

import contacts.contacts.PersonContact
import contacts.contacts.Contact
import contacts.contacts.OrganizationContact
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class PhoneBook {
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

    fun addContacts(list: List<Contact>) {
        contacts.addAll(list)
    }

    fun removeAllContacts() {
        contacts.clear()
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

    @OptIn(ExperimentalSerializationApi::class)
    private val json = Json {
        prettyPrint = true
    }

    fun addContact() {
        println("Enter the type (person, organization):")
        val contactType = ContactType.of(readln()) ?: return println("There is no such type!")
        val contact: Contact = when (contactType) {
            ContactType.PERSON -> PersonContact.createInstance()
            ContactType.ORGANIZATION -> OrganizationContact.createInstance()
        }
        contacts.add(contact)
        println(json.encodeToString(contacts))
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
}
