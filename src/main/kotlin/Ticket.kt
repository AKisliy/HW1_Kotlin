import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val visitor: Visitor,
    var session: Session,
    val seatNumber: Int,
    val id: Long
)
