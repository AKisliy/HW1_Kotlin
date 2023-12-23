enum class Colors(val color: String){
    RED("\u001b[31m"),
    BLUE("\u001B[34m"),
    BRIGHT_BlUE("\u001B[94m"),
    PURPLE("\u001B[35m"),
    CYAN("\u001B[96m")
}

class OutputController {
    fun showAvailableSessions(sessions: MutableList<Session>){
        for(i in sessions.indices){
            println("${i + 1}) ${sessions[i]}")
        }
    }

    fun showAvailableMovies(movies: MutableList<Movie>){
        for(i in movies.indices){
            println("${i + 1}) ${movies[i]}")
        }
    }

    fun showMenu(){
        printWithColor("......Welcome to Cinema-Manager app.....", Colors.CYAN)
        printWithColor("Please, choose what do you want to do", Colors.BRIGHT_BlUE)
        setColor(Colors.PURPLE)
        println("1 - Sell ticket")
        println("2 - Refund ticket")
        println("3 - Free seats at the session")
        println("4 - Edit movie data")
        println("5 - Edit session data")
        println("(to exit press Esc)")
        resetColor()
    }

    fun showChangeMovieOption(){
        println("Choose what you want to change:")
        println("1 - change name")
        println("2 - change description")
        println("3 - change duration")
    }

    fun showChangeSessionOption(){
        println("Choose what you want to change:")
        println("1 - change movie")
        println("2 - change time")
    }

    fun printWithColor(string: String, color: Colors){
        val reset = "\u001b[0m"
        println(color.color + string + reset)
    }

    private fun setColor(color: Colors){
        print(color.color)
    }

    private fun resetColor(){
        val reset = "\u001b[0m"
        print(reset)
    }
}