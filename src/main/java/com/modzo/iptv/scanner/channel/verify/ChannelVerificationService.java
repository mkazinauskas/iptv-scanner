package com.modzo.iptv.scanner.channel.verify;

import com.modzo.iptv.scanner.domain.Channel;
import com.modzo.iptv.scanner.domain.Channels;
import com.modzo.iptv.scanner.domain.commands.ChangeChannelStatusHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
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

    @Async
    public void verifyById(long channelId) {
        Channel channel = channels.findById(channelId)
                .orElseThrow(() -> new RuntimeException("Channel was not found!"));
        verifyChannel(channelId, channel);
    }

    @Async
    public void verifyByStatus(Channel.Status status) {
        Page<Channel> requiredChannels = channels.findAllByStatus(status, Pageable.unpaged());
        requiredChannels.stream().forEach(channel -> verifyChannel(channel.getId(), channel));
    }

    private void verifyChannel(long channelId, Channel channel) {
        changeChannelStatusHandler.handle(new ChangeChannelStatusHandler.Request(channelId, Channel.Status.IN_VALIDATION));
        Channel.Status status = verifier.isWorkingChannel(channel) ? Channel.Status.WORKING : Channel.Status.NOT_WORKING;
        changeChannelStatusHandler.handle(new ChangeChannelStatusHandler.Request(channelId, status));
    }
}