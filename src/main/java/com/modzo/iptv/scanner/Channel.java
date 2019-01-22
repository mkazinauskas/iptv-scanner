package com.modzo.iptv.scanner;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.net.URI;
import java.util.Objects;

public class Channel {

    private final String name;
    private final String channel;
    private final URI uri;
    private final boolean valid;

    public Channel(String name, String channel, URI uri, boolean valid) {
        this.name = name;
        this.channel = channel;
        this.uri = uri;
        this.valid = valid;
    }

    public Channel(String name, String channel, URI uri) {
        this.name = name;
        this.channel = channel;
        this.uri = uri;
        this.valid = true;
    }

    public String getName() {
        return name;
    }

    public String getChannel() {
        return channel;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isValid() {
        return valid;
    }

    public Channel invalidChannel() {
        return new Channel(this.name, channel, this.uri, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Channel channel = (Channel) o;
        return Objects.equals(uri, channel.uri);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uri);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("channel", channel)
                .append("uri", uri)
                .append("valid", valid)
                .toString();
    }
}