package com.citi.dw.actors.backoff3

import akka.actor._
import akka.pattern._
import scala.util.Random

import scala.concurrent.duration._

object BackoffSupervisorDemo extends App {
  import ParentalActor._
  import InnerChild._

  def sendToParent(msg: TestMessage) = parent ! msg

  val testSystem = ActorSystem("testSystem")
  val parent = testSystem.actorOf(ParentalActor.props,"parent")

  val deadLetterMonitor = testSystem.actorOf(DeadLetterMonitor.props(parent),"dlmonitor")
  testSystem.eventStream.subscribe(deadLetterMonitor,classOf[DeadLetter]) //listen to DeadLetter

  parent ! TestMessage("Hello message 1 to supervisor")
  parent ! TestMessage("Hello message 2 to supervisor")
  parent ! TestMessage("Hello message 3 to supervisor")
  parent ! TestMessage("Hello message 4 to supervisor")
  parent ! TestMessage("Hello message 5 to supervisor")
  parent ! TestMessage("Hello message 6 to supervisor")


  scala.io.StdIn.readLine()

  testSystem.terminate()

}