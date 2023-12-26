/**
 * AppController - class which is responsible for main app's stages: start, processing and finish
 */
class AppController {
    private val filesController = FilesController()
    /**
     * startApp - starts the app. Take all the information from JSON files and returns CinemaManager with proper lists
     */
    fun startApp(): CinemaManager{
        val movies = filesController.getData<MutableList<Movie>>(FilePaths.MOVIES_FILE.path)
        val sessions = filesController.getData<MutableList<Session>>(FilePaths.SESSIONS_FILE.path)
        val tickets = filesController.getData<MutableList<Ticket>>(FilePaths.TICKETS_FILE.path)
        val cinemaHall = filesController.getData<CinemaHall>(FilePaths.CINEMAHALL_FILE.path)

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
        oMovies.addObserver { _, _ -> filesController.saveChanges(movies, FilePaths.MOVIES_FILE.path) }
        oSessions.addObserver{ _, _ -> filesController.saveChanges(sessions, FilePaths.SESSIONS_FILE.path)}
        oTickets.addObserver{_, _ -> filesController.saveChanges(tickets, FilePaths.TICKETS_FILE.path)}
        cinemaHall.addObserver{_,_ -> filesController.saveChanges(cinemaHall, FilePaths.CINEMAHALL_FILE.path)}

        return CinemaManager(oMovies, oSessions, cinemaHall, oTickets, Interactor())
    }
    /**
     * appProcess - processing the app
     */
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
    /**
     * finishApp - saves all manager information in files and finish the app
     */
    fun finishApp(manager: CinemaManager){
        manager.sessions.saveChanges()
        manager.cinemaHall.notifyObservers()
        manager.tickets.saveChanges()
        manager.movies.saveChanges()
        manager.interactor.printWithColor("Thank you for using!", Colors.BLUE)
    }
}