package contacts.validation

object Validator : PhoneNumberValidator {
    override fun validateNumber(num: String): Boolean =
        num.matches(
            Regex(
                "^\\+?\\([A-Za-z0-9]+\\)([ -][A-Za-z0-9]{2,})*\$|" +
                        "^\\+?[A-Za-z0-9]+[ -]\\(?[A-Za-z0-9]{2,}\\)?([ -][A-Za-z0-9]{2,})*?\$|" +
                        "^\\+?[A-Za-z0-9]+([ -][A-Za-z0-9]{2,})*\$"
            )
        )
}
