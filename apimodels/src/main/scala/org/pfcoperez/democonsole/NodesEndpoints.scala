package org.pfcoperez.democonsole

import endpoints.{algebra, generic}

trait NodesEndpoints extends algebra.Endpoints
    with algebra.JsonSchemaEntities
    with generic.JsonSchemas {

  import NodesEndpoints._

  def base: Path[Unit]
  final def nodesBasePath: Path[Unit] = base / "nodes"

  val nodes = endpoint(get(nodesBasePath), jsonResponse[Seq[Node]]())
  val node = endpoint(get(nodesBasePath / segment[String]("id", Some("Node identifier"))), jsonResponse[Node]())

  val nodesRoutes = Seq(nodes, node)

  implicit lazy val jsonSchemaNode: JsonSchema[Node] = genericJsonSchema
}

object NodesEndpoints {
  case class Node(id: String, name: String, dockerImage: String)
}
