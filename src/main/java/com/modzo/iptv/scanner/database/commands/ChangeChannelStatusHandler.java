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
        channel.setStatus(request.status);
    }

    public static class Request {
        private final long id;

        private final Channel.Status status;

        public Request(long id, Channel.Status status) {
            this.id = id;
            this.status = status;
        }
    }
}
