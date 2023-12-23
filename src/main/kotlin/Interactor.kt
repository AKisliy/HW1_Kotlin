class Interactor(
    private val inputController: InputController,
    private val outputController: OutputController
){
    fun getSession(sessions: MutableList<Session>): Session{
        outputController.showAvailableSessions(sessions)
        return inputController.getSession(sessions)
    }

    fun askForRetry(): Boolean{
        println("Would you like to continue? Y/N")
        return inputController.getUserApproval()
    }
}