package org.pfcoperez.democonsole.docgen

import endpoints.openapi
import endpoints.openapi.model.{Info, OpenApi}
import org.pfcoperez.democonsole.AdminEndpoints


object AdminDocumentation extends AdminEndpoints
  with openapi.Endpoints
  with openapi.JsonSchemaEntities {

  val api: OpenApi = openApi(
    Info(
      title = "Clusters administration API",
      version = "0.1"
    )
  )(adminRoutes:_*)

}
