import kotlinx.datetime.LocalDateTime

class Interactor(){
    private val outputController: OutputController = OutputController()
    private val inputController: InputController = InputController(::errorPrinter)

    fun getSession(sessions: MutableList<Session>): Session{
        outputController.showAvailableSessions(sessions)
        return inputController.getSession(sessions)
    }

    fun getMovie(movies: MutableList<Movie>): Movie{
        outputController.showAvailableMovies(movies)
        return inputController.getMovie(movies)
    }

    fun getMenuChoice(): Int{
        outputController.showMenu()
        return getNumberIn(1, Constants.MENU_OPTIONS.value)
    }

    fun getCreditCard(): Long{
         println("Please enter the customer's credit card:")
        return inputController.getCreditCard()
    }

    fun getMovieEditingOptions():Int{
        outputController.showChangeMovieOption()
        return inputController.getNumberIn(1,Constants.MOVIES_OPTIONS.value)
    }

    fun getSessionEditingOptions():Int{
        outputController.showChangeSessionOption()
        return inputController.getNumberIn(1,Constants.SESSION_OPTIONS.value)
    }


    fun askForApproval(message: String): Boolean{
        println(message)
        println("Y/N")
        return inputController.getUserApproval()
    }

    fun getNumberIn(start: Int, end: Int): Int{
        return inputController.getNumberIn(start, end)
    }

    fun printWithColor(message: String, colors: Colors){
        outputController.printWithColor(message, colors)
    }

    fun getDateTime(): LocalDateTime{
        println("(example: 2023-12-23T10:30)")
        return inputController.getDateTime()
    }
    private fun errorPrinter(message: String){
        outputController.printWithColor(message, Colors.RED)
    }
}