package models

import java.io.File

fun main(args: Array<String>) {
    val file: File? = when {
        args.isEmpty() || args[0].isBlank() -> null
        args.size == 1 -> File(args[0] + ".json")
        else -> error("Invalid command-line arguments!")
    }
    val contactManager = ContactManager(file)
    contactManager.run()
}
