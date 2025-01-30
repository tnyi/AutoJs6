package org.autojs.autojs.core.websocket

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.autojs.autojs.core.console.LogCallback

class WebSocketManager private constructor() {
    private val TAG = "WebSocketManager"
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null
    private var logCallback: LogCallback? = null
    private var scriptName: String? = null
    
    fun connect(baseUrl: String, scriptName: String) {
        this.scriptName = scriptName
        val url = if (baseUrl.endsWith("/")) baseUrl.dropLast(1) else baseUrl
        
        Log.d(TAG, "Connecting to WebSocket: $url")
        
        val request = Request.Builder()
            .url(url)
            .build()
            
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d(TAG, "WebSocket connected")
                setupLogCallback()
            }
            
            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e(TAG, "WebSocket connection failed", t)
            }
        })
    }
    
    private fun setupLogCallback() {
        logCallback = object : LogCallback {
            override fun onLog(level: String, message: String) {
                Log.d(TAG, "Sending log: $message")
                webSocket?.send(message)
            }
        }
        org.autojs.autojs.core.console.ConsoleImpl.setGlobalLogCallback(logCallback)
    }
    
    fun disconnect() {
        Log.d(TAG, "Disconnecting WebSocket for script: $scriptName")
        webSocket?.close(1000, "Script Finished")
        webSocket = null
        org.autojs.autojs.core.console.ConsoleImpl.setGlobalLogCallback(null)
        logCallback = null
        scriptName = null
    }
    
    companion object {
        private var instance: WebSocketManager? = null
        
        @JvmStatic
        fun getInstance(): WebSocketManager {
            if (instance == null) {
                instance = WebSocketManager()
            }
            return instance!!
        }
    }
} 