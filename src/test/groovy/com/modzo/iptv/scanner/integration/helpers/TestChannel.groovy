package com.modzo.iptv.scanner.integration.helpers

import com.modzo.iptv.scanner.database.Channel
import com.modzo.iptv.scanner.database.Channels
import com.modzo.iptv.scanner.database.commands.CreateChannelHandler
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TestChannel {
    private final CreateChannelHandler handler

    private final Channels channels

    TestChannel(CreateChannelHandler handler, Channels channels) {
        this.handler = handler
        this.channels = channels
    }

    @Transactional
    Channel create(String name = randomName(),
                   int soundTrack = randomSoundTrack(),
                   String url = randomUrl()) {
        CreateChannelHandler.Response result = handler.handle(new CreateChannelHandler.Request(name, soundTrack, url))
        return channels.findById(result.id).get()
    }

    static String randomName() {
        return RandomStringUtils.randomAlphanumeric(10)
    }

    static int randomSoundTrack() {
        return RandomStringUtils.randomNumeric(7) as int
    }

    static String randomUrl() {
        int randomPort = RandomStringUtils.randomNumeric(4) as int
        return "udp://@233.136.41.188:${randomPort}"
    }
}
