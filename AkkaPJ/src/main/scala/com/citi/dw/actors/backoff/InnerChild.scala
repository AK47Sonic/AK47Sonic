package com.citi.dw.actors.backoff

import akka.actor._
import akka.pattern._

object InnerChild {
  case class TestMessage(msg: String)
  class ChildException extends Exception

  def props = Props[InnerChild]
}
class InnerChild extends Actor with ActorLogging {
  import InnerChild._
  override def receive: Receive = {
    case TestMessage(msg) => //模拟子级功能
      log.info(s"Child received message: ${msg}")
  }
}