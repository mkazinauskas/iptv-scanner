package com.modzo.iptv.scanner;

import java.net.*;

public class Channel {

    private final String name;
    private final URI uri;
    private final boolean valid;

    public Channel(String name, URI uri, boolean valid) {
        this.name = name;
        this.uri = uri;
        this.valid = valid;
    }

    public Channel(String name, URI uri) {
        this.name = name;
        this.uri = uri;
        this.valid = true;
    }

    public String getName() {
        return name;
    }

    public URI getUri() {
        return uri;
    }

    public boolean isValid() {
        return valid;
    }

    public Channel invalidChannel (){
        return new Channel(this.name, this.uri, false);
    }
}