lazy val exampleLib = project.in(file("lib"))
  .settings(
    scalaVersion := "3.0.0",
    libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.6"
  )

lazy val exampleTest = project.in(file("test"))
  .settings(
    scalaVersion := "3.0.0",
    crossScalaVersions := Seq("3.0.0", "2.13.6"),
    scalacOptions ++= {
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, 13)) => Seq("-Ytasty-reader")
        case _ => Seq.empty
      }
    },
  )
  .dependsOn(exampleLib)
