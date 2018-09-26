package org.pfcoperez.democonsole.allocators

import endpoints.algebra.Endpoints
import endpoints.algebra.circe.JsonEntitiesFromCodec
import io.circe.generic.auto._

trait DummyModel extends Endpoints with JsonEntitiesFromCodec {
  import DummyModel._

  def base: Path[Unit]

  val ping = endpoint(get(base/"ping"), jsonResponse[Message]())
  val pong = endpoint(get(base/"pong"), jsonResponse[Message]())
}

trait EvenDummierModel extends Endpoints with JsonEntitiesFromCodec {

  def base: Path[Unit]

  val pim = endpoint(get(base/"pim"), emptyResponse())
  val pum = endpoint(get(base/"pum"), emptyResponse())

}

trait CommandsModel extends Endpoints with DummyModel with EvenDummierModel {
  def base: Path[Unit] = path / "commands"
}

object DummyModel {
  case class Message(content: String)
}
