import kotlinx.datetime.LocalDateTime

class CinemaManager(
    val movies: ObservableList<Movie>,
    val sessions: ObservableList<Session>,
    val cinemaHall: CinemaHall,
    var tickets: ObservableList<Ticket>,
    val interactor: Interactor
){
    fun sellTicket(){
        println("To buy the ticket please choose the session(choose the number of session):")
        var session = interactor.getSession(sessions)
        while(session.isFull())
        {
            println("Sorry, there are no available seats for this session!")
            println("You can still pick another session from list")
            if(interactor.askForApproval("Would you like to pick another session?")){
                session = interactor.getSession(sessions)
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
        val card = interactor.getCreditCard()
        interactor.printWithColor("Ticket №${cinemaHall.ticketId} was sold to $name", Colors.GREEN)
        cinemaHall.ticketWasSold()
        tickets.add(Ticket(Visitor(name, card), session, seat!!, cinemaHall.ticketId))
        sessions.saveChanges()
    }

    fun refundTicket(){
        val ticket = interactor.getTicketToRefund(tickets) ?: return
        ticket.session.freeSeat(ticket.seatNumber)
        tickets.removeIf { it.id == ticket.id }
        println("Successfully refunded to ${ticket.visitor.name} (card number: ${ticket.visitor.card})")
        sessions.saveChanges()
    }

    fun displaySeatStatus(){
        println("To see available seats, please, choose the session:")
        val session = interactor.getSession(sessions)
        println("Available seats:")
        println(session.getAvailableSeats())
    }

    fun editMovieData(){
        println("Please choose movie from the list to edit it:")
        val movie = interactor.getMovie(movies)
        val choice = interactor.getMovieEditingOptions()
        when(choice){
            1 -> {
                println("Enter new movie name:")
                movie.name = readln()
            }
            2 -> {
                println("Enter new description:")
                movie.shortDescription = readln()
            }
            3 -> {
                var newDuration: Int?
                do {
                    println("Enter new duration(in minutes):")
                    newDuration = readln().toIntOrNull()
                    if (newDuration == null || !checkMovieTime(movie, newDuration)) {
                        println("Can't change to this value.")
                        if (interactor.askForApproval("Would you like to try another duration?")) {
                            continue
                        }
                        return
                    }
                    break
                } while(true)
                movie.movieDuration = newDuration!!
            }
        }
        movies.saveChanges()
        sessions.saveChanges()
        interactor.printWithColor("Success!", Colors.GREEN)
    }

    fun editSessionData(){
        println("Please choose session to edit:")
        val session = interactor.getSession(sessions)
        val choice = interactor.getSessionEditingOptions()
        when(choice){
            1->{
                do{
                    println("Choose new movie for this session")
                    val movie = interactor.getMovie(movies)
                    if(!session.changeMovie(movie)){
                        println("Can't change to this movie! The duration is too big")
                        if(interactor.askForApproval("Do you want to try again?")){
                            continue
                        }
                        return
                    }
                    break
                } while(true)
                interactor.printWithColor("Session was successfully updated", Colors.GREEN)
            }
            2->{
                interactor.printWithColor(
                    "This session's movie lasts ${session.movieDuration} minutes\n" +
                            "You shouldn't make the session shorter than the movie\n" +
                            "Otherwise, the information won't be updated",
                    Colors.YELLOW)
                do {
                    println("Enter new start:")
                    val newStart = interactor.getDateTime()
                    println("Enter new end:")
                    val newEnd = interactor.getDateTime()
                    if(!checkSessionTime(session, newStart, newEnd) || !session.changeDuration(newStart, newEnd)){
                        interactor.printWithColor("Can't change to this time", Colors.RED)
                        if(interactor.askForApproval("Do you want to try another time?")){
                            continue
                        }
                        return
                    }
                    break
                } while(true)
                interactor.printWithColor("Session was successfully updated", Colors.GREEN)
            }
        }
        sessions.saveChanges()
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

    private fun checkMovieTime(movie: Movie, newDuration: Int): Boolean{
        for(s in sessions){
            if(s.movieName == movie.name){
                if(s.durationInMinutes() < newDuration)
                    return false
            }
        }
        return true
    }
}