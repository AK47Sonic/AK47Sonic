package com.citi.dw.actors.backoff

import akka.actor._
import akka.pattern._
import scala.concurrent.duration._

//注意：下面是Supervisor的父级，不是InnerChild的父级
object ParentalActor {
  case class SendToSupervisor(msg: InnerChild.TestMessage)
  case class SendToInnerChild(msg: InnerChild.TestMessage)
  case class SendToChildSelection(msg: InnerChild.TestMessage)
  def props = Props[ParentalActor]
//  def props = Props(classOf[ParentalActor])
}
class ParentalActor extends Actor with ActorLogging {
  import ParentalActor._
  //在这里构建子级Actor supervisor
  val supervisor = context.actorOf(Supervisor.props,"supervisor")
  supervisor ! BackoffSupervisor.getCurrentChild //要求supervisor返回当前子级Actor
  var innerChild: Option[ActorRef] = None   //返回的当前子级ActorRef
  val selectedChild = context.actorSelection("/user/parent/supervisor/innerChild")
  override def receive: Receive = {
    case BackoffSupervisor.CurrentChild(ref) =>   //收到子级Actor信息
      innerChild = ref
    case SendToSupervisor(msg) => supervisor ! msg
    case SendToChildSelection(msg) => selectedChild ! msg
    case SendToInnerChild(msg) => innerChild foreach(child => child ! msg)
  }

}