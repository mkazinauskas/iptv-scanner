package com.modzo.iptv.scanner.database.commands;

import com.modzo.iptv.scanner.database.Channel;
import com.modzo.iptv.scanner.database.Channels;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ChangeChannelStatusHandler {

    private final Channels channels;

    public ChangeChannelStatusHandler(Channels channels) {
        this.channels = channels;
    }

    @Transactional
    public void handle(Request request) {
        Channel channel = channels.findById(request.id)
                .orElseThrow(() -> new RuntimeException("Channel not found"));
        channel.setWorking(request.working);
    }

    public static class Request {
        private final long id;

        private final boolean working;

        public Request(long id, boolean working) {
            this.id = id;
            this.working = working;
        }
    }
}
