import kotlinx.serialization.Serializable

@Serializable
data class Visitor(
    val name: String,
    val card: Long
)