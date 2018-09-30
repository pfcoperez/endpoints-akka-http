package org.pfcoperez.democonsole

import akka.http.scaladsl.server.Directives._
import endpoints.akkahttp

class Server extends AdminEndpoints
  with akkahttp.server.Endpoints
  with akkahttp.server.JsonSchemaEntities {

  import org.pfcoperez.democonsole.ServersEndpoints.Server
  import org.pfcoperez.democonsole.NodesEndpoints.Node

  val aServer = Server(
    id = "server01",
    ip = "192.168.1.1"
  )

  val aNode = Node(
    id = "node01",
    name = "a",
    dockerImage = "alpine:latest"
  )

  val route = Seq(
    servers.implementedBy(_ => Seq(aServer)),
    server.implementedBy(_ => Some(aServer)),
    nodes.implementedBy(_ => Seq(aNode)),
    node.implementedBy(_ => aNode),
    Documentation.documentationRoute
  ).reduce(_ ~ _)
}
