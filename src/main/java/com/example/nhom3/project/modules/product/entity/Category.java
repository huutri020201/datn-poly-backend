package com.example.nhom3.project.modules.product.entity;

import com.example.nhom3.project.common.utils.SlugHelper;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    Category parent;

    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String slug;

    @Column(name = "category_level")
    int categoryLevel;

    @PrePersist
    @PreUpdate
    public void ensureSlug() {
        this.slug = SlugHelper.generate(this.name);
    }
}