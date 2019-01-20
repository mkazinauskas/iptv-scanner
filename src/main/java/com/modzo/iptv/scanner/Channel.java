package com.modzo.iptv.scanner;

import java.net.*;

public class Channel {

    private final String name;
    private final URI uri;


    public Channel(String name, URI uri) {
        this.name = name;
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public URI getUri() {
        return uri;
    }
}
