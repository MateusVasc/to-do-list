package com.matt.to_do_list.service;

import com.matt.to_do_list.dto.request.CreateItemRequest;
import com.matt.to_do_list.dto.request.UpdateItemRequest;
import com.matt.to_do_list.dto.response.ItemResponse;
import com.matt.to_do_list.model.Item;
import com.matt.to_do_list.repository.ItemRepository;
import com.matt.to_do_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemResponse createItem(CreateItemRequest request) {
        if (this.userRepository.findById(request.ownerId()).isEmpty()) {
            throw new RuntimeException("Owner not found");
        }

        Item itemToCreate = new Item(request);
        this.itemRepository.save(itemToCreate);
        return new ItemResponse(itemToCreate);
    }

    public ItemResponse updateItem(UUID id, UpdateItemRequest request) {
        Item itemToUpdate = this.itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if (request.title() != null) {
            itemToUpdate.setTitle(request.title());
        }

        if (request.description() != null) {
            itemToUpdate.setDescription(request.description());
        }

        if (request.endsAt() != null) {
            itemToUpdate.setEndsAt(request.endsAt());
        }

        itemRepository.save(itemToUpdate);
        return new ItemResponse(itemToUpdate);
    }

    public Page<ItemResponse> getAllItemsByOwnerId(UUID ownerId, Pageable pageable) {
        Page<Item> items = this.itemRepository.findByOwnerId(ownerId, pageable);
        return items.map(ItemResponse::new);
    }

    public void deleteItemById(UUID itemId) {
        if (this.itemRepository.findById(itemId).isEmpty()) {
            throw new RuntimeException("Item not found");
        }

        this.itemRepository.deleteById(itemId);
    }
}
