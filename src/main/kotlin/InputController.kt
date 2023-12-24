import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import java.lang.IllegalArgumentException
class InputController(
    private val errorPrinter: (String) -> Unit
) {
    fun getSession(sessions: ObservableList<Session>): Session{
        val choice = getNumberInRange(1, sessions.size)
        return sessions[choice - 1]
    }

    fun getMovie(movies: ObservableList<Movie>): Movie{
        val choice = getNumberInRange(1, movies.size)
        return movies[choice - 1]
    }

    fun getTicket(tickets: ObservableList<Ticket>): Ticket?{
        val number = readln().toLongOrNull()
        if(number == null || !tickets.any { it.id == number })
        {
            errorPrinter("No ticket with such number")
            return null
        }
        return tickets.first { it.id == number }
    }

    fun getCreditCard(): Long{
        var card = readln().toLongOrNull()
        while(card == null)
        {
            errorPrinter("The input isn't a valid credit card!! Try again:")
            card = readln().toLongOrNull()
        }
        return card
    }

    fun getNumberInRange(rangeStart: Int, rangeEnd: Int): Int{
        var choice = readln().toIntOrNull()
        while(choice == null || choice < rangeStart || choice > rangeEnd){
            errorPrinter("Wrong input! Try again:")
            choice = readln().toIntOrNull()
        }
        return choice
    }

    fun getUserApproval(): Boolean{
        var input = readlnOrNull()
        while(input == null || (input[0] != 'Y' && input[0] != 'N')){
            errorPrinter("Unknown command. Try again!")
            input = readlnOrNull()
        }
        return input[0] == 'Y'
    }

    fun getDateTime(): LocalDateTime{
        var input = readlnOrNull()
        var date: LocalDateTime
        while(true){
            if(input == null){
                errorPrinter("Incorrect input!! Try again")
                input = readlnOrNull()
                continue
            }
            try{
                date = input.toLocalDateTime()
                break
            }
            catch (e: IllegalArgumentException){
                errorPrinter("Can't convert your input to valid date")
                println("Try again!!")
                input = readlnOrNull()
            }
        }
        return date
    }
}