package ma.local.multi_tenant_blog_platform.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
    name = "articles",
    uniqueConstraints = @UniqueConstraint(columnNames = {"tenant_id", "slug"})
)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tenant_id")
    @JsonIgnore
    private Tenant tenant;
}