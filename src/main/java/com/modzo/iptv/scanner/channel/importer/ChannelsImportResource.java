package com.modzo.iptv.scanner.channel.importer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ChannelsImportResource {
    private final ChannelsImportService channelsImportService;

    public ChannelsImportResource(ChannelsImportService channelsImportService) {
        this.channelsImportService = channelsImportService;
    }

    @PostMapping(value = "/channels/import")
    public ResponseEntity importChannels(@RequestParam("file") MultipartFile playlist) {
        channelsImportService.importPlaylist(playlist);
        return ResponseEntity.ok().build();
    }
}