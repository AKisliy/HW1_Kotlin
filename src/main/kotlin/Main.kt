fun main(args: Array<String>) {
    val appController = AppController()
    val manager = appController.startApp()
    //manager.inputController.getUserApproval()
    appController.appProccess(manager)
    appController.finishApp(manager)
}


