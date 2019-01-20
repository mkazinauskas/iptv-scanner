package com.modzo.iptv.scanner;

import com.modzo.iptv.scanner.exporter.M3uExporter;
import com.modzo.iptv.scanner.importer.ReadChannelsFromFile;
import com.modzo.iptv.scanner.verifier.ChannelVerifier;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class SimpleTest {
    private static final ThreadPoolExecutor EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
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

        String allChannels = new M3uExporter().export(channels);
        toFile(allChannels, "all.m3u");

        ChannelVerifier verifier = new ChannelVerifier("http://localhost:1111/udp/", 5);

        List<Channel> verifiedChannels = channels.stream()
                .map(channel -> EXECUTOR.submit(() -> {
                    if (verifier.isValidChannel(channel)) {
                        return channel;
                    } else {
                        return channel.invalidChannel();
                    }
                }))
                .collect(Collectors.toList())
                .stream()
                .map(future -> {
                    try {
                        Channel channel = future.get();
                        System.out.println("Processed: " + channel.getName());
                        return channel;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage(), e);
                    } catch (ExecutionException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }
                })
                .collect(Collectors.toList());

        EXECUTOR.shutdown();

        String exportedValidChannels = new M3uExporter().export(verifiedChannels.stream().filter(Channel::isValid).collect(Collectors.toList()));
        String exportedInvalidChannels = new M3uExporter().export(verifiedChannels.stream().filter(channel -> !channel.isValid()).collect(Collectors.toList()));

        toFile(exportedValidChannels, "valid.m3u");
        toFile(exportedInvalidChannels, "invalid.m3u");
    }

    private static void toFile(String exportedInvalidChannels, String fileName) {
        try {
            Path path = Paths.get(fileName);
            System.out.println(path.getFileName().toString());
            try {
                Files.createFile(path);
            } catch (Exception ignored) {
            }
            Files.write(path, exportedInvalidChannels.getBytes());
            System.out.println(Files.readAllLines(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
