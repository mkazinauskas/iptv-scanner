package com.modzo.iptv.scanner.channel;

import com.modzo.iptv.scanner.domain.Channel;
import com.modzo.iptv.scanner.domain.Channels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelsResource {

    private final Channels channels;

    public ChannelsResource(Channels channels) {
        this.channels = channels;
    }

    @GetMapping(value = "/channels")
    public ResponseEntity<Page<ChannelResponse>> export(Pageable pageable) {
        Page<Channel> channelList = channels.findAll(pageable);
        return ResponseEntity.ok().body(channelList.map(ChannelResponse::new));
    }
}
