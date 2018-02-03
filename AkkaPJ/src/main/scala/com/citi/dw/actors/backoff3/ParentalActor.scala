package com.citi.dw.actors.backoff3

import akka.actor._
import akka.pattern._
import scala.util.Random

import scala.concurrent.duration._

//注意：下面是Supervisor的父级，不是InnerChild的父级
object ParentalActor {
  case class SendToSupervisor(msg: InnerChild.TestMessage)
  case class SendToInnerChild(msg: InnerChild.TestMessage)
  case class SendToChildSelection(msg: InnerChild.TestMessage)
  def props = Props[ParentalActor]
}
class ParentalActor extends Actor with ActorLogging {
  import ParentalActor._
  //在这里构建子级Actor supervisor
  val supervisor = context.actorOf(Supervisor.props,"supervisor")
  override def receive: Receive = {
    case msg@ _ => supervisor ! msg
  }

}