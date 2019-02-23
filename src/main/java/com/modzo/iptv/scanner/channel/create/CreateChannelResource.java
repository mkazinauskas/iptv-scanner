package com.modzo.iptv.scanner.channel.create;

import com.modzo.iptv.scanner.database.commands.CreateChannelHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class CreateChannelResource {
    private final CreateChannelHandler handler;

    public CreateChannelResource(CreateChannelHandler handler) {
        this.handler = handler;
    }

    @PostMapping(value = "/channels")
    public ResponseEntity createChannel(@RequestBody CreateChannelRequest request) {
        CreateChannelHandler.Response result = handler.handle(
                new CreateChannelHandler.Request(request.getName(), request.getSoundTrack(), request.getUrl())
        );
        return ResponseEntity.created(URI.create("/channels/" + result.getId())).build();
    }
}