name := "discover"

version := "0.13.8"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "javax.persistence" % "persistence-api" % "1.0.2"
)

play.Project.playJavaSettings