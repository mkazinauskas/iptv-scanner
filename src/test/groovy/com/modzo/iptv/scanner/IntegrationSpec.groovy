package com.modzo.iptv.scanner

import com.modzo.iptv.scanner.channel.helpers.TestChannel
import com.modzo.iptv.scanner.domain.Channels
import com.modzo.iptv.scanner.domain.commands.ChangeChannelStatusHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationSpec extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    Channels channels

    @Autowired
    TestChannel testChannel

    @Autowired
    ChangeChannelStatusHandler changeChannelStatusHandler
}