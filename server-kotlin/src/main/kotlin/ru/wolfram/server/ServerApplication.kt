package ru.wolfram.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import ru.wolfram.server.component.WebSocketHandler
import java.io.File
import java.io.IOException

@SpringBootApplication
class ServerApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val context = runApplication<ServerApplication>(*args)
            val webSocketHandler = context.getBean(WebSocketHandler::class.java)
            while (true) {
                try {
                    val fileName = readlnOrNull()?.trim()
                    if (fileName != null) {
                        val jsonString = File("./src/main/resources/views/$fileName.json").readText(Charsets.UTF_8)
                        webSocketHandler.broadcast(jsonString)
                    }
                } catch (_: IOException) {
                    println("Probably file was not found")
                }
            }
        }
    }
}