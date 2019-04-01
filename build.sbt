
resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.4.0-SNAPSHOT"
)