package ma.local.multi_tenant_blog_platform.controller;

import jakarta.validation.Valid;
import ma.local.multi_tenant_blog_platform.dto.ArticleRequest;
import ma.local.multi_tenant_blog_platform.dto.ArticleResponse;
import ma.local.multi_tenant_blog_platform.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants/{tenantCode}/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAllArticles(@PathVariable String tenantCode) {
        return ResponseEntity.ok(articleService.getAllArticles(tenantCode));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<ArticleResponse> getArticleBySlug(
            @PathVariable String tenantCode,
            @PathVariable String slug) {
        return ResponseEntity.ok(articleService.getArticleByTenantAndSlug(tenantCode, slug));
    }

    @PostMapping
    public ResponseEntity<ArticleResponse> createArticle(
            @PathVariable String tenantCode,
            @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(articleService.createArticle(tenantCode, request));
    }

    @PutMapping("/{slug}")
    public ResponseEntity<ArticleResponse> updateArticle(
            @PathVariable String tenantCode,
            @PathVariable String slug,
            @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(articleService.updateArticle(tenantCode, slug, request));
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<Void> deleteArticle(
            @PathVariable String tenantCode,
            @PathVariable String slug) {
        articleService.deleteArticle(tenantCode, slug);
        return ResponseEntity.noContent().build();
    }
}