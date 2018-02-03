package com.citi.dw.actors.backoff3

import akka.actor._
import akka.pattern._
import scala.util.Random

import scala.concurrent.duration._

object DeadLetterMonitor {
  def props(parentRef: ActorRef) = Props(new DeadLetterMonitor(parentRef))
}
class DeadLetterMonitor(receiver: ActorRef) extends Actor with ActorLogging {
  import InnerChild._
  import context.dispatcher
  override def receive: Receive = {
    case DeadLetter(msg,sender,_) =>
      //wait till InnerChild finishes restart then resend
      context.system.scheduler.scheduleOnce(1 second,receiver,msg.asInstanceOf[TestMessage])
  }
}