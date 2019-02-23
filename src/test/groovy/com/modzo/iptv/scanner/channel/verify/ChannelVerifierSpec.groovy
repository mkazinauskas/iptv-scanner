package com.modzo.iptv.scanner.channel.verify

import com.modzo.iptv.scanner.domain.Channel
import spock.lang.Specification

class ChannelVerifierSpec extends Specification {
    ChannelVerifier testTarget = new ChannelVerifier('http://localhost:1111/udp/', 1)

    void 'channel should be working'() {
        given:
            Channel channel = new Channel(name: 'Working channel', soundTrack: -1, uri: URI.create('udp://@233.136.41.179:1234'))
        expect:
            testTarget.isWorkingChannel(channel)
    }

    void 'channel should be not working'() {
        given:
            Channel channel = new Channel(name: 'Working channel', soundTrack: -2, uri: URI.create('udp://@239.255.1.1:1234'))
        expect:
            !testTarget.isWorkingChannel(channel)
    }
}
