package com.tomtrein.ws

import com.tomtrein.ws.EventSocket
import org.eclipse.jetty.websocket.servlet.WebSocketServlet
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory


@SuppressWarnings(Array("serial")) class EventServlet extends WebSocketServlet {
    override def configure(factory: WebSocketServletFactory): Unit = {
        factory.register(classOf[EventSocket])
    }
}
