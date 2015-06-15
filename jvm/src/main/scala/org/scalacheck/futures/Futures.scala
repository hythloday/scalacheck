package org.scalacheck.futures

import scala.language.implicitConversions

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

import org.scalacheck.Prop

object Futures {
  implicit val timeout: Duration = 10.millis
  implicit def futureToProp(f: Future[Prop])(implicit timeout: Duration): Prop = Await.result(f, timeout)
  implicit def futureBooleanToProp(f: Future[Boolean])(implicit timeout: Duration): Prop = Prop(Await.result(f, timeout))
}
