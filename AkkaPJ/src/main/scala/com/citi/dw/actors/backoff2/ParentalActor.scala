package com.citi.dw.actors.backoff2

import akka.actor._
import akka.pattern._
import scala.concurrent.duration._

object ParentalActor {
  def props = Props[ParentalActor]
//  def props = Props(classOf[ParentalActor])
}

//注意：下面是Supervisor的父级，不是InnerChild的父级
class ParentalActor extends Actor with ActorLogging {
// import com.citi.dw.actors.backoff2.ParentalActor._
  //在这里构建子级Actor supervisor
  val supervisor = context.actorOf(Supervisor.props,"supervisor")
  override def receive: Receive = {
    case msg@ _ => supervisor ! msg
  }

}