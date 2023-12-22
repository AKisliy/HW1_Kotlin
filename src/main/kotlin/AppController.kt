import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter

class AppController {
    fun startApp(): CinemaManager{
        var file = File("movies.txt")
        var content = file.readText()
        val movies = Json.decodeFromString<MutableList<Movie>>(content)

        file = File("sessions.txt")
        content = file.readText()
        val sessions = Json.decodeFromString<MutableList<Session>>(content)

        file = File("tickets.txt")
        content = file.readText()
        val tickets = Json.decodeFromString<MutableList<Ticket>>(content)

        file = File("cinemaHall.txt")
        content = file.readText()
        val cinemaHall = Json.decodeFromString<CinemaHall>(content)
        val output = OutputController()
        output.showMenu()

        return CinemaManager(movies, sessions, cinemaHall, tickets,InputController(), output)
    }

    fun finishApp(manager: CinemaManager){
        var jsonString = Json.encodeToString(manager.movies)
        var file = File("movies.txt")
        var writer = FileWriter(file)
        writer.write(jsonString)
        writer.close()

        jsonString = Json.encodeToString(manager.cinemaHall)
        file = File("cinemaHall.txt")
        writer = FileWriter(file)
        writer.write(jsonString)
        writer.close()

        jsonString = Json.encodeToString(manager.tickets)
        file = File("tickets.txt")
        writer = FileWriter(file)
        writer.write(jsonString)
        writer.close()

        jsonString = Json.encodeToString(manager.sessions)
        file = File("sessions.txt")
        writer = FileWriter(file)
        writer.write(jsonString)
        writer.close()
    }
}