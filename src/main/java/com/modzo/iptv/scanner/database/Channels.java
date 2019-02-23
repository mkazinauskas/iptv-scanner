package com.modzo.iptv.scanner.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Channels extends JpaRepository<Channel, Long> {
    Page<Channel> findAllByStatus(Channel.Status status, Pageable pageable);
}
