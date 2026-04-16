package ma.local.multi_tenant_blog_platform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleResponse {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private String author;
    private String tenantCode;
}
