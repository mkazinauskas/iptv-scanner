package com.modzo.iptv.scanner;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {

    @NotNull
    private Integer pingRetries;

    @NotNull
    private Integer delayRetries;

    @NotNull
    private List<String> sortingList;

    public Integer getPingRetries() {
        return pingRetries;
    }

    public void setPingRetries(Integer pingRetries) {
        this.pingRetries = pingRetries;
    }

    public Integer getDelayRetries() {
        return delayRetries;
    }

    public void setDelayRetries(Integer delayRetries) {
        this.delayRetries = delayRetries;
    }

    public List<String> getSortingList() {
        return sortingList;
    }

    public void setSortingList(List<String> sortingList) {
        this.sortingList = sortingList;
    }
}
