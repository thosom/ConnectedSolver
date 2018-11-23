name := "ArduinoTest"

version := "0.1"

scalaVersion := "2.12.7"

libraryDependencies += "com.fazecast" % "jSerialComm" % "[2.0.0,3.0.0)"

libraryDependencies ++= Seq(
  "org.eclipse.jetty" % "jetty-client" % "9.1.5.v20140505",
  "org.eclipse.jetty" % "jetty-security" % "9.1.5.v20140505",
  "org.eclipse.jetty" % "jetty-server" % "9.1.5.v20140505",
  "org.eclipse.jetty.websocket" % "websocket-client" % "9.1.5.v20140505",
  "org.eclipse.jetty.websocket" % "websocket-server" % "9.1.5.v20140505"
)