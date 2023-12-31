import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileWriter

fun main(args: Array<String>) {
    val appController = AppController()
    // setup all data and start the app
    val manager = appController.startApp()
    // main logic of app processing
    appController.appProcess(manager)

    appController.finishApp(manager)
}


