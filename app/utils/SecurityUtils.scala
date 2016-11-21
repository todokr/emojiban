package utils

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

import org.apache.commons.codec.binary.Hex

object SecurityUtils {

  def sha256(s: String): String = {
    val md = MessageDigest.getInstance("SHA-256")
    md.update(s.getBytes(StandardCharsets.UTF_8))
    Hex.encodeHexString(md.digest)
  }
}
