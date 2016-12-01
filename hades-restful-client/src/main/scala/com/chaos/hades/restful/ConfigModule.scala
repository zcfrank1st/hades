package com.chaos.hades.restful

import com.typesafe.config.ConfigFactory

trait ConfigModule {
  val config = ConfigFactory.load()
}