package com.chaos.hades.restful

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsonFormat}

/**
  * Created by zcfrank1st on 01/12/2016.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val response = jsonFormat2(Message)

  implicit def mapResponseFormat[A :JsonFormat] = jsonFormat2(MapMessage.apply[A])
}
