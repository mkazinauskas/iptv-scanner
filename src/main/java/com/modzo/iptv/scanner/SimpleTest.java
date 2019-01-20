package com.modzo.iptv.scanner;

import com.modzo.iptv.scanner.exporter.M3uExporter;
import com.modzo.iptv.scanner.importer.ReadChannelsFromFile;
import com.modzo.iptv.scanner.verifier.ChannelVerifier;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SimpleTest {
    public static void main(String[] args) throws IOException {
        String playlist;
        try {
            playlist = FileUtils.readFileToString(new File(SimpleTest.class.getResource("/playlist.m3u").getPath()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Failed to read file");
            return;
        }

        List<Channel> channels = new ReadChannelsFromFile().doImport(playlist);
        if (channels.size() == 0) {
            System.out.print("No channels parsed");
            return;
        }
        ChannelVerifier verifier = new ChannelVerifier();

        List<Channel> validChannels = new ArrayList<>();
        List<Channel> invalidChannels = new ArrayList<>();
        channels.forEach(
                channel -> {
                    if (verifier.isValidChannel(channel)) {
                        validChannels.add(channel);
                    } else {
                        invalidChannels.add(channel);
                    }

                }
        );
        String exportedValidChannels = new M3uExporter().export(validChannels);
        String exportedInvalidChannels = new M3uExporter().export(invalidChannels);

        Files.write(Paths.get(SimpleTest.class.getResource("/valid.m3u").getPath()), exportedValidChannels.getBytes());
        Files.write(Paths.get(SimpleTest.class.getResource("/invalid.m3u").getPath()), exportedInvalidChannels.getBytes());
    }
}
