package com.matt.to_do_list.controller;

import com.matt.to_do_list.dto.request.CreateItemRequest;
import com.matt.to_do_list.dto.request.UpdateItemRequest;
import com.matt.to_do_list.dto.response.ItemResponse;
import com.matt.to_do_list.service.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/to-do-list/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    @Transactional
    public ResponseEntity<ItemResponse> createItem(@RequestBody CreateItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.itemService.createItem(request));
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<ItemResponse> updateItem(@PathVariable UUID id, @RequestBody UpdateItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.itemService.updateItem(id, request));
    }

    @GetMapping
    public ResponseEntity<Page<ItemResponse>> getAllUserItems(@RequestParam UUID ownerId, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.itemService.getAllItemsByOwnerId(ownerId, pageable));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteItemById(@PathVariable UUID id) {
        this.itemService.deleteItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
