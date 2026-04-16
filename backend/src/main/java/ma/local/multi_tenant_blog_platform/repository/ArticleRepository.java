package ma.local.multi_tenant_blog_platform.repository;

import ma.local.multi_tenant_blog_platform.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByTenant_Code(String tenantCode);
    Optional<Article> findByTenant_CodeAndSlug(String tenantCode, String slug);
    boolean existsByTenant_CodeAndSlug(String tenantCode, String slug);
}
