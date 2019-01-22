package com.modzo.iptv.scanner.importer;

import com.modzo.iptv.scanner.Channel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ReadChannelsFromFile {

    public List<Channel> doImport(String content) {
        List<String> lines = Stream.of(content.split("\n"))
                .filter(StringUtils::isNotBlank)
                .map(StringUtils::trim)
                .filter(line -> line.startsWith("#EXTINF:") || line.startsWith("udp://@"))
                .collect(Collectors.toList());

        Set<Channel> channels = new LinkedHashSet<>();
        int i = 0;
        while (i + 2 <= lines.size()) {
            String firstLine = lines.get(i);
            i++;
            String secondLine = lines.get(i);
            i++;

            if (firstLine.startsWith("#EXTINF:") && secondLine.startsWith("udp://@")) {
                String[] channelWithName = firstLine.replace("#EXTINF:", "").split(",");
                String channel = channelWithName[0];
                String name = channelWithName[1];

                channels.add(
                        new Channel(name, channel, URI.create(secondLine))
                );
            } else {
                i--;
            }
        }

        return new ArrayList<>(channels);
    }
}