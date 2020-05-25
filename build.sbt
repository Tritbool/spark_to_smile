name := "spark_smile_utilities"


resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
resolvers += Resolver.mavenLocal
version := "0.1"
scalaVersion := "2.12.4"
scalacOptions += "-target:jvm-1.8"
//crossScalaVersions := Seq("2.11.12", "2.12.10"),
organization := "com.tritcorp.exp"
organizationName := "Gauthier LYAN"

libraryDependencies ++= Seq(
  "com.tritcorp.exp" %% "mt4s" % "1.3.4" % "test",
  "org.apache.spark" %% "spark-core" % "2.4.5",
  "org.apache.spark" %% "spark-sql" % "2.4.5",
  "org.apache.spark" %% "spark-hive" % "2.4.5",
  "org.apache.spark" %% "spark-mllib" % "2.4.5",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "com.typesafe" % "config" % "1.4.0",
  "com.github.haifengl" %% "smile-scala" % "2.4.0"
)
test in assembly := {}
assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

