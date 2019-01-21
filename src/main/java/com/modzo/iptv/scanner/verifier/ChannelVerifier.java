package com.modzo.iptv.scanner.verifier;

import com.modzo.iptv.scanner.Channel;
import com.modzo.iptv.scanner.ImprovementNeeded;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ChannelVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelVerifier.class);

    private final String udpxy;

    private final int retries;

    public ChannelVerifier(String udpxy, int retries) {
        Objects.requireNonNull(udpxy);
        this.udpxy = udpxy;
        this.retries = retries;
    }

    @ImprovementNeeded("To properties...")
    public ChannelVerifier() {
        this.udpxy = "http://localhost:1111/udp/";
        this.retries = 1;
    }

    @ImprovementNeeded("Rewrite with methods...")
    public boolean isValidChannel(Channel channel) {
        URL url;
        try {
            url = new URL(udpxy + channel.getUri().getHost() + ":" + channel.getUri().getPort());
        } catch (MalformedURLException ex) {
            LOGGER.warn("Failed to construct url", ex);
            return false;
        }

        for (int i = 0; i < retries; i++) {

            if (isWorking(url)) {
                return true;
            }
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException ex) {
                LOGGER.debug("Failed to sleep thread", ex);
            }
        }
        return false;
    }

    private static boolean isWorking(URL url) {
        byte[] b = null;
        try {
            b = IOUtils.readFully((url).openStream(), 5);
        } catch (Exception ex) {
            LOGGER.debug("Failed to open steam", ex);
            return false;
        }
        return b != null;
    }
}
