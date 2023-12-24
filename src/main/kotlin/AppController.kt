import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter

class AppController {
    private val filesController = FilesController()

    fun startApp(): CinemaManager{
        val movies = filesController.getData<MutableList<Movie>>("movies.txt")
        val sessions = filesController.getData<MutableList<Session>>("sessions.txt")
        val tickets = filesController.getData<MutableList<Ticket>>("tickets.txt")
        val cinemaHall = filesController.getData<CinemaHall>("cinemaHall.txt")

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
        val oMovies = ObservableList(movies)
        val oSessions = ObservableList(sessions)
        val oTickets = ObservableList(tickets)
        oMovies.addObserver { _, _ -> filesController.saveChanges(movies, "movies.txt") }
        oSessions.addObserver{ _, _ -> filesController.saveChanges(sessions, "sessions.txt")}
        oTickets.addObserver{_, _ -> filesController.saveChanges(tickets, "tickets.txt")}
        cinemaHall.addObserver{_,_ -> filesController.saveChanges(cinemaHall, "cinemaHall.txt")}

        return CinemaManager(oMovies, oSessions, cinemaHall, oTickets, Interactor())
    }

    fun appProcess(cinemaManager: CinemaManager){
        var choice = cinemaManager.interactor.getMenuChoice()
        while(true){
            Runtime.getRuntime().exec("clear")
            when(choice){
                1-> cinemaManager.sellTicket()
                2-> cinemaManager.refundTicket()
                3-> cinemaManager.displaySeatStatus()
                4-> cinemaManager.editMovieData()
                5-> cinemaManager.editSessionData()
                6-> return
            }
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
        manager.sessions.saveChanges()
        manager.cinemaHall.notifyObservers()
        manager.tickets.saveChanges()
        manager.movies.saveChanges()
        manager.interactor.printWithColor("Thank you for using!", Colors.BLUE)
    }
}