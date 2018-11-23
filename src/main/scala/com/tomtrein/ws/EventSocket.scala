package com.tomtrein.ws

import org.eclipse.jetty.websocket.api.{Session, WebSocketAdapter, WebSocketException}


class EventSocket extends WebSocketAdapter {

    var session : Session = null

    override def onWebSocketConnect(sess: Session): Unit = {
        super.onWebSocketConnect(sess)
        System.out.println("Socket Connected: " + sess)
        session = sess
        sess.getRemote.sendString("HELLO")
    }

    override def onWebSocketText(message: String): Unit = {
        super.onWebSocketText(message)
        System.out.println("Received TEXT message: " + message)
        session.getRemote.sendString("Received TEXT message: " + message)
    }

    override def onWebSocketClose(statusCode: Int, reason: String): Unit = {
        super.onWebSocketClose(statusCode, reason)
        System.out.println("Socket Closed: [" + statusCode + "] " + reason)
    }

    override def onWebSocketError(cause: Throwable): Unit = {
        super.onWebSocketError(cause)
        cause.printStackTrace(System.err)
    }
}