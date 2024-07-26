package com.b1.category.entity;

import com.b1.common.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "categories")
public class Category extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryStatus status;

    @Builder(access = AccessLevel.PRIVATE)
    private Category(final String name, final CategoryStatus status) {
        this.name = name;
        this.status = status;
    }

    public static Category addCategory(final String name) {
        return Category.builder()
                .name(name)
                .status(CategoryStatus.ENABLE)
                .build();
    }

    public void update(final String name) {
        this.name = name;
    }

    public void disableStatus() {
        this.status = CategoryStatus.DISABLE;
    }

    public void enableStatus() {
        this.status = CategoryStatus.ENABLE;
    }
}
