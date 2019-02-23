package com.modzo.iptv.scanner.channel.verify

import com.modzo.iptv.scanner.IntegrationSpec
import com.modzo.iptv.scanner.domain.Channel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class VerifyChannelIntegrationSpec extends IntegrationSpec {

    void 'should verify if channel is working'() {
        given:
            long savedChannelId = testChannel.create().id
        when:
            ResponseEntity<String> verifyResponse = restTemplate.postForEntity(
                    "/channels/${savedChannelId}/verify", null, String
            )
        then:
            verifyResponse.statusCode == HttpStatus.ACCEPTED

            Channel savedChannel = channels.findById(savedChannelId).get()
            savedChannel.status == Channel.Status.WORKING
    }
}