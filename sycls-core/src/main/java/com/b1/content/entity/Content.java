package com.b1.content.entity;

import com.b1.category.entity.Category;
import com.b1.common.TimeStamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contents")
public class Content extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "content_id")
    private Long id;

    @Column(nullable = false, length = 30)
    private String title;

    @Column(nullable = false, length = 50)
    private String description;

    @Column(nullable = false, length = 300)
    private String mainImagePath;

    @OneToMany(mappedBy = "content", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ContentDetailImage> contentDetailImageList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder(access = AccessLevel.PRIVATE)
    private Content(String title, String description, String mainImagePath,
            List<ContentDetailImage> contentDetailImageList, Category category) {
        this.title = title;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.contentDetailImageList = contentDetailImageList;
        this.category = category;
    }
}
