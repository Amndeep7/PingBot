package com.amndeep.pingbot

import java.io.File

import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent
import sx.blah.discord.util.RequestBuffer
import sx.blah.discord.util.audio.AudioPlayer

class PingBot(client: IDiscordClient) {
  client.getDispatcher.registerListener(this)

  var on: Boolean = false

  @EventSubscriber
  def onReady(event: ReadyEvent): Unit = {
    println("Is ready")
  }

  // syntax: @bot replacement text == spoiler text
  @EventSubscriber
  def onMention(event: MentionEvent): Unit = {
    println("Mentioned:\n\tby: " + event.getAuthor + "\n\tcontents: " + event.getMessage.getContent)

    if(event.getAuthor.equals(client.getOurUser.getLongID)) {
      println("Don't do recursion")
      return
    }

    val voice = event.getAuthor.getVoiceStateForGuild(event.getGuild).getChannel

    if(on) {
      on = !on
      voice.leave()
    } else {
      on = !on

      voice.join()

      val ping = new File("mia_ping.mp3")

      val player = new AudioPlayer(event.getGuild)
      player.setVolume(0.5f)

      val track = player.queue(ping)
    }
  }
}