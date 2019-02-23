package com.modzo.iptv.scanner.channel.verify

import com.modzo.iptv.scanner.IntegrationSpec
import com.modzo.iptv.scanner.domain.Channel
import com.modzo.iptv.scanner.domain.commands.ChangeChannelStatusHandler
import net.jodah.failsafe.Failsafe
import net.jodah.failsafe.RetryPolicy
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import java.util.concurrent.TimeUnit

import static com.modzo.iptv.scanner.domain.Channel.Status.*
import static org.springframework.data.domain.Pageable.unpaged

class VerifyChannelIntegrationSpec extends IntegrationSpec {

    void 'should verify if channel is not working'() {
        given:
            long savedChannelId = testChannel.create().id
        when:
            ResponseEntity<String> verifyResponse = restTemplate.postForEntity(
                    "/channels/${savedChannelId}/verification", null, String
            )
        then:
            verifyResponse.statusCode == HttpStatus.ACCEPTED
        and:
            waitForFinishedValidation(savedChannelId, IN_VALIDATION, WORKING)
        and:
            Channel savedChannel = channels.findById(savedChannelId).get()
            savedChannel.status == NOT_WORKING
    }

    void 'should verify all working channels and tell that is not working'() {
        given:
            workingChannelExists()
        and:
            List<Long> workingChannels = channels.findAllByStatus(WORKING, unpaged()).getContent()*.id
        when:
            ResponseEntity<String> verifyResponse = restTemplate.postForEntity(
                    "/channels/verification?status=${WORKING}", null, String
            )
        then:
            verifyResponse.statusCode == HttpStatus.ACCEPTED
        and:
            workingChannels.each { channelId ->
                waitForFinishedValidation(channelId, IN_VALIDATION, WORKING)
            }
        and:
            workingChannels.each { Long channelId ->
                Channel savedChannel = channels.findById(channelId).get()
                savedChannel.status == NOT_WORKING
            }
    }

    private void workingChannelExists() {
        long workingChannel = testChannel.create().id
        changeChannelStatusHandler.handle(new ChangeChannelStatusHandler.Request(workingChannel, WORKING))
    }

    private void waitForFinishedValidation(long savedChannelId, Channel.Status... notExpectedStatuses) {
        RetryPolicy retryPolicy = new RetryPolicy()
                .retryWhen(false)
                .withDelay(3, TimeUnit.SECONDS)
                .withMaxRetries(5)

        Failsafe.with(retryPolicy).get({ isValidated(savedChannelId, notExpectedStatuses as Set) })
    }

    private boolean isValidated(long channelId, Set<Channel.Status> notExpectedStatuses) {
        Channel savedChannel = channels.findById(channelId).get()
        !notExpectedStatuses.contains(savedChannel.status)
    }
}