import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.navneetgupta",
      scalaVersion := "2.12.6",
      version      := "0.0.1"
    )),
    name := "file-upload-akka",
    libraryDependencies ++= {
	  val akkaVersion = "2.5.13"
	  Seq(
	  	"com.typesafe.akka" %% "akka-actor" % akkaVersion,
	    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
	    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
	    "com.typesafe.akka" %% "akka-http"   % "10.1.3",
	    "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3",
		"com.typesafe.akka" %% "akka-http-testkit" % "10.1.3"
	  )
	}
  )
