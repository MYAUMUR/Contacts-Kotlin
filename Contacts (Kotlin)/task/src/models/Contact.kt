package models

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable
import models.util.SystemClock
import models.validation.Validator

@Serializable
sealed class Contact {
    abstract var name: String
    abstract var number: String?

    @Required
    var timeCreated: String = SystemClock.getCurrentTime()
    @Required
    var timeLastEdit: String = timeCreated

    abstract fun matchesQuery(query: String): Boolean

    abstract fun getDisplayName(): String

    protected fun setPhoneNumber(newNumber: String?): String? = when {
        newNumber == null -> null
        Validator.validateNumber(newNumber) -> newNumber
        else -> {
            println("Wrong number format!")
            null
        }
    }

    abstract fun changeProperty()

    override fun toString(): String {
        val phoneNumberText = number ?: "[no data]"
        return """
            Number: $phoneNumberText
            Time created: $timeCreated
            Time last edit: $timeLastEdit
        """.trimIndent()
    }
}
