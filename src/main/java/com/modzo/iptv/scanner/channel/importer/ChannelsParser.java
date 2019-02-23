package com.modzo.iptv.scanner.channel.importer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ChannelsParser {

    public Set<ParsedChannel> asChannelList(String playlist) {
        List<String> lines = Stream.of(playlist.split("\n"))
                .filter(StringUtils::isNotBlank)
                .map(StringUtils::trim)
                .filter(line -> line.startsWith("#EXTINF:") || line.startsWith("udp://@"))
                .collect(Collectors.toList());

        Set<ParsedChannel> channels = new LinkedHashSet<>();
        int i = 0;
        while (i + 2 <= lines.size()) {
            String firstLine = lines.get(i);
            i++;
            String secondLine = lines.get(i);
            i++;

            if (firstLine.startsWith("#EXTINF:") && secondLine.startsWith("udp://@")) {
                String[] channelWithName = firstLine.replace("#EXTINF:", "").split(",");
                int soundTrack = Integer.valueOf(channelWithName[0]);
                String name = channelWithName[1];

                channels.add(
                        new ParsedChannel(name, soundTrack, URI.create(secondLine))
                );
            } else {
                i--;
            }
        }

        return channels;
    }

    static class ParsedChannel {
        private final String name;
        private final int soundTrack;
        private final URI uri;

        ParsedChannel(String name, int soundTrack, URI uri) {
            this.name = name;
            this.soundTrack = soundTrack;
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public int getSoundTrack() {
            return soundTrack;
        }

        public URI getUri() {
            return uri;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            ParsedChannel that = (ParsedChannel) o;

            return new EqualsBuilder()
                    .append(uri, that.uri)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(uri)
                    .toHashCode();
        }
    }
}