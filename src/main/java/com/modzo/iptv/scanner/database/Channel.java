package com.modzo.iptv.scanner.database;

import javax.persistence.*;
import java.time.ZonedDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "channels")
public class Channel {

   public enum Status{
        WORKING, NOT_WORKING, UNKNOWN;
    }

    @Id
    @GeneratedValue(generator = "channels_sequence", strategy = SEQUENCE)
    @SequenceGenerator(name = "channels_sequence", sequenceName = "channels_sequence", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(name = "creation_date")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.UNKNOWN;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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