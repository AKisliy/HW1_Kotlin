import kotlinx.datetime.LocalDateTime

class Interactor{
    private val outputController: OutputController = OutputController()
    private val inputController: InputController = InputController(::errorPrinter)

    fun getSession(sessions: ObservableList<Session>): Session{
        outputController.showAvailableSessions(sessions)
        return inputController.getSession(sessions)
    }

    fun getMovie(movies: ObservableList<Movie>): Movie{
        outputController.showAvailableMovies(movies)
        return inputController.getMovie(movies)
    }

    fun getMenuChoice(): Int{
        outputController.showMenu()
        return getNumberInRange(1, Constants.MENU_OPTIONS.value)
    }

    fun getCreditCard(): Long{
         println("Please enter the customer's credit card:")
        return inputController.getCreditCard()
    }

    fun getMovieEditingOptions():Int{
        outputController.showChangeMovieOption()
        return inputController.getNumberInRange(1,Constants.MOVIES_OPTIONS.value)
    }

    fun getSessionEditingOptions():Int{
        outputController.showChangeSessionOption()
        return inputController.getNumberInRange(1,Constants.SESSION_OPTIONS.value)
    }


    fun askForApproval(message: String): Boolean{
        println(message)
        println("Y/N")
        return inputController.getUserApproval()
    }

    fun getNumberInRange(start: Int, end: Int): Int{
        return inputController.getNumberInRange(start, end)
    }

    fun printWithColor(message: String, colors: Colors){
        outputController.printWithColor(message, colors)
    }

    fun getDateTime(): LocalDateTime{
        println("(example: 2023-12-23T10:30)")
        return inputController.getDateTime()
    }

    fun getTicketToRefund(tickets: ObservableList<Ticket>): Ticket?{
        println("Enter the unique ticket number:")
        var ticket = inputController.getTicket(tickets)
        while(true){
            if(ticket == null)
            {
                if(askForApproval("Try again?")) {
                    ticket = inputController.getTicket(tickets)
                    continue
                }
            }
            break
        }
        return null
    }

    private fun errorPrinter(message: String){
        outputController.printWithColor(message, Colors.RED)
    }
}