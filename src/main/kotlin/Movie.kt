import kotlinx.serialization.Serializable

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
}
