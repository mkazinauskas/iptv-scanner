package com.modzo.iptv.scanner.channel;

import com.modzo.iptv.scanner.domain.Channel;

import java.time.ZonedDateTime;

class ChannelResponse {
    private final Long id;

    private final ZonedDateTime creationDate;

    private final String name;

    private final Channel.Status status;

    private final Integer soundTrack;

    private final String uri;

    public ChannelResponse(Channel channel) {
        this.id = channel.getId();
        this.creationDate = channel.getCreationDate();
        this.name = channel.getName();
        this.status = channel.getStatus();
        this.soundTrack = channel.getSoundTrack();
        this.uri = channel.getUri().toString();
    }

    public Long getId() {
        return id;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public String getName() {
        return name;
    }

    public Channel.Status getStatus() {
        return status;
    }

    public Integer getSoundTrack() {
        return soundTrack;
    }

    public String getUri() {
        return uri;
    }
}
