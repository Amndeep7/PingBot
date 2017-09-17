package com.amndeep.pingbot

import scala.io.Source
import scala.util.{Failure, Success, Try}

import sx.blah.discord.api.ClientBuilder

object Driver {
  def login(token: String): Try[PingBot] = {
    val builder = new ClientBuilder
    builder.withToken(token)
    Try({
      val client = builder.login
      new PingBot(client)
    })
  }

  def main(args: Array[String]): Unit = {
    val token: String = Source.fromFile("token").getLines.next()
    println(token)
    login(token) match {
      case Failure(exception) => {
        println("Login failed")
        throw exception
      }
      case Success(bot) => {
        println("Login succeeded")
        println(bot)
      }
    }
  }
}