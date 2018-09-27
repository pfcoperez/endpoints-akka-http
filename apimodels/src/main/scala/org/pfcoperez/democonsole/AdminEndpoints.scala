package org.pfcoperez.democonsole

import endpoints.algebra.Endpoints

trait AdminEndpoints extends Endpoints
  with ServersEndpoints
  with NodesEndpoints {

  override def base: Path[Unit] = path / "admin"

  val adminRoutes = serverRoutes ++ nodesRoutes

}
