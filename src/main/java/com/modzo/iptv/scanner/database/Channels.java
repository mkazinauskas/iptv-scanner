package com.modzo.iptv.scanner.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Channels extends JpaRepository<Channel, Long> {
}
