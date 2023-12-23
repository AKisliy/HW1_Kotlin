import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

@Serializable
data class Session(
    private var movie: Movie,
    var start: LocalDateTime,
    var end: LocalDateTime,
    private val reservedSeats: Array<Boolean>
){
    val movieName: String
        get() = movie.name

    val movieDuration: Int
        get() = movie.movieDuration

    fun getAvailableSeats(): String{
        val stringBuilder = StringBuilder()
        for(i in reservedSeats.indices) {
            if(i == reservedSeats.size - 1)
                break
            if (!reservedSeats[i])
                stringBuilder.append("${i + 1}, ")
        }
        stringBuilder.append("${reservedSeats.size}")
        return stringBuilder.toString()
    }

    fun reserveSeat(seat: Int?): Boolean{
        if(seat == null)
            return false
        if(seat < 1 || seat > reservedSeats.size || reservedSeats[seat - 1])
            return false
        reservedSeats[seat - 1] = true
        return true
    }

    fun freeSeat(seat: Int?): Boolean{
        if(seat == null)
            return false
        if(seat < 1 || seat > reservedSeats.size || !reservedSeats[seat - 1])
            return false
        reservedSeats[seat - 1] = false
        return true
    }

    fun isFull(): Boolean{
        return reservedSeats.all { it }
    }

    fun durationInMinutes(): Int{
        return (end.toInstant(TimeZone.currentSystemDefault()) - start.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes.toInt()
    }

    fun changeDuration(start: LocalDateTime, end: LocalDateTime): Boolean{
        val tempStart = this.start
        val tempEnd = this.end
        this.start = start
        this.end = end
        if(durationInMinutes() < movie.movieDuration){
            this.start = tempStart
            this.end = tempEnd
            return false
        }
        return true
    }

    fun changeMovie(movie: Movie): Boolean{
        if(movie.movieDuration < durationInMinutes()){
            return false
        }
        this.movie = movie
        return true
    }

    override fun toString(): String {
        return "${movie.name} : ${if (start.hour == 0) "00" else start.hour}:${ if(start.minute == 0) "00" else start.minute} - " +
                "${if (end.hour == 0) "00" else end.hour}:${if (end.minute == 0) "00" else end.minute}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Session

        if (movie != other.movie) return false
        if (start != other.start) return false
        if (end != other.end) return false
        if (!reservedSeats.contentEquals(other.reservedSeats)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = movie.hashCode()
        result = 31 * result + start.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + reservedSeats.contentHashCode()
        return result
    }
}
