package com.em_projects.grocery.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI
import java.net.URISyntaxException


class GroceryViewModel : ViewModel() {
    private lateinit var webSocketClient: WebSocketClient
    private val groceryWsUrl = "wss://superdo-groceries.herokuapp.com/"

    init {
        createWebSocketClient()
    }

    private fun createWebSocketClient() {
        val uri: URI
        try {
            // Connect to local host
            uri = URI(groceryWsUrl)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            return
        }
        webSocketClient = object : WebSocketClient(uri) {

            override fun onOpen() {
                Log.i("WebSocket", "Session is starting")
                webSocketClient.send("Hello World!");
            }

            override fun onTextReceived(s: String) {
                Log.i("WebSocket", "Message received")
            }

            override fun onBinaryReceived(data: ByteArray) {
                Log.i("WebSocket", "Message received")
            }
            override fun onPingReceived(data: ByteArray) {
                Log.i("WebSocket", "Message received")
            }
            override fun onPongReceived(data: ByteArray) {
                Log.i("WebSocket", "Message received")
            }
            override fun onException(e: Exception) {
                println(e.message)
            }

            override fun onCloseReceived() {
                Log.i("WebSocket", "Closed ")
                println("onCloseReceived")
            }
        }
        webSocketClient.setConnectTimeout(10000)
        webSocketClient.setReadTimeout(60000)
        webSocketClient.enableAutomaticReconnection(500)
        webSocketClient.connect()
    }
}