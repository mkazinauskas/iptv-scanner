package com.modzo.iptv.scanner.verifier

import com.modzo.iptv.scanner.Channel
import spock.lang.Specification

class ChannelVerifierSpec extends Specification {
    ChannelVerifier testTarget = new ChannelVerifier()

    void 'channel should be working'() {
        given:
            Channel channel = new Channel('Working channel', URI.create('udp://@233.136.41.158:1234'))
        expect:
           testTarget.isValidChannel(channel)
    }

    void 'channel should be not working'() {
        given:
            Channel channel = new Channel('Working channel', URI.create('udp://@239.255.12.13:1234'))
        expect:
            !testTarget.isValidChannel(channel)
    }
}
