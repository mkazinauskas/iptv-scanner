package com.modzo.iptv.scanner.database;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "channels")
public class Channel {

    @Id
    @GeneratedValue(generator = "channels_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "channels_sequence", sequenceName = "channels_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Column(name = "name")
    private String name;

    @Column(name = "working", nullable = false)
    private boolean working;

    @Column(name = "sound_track", nullable = false)
    private Integer soundTrack;

    @Column(name = "url", unique = true)
    private String url;

    public Channel() {
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean getWorking() {
        return working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public Integer getSoundTrack() {
        return soundTrack;
    }

    public void setSoundTrack(Integer soundTrack) {
        this.soundTrack = soundTrack;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}