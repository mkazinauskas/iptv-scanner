package com.modzo.iptv.scanner.integration

import com.modzo.iptv.scanner.database.Channel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class VerifyChannelSpec extends IntegrationSpec {

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