import { Link } from "react-router-dom";
import { type Article } from "../types/article";

interface Props {
  article: Article;
}

function ArticleCard({ article }: Props) {
  return (
    <div className="card">
      <h3>{article.title}</h3>
      <p>{article.content.substring(0, 120)}...</p>
      <p><strong>Author:</strong> {article.author}</p>

      <Link to={`/${article.tenantCode}/articles/${article.slug}`}>
        Read more
      </Link>
    </div>
  );
}

export default ArticleCard;