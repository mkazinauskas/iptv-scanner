package com.modzo.iptv.scanner.verifier;

import com.modzo.iptv.scanner.ApplicationConfiguration;
import com.modzo.iptv.scanner.Channel;
import com.modzo.iptv.scanner.ImprovementNeeded;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Objects;

@Component
public class ChannelVerifier {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelVerifier.class);

    private final String udpxy;

    private final int retries;

    private final RestTemplate restTemplate = new RestTemplateBuilder()
            .setConnectTimeout(Duration.ofSeconds(10))
            .setReadTimeout(Duration.ofSeconds(5))
            .messageConverters(new CustomByArrayMessageConverter())
            .requestFactory(NotBufferingRequestInterceptor.class)
            .build();

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
                Thread.sleep(5000L);
            } catch (InterruptedException ex) {
                LOGGER.debug("Failed to sleep thread", ex);
            }
        }
        return false;
    }

    private boolean isWorking(URL url) {
        return validViaCrazyStuff(url) || validViaRestTemplate(url);
    }

    private boolean validViaRestTemplate(URL url) {
        try {
            ResponseEntity<byte[]> response = restTemplate.getForEntity(url.toURI(), byte[].class);
            return response.getBody() != null && response.getBody().length > 0;
        } catch (URISyntaxException | ResourceAccessException e) {
            return false;
        }
    }

    private boolean validViaCrazyStuff(URL url) {
        try {
            return IOUtils.readFully((url).openStream(), 5).length > 0;
        } catch (Exception ex) {
            LOGGER.debug("Failed read url...", ex);
            return false;
        }
    }
}