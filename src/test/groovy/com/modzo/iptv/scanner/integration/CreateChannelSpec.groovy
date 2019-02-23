package com.modzo.iptv.scanner.integration

import com.modzo.iptv.scanner.channel.create.CreateChannelRequest
import com.modzo.iptv.scanner.database.Channel
import com.modzo.iptv.scanner.integration.helpers.TestChannel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CreateChannelSpec extends IntegrationSpec {

    void 'should create new channel in database'() {
        given:
            CreateChannelRequest request = new CreateChannelRequest(
                    TestChannel.randomName(),
                    TestChannel.randomSoundTrack(),
                    TestChannel.randomUrl()
            )
        when:
            ResponseEntity<String> response = restTemplate.postForEntity('/channels', request, String)
        then:
            response.statusCode == HttpStatus.CREATED
            Long savedChannelId = response.headers.getLocation().toString().split('/').last() as Long
            Channel savedChannel = channels.findById(savedChannelId).get()
            savedChannel.creationDate
            savedChannel.name == request.name
            savedChannel.soundTrack == request.soundTrack
            savedChannel.url == request.url
    }
}