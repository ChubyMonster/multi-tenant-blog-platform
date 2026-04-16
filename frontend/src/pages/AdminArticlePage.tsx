import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  getArticlesByTenant,
  createArticle,
  deleteArticle,
} from "../api/articleApi";
import { type Article, type ArticleRequest } from "../types/article";

function AdminArticlesPage() {
  const { tenantCode } = useParams<{ tenantCode: string }>();

  const [articles, setArticles] = useState<Article[]>([]);
  const [form, setForm] = useState<ArticleRequest>({
    title: "",
    slug: "",
    content: "",
    author: "",
  });

  const [error, setError] = useState("");

  useEffect(() => {
    if (tenantCode) loadArticles();
  }, [tenantCode]);

  const loadArticles = async () => {
    try {
      const res = await getArticlesByTenant(tenantCode!);
      setArticles(res.data);
    } catch {
      setError("Failed to load articles");
    }
  };

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    try {
      await createArticle(tenantCode!, form);
      setForm({ title: "", slug: "", content: "", author: "" });
      loadArticles();
    } catch (err: any) {
      setError(err.response?.data?.message || "Error");
    }
  };

  const handleDelete = async (slug: string) => {
    await deleteArticle(tenantCode!, slug);
    loadArticles();
  };

  return (
    <div>
      <h1>Admin - {tenantCode}</h1>

      <form className="form" onSubmit={handleSubmit}>
        <input name="title" value={form.title} onChange={handleChange} placeholder="Title" />
        <input name="slug" value={form.slug} onChange={handleChange} placeholder="Slug" />
        <textarea name="content" value={form.content} onChange={handleChange} />
        <input name="author" value={form.author} onChange={handleChange} placeholder="Author" />
        <button type="submit">Create</button>
      </form>

      {error && <p className="error">{error}</p>}

      <div className="grid">
        {articles.map((a) => (
          <div key={a.id} className="card">
            <h3>{a.title}</h3>
            <button onClick={() => handleDelete(a.slug)}>Delete</button>
          </div>
        ))}
      </div>
    </div>
  );
}

export default AdminArticlesPage;