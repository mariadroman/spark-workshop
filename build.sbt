name := "spark-workshop"

lazy val sparkWorkshop = project
  .in(file("."))
  .settings(
    settings,
    libraryDependencies ++= library.dependencies
  )

// Dependencies ========================================================================================================

lazy val library = new {

  val version = new {
    val scala          = "2.11.12"
    val scalaTest      = "3.0.1"
    val scalaCheck     = "1.13.5"
    val log4j          = "2.8.1"
    val typesafeConfig = "1.3.1"
    val spark          = "2.4.0"
  }

  val scalaCheck      = "org.scalacheck"             %% "scalacheck"      % version.scalaCheck % Test
  val scalaTest       = "org.scalatest"              %% "scalatest"       % version.scalaTest % Test
  val typesafeConfig  = "com.typesafe"               % "config"           % version.typesafeConfig
  val log4jCore       = "org.apache.logging.log4j"   % "log4j-core"       % version.log4j
  val log4j           = "org.apache.logging.log4j"   % "log4j-api"        % version.log4j
  val spark           = "org.apache.spark"           %% "spark-sql"       % version.spark

  val log = Seq(log4j, log4jCore)

  val dependencies = Seq(scalaCheck, scalaTest, typesafeConfig, spark)
}

// Settings ============================================================================================================

lazy val settings = Seq(
  scalaVersion := library.version.scala,
  crossScalaVersions := Seq(scalaVersion.value, library.version.scala),
  organization := "com.lunatech",
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-language:_",
    "-target:jvm-1.8",
    "-encoding",
    "UTF-8",
    "-feature"
  ),
  updateOptions := updateOptions.value.withGigahorse(false) // https://github.com/sbt/sbt/issues/3570
)
