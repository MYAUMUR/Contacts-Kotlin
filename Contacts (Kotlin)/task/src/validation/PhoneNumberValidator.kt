package models.validation

interface PhoneNumberValidator {
    fun validateNumber(num: String): Boolean
}
