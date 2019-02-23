package com.modzo.iptv.scanner.channel.export

import com.modzo.iptv.scanner.ApplicationConfiguration
import com.modzo.iptv.scanner.channel.export.ChannelSorter
import com.modzo.iptv.scanner.domain.Channel
import spock.lang.Specification

class ChannelSorterSpec extends Specification {

    void 'should sort channels'() {
        given:
            List<String> sortingList = [
                    'tv3 hd',
                    'tv3',
                    'alfa',
                    'zeta'
            ]
            ChannelSorter testTarget = new ChannelSorter(new ApplicationConfiguration(sortingList: sortingList))
        and:
            List<Channel> channels = [
                    new Channel(name: 'zeta', soundTrack: -1, uri: URI.create('udp://@233.136.1.158:1234')),
                    new Channel(name: 'beta', soundTrack: 2, uri: URI.create('udp://@233.136.2.158:1234')),
                    new Channel(name: 'tv3 hd', soundTrack: -1, uri: URI.create('udp://@233.136.3.158:1234')),
                    new Channel(name: 'tv3', soundTrack: 3, uri: URI.create('udp://@233.136.41.4:1234'))
            ]
        when:
            List<Channel> results = testTarget.sort(channels)
        then:
            results.size() == 4
        and:
            results[0].name == 'tv3 hd'
            results[1].name == 'tv3'
            results[2].name == 'zeta'
            results[3].name == 'beta'
    }
}
