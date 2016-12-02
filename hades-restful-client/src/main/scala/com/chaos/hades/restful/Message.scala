package com.chaos.hades.restful

/**
  * Created by zcfrank1st on 01/12/2016.
  */
final case class Message (code: Int, value: String)

final case class MapMessage[T] (code: Int, value: Map[T, T])
