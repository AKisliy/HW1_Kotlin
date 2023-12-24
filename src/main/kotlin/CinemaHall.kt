import kotlinx.serialization.Serializable
import java.util.Observable

@Serializable
class CinemaHall(
    val totalSeats: Int,
    var ticketId: Long
): Observable(){
    fun ticketWasSold(){
        ++ticketId
        setChanged()
        notifyObservers()
    }
}