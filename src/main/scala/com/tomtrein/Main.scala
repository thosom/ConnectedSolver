package com.tomtrein

import java.io.PrintWriter
import java.util.Scanner

import com.fazecast.jSerialComm.SerialPort
import com.tomtrein.ws.EventServer


object Main {

  def main(args: Array[String]): Unit = {
    webservermain()
  }

  def webservermain(): Unit ={
    Serial.open("/dev/ttyUSB2",","){
      EventServer.run()
    }
  }


  def oldmain(args: Array[String]): Unit = {

    Serial.open("/dev/ttyUSB2"){
      val coin = new Scanner(System.in)
      while (true){

        //println("Serial has ?")
        while (Serial.in.available() > 0 && Serial.scanner.hasNextLine){
          //println("YES")
          println(Serial.scanner.nextLine())
        }

        //println("HasNext?")
        if (System.in.available()>0){
          var char = System.in.read()
          if (char == '\n'){
            char = ' '
          }
          Serial.out.write(char)
          Serial.out.flush()
          //println("Sent")
        }
        //println("Noline")
        Thread.sleep(100)
      }
    }
  }

}
