package com.modzo.iptv.scanner.sorter

import com.modzo.iptv.scanner.Channel
import spock.lang.Specification

class ChannelSorterSpec extends Specification {

    void 'should sort channels'() {
        given:
            List<String> sortList = [
                    'tv3 hd',
                    'tv3',
                    'alfa',
                    'zeta'
            ]
        and:
            List<Channel> channels = [
                    new Channel('zeta', URI.create('udp://@233.136.1.158:1234')),
                    new Channel('beta', URI.create('udp://@233.136.2.158:1234')),
                    new Channel('tv3 hd', URI.create('udp://@233.136.3.158:1234')),
                    new Channel('tv3', URI.create('udp://@233.136.41.4:1234')),
            ]
        when:
            List<Channel> results = new ChannelSorter(sortList).sort(channels)
        then:
            results.size() == 4
        and:
            results[0].name == 'tv3 hd'
            results[1].name == 'tv3'
            results[2].name == 'zeta'
            results[3].name == 'beta'
    }
}
