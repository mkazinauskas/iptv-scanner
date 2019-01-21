package com.modzo.iptv.scanner.sorter;

import com.modzo.iptv.scanner.ApplicationConfiguration;
import com.modzo.iptv.scanner.Channel;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ChannelSorter {

    private final List<String> sortList;

    public ChannelSorter(ApplicationConfiguration applicationConfiguration) {
        this.sortList = applicationConfiguration.getSortingList().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    public List<Channel> sort(List<Channel> channels) {
        List<Channel> unusedChannels = new LinkedList<>(channels);

        List<Channel> finalList = new LinkedList<>();
        for (String item : sortList) {
            List<Channel> requestedChannels = findRequestedChannels(item, unusedChannels);
            finalList.addAll(requestedChannels);

            unusedChannels.removeAll(requestedChannels);

        }
        finalList.addAll(unusedChannels.stream().sorted(Comparator.comparing(Channel::getName)).collect(Collectors.toList()));
        return finalList;
    }

    private List<Channel> findRequestedChannels(String name, List<Channel> channels) {
        Set<Channel> finalList = new LinkedHashSet<>();
        channels.parallelStream()
                .filter(channel -> channel.getName().equalsIgnoreCase(name))
                .forEach(finalList::add);

        channels.parallelStream()
                .filter(channel -> channel.getName().toLowerCase().replaceAll(" ", "")
                        .startsWith(name.replaceAll(" ", "")))
                .forEach(finalList::add);

        channels.parallelStream()
                .filter(channel -> channel.getName().toLowerCase().startsWith(name))
                .forEach(finalList::add);

        return new ArrayList<>(finalList);
    }
}