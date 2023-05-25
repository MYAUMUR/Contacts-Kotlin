package models.util

enum class Gender(val value: String) {
    MALE("M"), FEMALE("F");

    companion object {
        fun of(string: String): Gender? = when (string.uppercase()) {
            MALE.value -> MALE
            FEMALE.value -> FEMALE
            else -> null
        }
    }
}
