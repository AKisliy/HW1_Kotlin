import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import java.util.*

@Serializable
data class Session(
    var movie: Movie,
    var start: LocalDateTime,
    var end: LocalDateTime,
    val reservedSeats: Array<Boolean>
){
    override fun toString(): String {
        return "${movie.name} : ${start.hour}:${start.minute} - ${end.hour}:${end.minute}"
    }

    fun getAvailableSeats(): String{
        val stringBuilder = StringBuilder()
        for(i in reservedSeats.indices) {
            if (!reservedSeats[i])
                stringBuilder.append("${i}, ")
        }
        return stringBuilder.toString()
    }

    fun reserveSeat(seat: Int?): Boolean{
        if(seat == null)
            return false
        if(seat < 0 || seat > reservedSeats.size || reservedSeats[seat])
            return false
        reservedSeats[seat] = true
        return true
    }

    fun isFull(): Boolean{
        return reservedSeats.all { it }
    }

    fun durationInMinutes(): Int{
        return (end.toInstant(TimeZone.currentSystemDefault()) - start.toInstant(TimeZone.currentSystemDefault())).inWholeMinutes.toInt()
    }

    fun changeDuration(start: LocalDateTime, end: LocalDateTime): Boolean{
        if(durationInMinutes() < movie.movieDuration){
            return false
        }
        this.start = start
        this.end = end
        return true
    }

    fun changeMovie(movie: Movie): Boolean{
        if(movie.movieDuration < durationInMinutes()){
            return false
        }
        this.movie = movie
        return true
    }
}
