package com.modzo.iptv.scanner.channel.export;

import com.modzo.iptv.scanner.domain.Channel;
import com.modzo.iptv.scanner.domain.Channels;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;

@Component
class ChannelExportService {

    private final M3uExporter m3uExporter;

    private final Channels channels;

    private final ChannelSorter channelSorter;

    public ChannelExportService(M3uExporter m3uExporter,
                                Channels channels,
                                ChannelSorter channelSorter) {
        this.m3uExporter = m3uExporter;
        this.channels = channels;
        this.channelSorter = channelSorter;
    }

    public byte[] SortAndExport(Pageable pageable) {
        Page<Channel> channelsToExport = channels.findAll(pageable);
        return sortAndExport(channelsToExport);
    }

    public byte[] exportByStatus(Channel.Status status, Pageable pageable) {
        Page<Channel> channelsToExport = channels.findAllByStatus(status, pageable);
        return sortAndExport(channelsToExport);
    }

    private byte[] sortAndExport(Page<Channel> channelsToExport) {
        List<Channel> content = channelsToExport.getContent();
        List<Channel> sortedChannels = channelSorter.sort(content);
        return m3uExporter.export(sortedChannels).getBytes(Charset.forName("UTF-8"));
    }
}