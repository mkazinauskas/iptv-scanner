package com.modzo.iptv.scanner.channel.create;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateChannelRequest {

    @NotEmpty
    private final String name;

    @NotNull
    private final int soundTrack;

    @NotNull
    @URL
    private final String url;

    @JsonCreator
    public CreateChannelRequest(@JsonProperty("name") String name,
                                @JsonProperty("soundTrack") int soundTrack,
                                @JsonProperty("url") String url) {
        this.name = name;
        this.soundTrack = soundTrack;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public int getSoundTrack() {
        return soundTrack;
    }

    public String getUrl() {
        return url;
    }
}
