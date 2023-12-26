import kotlinx.serialization.Serializable
/**
 * Ticket - represents the ticket object
 */
@Serializable
data class Ticket(
    val visitor: Visitor,
    var session: Session,
    val seatNumber: Int,
    val id: Long
)
