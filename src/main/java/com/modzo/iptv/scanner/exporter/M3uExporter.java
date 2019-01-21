package com.modzo.iptv.scanner.exporter;

import com.modzo.iptv.scanner.Channel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class M3uExporter {

    public String export(List<Channel> channels) {
        return "#EXTM3U\n".concat(channelLines(channels));
    }

    private String channelLines(List<Channel> channels) {
        return channels.stream()
                .map(channel -> "#EXTINF:-1," + channel.getName() + "\n" + channel.getUri().toString())
                .collect(Collectors.joining("\n\n"));
    }
}
