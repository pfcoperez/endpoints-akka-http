package org.pfcoperez.democonsole.allocators

import endpoints.akkahttp.server
import org.pfcoperez.democonsole.allocators.DummyModel.Message

import akka.http.scaladsl.server.Directives._


class Server extends CommandsModel
  with server.Endpoints
  with server.JsonEntitiesFromCodec {

  //val route = ping.implementedBy(_ => Message("hello"))
  val a = ping.implementedBy(_ => Message("ping"))
  val b = pong.implementedBy(_ => Message("pong"))
  val c = pim.implementedBy(_ => Message("pim"))
  val d = pum.implementedBy(_ => Message("pum"))

  val route = List(a, b, c, d).reduce(_ ~ _)

}
