import kotlinx.datetime.LocalDateTime
import kotlin.properties.Delegates

class CinemaManager(
    val movies: MutableList<Movie>,
    val sessions: MutableList<Session>,
    val cinemaHall: CinemaHall,
    var tickets: MutableList<Ticket>,
    val inputController: InputController,
    val outputController: OutputController,
    val interactor: Interactor
){
//    var movies: MutableList<Movie> by Delegates.observable(mutableListOf()){_,_,_ ->
//        println("DA!!")
//    }
//    init {
//        movies = _movies
//    }
    fun sellTicket(){
        println("To buy the ticket please choose the session(choose the number of session):")
        var session = interactor.getSession(sessions)
        while(session.isFull())
        {
            println("Sorry, there are no available seats for this session!")
            println("You can still pick another session from list")
            if(interactor.askForRetry()){
                session = inputController.getSession(sessions)
                continue
            }
            return
        }
        println("Enter costumer's name")
        val name = readln()
        println("Available seats:")
        println(session.getAvailableSeats())
        var seat = readln().toIntOrNull()
        while(!session.reserveSeat(seat)){
            println("There's no such available seat. Try again:")
            seat = readln().toIntOrNull()
        }
        val card = inputController.getCreditCard()
        println("Ticket №${cinemaHall.ticketId + 1} was sold to $name")
        cinemaHall.ticketWasSold()
        tickets.add(Ticket(Visitor(name, card), session, seat!!, cinemaHall.ticketId))
    }

    fun refundTicket(){
        println("Enter the unique ticket number:")
        var number = readln().toLongOrNull()
        while(true){
            if(number == null || !tickets.any { it.id == number!! })
            {
                println("No ticket with such number!! Try again:")
                number = readln().toLongOrNull()
                continue
            }
            break
        }
        val ticket = tickets.first { it.id == number }
        ticket.session.freeSeat(number?.toInt())
        tickets.removeIf { it.id == number }
        println("Successfully refunded to ${ticket.visitor.name} (card number: ${ticket.visitor.card})")
    }

    fun displaySeatStatus(){
        println("To see available seats, please, choose the session:")
        outputController.showAvailableSessions(sessions)
        val session = inputController.getSession(sessions)
        println("Available seats:")
        println(session.getAvailableSeats())
    }

    fun editMovieData(){
        println("Please choose movie from the list to edit it:")
        outputController.showAvailableMovies(movies)
        val movie = inputController.getMovie(movies)
        outputController.showChangeMovieOption()
        val choice = inputController.getNumberIn(1,3)
        when(choice){
            1 -> {
                println("Enter new movie name:")
                movie.name = readln()
                println("Movie name successfully changed")
            }
            2 -> {
                println("Enter new description:")
                movie.shortDescription = readln()
                println("Movie description successfully changed")
            }
            3 -> {
                println("Enter new duration(in minutes):")
                var newDuration = readln().toIntOrNull()
                while(newDuration == null){
                    println("Can't change to this value.)")
                }
                movie.movieDuration = newDuration
            }
        }
    }

    fun editSessionData(){
        println("Please choose session to edit:")
        outputController.showAvailableSessions(sessions)
        val session = inputController.getSession(sessions)
        outputController.showChangeSessionOption()
        val choice = inputController.getNumberIn(1,2)
        when(choice){
            1->{
                var changed = false
                do{
                    println("Choose new movie for this session")
                    outputController.showAvailableMovies(movies)
                    val movie = inputController.getMovie(movies)
                    if(!session.changeMovie(movie)){
                        println("Can't change to this movie! The duration is too big")
                        println("Do you want to try again? Y/N")
                        if(inputController.getUserApproval()){
                            continue
                        }
                        break
                    }
                    changed = true
                    break
                } while(true)
                if(changed){
                    println("Session was successfully updated")
                }
            }
            2->{
                var changed = false
                outputController.printWithColor(
                    "This session's movie lasts ${session.movieDuration} minutes\n" +
                            "You shouldn't make the session shorter than the movie\n" +
                            "Otherwise, the information won't be updated",
                    Colors.RED)
                do {
                    println("Enter new start:(example: 2023-12-23T10:30)")
                    val newStart = inputController.getDateTime()
                    println("Enter new end: (example: 2023-12-23T10:30)")
                    val newEnd = inputController.getDateTime()
                    if(!checkSessionTime(session, newStart, newEnd) || !session.changeDuration(newStart, newEnd)){
                        outputController.printWithColor("Can't change to this time", Colors.RED)
                        println("Do you want to try again? Y/N")
                        if(inputController.getUserApproval()){
                            continue
                        }
                        break
                    }
                    changed = true
                    break
                } while(true)
                if(changed)
                    println("Session was successfully updated")
            }
        }
    }

    private fun checkSessionTime(session: Session, start: LocalDateTime, end: LocalDateTime): Boolean{
        for(s in sessions){
            if(s == session)
                continue
            if((s.start in session.start..session.end)
                || (s.end in session.start..session.end)
                || (s.start <= session.start && s.end >= session.end))
            {
                return false
            }
        }
        return true
    }
}