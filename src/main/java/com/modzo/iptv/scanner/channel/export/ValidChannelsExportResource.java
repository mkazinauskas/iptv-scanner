package com.modzo.iptv.scanner.channel.export;

import com.modzo.iptv.scanner.channel.verify.ChannelVerificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidChannelsExportResource {
    private final ChannelVerificationService verificationService;

    public ValidChannelsExportResource(ChannelVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @GetMapping(value = "/channels/valid")
    public ResponseEntity valid() {
        return ResponseEntity.accepted().build();
    }
}
