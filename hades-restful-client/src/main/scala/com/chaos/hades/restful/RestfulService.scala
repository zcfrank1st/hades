package com.chaos.hades.restful

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.chaos.hades.core.Hades.HadesBuilder
import com.chaos.hades.core.HadesProfile
import com.typesafe.scalalogging.Logger

import scala.io.StdIn

/**
  * Created by zcfrank1st on 01/12/2016.
  */
object RestfulService extends JsonSupport with ConfigModule {
  val mainLogger = Logger("hades-restful")
  val connections = config.getString("hades.connections")

  val getConfigRouter =
    pathPrefix ("config" / Segment / Segment / Segment) { (env, project, key) =>
      path ("") {
        get {
          try {
            val hades = new HadesBuilder().connections(connections).build()
            if ("prod" == env) {
              hades.profile(HadesProfile.PRD)
            } else {
              hades.profile(HadesProfile.DEV)
            }
            val value = hades.getConf(project, key)
            hades.destroy()
            complete {
              Message(0, value)
            }
          } catch {
            case e: Throwable =>
              mainLogger.error(s"error request ${e.getMessage}")
              complete {
                Message(1, "")
              }
          }
        }
      }
    }

  val getAllConfigsRouter =
    pathPrefix("configs" / Segment / Segment) { (env, project) =>
      path("") {
        get {
          try {
            val hades = new HadesBuilder().connections(connections).build()
            if ("prod" == env) {
              hades.profile(HadesProfile.PRD)
            } else {
              hades.profile(HadesProfile.DEV)
            }
            import scala.collection.JavaConverters._
            val value = hades.scanProjectConf(project).asScala
            hades.destroy()
            complete {
              MapMessage[String](0, value.toMap)
            }
          } catch {
            case e: Throwable =>
              mainLogger.error(s"error request ${e.getMessage}")
              complete {
                MapMessage[String](1, Map())
              }
          }
        }
      }
    }

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("hades-restful")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val port = config.getInt("hades.restful.port")

    val bindingFuture = Http().bindAndHandle(getConfigRouter ~ getAllConfigsRouter, "0.0.0.0", port)

    mainLogger.info(s"Server is running on port " + port  + " ... Press RETURN to stop...")

    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
