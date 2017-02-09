package controllers

import com.google.inject.Inject
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, RequestHeader}
import services.ApiSampleService

import scala.concurrent.{ExecutionContext, Future}


/**
  * Created by nishiyama on 2016/12/05.
  */
class IndexController @Inject() (
  service: ApiSampleService
) (
  implicit ec: ExecutionContext
) extends Controller {
  import com.stanby.trace.play25.implicits.TraceImplicits._


  def index = Action.async { implicit req =>
    Future.successful(Ok(Json.obj("status" -> "ok")))
  }


  def once = Action.async { implicit req =>
    Logger.debug(req.headers.toSimpleMap.map{ case (k, v) => s"${k}:${v}"}.toSeq.mkString("\n"))

    service.sample("http://localhost:9992/api/once").map(_ => Ok(Json.obj("OK"->"OK")))

  }

  def nest = Action.async { implicit req =>
    Logger.debug(req.headers.toSimpleMap.map{ case (k, v) => s"${k}:${v}"}.toSeq.mkString("\n"))

    service.sample("http://localhost:9992/api/nest").map(v => Ok(Json.obj("result" -> v)))
  }
}
