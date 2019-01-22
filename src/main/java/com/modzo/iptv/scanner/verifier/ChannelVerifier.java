package com.modzo.iptv.scanner.verifier;

import com.modzo.iptv.scanner.ApplicationConfiguration;
import com.modzo.iptv.scanner.Channel;
import com.modzo.iptv.scanner.ImprovementNeeded;
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

    private final int retries;

    private final static MediaPlayerFactory factory = new MediaPlayerFactory();

    @Autowired
    public ChannelVerifier(ApplicationConfiguration configuration) {
        this(configuration.getUdpxyUrl(), configuration.getPingRetries());
    }

    ChannelVerifier(String udpxy, int retries) {
        Objects.requireNonNull(udpxy);
        this.retries = retries;
    }

    @ImprovementNeeded("Rewrite with methods...")
    public boolean isValidChannel(Channel channel) {
        HeadlessMediaPlayer embeddedMediaPlayer = factory.newHeadlessMediaPlayer();
        embeddedMediaPlayer.prepareMedia(channel.getUri().toString());
        embeddedMediaPlayer.play();

        RetryPolicy retryPolicy = new RetryPolicy()
                .retryWhen(false)
                .withDelay(10, TimeUnit.SECONDS)
                .withMaxRetries(retries);

        Failsafe.with(retryPolicy).get(embeddedMediaPlayer::isPlaying);

        if (embeddedMediaPlayer.isPlaying()) {
            embeddedMediaPlayer.stop();
            return true;
        } else {
            LOGGER.debug("Channel is not working `{}`", channel.toString());
            embeddedMediaPlayer.stop();
            return false;
        }
    }
}