package endpoints.akkahttp.server.circe

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.{HttpEntity, MediaTypes}
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import endpoints.akkahttp.server.Endpoints
import endpoints.algebra
import endpoints.algebra.Documentation
import io.circe.parser

trait JsonSchemaEntities extends Endpoints with algebra.JsonSchemaEntities with endpoints.circe.JsonSchemas {

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

}
