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

        for(i in movies.indices){
            for(j in sessions.indices){
                if(movies[i].name == sessions[i].movieName)
                    sessions[i].changeMovie(movies[i])
            }
        }
        for(i in sessions.indices){
            for(j in tickets.indices){
                if(tickets[j].session.movieName == sessions[i].movieName)
                    tickets[j].session = sessions[i];
            }
        }

        return CinemaManager(movies, sessions, cinemaHall, tickets,ObservableList(movies), Interactor())
    }

    fun appProccess(cinemaManager: CinemaManager){
        var choice = cinemaManager.interactor.getMenuChoice()
        while(true){
            Runtime.getRuntime().exec("clear")
            when(choice){
                1-> cinemaManager.sellTicket()
                2-> cinemaManager.refundTicket()
                3-> cinemaManager.displaySeatStatus()
                4-> cinemaManager.editMovieData()
                5-> cinemaManager.editSessionData()
            }
            println("Now you can do other things, or close the app")
            if(cinemaManager.interactor.askForApproval("Return to menu?"))
            {
                Runtime.getRuntime().exec("clear")
                choice = cinemaManager.interactor.getMenuChoice()
                continue
            }
            break
        }
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