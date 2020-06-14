package com.em_projects.grocery.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.em_projects.grocery.model.GroceryItemModel
import com.em_projects.grocery.model.remote.DataWrapper
import org.json.JSONObject
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI
import java.net.URISyntaxException


class GroceryViewModel : ViewModel() {
    private  var webSocketClient: WebSocketClient? = null
    private val groceryWsUrl = "wss://superdo-groceries.herokuapp.com/receive"


    fun connect(): LiveData<DataWrapper<GroceryItemModel>> {
        var uri: URI? = null
        val liveData: MutableLiveData<DataWrapper<GroceryItemModel>> = MutableLiveData()
        val dataWrapper: DataWrapper<GroceryItemModel> = DataWrapper()
        try {
            // Connect to local host
            uri = URI(groceryWsUrl)
        } catch (e: URISyntaxException) {
            dataWrapper.throwable = e
            liveData.postValue(dataWrapper)
        }
        webSocketClient = object : WebSocketClient(uri) {

            override fun onOpen() {
                Log.i("WebSocket", "Session is starting")
            }

            override fun onTextReceived(s: String) {
                Log.i("WebSocket", "Message received")
                val json = JSONObject(s)
                dataWrapper.data = GroceryItemModel(json.getString("bagColor"), json.getString("weight"), json.getString("name"))
                liveData.postValue(dataWrapper)
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
                dataWrapper.throwable = e
                liveData.postValue(dataWrapper)
            }

            override fun onCloseReceived() {
                Log.i("WebSocket", "Closed ")
                println("onCloseReceived")
            }
        }
        webSocketClient?.setConnectTimeout(10000)
        webSocketClient?.setReadTimeout(60000)
        webSocketClient?.enableAutomaticReconnection(500)
        webSocketClient?.connect()

        return liveData
    }

    fun disconnect() {
        webSocketClient?.close()
    }
}