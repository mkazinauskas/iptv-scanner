package com.modzo.iptv.scanner.channel.export

import com.modzo.iptv.scanner.domain.Channel
import spock.lang.Specification

class M3uExporterSpec extends Specification {

    M3uExporter testTarget = new M3uExporter()

    void 'should export list'() {
        given:
            List<Channel> channels = [
                    new Channel(name: 'LRT Televizija HD', soundTrack: -1, uri: URI.create('udp://@233.136.41.158:1234')),
                    new Channel(name: 'LRT Televizija', soundTrack: 2, uri: URI.create('udp://@233.136.41.170:1234')),
            ]
        when:
            String result = testTarget.export(channels)
        then:
            String expected = getClass().getResource('/exporter/exported.m3u').text
            result == expected
    }
}
