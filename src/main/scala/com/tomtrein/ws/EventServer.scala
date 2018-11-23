package com.tomtrein.ws

import com.tomtrein.ws.EventServlet
import com.tomtrein.ws.EventServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder


object EventServer {
    def run(): Unit = {
        val server = new Server
        val connector = new ServerConnector(server)
        connector.setPort(8080)
        server.addConnector(connector)
        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        val context = new ServletContextHandler(ServletContextHandler.SESSIONS)
        context.setContextPath("/")
        server.setHandler(context)
        // Add a websocket to a specific path spec
        val holderEvents = new ServletHolder("ws-events", classOf[EventServlet])
        context.addServlet(holderEvents, "/events/*")
        try {
            server.start()
            server.dump(System.err)
            server.join()
        } catch {
            case t: Throwable =>
                t.printStackTrace(System.err)
        }
    }
}

