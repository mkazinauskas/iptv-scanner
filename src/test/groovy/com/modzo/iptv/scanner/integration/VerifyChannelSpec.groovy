package com.modzo.iptv.scanner.integration

import com.modzo.iptv.scanner.channel.create.CreateChannelRequest
import com.modzo.iptv.scanner.database.Channel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.testcontainers.shaded.org.apache.commons.lang.RandomStringUtils

class VerifyChannelSpec extends IntegrationSpec {

    void 'should verify if channel is working'() {
        given:
            CreateChannelRequest request = new CreateChannelRequest(
                    RandomStringUtils.randomAlphanumeric(10),
                    -1,
                    'udp://@233.136.41.188:1234'
            )
        when:
            ResponseEntity<String> createResponse = restTemplate.postForEntity('/channels', request, String)
        then:
            createResponse.statusCode == HttpStatus.CREATED
            Long savedChannelId = createResponse.headers.getLocation().toString().split('/').last() as Long
        when:
            ResponseEntity<String> verifyResponse = restTemplate.postForEntity(
                    "/channels/${savedChannelId}/verification", null, String
            )
        then:
            verifyResponse.statusCode == HttpStatus.ACCEPTED

            Channel savedChannel = channels.findById(savedChannelId).get()
            savedChannel.working
    }
}