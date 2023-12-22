import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.LocalDateTime
import java.lang.IllegalArgumentException

class InputController {
    fun getSession(sessions: MutableList<Session>): Session{
        var choice = readln().toIntOrNull()
        while(true) {
            if (choice == null || choice < 1 || choice > sessions.size) {
                println("Wrong session! Try again.")
                choice = readln().toIntOrNull()
                continue
            }
            break
        }
        return sessions[choice!! - 1]
    }

    fun getMovie(movies: MutableList<Movie>): Movie{
        var choice = readln().toIntOrNull()
        while(true) {
            if (choice == null || choice < 0 || choice >= movies.size) {
                println("Wrong movie index! Try again.")
                choice = readln().toIntOrNull()
                continue
            }
            break
        }
        return movies[choice!!]
    }

    fun getCreditCard(): Long{
        println("Please enter the customer's credit card:")
        var card = readln().toLongOrNull()
        while(card == null)
        {
            println("The input isn't a valid credit card!! Try again:")
            card = readln().toLongOrNull()
        }
        return card
    }

    fun getNumberIn(rangeStart: Int, rangeEnd: Int): Int{
        var choice = readln().toIntOrNull()
        while(choice == null || choice < rangeStart || choice > rangeEnd){
            println("Wrong input! Try again:")
            choice = readln().toIntOrNull()
        }
        return choice
    }

    fun movieChangeOption(): Int{
        println("Choose what you want to change:")
        println("1 - change name")
        println("2 - change description")
        println("3 - change duration")
        var choice = readln().toIntOrNull()
        while(choice == null || choice < 1 || choice > 3){
            println("Wrong input! Try again:")
            choice = readln().toIntOrNull()
        }
        return choice
    }

    fun sessionChangeOption(): Int{
        println("Choose what you want to change:")
        println("1 - change movie")
        println("2 - change time")
        var choice = readln().toIntOrNull()
        while(choice == null || choice < 1 || choice > 2){
            println("Wrong input!! Try again:")
            choice = readln().toIntOrNull()
        }
        return choice
    }

    fun getUserApproval(): Boolean{
        var input = readlnOrNull()
        while(input == null || input[0] != 'Y' || input[0] != 'N'){
            println("Unknown command. Try again!")
            input = readlnOrNull()
        }
        return input[0] == 'Y'
    }

    fun getDateTime(): LocalDateTime{
        var input = readlnOrNull()
        var date: LocalDateTime
        while(true){
            if(input == null){
                println("Incorrect input!! Try again")
                input = readlnOrNull()
                continue
            }
            try{
                date = input.toLocalDateTime()
                break
            }
            catch (e: IllegalArgumentException){
                println("Can't convert your input to valid date")
                println("Try again!!")
            }
        }
        return date
    }
}