import timber.log.Timber
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.DateTimeException
import java.util.Date
import kotlin.random.Random

data class HNItem(
    val by: String,
    val descendants: Long?,
    val id: Long,
    val kids: List<Long>?,
    val score: Long?,
    val time: Long,
    val title: String?,
    val type: String,
    val url: String?
)

fun HNItem.dateTimeToString() : String? {
    return try {
        val dateFormat = SimpleDateFormat("dd/MM/YY")
        val netDate = Date(this.time * 1000)
        dateFormat.format(netDate)
    } catch (e: Exception){
        Timber.d(e.toString())
        null
    }
}

val sampleItem = HNItem(
    title = "Artists must be allowed to make bad work",
    by = "ADC",
    id = Random.nextLong(),
    time = 1684225532,
    type = "story",
    url = "https://www.google.com",
    descendants = Random.nextLong(),
    kids = listOf(Random.nextLong(), Random.nextLong(), Random.nextLong()),
    score = null
)