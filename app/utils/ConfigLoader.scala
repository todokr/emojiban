package utils

import java.io.InputStreamReader
import com.typesafe.config.{Config, ConfigFactory}
import scala.collection.JavaConverters._

trait AppConfigProvider {
  lazy implicit val config = AppConfig.loadAppConfig
}

case class AppConfig(
  s3Url: String
)

object AppConfig {
  import ConfigLoader._

  private val config = loadConfig()

  private[utils] def loadAppConfig: AppConfig = {
    AppConfig(
      s3Url = config.getString("aws.s3.url")
    )
  }
}

/**
  * 設定ファイルを読み込むためのメソッドを提供するオブジェクト。
  */
private object ConfigLoader {
  def loadConfig(): Config = {
    val in = new InputStreamReader(Thread.currentThread.getContextClassLoader.getResourceAsStream("application.conf"), "UTF-8")
    ConfigFactory.parseReader(in)
  }

  implicit class RichConfig(config: Config){
    /**
      * 初期値を指定して設定値を取得します。
      */
    def getString(key: String, defaultValue: String): String = {
      if(config.hasPath(key)){
        config.getString(key)
      } else {
        defaultValue
      }
    }

    /**
      * 初期値を指定して設定値を取得します。
      */
    def getStringSeq(key: String, defaultValues: Seq[String]): Seq[String] = {
      if(config.hasPath(key)){
        config.getStringList(key).asScala.toSeq
      } else {
        defaultValues
      }
    }

    /**
      * 設定値をOptionで取得します。設定が存在しない場合はNoneを返します。
      */
    def getOptionString(key: String): Option[String] = {
      if(config.hasPath(key)){
        Some(config.getString(key))
      } else {
        None
      }
    }
  }
}