import kotlinx.serialization.Serializable
/**
 * Visitor - represents the cinema visitor
 */
@Serializable
data class Visitor(
    val name: String,
    val card: Long
)