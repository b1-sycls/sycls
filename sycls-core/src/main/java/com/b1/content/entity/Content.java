package com.b1.content.entity;

import com.b1.category.entity.Category;
import com.b1.common.TimeStamp;
import jakarta.persistence.CascadeType;
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

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContentStatus status;

    @OneToMany(mappedBy = "content", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<ContentDetailImage> contentDetailImageList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder(access = AccessLevel.PRIVATE)
    private Content(final String title, final String description, final Category category,
            final ContentStatus status, final String mainImagePath) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = status;
        this.mainImagePath = mainImagePath;
    }

    public static Content addContent(final String title, final String description,
            final Category category, final String mainImagePath) {
        return Content.builder()
                .title(title)
                .description(description)
                .category(category)
                .status(ContentStatus.HIDDEN)
                .mainImagePath(mainImagePath)
                .build();
    }

    public void updateContent(final Category category, final String title, final String description,
            final String mainImagePath, final List<ContentDetailImage> detailImageList) {
        this.category = category;
        this.title = title;
        this.description = description;
        this.mainImagePath = mainImagePath;
        this.contentDetailImageList = detailImageList;
    }

    public void addContentDetailImageList(final List<ContentDetailImage> contentDetailImageList) {
        this.contentDetailImageList = contentDetailImageList;
    }

    public void updateStatus(final ContentStatus status) {
        this.status = status;
    }
}
