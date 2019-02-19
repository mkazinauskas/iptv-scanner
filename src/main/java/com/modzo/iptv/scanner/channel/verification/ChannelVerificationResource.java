package com.modzo.iptv.scanner.channel.verification;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelVerificationResource {
    private final ChannelVerificationService verificationService;

    public ChannelVerificationResource(ChannelVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping(value = "/channels/{channelId}/verification")
    public ResponseEntity createChannel(@PathVariable("channelId") long channelId) {
        verificationService.verify(channelId);
        return ResponseEntity.accepted().build();
    }
}
