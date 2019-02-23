package com.modzo.iptv.scanner.channel.create

import com.modzo.iptv.scanner.IntegrationSpec
import com.modzo.iptv.scanner.domain.Channel
import com.modzo.iptv.scanner.channel.helpers.TestChannel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class CreateChannelIntegrationSpec extends IntegrationSpec {

    void 'should create new channel in database'() {
        given:
            CreateChannelRequest request = new CreateChannelRequest(
                    TestChannel.randomName(),
                    TestChannel.randomSoundTrack(),
                    TestChannel.randomUri()
            )
        when:
            ResponseEntity<String> response = restTemplate.postForEntity('/channels', request, String)
        then:
            response.statusCode == HttpStatus.CREATED
        and:
            Long savedChannelId = response.headers.getLocation().toString().split('/').last() as Long
            Channel savedChannel = channels.findById(savedChannelId).get()
            savedChannel.creationDate
            savedChannel.name == request.name
            savedChannel.soundTrack == request.soundTrack
            savedChannel.uri == request.uri
    }
}