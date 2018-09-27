package org.pfcoperez.democonsole

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{HttpEntity, MediaTypes}
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import endpoints.algebra.Documentation
import endpoints.{akkahttp, algebra}
import org.pfcoperez.democonsole.NodesEndpoints.Node
import io.circe.parser

class Server extends AdminEndpoints
  with akkahttp.server.Endpoints
  //with play.server.Endpoints
  //with akkahttp.server.JsonEntitiesFromCodec with algebra.JsonSchemaEntities with endpoints.circe.JsonSchemas {
  //with play.server.circe.JsonSchemaEntities {
 with algebra.JsonSchemaEntities with endpoints.circe.JsonSchemas {

  def jsonRequest[A : JsonSchema](docs: Documentation): RequestEntity[A] = {

    implicit val fromEntityUnmarshaller: FromEntityUnmarshaller[A] =
      Unmarshaller.stringUnmarshaller
        .forContentTypes(MediaTypes.`application/json`)
        .map {
          data => parser
            .parse(data)
            .right
            .flatMap(implicitly[JsonSchema[A]].decoder.decodeJson)
            .fold(throw _, identity)
        }

    Directives.entity[A](implicitly)
  }

  def jsonResponse[A : JsonSchema](docs: Documentation): Response[A] = { a =>
    implicit val toEntityMarshaller: ToEntityMarshaller[A] =
      Marshaller.withFixedContentType(MediaTypes.`application/json`) { value =>
        val encoder = implicitly[JsonSchema[A]].encoder
        val encoded = encoder.apply(value).toString
        HttpEntity(MediaTypes.`application/json`, encoded)
      }
    Directives.complete(a)
  }

  import org.pfcoperez.democonsole.ServersEndpoints.Server
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
    server.implementedBy(_ => aServer),
    nodes.implementedBy(_ => Seq(aNode)),
    node.implementedBy(_ => aNode)
  ).reduce(_ ~ _)
}
