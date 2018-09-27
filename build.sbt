name := "endpoints-akka-http"

lazy val commonSettings = Seq(
  organization := "org.pfcoperez",
  version := "0.1",
  scalaVersion := "2.12.6"
)

lazy val endpointsVersion = "0.6.0"
val circeVersion = "0.9.3"

lazy val endpointsAlgebra = "org.julienrf" %% "endpoints-algebra" % endpointsVersion
lazy val endpointsGeneric = "org.julienrf" %% "endpoints-json-schema-generic" % endpointsVersion
lazy val endpointsOpenApi = "org.julienrf" %% "endpoints-openapi" % endpointsVersion
lazy val endpointsCirce = Seq(
  "org.julienrf" %% "endpoints-algebra-circe" % endpointsVersion,
  "org.julienrf" %% "endpoints-json-schema-circe" % endpointsVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion
)
lazy val endpointsDependencies = Seq(
  endpointsAlgebra,
  endpointsOpenApi,
  endpointsGeneric
) ++ endpointsCirce

lazy val apimodels = (project in file("apimodels"))
  .settings(
    commonSettings,
    libraryDependencies ++= Seq(
      endpointsGeneric,
      endpointsOpenApi,
      endpointsAlgebra
    )
  )

lazy val apiserver = (project in file("apiserver"))
  .dependsOn(apimodels)
  .settings(
    commonSettings,
    libraryDependencies ++= endpointsDependencies,
    libraryDependencies ++= Seq(
      "org.julienrf" %% "endpoints-akka-http-server" % endpointsVersion
    )
  )

lazy val root = (project in file("."))
  .aggregate(apimodels, apiserver)

