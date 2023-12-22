class OutputController {
    fun showAvailableSessions(sessions: MutableList<Session>){
        for(i in sessions.indices){
            println("${i + 1}) ${sessions[i]}")
        }
    }

    fun showAvailableMovies(movies: MutableList<Movie>){
        for(i in movies.indices){
            println("${i}) ${movies[i]}")
        }
    }

    fun showMenu(){
        println("......Welcome to Cinema-Manager app.....")
        println("Please, choose what do you want to do")
        println("1 - Sell ticket")
        println("2 - Refund ticket")
        println("3 - Free seats at the session")
        println("4 - Edit movie data")
        println("5 - Edit session data")
        println("(to exit press Esc)")
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
}