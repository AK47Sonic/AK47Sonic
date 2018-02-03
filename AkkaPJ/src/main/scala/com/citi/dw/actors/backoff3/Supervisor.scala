package com.citi.dw.actors.backoff3

import akka.actor._
import akka.pattern._
import scala.util.Random

import scala.concurrent.duration._

object Supervisor {
  def props: Props = { //在这里定义了监管策略和child Actor构建
    def decider: PartialFunction[Throwable, SupervisorStrategy.Directive] = {
      case InnerChild.CException(tmsg) =>
        println(s"Message causing exception: ${tmsg.msg}") //we can extract message here
        BackoffSupervisorDemo.sendToParent(tmsg)  //resend message
        SupervisorStrategy.Restart
    }

    val options = Backoff.onFailure(InnerChild.props, "innerChild", 1 second, 5 seconds, 0.0)
      .withManualReset
      .withSupervisorStrategy(
        OneForOneStrategy(maxNrOfRetries = 5, withinTimeRange = 5 seconds)(
          decider.orElse(SupervisorStrategy.defaultDecider)
        )
      )
    BackoffSupervisor.props(options)
  }
}