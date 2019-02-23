package com.modzo.iptv.scanner.channel.importer

import spock.lang.Specification

class ImportChannelsFromFileSpec extends Specification {

    ChannelsParser testTarget = new ChannelsParser()

    void 'should import channels from file'() {
        given:
            String fileContent = getClass().getResource('/importer/import.m3u').text
        expect:
            fileContent != null
        when:
            List<ChannelsParser.ParsedChannel> channels = testTarget.asChannelList(fileContent) as List
        then:
            channels[0].name == 'LRT Televizija HD'
            channels[0].soundTrack == -1
            channels[0].uri.toString() == 'udp://@233.136.41.158:1234'

            channels[1].name == 'LRT Televizija'
            channels[1].soundTrack == 2
            channels[1].uri.toString() == 'udp://@233.136.41.170:1234'
    }
}
