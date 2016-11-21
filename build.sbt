name := "emojiban"

version := "1.0"

lazy val `emojiban` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  filters,
  "commons-codec"       % "commons-codec"                % "1.10",
  "org.scalikejdbc"    %% "scalikejdbc"                  % "2.5.0",
  "org.scalikejdbc"    %% "scalikejdbc-config"           % "2.5.0",
  "org.scalikejdbc"    %% "scalikejdbc-play-initializer" % "2.5.1",
  "org.scalikejdbc"    %% "scalikejdbc-jsr310"           % "2.5.0",
  "org.scalaz"         %% "scalaz-core"                  % "7.2.2",
  "mysql"               % "mysql-connector-java"         % "5.1.36",
  "com.h2database"      % "h2"                           % "1.4.193",
  "com.github.seratch" %% "awscala"                      % "0.5.8",
  "org.flywaydb"       %% "flyway-play"                  % "2.2.0",
  "ch.qos.logback"      % "logback-classic"              % "1.1.7",
  specs2 % Test
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

scalikejdbcSettings
