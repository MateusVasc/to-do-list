package com.matt.to_do_list.repository;

import com.matt.to_do_list.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    Page<Item> findByOwnerId(UUID ownerId, Pageable pageable);
}
