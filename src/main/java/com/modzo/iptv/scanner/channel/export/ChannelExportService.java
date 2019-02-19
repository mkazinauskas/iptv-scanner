package com.modzo.iptv.scanner.channel.export;

import com.modzo.iptv.scanner.database.Channel;
import com.modzo.iptv.scanner.database.Channels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;

@Component
class ChannelExportService {

    private final M3uExporter m3uExporter;

    private final Channels channels;

    public ChannelExportService(M3uExporter m3uExporter, Channels channels) {
        this.m3uExporter = m3uExporter;
        this.channels = channels;
    }

    public byte[] export(Pageable pageable) {
        Page<Channel> channelsToExport = channels.findAll(pageable);
        return m3uExporter.export(channelsToExport.getContent()).getBytes(Charset.forName("UTF-8"));
    }
}
