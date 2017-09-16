package com.amndeep.spoilerbot

import sx.blah.discord.api.IDiscordClient
import sx.blah.discord.api.events.EventSubscriber
import sx.blah.discord.handle.impl.events.ReadyEvent
import sx.blah.discord.handle.impl.events.guild.channel.message.MentionEvent
import sx.blah.discord.handle.impl.events.guild.channel.message.reaction.ReactionEvent
import sx.blah.discord.util.RequestBuffer

class SpoilerBot(client: IDiscordClient) {
  client.getDispatcher.registerListener(this)

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

    val text = """(?s)^<@!?\d+> (.*?) == (.*)""".r
    event.getMessage.getContent match {
      case text(replacement, spoiler) =>
        println("found\n\t" + replacement + "\n\t" + spoiler)
        RequestBuffer.request(() => { event.getMessage.delete() }) // TODO: rest of the exceptions
        RequestBuffer.request(() => { event.getMessage.getChannel.sendMessage("I was mentioned by " + event.getAuthor.mention() + ", who wanted \"" + spoiler + "\" replaced by \"" + replacement + "\"") })
      case _ => println("not found")
    }
  }

  @EventSubscriber
  def onReaction(event: ReactionEvent): Unit = {
    println("Reaction:\n\tMsg authored by: " + event.getAuthor + "\n\tcontents: " + event.getMessage.getContent + "\n\tRtn by: " + event.getUser)

    if(event.getAuthor.equals(client.getOurUser)) {
      val channel = RequestBuffer.request(() => { client.getOrCreatePMChannel(event.getUser) })
      RequestBuffer.request(() => { channel.get.sendMessage("FUCK YOU") })
    }
  }
}