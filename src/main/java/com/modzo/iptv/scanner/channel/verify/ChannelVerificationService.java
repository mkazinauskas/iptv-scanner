package com.modzo.iptv.scanner.channel.verify;

import com.modzo.iptv.scanner.database.Channel;
import com.modzo.iptv.scanner.database.Channels;
import com.modzo.iptv.scanner.database.commands.ChangeChannelStatusHandler;
import org.springframework.stereotype.Service;

@Service
public class ChannelVerificationService {

    private final Channels channels;

    private final ChannelVerifier verifier;

    private final ChangeChannelStatusHandler changeChannelStatusHandler;

    public ChannelVerificationService(Channels channels,
                                      ChannelVerifier verifier,
                                      ChangeChannelStatusHandler changeChannelStatusHandler) {
        this.channels = channels;
        this.verifier = verifier;
        this.changeChannelStatusHandler = changeChannelStatusHandler;
    }

    public void verify(long channelId) {
        Channel channel = channels.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel was not found!"));
        Channel.Status status = verifier.isWorkingChannel(channel) ? Channel.Status.WORKING : Channel.Status.NOT_WORKING;
        changeChannelStatusHandler.handle(new ChangeChannelStatusHandler.Request(channelId, status));
    }
}