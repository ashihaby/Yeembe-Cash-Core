package scorex.utils

import monix.eval.Task
import monix.execution.Scheduler
import org.slf4j.{Logger, LoggerFactory}

case class LoggerFacade(logger: Logger) {
  def trace(message: => String): Unit = {
    if (logger.isTraceEnabled)
      logger.trace(message)
  }

  def debug(message: => String, arg: Any): Unit = {
    if (logger.isDebugEnabled)
      logger.debug(message, arg)
  }

  def debug(message: => String): Unit = {
    if (logger.isDebugEnabled)
      logger.debug(message)
  }

  def info(message: => String): Unit = {
    if (logger.isInfoEnabled)
      logger.info(message)
  }

  def info(message: => String, arg: Any): Unit = {
    if (logger.isInfoEnabled)
      logger.info(message, arg)
  }

  def info(message: => String, throwable: Throwable): Unit = {
    if (logger.isInfoEnabled)
      logger.info(message, throwable)
  }

  def warn(message: => String): Unit = {
    if (logger.isWarnEnabled)
      logger.warn(message)
  }

  def warn(message: => String, throwable: Throwable): Unit = {
    if (logger.isWarnEnabled)
      logger.warn(message, throwable)
  }

  def error(message: => String): Unit = {
    if (logger.isErrorEnabled)
      logger.error(message)
  }

  def error(message: => String, throwable: Throwable): Unit = {
    if (logger.isErrorEnabled)
      logger.error(message, throwable)
  }
}

trait ScorexLogging {
  protected def log = LoggerFacade(LoggerFactory.getLogger(this.getClass))

  implicit class TaskExt[A](t: Task[A]) {
    implicit def runAsyncLogErr(implicit s: Scheduler): Unit = {
      t.onErrorHandle[Any]((th: Throwable) => log.error("Error executing task", th)).runAsync
    }
  }

}
