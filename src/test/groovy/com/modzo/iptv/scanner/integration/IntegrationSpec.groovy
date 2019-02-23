package com.modzo.iptv.scanner.integration

import com.modzo.iptv.scanner.database.Channels
import com.modzo.iptv.scanner.database.commands.ChangeChannelStatusHandler
import com.modzo.iptv.scanner.integration.helpers.TestChannel
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