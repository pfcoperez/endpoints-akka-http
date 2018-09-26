name := "endpoints-akka-http"

lazy val commonSettings = Seq(
  organization := "org.pfcoperez",
  version := "0.1",
  scalaVersion := "2.12.6"
)

lazy val endpointsVersion = "0.6.0"
val circeVersion = "0.9.3"

lazy val endpointsAlgebra = "org.julienrf" %% "endpoints-algebra" % endpointsVersion
lazy val endpointsCirce = Seq(
  "org.julienrf" %% "endpoints-algebra-circe" % endpointsVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)
lazy val endpointsDependencies = Seq(
  // core API
  endpointsAlgebra,
  // Akka-http server backend
  "org.julienrf" %% "endpoints-akka-http-server" % endpointsVersion
) ++ endpointsCirce

lazy val apimodels = (project in file("apimodels"))
  .settings(
    commonSettings,
    libraryDependencies ++= (endpointsAlgebra +: endpointsCirce)
  )

lazy val apiserver = (project in file("apiserver"))
  .dependsOn(apimodels)
  .settings(
    commonSettings,
    libraryDependencies ++= endpointsDependencies
  )

lazy val root = (project in file("."))
  .aggregate(apimodels, apiserver)

