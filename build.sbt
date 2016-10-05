name := "SparkGA"
version := "0.1"
scalaVersion := "2.11.8"
libraryDependencies ++=
Seq("org.apache.spark" % 
    "spark-core_2.10" % "2.0.0" % "provided", "joda-time" % "joda-time" % "2.8.2")
assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
