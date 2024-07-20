package com.b1.sycls.domain.content.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contents_detail_images")
public class ContentDetailImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_image_id")
    private Long id;

    @Column(nullable = false, length = 300)
    private String detailImagePath;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentDetailImageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    @Builder(access = AccessLevel.PRIVATE)
    private ContentDetailImage(String detailImagePath, ContentDetailImageStatus status,
            Content content) {
        this.detailImagePath = detailImagePath;
        this.status = status;
        this.content = content;
    }
}
