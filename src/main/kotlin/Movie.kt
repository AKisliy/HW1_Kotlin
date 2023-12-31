import kotlinx.serialization.Serializable
/**
 * Movie - represents the movie object with title, description and duration
 */
@Serializable
data class Movie(
    private var title: String,
    private var description: String,
    private var duration: Int
){
    var name: String
        get() = title
        set(value){
            title = value
        }
    var shortDescription: String
        get() = description
        set(value){
            description = value
        }
    var movieDuration: Int
        get() = duration
        set(value) {
            duration = value
        }

    override fun toString(): String {
        return "${name} - ${description}, $duration min"
    }
}
