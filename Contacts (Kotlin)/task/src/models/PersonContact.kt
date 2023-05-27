package models

import kotlinx.serialization.Serializable
import models.util.Gender
import models.util.SystemClock

@Serializable
class PersonContact private constructor(
    override var name: String,
    private var surname: String,
    private var birthDate: String?,
    private var gender: Gender?,
    override var number: String? = null,
) : Contact() {

    private fun setBirthDate(date: String?): String? {
        return if (date.isNullOrEmpty()) {
            println("Bad birth date")
            null
        } else date
    }

    private fun getAllEditableProperties(): String = EditableField.values().joinToString { it.name.lowercase() }

    override fun matchesQuery(query: String): Boolean {
        val normalizedQuery = query.lowercase()
        return name.lowercase().contains(normalizedQuery) ||
                surname.lowercase().contains(normalizedQuery) ||
                number?.contains(normalizedQuery) == true
    }

    override fun getDisplayName(): String {
        return "$name $surname"
    }

    override fun changeProperty() {
        println("Select a field (${getAllEditableProperties()}):")
        val fields = EditableField.of(readln()) ?: return println("Wrong field name!")
        when (fields) {
            EditableField.NAME -> {
                println("Enter name:")
                name = readln()
            }

            EditableField.SURNAME -> {
                println("Enter surname:")
                surname = readln()
            }

            EditableField.BIRTH -> {
                println("Enter birth date:")
                birthDate = setBirthDate(readln())
            }

            EditableField.GENDER -> {
                println("Enter gender (M, F):")
                gender = Gender.of(readln().uppercase())
            }

            EditableField.NUMBER -> {
                println("Enter number:")
                number = setPhoneNumber(readln())
            }
        }
        timeLastEdit = SystemClock.getCurrentTime()
    }

    override fun toString(): String {
        val birthDateText = birthDate ?: "[no data]"
        val genderText = gender?.value ?: "[no data]"
        return """
            Name: $name
            Surname: $surname
            Birth date: $birthDateText
            Gender: $genderText
        """.trimIndent() + "\n" + super.toString()
    }

    companion object {
        fun createInstance(): PersonContact {
            println("Enter the name:")
            var name = readln()
            while (name.isBlank()) {
                println("Invalid name. Please input again:")
                name = readln()
            }
            println("Enter the surname:")
            var surname = readln()
            while (surname.isBlank()) {
                println("Invalid surname. Please input again:")
                surname = readln()
            }
            println("Enter the birth date:")
            val birthDate = readln().ifBlank {
                println("Bad birth date!")
                null
            }
            println("Enter the gender (M, F):")
            val gender = Gender.of(readln())
            if (gender == null) println("Bad gender!")
            println("Enter the number:")
            val number = readln()
            return PersonContact(name, surname, birthDate, gender, number)
        }
    }

    enum class EditableField {
        NAME, SURNAME, BIRTH, GENDER, NUMBER;

        companion object {
            fun of(str: String): EditableField? {
                return if (str.uppercase() !in EditableField.values().map(EditableField::name)) null
                else EditableField.valueOf(str.uppercase())
            }
        }
    }
}
