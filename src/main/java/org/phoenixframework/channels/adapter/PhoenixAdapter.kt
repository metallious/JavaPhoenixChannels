package de.suitepad.suitetv.app.api

import com.google.gson.Gson
import de.suitepad.suitetv.model.ControllerHubMessage
import org.phoenixframework.channels.Channel
import org.phoenixframework.channels.LoggerBuilder
import org.phoenixframework.channels.Socket
import java.io.IOException

/**
 * Created by ahmed on 12/15/17.
 */
class PhoenixAdapter(deviceId : String,
                     token : String,
                     ip: String,
                     private val gson: Gson) {

    private var socket: Socket
    private lateinit var channel: Channel
    private val logger = LoggerBuilder()
            .setTag(PhoenixAdapter::class.java.name)
            .build()

    var callback: MessageCallback? = null

    init {
        socket = Socket(
                "ws://$ip/socket/websocket?token=${token}",
                30*1000
        )
        socket.connect()
        logger.info("Connecting...")
        socket.onOpen {
            logger.info("getting channel ${deviceId}")
            channel = socket.chan(
                    "suite_tv_box:${deviceId}", null
            )

            try {
                channel.join()
                        .receive("ok") { logger.info("ok") }
                        .receive("error") { logger.info("error") }
                        .receive("timeout") { logger.info("timeout") }
                logger.info("Joining channel...")
            } catch (e: IOException) {
                e.printStackTrace()
            }

            channel.on("new_instruction", { envelope ->
                run {
                    val message = gson.fromJson(
                            envelope.payload.toString(),
                            ControllerHubMessage::class.java
                    )
                    callback?.onMessage(message)
                }
            })
        }

    }

    fun close() {
        channel.leave()
        socket.disconnect()
    }

    interface MessageCallback {
        fun onMessage(message: ControllerHubMessage)
    }

}