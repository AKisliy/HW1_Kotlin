import kotlinx.serialization.Serializable
import java.util.Observable
/**
 * CinemaHall - represents the cinema hall
 * @param totalSeats - number of seats in cinema hall
 * @param ticketId - Id of the last ticket that was sold
 */
@Serializable
class CinemaHall(
    val totalSeats: Int,
    var ticketId: Long
): Observable(){
    /**
     * ticketWasSold - function is called when the ticket was sold. Increments ID.
     */
    fun ticketWasSold(){
        ++ticketId
        setChanged()
        notifyObservers()
    }
}