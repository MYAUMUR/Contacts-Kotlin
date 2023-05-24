package contacts.validation

interface PhoneNumberValidator {
    fun validateNumber(num: String): Boolean
}
