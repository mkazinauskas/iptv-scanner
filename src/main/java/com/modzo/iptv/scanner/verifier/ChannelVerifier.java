package com.modzo.iptv.scanner.verifier;

import com.modzo.iptv.scanner.ApplicationConfiguration;
import com.modzo.iptv.scanner.Channel;
import com.modzo.iptv.scanner.ImprovementNeeded;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

@Component
public class ChannelVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelVerifier.class);

    private final String udpxy;

    private final int retries;

    @Autowired
    public ChannelVerifier(ApplicationConfiguration configuration) {
        this(configuration.getUdpxyUrl(), configuration.getPingRetries());
    }

    ChannelVerifier(String udpxy, int retries) {
        Objects.requireNonNull(udpxy);
        this.udpxy = udpxy;
        this.retries = retries;
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
            ResponseEntity<String> forEntity = new RestTemplate().getForEntity(url.toURI(), String.class);
            b = IOUtils.readFully((url).openStream(), 5);
        } catch (Exception ex) {
            LOGGER.debug("Failed to open steam", ex);
            return false;
        }
        return b != null;
    }
}
