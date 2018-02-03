package com.citi.dw.actors.backoff3

import akka.actor._
import akka.pattern._
import scala.util.Random

import scala.concurrent.duration._

object InnerChild {
  case class TestMessage(msg: String)
  class ChildException(val errmsg: TestMessage) extends Exception
  object CException {  //for pattern match of class with parameter
    def apply(msg: TestMessage) = new ChildException(msg)
    def unapply(cex: ChildException) = Some(cex.errmsg)
  }
  def props = Props[InnerChild]
}
class InnerChild extends Actor with ActorLogging {
  import InnerChild._
  context.parent ! BackoffSupervisor.Reset  //reset backoff counts
  override def receive: Receive = {
    case TestMessage(msg) => //模拟子级功能
      if (Random.nextBoolean())   //任意产生异常
        throw new ChildException(TestMessage(msg))
      else
        log.info(s"Child received message: ${msg}")
  }
}