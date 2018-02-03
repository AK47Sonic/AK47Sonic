package com.citi.dw.actors.backoff

import akka.actor._
import akka.pattern._
import com.citi.dw.actors.backoff.InnerChild.TestMessage

object BackoffSupervisorDemo extends App {
  import ParentalActor._
  val testSystem = ActorSystem("testSystem")
  val parent = testSystem.actorOf(ParentalActor.props,"parent")

  Thread.sleep(1000)   //wait for BackoffSupervisor.CurrentChild(ref) received

  parent ! SendToSupervisor(TestMessage("Hello message 1 to supervisor"))
  parent ! SendToInnerChild(TestMessage("Hello message 2 to innerChild"))
  parent ! SendToChildSelection(TestMessage("Hello message 3 to selectedChild"))


  scala.io.StdIn.readLine()

  testSystem.terminate()

}