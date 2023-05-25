package models.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

object SystemClock {
    fun getCurrentTime(): String {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        @Suppress("SpellCheckingInspection") val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")
        return currentTime.toJavaLocalDateTime().format(formatter).toLocalDateTime().toString()
    }
}
