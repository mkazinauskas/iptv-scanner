package com.modzo.iptv.scanner.importer

import com.modzo.iptv.scanner.Channel
import spock.lang.Specification

class ImportChannelsFromFileSpec extends Specification {

    ReadChannelsFromFile testTarget = new ReadChannelsFromFile()

    void 'should import channels from file'() {
        given:
            String fileContent = getClass().getResource('/importer/import.m3u').text
        expect:
            fileContent != null
        when:
            List<Channel> channels = testTarget.doImport(fileContent)
        then:
            channels[0].name == 'LRT Televizija HD'
            channels[0].channel == '-1'
            channels[0].uri.toString() == 'udp://@233.136.41.158:1234'

            channels[1].name == 'LRT Televizija'
            channels[1].channel == '2'
            channels[1].uri.toString() == 'udp://@233.136.41.170:1234'
    }
}
