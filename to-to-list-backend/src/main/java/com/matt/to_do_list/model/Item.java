package com.matt.to_do_list.model;


import com.matt.to_do_list.dto.request.CreateItemRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "itens")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime endsAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public Item(CreateItemRequest request) {
        this.title = request.title();
        this.description = request.description();
        this.createdAt = LocalDateTime.now();
        this.endsAt = request.endsAt();
        this.owner = new User(request.ownerId());
    }
}
