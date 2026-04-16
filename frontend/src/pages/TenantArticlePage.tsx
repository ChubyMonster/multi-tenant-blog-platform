import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getArticlesByTenant } from "../api/articleApi";
import { type Article } from "../types/article";
import ArticleCard from "../components/ArticleCard";

function TenantArticlesPage() {
  const { tenantCode } = useParams<{ tenantCode: string }>();

  const [articles, setArticles] = useState<Article[]>([]);
  const [error, setError] = useState("");

  useEffect(() => {
    if (tenantCode) loadArticles();
  }, [tenantCode]);

  const loadArticles = async () => {
    try {
      const response = await getArticlesByTenant(tenantCode!);
      setArticles(response.data);
    } catch {
      setError("Failed to load articles");
    }
  };

  return (
    <div>
      <h1>Articles for {tenantCode}</h1>
      {error && <p className="error">{error}</p>}

      <div className="grid">
        {articles.map((article) => (
          <ArticleCard key={article.id} article={article} />
        ))}
      </div>
    </div>
  );
}

export default TenantArticlesPage;