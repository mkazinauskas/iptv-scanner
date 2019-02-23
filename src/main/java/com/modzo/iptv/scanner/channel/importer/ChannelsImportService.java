package com.modzo.iptv.scanner.channel.importer;

import com.modzo.iptv.scanner.domain.commands.CreateChannelHandler;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
class ChannelsImportService {

    private static final Logger LOG = LoggerFactory.getLogger(ChannelsImportService.class);

    private final ChannelsParser channelsParser;

    private final CreateChannelHandler createChannelHandler;

    public ChannelsImportService(ChannelsParser channelsParser,
                                 CreateChannelHandler createChannelHandler) {
        this.channelsParser = channelsParser;
        this.createChannelHandler = createChannelHandler;
    }

    public void importPlaylist(MultipartFile playlist) {
        String playlistContent;
        try {
            playlistContent = IOUtils.toString(playlist.getInputStream(), "UTF-8");
        } catch (IOException e) {
            LOG.error("Failed to read playlist file", e);
            throw new RuntimeException("Failed to read playlist file", e.getCause());
        }
        Set<ChannelsParser.ParsedChannel> parsedChannels = channelsParser.asChannelList(playlistContent);
        List<Throwable> errors = new ArrayList<>();
        tryToSave(parsedChannels, errors);
        if (!errors.isEmpty()) {
            throw new PlaylistSavingFailed(errors);
        }
    }

    private void tryToSave(Set<ChannelsParser.ParsedChannel> parsedChannels, List<Throwable> errors) {
        parsedChannels.forEach(parsedChannel -> {
                    try {
                        createChannelHandler.handle(
                                new CreateChannelHandler.Request(
                                        parsedChannel.getName(),
                                        parsedChannel.getSoundTrack(),
                                        parsedChannel.getUri()
                                )
                        );
                    } catch (Exception exception) {
                        errors.add(exception.getCause());
                    }
                }
        );
    }
}