package com.modzo.iptv.scanner.channel.importer;

import java.util.List;

public class PlaylistSavingFailed extends RuntimeException {
    private final List<Throwable> errors;

    PlaylistSavingFailed(List<Throwable> errors) {
        super("Playlist reading failed");
        this.errors = errors;
    }

    public List<Throwable> getErrors() {
        return errors;
    }
}
