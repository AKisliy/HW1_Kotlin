import kotlinx.serialization.Serializable

@Serializable
class CinemaHall(
    val totalSeats: Int,
    var ticketId: Long
){
    fun ticketWasSold(){
        ++ticketId
    }
}