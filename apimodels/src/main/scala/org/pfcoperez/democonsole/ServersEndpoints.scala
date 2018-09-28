package org.pfcoperez.democonsole

import endpoints.{algebra, generic}

trait ServersEndpoints extends algebra.Endpoints
  with algebra.JsonSchemaEntities
  with generic.JsonSchemas {

  import ServersEndpoints._

  def base: Path[Unit]
  final def serversBasePath: Path[Unit] = base / "platform" / "servers"

  val servers = endpoint(get(serversBasePath), jsonResponse[Seq[Server]]())
  val server = endpoint(get(serversBasePath / segment[String]("id", Some("Server identifier"))), jsonResponse[Server]())

  val serverRoutes = Seq(servers, server)

  implicit lazy val jsonSchemaServer: JsonSchema[Server] = genericJsonSchema
}

object ServersEndpoints {
  case class Server(id: String, ip: String)
}
