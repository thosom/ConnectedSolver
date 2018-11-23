package com.tomtrein

import org.eclipse.jetty.websocket.api.{Session, WebSocketAdapter}

class SerialWebSocket extends WebSocketAdapter {

  var session : Session = null

  override def onWebSocketConnect(sess: Session): Unit = {
    super.onWebSocketConnect(sess)
    System.out.println("Socket Connected: " + sess)
    session = sess
    sess.getRemote.sendString("HELLO")
  }

  override def onWebSocketText(message: String): Unit = {
    super.onWebSocketText(message)
    Serial.out.println(message)
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
