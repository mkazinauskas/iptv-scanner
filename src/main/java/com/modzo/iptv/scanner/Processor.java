package com.modzo.iptv.scanner;

import com.modzo.iptv.scanner.exporter.FileWriter;
import com.modzo.iptv.scanner.exporter.M3uExporter;
import com.modzo.iptv.scanner.importer.ReadChannelsFromFile;
import com.modzo.iptv.scanner.sorter.ChannelSorter;
import com.modzo.iptv.scanner.verifier.ChannelVerifier;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

//@Component
public class Processor implements CommandLineRunner {

    private static final ThreadPoolExecutor EXECUTOR = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);

    private final ChannelSorter channelSorter;

    private final M3uExporter exporter;

    private final ReadChannelsFromFile readChannelsFromFile;

    private final ChannelVerifier channelVerifier;

    private final FileWriter fileWriter;

    public Processor(ChannelSorter channelSorter,
                     M3uExporter exporter,
                     ReadChannelsFromFile readChannelsFromFile,
                     ChannelVerifier channelVerifier,
                     FileWriter fileWriter) {
        this.channelSorter = channelSorter;
        this.exporter = exporter;
        this.readChannelsFromFile = readChannelsFromFile;
        this.channelVerifier = channelVerifier;
        this.fileWriter = fileWriter;
    }

    @Override
    public void run(String... args) throws Exception {
        String playlist;
        try {
            playlist = FileUtils.readFileToString(new File(getClass().getResource("/playlist.m3u").getPath()), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("Failed to read file");
            return;
        }

        List<Channel> channels = readChannelsFromFile.doImport(playlist);
        if (channels.size() == 0) {
            System.out.print("No channels parsed");
            return;
        }

        channels = channelSorter.sort(channels);

        String allChannels = exporter.export(channels);
        fileWriter.toFile(allChannels, "all.m3u");

        List<Channel> verifiedChannels = channels.stream()
                .map(channel -> EXECUTOR.submit(() -> {
                    if (channelVerifier.isValidChannel(channel)) {
                        System.out.println("Processed: " + channel.toString());
                        return channel;
                    } else {
                        Channel invalidChannel = channel.invalidChannel();
                        System.out.println("Processed and marked failed: " + invalidChannel.toString());
                        return invalidChannel;
                    }
                }))
                .collect(Collectors.toList())
                .stream()
                .map(future -> {
                    try {
                        System.out.println("Tasks left: " + EXECUTOR.getQueue().size());
                        return future.get();
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e.getMessage(), e);
                    }
                })
                .collect(Collectors.toList());

        EXECUTOR.shutdown();

        String exportedValidChannels = new M3uExporter().export(verifiedChannels.stream().filter(Channel::isValid).collect(Collectors.toList()));
        String exportedInvalidChannels = new M3uExporter().export(verifiedChannels.stream().filter(channel -> !channel.isValid()).collect(Collectors.toList()));

        fileWriter.toFile(exportedValidChannels, "valid.m3u");
        fileWriter.toFile(exportedInvalidChannels, "invalid.m3u");
    }
}
