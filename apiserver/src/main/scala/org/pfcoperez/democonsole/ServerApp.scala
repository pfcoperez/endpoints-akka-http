package org.pfcoperez.democonsole

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import org.pfcoperez.democonsole.allocators.Server

object ServerApp extends App {

  implicit val system = ActorSystem("demo-server-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val routes = (new Server).route

  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8080)

  scala.io.StdIn.readLine() // let it run until user presses return

  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done

}
