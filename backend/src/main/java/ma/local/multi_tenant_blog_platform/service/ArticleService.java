package ma.local.multi_tenant_blog_platform.service;

import ma.local.multi_tenant_blog_platform.dto.ArticleRequest;
import ma.local.multi_tenant_blog_platform.dto.ArticleResponse;
import ma.local.multi_tenant_blog_platform.entity.Article;
import ma.local.multi_tenant_blog_platform.entity.Tenant;
import ma.local.multi_tenant_blog_platform.exception.DuplicateResourceException;
import ma.local.multi_tenant_blog_platform.exception.ResourceNotFoundException;
import ma.local.multi_tenant_blog_platform.repository.ArticleRepository;
import ma.local.multi_tenant_blog_platform.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TenantRepository tenantRepository;

    public ArticleService(ArticleRepository articleRepository, TenantRepository tenantRepository) {
        this.articleRepository = articleRepository;
        this.tenantRepository = tenantRepository;
    }

    public List<ArticleResponse> getAllArticles(String tenantCode) {
        return articleRepository.findByTenant_Code(tenantCode)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ArticleResponse getArticleByTenantAndSlug(String tenantCode, String slug) {
        Article article = articleRepository.findByTenant_CodeAndSlug(tenantCode, slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        return mapToResponse(article);
    }

    public ArticleResponse createArticle(String tenantCode, ArticleRequest request) {
        Tenant tenant = tenantRepository.findByCode(tenantCode)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found"));

        if (articleRepository.existsByTenant_CodeAndSlug(tenantCode, request.getSlug())) {
            throw new DuplicateResourceException("Article slug already exists for this tenant");
        }

        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setSlug(request.getSlug());
        article.setContent(request.getContent());
        article.setAuthor(request.getAuthor());
        article.setTenant(tenant);

        return mapToResponse(articleRepository.save(article));
    }

    public ArticleResponse updateArticle(String tenantCode, String slug, ArticleRequest request) {
        Article existingArticle = articleRepository.findByTenant_CodeAndSlug(tenantCode, slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        existingArticle.setTitle(request.getTitle());
        existingArticle.setContent(request.getContent());
        existingArticle.setAuthor(request.getAuthor());

        return mapToResponse(articleRepository.save(existingArticle));
    }

    public void deleteArticle(String tenantCode, String slug) {
        Article article = articleRepository.findByTenant_CodeAndSlug(tenantCode, slug)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found"));

        articleRepository.delete(article);
    }

    private ArticleResponse mapToResponse(Article article) {
        ArticleResponse response = new ArticleResponse();
        response.setId(article.getId());
        response.setTitle(article.getTitle());
        response.setSlug(article.getSlug());
        response.setContent(article.getContent());
        response.setAuthor(article.getAuthor());
        response.setTenantCode(article.getTenant().getCode());
        return response;
    }
}