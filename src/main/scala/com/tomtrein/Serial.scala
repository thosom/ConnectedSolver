package com.tomtrein

import java.io.{InputStream, PipedInputStream, PipedOutputStream, PrintWriter}
import java.util.Scanner

import com.fazecast.jSerialComm._

object Serial{

  private var serial : Serial = _
  private var isOpen : Boolean = _
  def in: InputStream = if (isOpen) serial.in else throw new Exception("Cannot use unopened serial")
  def scanner: Scanner = if (isOpen) serial.scanner else throw new Exception("Cannot use unopened serial")
  def out: PrintWriter = if (isOpen) serial.out else throw new Exception("Cannot use unopened serial")


  def open[T](port: String,splitter:String)(code: => T) : T = {
    this.serial = new Serial(splitter)
    if (!serial.open(port))
      throw new Exception("Cannot open serial on: "+port)
    isOpen = true
    val ret: T = try {
      code
    } catch {
      case e: Exception => {
        isOpen = false; serial.close(); throw e
      }
      case e : Error => {
        isOpen = false; serial.close(); throw e
      }
    }
    isOpen = false
    if (!serial.close())
      throw new Exception("Cannot close serial on: "+port)
    ret
  }

  def open[T](port : String)(code :  => T) : T = {
    this.serial = new Serial()
    if (!serial.open(port))
      throw new Exception("Cannot open serial on: "+port)
    isOpen = true
    val ret: T = try {
      code
    } catch {
      case e: Exception => {
        isOpen = false; serial.close(); throw e
      }
      case e : Error => {
        isOpen = false; serial.close(); throw e
      }
    }
    isOpen = false
    if (!serial.close())
      throw new Exception("Cannot close serial on: "+port)
    ret
  }

}



class Serial(val splitter : String) {

  def this(){
    this(null)
  }


  private var sp : SerialPort = _

  private val pipeout = new PipedOutputStream()
  private val m_in = new PipedInputStream(pipeout)
  private var m_out : PrintWriter = _
  private var m_scanner : Scanner = new Scanner(m_in)

  def out : PrintWriter = m_out
  def in : InputStream = m_in
  def scanner : Scanner = m_scanner



  def open(port : String): Boolean ={
    sp = SerialPort.getCommPort(port)
    sp.setComPortParameters(9600, 8, 1, 0)
    sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0)
    if (!sp.openPort())
      return false
    m_out = new PrintWriter(sp.getOutputStream){
      override def println(): Unit = {
        if (splitter != null){
          super.print(splitter)
          super.flush()
        } else {
          super.println()
        }
      }
    }
    sp.addDataListener(new SerialPortDataListener {
      override def getListeningEvents: Int = SerialPort.LISTENING_EVENT_DATA_RECEIVED | SerialPort.LISTENING_EVENT_DATA_AVAILABLE

      override def serialEvent(event: SerialPortEvent): Unit = {
        //println("TEST")
        pipeout.write(event.getReceivedData)
        pipeout.flush()
      }
    })
    sp.isOpen
  }

  def close(): Boolean ={
    sp.removeDataListener()
    sp.closePort()
  }

}
