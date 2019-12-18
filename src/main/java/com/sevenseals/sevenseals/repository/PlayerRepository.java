package com.sevenseals.sevenseals.repository;

import com.sevenseals.sevenseals.entity.Game;
import com.sevenseals.sevenseals.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}