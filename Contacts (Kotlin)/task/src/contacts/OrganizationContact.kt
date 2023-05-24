package contacts.contacts

import contacts.phonebook.SystemClock

class OrganizationContact private constructor(
    override var name: String,
    private var address: String,
    override var number: String?
) : Contact() {

    override fun changeProperty() {
        println("Select a field (${getAllEditableProperties()}):")
        val field = EditableField.of(readln()) ?: return println("Wrong field name!")
        when (field) {
            EditableField.ADDRESS -> {
                println("Enter address:")
                address = readln()
            }

            EditableField.NUMBER -> {
                println("Enter number:")
                number = setPhoneNumber(readln())
            }
        }
        timeLastEdit = SystemClock.getCurrentTime()
    }

    private fun getAllEditableProperties(): String = EditableField.values().joinToString { it.name.lowercase() }

    override fun toString(): String {
        return """
            Organization name: $name
            Address: $address
        """.trimIndent() + "\n" + super.toString()
    }

    companion object {
        fun createInstance(): OrganizationContact {
            println("Enter the organization name:")
            val name = readln()
            println("Enter the address:")
            val address = readln()
            println("Enter the number:")
            val number = readln()
            return OrganizationContact(name, address, number)
        }
    }

    enum class EditableField {
        ADDRESS, NUMBER;

        companion object {
            fun of(str: String): EditableField? {
                return if (str.uppercase() !in EditableField.values().map(EditableField::name)) null
                else EditableField.valueOf(str.uppercase())
            }
        }
    }
}
