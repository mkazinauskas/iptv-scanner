package com.modzo.iptv.scanner.channel.verify;

import com.modzo.iptv.scanner.ApplicationConfiguration;
import com.modzo.iptv.scanner.ImprovementNeeded;
import com.modzo.iptv.scanner.domain.Channel;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Component
public class ChannelVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelVerifier.class);

    private final ApplicationConfiguration configuration;

    private final static MediaPlayerFactory factory = new MediaPlayerFactory();

    public ChannelVerifier(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @ImprovementNeeded("Rewrite with methods...")
    public boolean isWorkingChannel(Channel channel) {
        HeadlessMediaPlayer headlesMediaPlayer = factory.newHeadlessMediaPlayer();
        headlesMediaPlayer.prepareMedia(channel.getUri().toString(), "no-video", "no-audio");
        headlesMediaPlayer.play();

        RetryPolicy retryPolicy = new RetryPolicy()
                .retryWhen(false)
                .withDelay(configuration.getDelayRetries(), TimeUnit.SECONDS)
                .withMaxRetries(configuration.getPingRetries());

        Failsafe.with(retryPolicy).get(headlesMediaPlayer::isPlaying);

        if (headlesMediaPlayer.isPlaying()) {
            headlesMediaPlayer.stop();
            return true;
        } else {
            LOGGER.debug("Channel is not working `{}`", channel.toString());
            headlesMediaPlayer.stop();
            return false;
        }
    }
}