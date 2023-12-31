import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
/**
 * Session - represents the session object
 */
@Serializable
data class Session(
    private var movie: Movie,
    private var _start: LocalDateTime,
    private var _end: LocalDateTime,
    private val reservedSeats: Array<Boolean>
){
    val start: LocalDateTime
        get() = _start

    val end: LocalDateTime
        get() = _end

    val movieName: String
        get() = movie.name

    val movieDuration: Int
        get() = movie.movieDuration
    /**
     * getAvailableSeats - returns string with numbers of free seats
     */
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
    /**
     * ifFull - returns if all the seats are reserved
     */
    fun isFull(): Boolean{
        return reservedSeats.all { it }
    }

    fun durationInMinutes(): Int{
        return (end.toInstant(TimeZone.currentSystemDefault()) - start.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes.toInt()
    }
    /**
     * changeDuration - function tries to set new start and end to session. If setting was successful returns true. Otherwise - false
     * @param start - new session start
     * @param end - new session end
     */
    fun changeDuration(start: LocalDateTime, end: LocalDateTime): Boolean{
        val tempStart = this.start
        val tempEnd = this.end
        this._start = start
        this._end = end
        if(durationInMinutes() < movie.movieDuration){
            this._start = tempStart
            this._end = tempEnd
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
