name := "emojiban"

version := "1.0"

lazy val `emojiban` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  filters,
  "org.scalikejdbc" %% "scalikejdbc"                  % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.5.0",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-jsr310"           % "2.5.0",
  "mysql"            % "mysql-connector-java"         % "5.1.36",
  "com.h2database"   % "h2"                           % "1.4.193",
  "org.flywaydb"    %% "flyway-play"                  % "2.2.0",
  "ch.qos.logback"   % "logback-classic"              % "1.1.7",
  specs2 % Test
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

scalikejdbcSettings
