package com.modzo.iptv.scanner.channel.importer

import com.modzo.iptv.scanner.IntegrationSpec
import com.modzo.iptv.scanner.domain.Channel
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class ImportChannelsIntegrationSpec extends IntegrationSpec {

    void 'should import all channels as m3u playlist'() {
        given:
            HttpEntity<MultiValueMap<String, Object>> requestEntity = fileRequest()
        when:
            ResponseEntity<String> response = restTemplate.exchange('/channels/import', HttpMethod.POST, requestEntity, String)
        then:
            response.statusCode == HttpStatus.OK
        and:
            Channel firstChannel = channels.findByName('LRT Televizija HD').get()
            firstChannel.uri.toString() == 'udp://@233.136.41.158:1234'
            firstChannel.soundTrack == -1
        and:
            Channel secondChannel = channels.findByName('LRT Televizija').get()
            secondChannel.uri.toString() == 'udp://@233.136.41.170:1234'
            secondChannel.soundTrack == 2
    }

    private static HttpEntity<MultiValueMap<String, Object>> fileRequest() {
        MultiValueMap<String, Object> bodyMap = new LinkedMultiValueMap<>()
        bodyMap.add("file", fileToImport())

        HttpHeaders headers = new HttpHeaders()
        headers.setContentType(MediaType.MULTIPART_FORM_DATA)

        return new HttpEntity<>(bodyMap, headers)
    }

    private static Resource fileToImport() {
        return new ClassPathResource('/importer/import.m3u')
    }
}