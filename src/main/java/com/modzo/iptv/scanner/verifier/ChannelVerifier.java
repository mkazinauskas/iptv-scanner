package com.modzo.iptv.scanner.verifier;

import com.modzo.iptv.scanner.Channel;
import com.modzo.iptv.scanner.ImprovementNeeded;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class ChannelVerifier {

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
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        }

        for (int i = 0; i < retries; i++) {

            if (read(url) == true) {
                return true;
            }
            try {
                Thread.sleep(5000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static boolean read(URL url) {
        byte[] b = null;
        try {
            b = IOUtils.readFully((url).openStream(), 5);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return b != null;
    }
}
