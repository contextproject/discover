name := "explore"

version := "0.13.8"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.35"
)

play.Project.playJavaSettings
