import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val visitor: Visitor,
    val session: Session,
    val seatNumber: Int,
    val id: Long
)
