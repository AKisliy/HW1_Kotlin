fun main(args: Array<String>) {
    val appController = AppController()
    val manager = appController.startApp()
    // main logic should be here(or in appController)
    appController.finishApp(manager)
}


