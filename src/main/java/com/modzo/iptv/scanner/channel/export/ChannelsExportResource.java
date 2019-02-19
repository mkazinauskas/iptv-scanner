package com.modzo.iptv.scanner.channel.export;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelsExportResource {
    private final ChannelExportService channelExportService;

    public ChannelsExportResource(ChannelExportService channelExportService) {
        this.channelExportService = channelExportService;
    }

    @GetMapping(value = "/channels/export")
    public ResponseEntity<ByteArrayResource> export(Pageable pageable) {

        byte[] data = channelExportService.export(pageable);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment;filename=all_channels.m3u")
                .contentType(MediaType.TEXT_PLAIN).contentLength(data.length)
                .body(resource);
    }
}
