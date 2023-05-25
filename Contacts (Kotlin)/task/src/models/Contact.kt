package models

import models.util.SystemClock
import models.validation.Validator
import kotlinx.serialization.Serializable

@Serializable
sealed class Contact(

) {
    abstract var name: String
    abstract var number: String?

    @kotlinx.serialization.Required
    var timeCreated: String = SystemClock.getCurrentTime()
    @kotlinx.serialization.Required
    var timeLastEdit: String = timeCreated

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
