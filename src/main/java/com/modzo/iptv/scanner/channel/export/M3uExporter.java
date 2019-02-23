package com.modzo.iptv.scanner.channel.export;

import com.modzo.iptv.scanner.domain.Channel;
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
                .map(channel -> "#EXTINF:" + channel.getSoundTrack() + "," + channel.getName() + "\n" + channel.getUri())
                .collect(Collectors.joining("\n\n"));
    }
}
