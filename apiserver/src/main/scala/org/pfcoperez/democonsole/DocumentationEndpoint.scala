package org.pfcoperez.democonsole

import endpoints.openapi.model.OpenApi
import endpoints.{akkahttp, algebra}
import io.circe.generic.auto._
import org.pfcoperez.democonsole.docgen.AdminDocumentation

trait DocumentationEndpoint extends algebra.Endpoints
  with endpoints.algebra.circe.JsonEntitiesFromCodec {

  val documentationEndpoint = endpoint(get(path / "documentation.json"), jsonResponse[OpenApi]())
}

object Documentation extends DocumentationEndpoint
  with akkahttp.server.Endpoints
  with akkahttp.server.JsonEntitiesFromCodec {

  val documentationRoute = documentationEndpoint.implementedBy(_ => AdminDocumentation.api)

}

