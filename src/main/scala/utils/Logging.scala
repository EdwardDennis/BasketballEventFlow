package utils

import org.slf4j
import org.slf4j.LoggerFactory

trait Logging {
  val logger: slf4j.Logger = LoggerFactory.getLogger("application")
}
