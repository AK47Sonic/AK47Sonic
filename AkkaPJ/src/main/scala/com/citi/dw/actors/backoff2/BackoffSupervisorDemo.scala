package com.citi.dw.actors.backoff2

import akka.actor.ActorSystem


object BackoffSupervisorDemo extends App {
  import InnerChild._
  val testSystem = ActorSystem("testSystem")
  val parent = testSystem.actorOf(ParentalActor.props,"parent")
  
  parent ! TestMessage("Hello message 1 to supervisor")
  parent ! TestMessage("Hello message 2 to supervisor")
  parent ! TestMessage("Hello message 3 to supervisor")
  parent ! TestMessage("Hello message 4 to supervisor")
  parent ! TestMessage("Hello message 5 to supervisor")
  parent ! TestMessage("Hello message 6 to supervisor")


  scala.io.StdIn.readLine()

  testSystem.terminate()

}