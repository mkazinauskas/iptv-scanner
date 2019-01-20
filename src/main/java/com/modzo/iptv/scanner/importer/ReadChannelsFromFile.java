package com.modzo.iptv.scanner.importer;

import com.modzo.iptv.scanner.Channel;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadChannelsFromFile {

    public List<Channel> doImport(String content) {
        List<String> lines = Stream.of(content.split("\n"))
                .filter(StringUtils::isNotBlank)
                .map(StringUtils::trim)
                .filter(line -> line.startsWith("#EXTINF:-1,") || line.startsWith("udp://@"))
                .collect(Collectors.toList());

        Set<Channel> channels = new LinkedHashSet<>();
        int i = 0;
        while (i + 2 <= lines.size() - 1) {
            String firstLine = lines.get(i);
            i++;
            String secondLine = lines.get(i);
            i++;

            if (firstLine.startsWith("#EXTINF:-1,") && secondLine.startsWith("udp://@")) {
                channels.add(
                        new Channel(firstLine.replace("#EXTINF:-1,", ""), URI.create(secondLine))
                );
            } else {
                i--;
            }
        }

        return new ArrayList<>(channels);
    }
}