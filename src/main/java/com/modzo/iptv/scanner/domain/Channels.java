package com.modzo.iptv.scanner.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Channels extends JpaRepository<Channel, Long> {
    Page<Channel> findAllByStatus(Channel.Status status, Pageable pageable);

    Optional<Channel> findByName(String name);
}
