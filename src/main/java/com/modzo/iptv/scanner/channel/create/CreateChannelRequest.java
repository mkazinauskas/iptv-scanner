package com.modzo.iptv.scanner.channel.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.net.URI;

public class CreateChannelRequest {

    @NotEmpty
    private final String name;

    @NotNull
    private final int soundTrack;

    @NotNull
    private final URI uri;

    @JsonCreator
    public CreateChannelRequest(@JsonProperty("name") String name,
                                @JsonProperty("soundTrack") int soundTrack,
                                @JsonProperty("url") URI uri) {
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
}