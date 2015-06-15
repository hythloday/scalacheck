package org.scalacheck.futures

import java.util.{TimerTask, Timer}

import Futures._

import org.scalacheck.{Prop, Properties}
import org.scalacheck.Prop._

import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.concurrent.{Future, Promise}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success


object FuturesSpecification extends Properties("Futures") {

  property("F[Prop] => Prop") = forAll { (n: Int) =>
    Future(n).map(f => Prop(f == n))
  }

  property("F[Boolean] => Prop") = forAll { (n: Int) =>
    Future(n).map(f => f == n)
  }

  property("Override timeouts") = {
    implicit val timeout: Duration = 1.second
    val timer = new Timer
    forAll { (n: Int) =>
      val p = Promise[Boolean]()
      timer.schedule(new TimerTask {
        override def run(): Unit = p.complete(Success(true))
      }, 200)
      p.future
    }
  }
}
