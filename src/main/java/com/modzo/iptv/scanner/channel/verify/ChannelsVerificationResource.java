package com.modzo.iptv.scanner.channel.verify;

import com.modzo.iptv.scanner.domain.Channel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChannelsVerificationResource {
    private final ChannelVerificationService verificationService;

    public ChannelsVerificationResource(ChannelVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping(value = "/channels/{channelId}/verification")
    public ResponseEntity verify(@PathVariable("channelId") long channelId) {
        verificationService.verifyById(channelId);
        return ResponseEntity.accepted().build();
    }

    @PostMapping(value = "/channels/verification")
    public ResponseEntity verifyAllChannelsWithStatus(@RequestParam("status") Channel.Status status) {
        verificationService.verifyByStatus(status);
        return ResponseEntity.accepted().build();
    }
}
