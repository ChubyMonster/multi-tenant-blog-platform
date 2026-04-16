import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import HomePage from "./pages/HomePage";
import TenantArticlesPage from "./pages/TenantArticlesPage";
import ArticleDetailsPage from "./pages/ArticleDetailsPage";
import AdminArticlesPage from "./pages/AdminArticlesPage";

function App() {
  return (
    <>
      <Navbar />
      <div className="container">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/:tenantCode/articles" element={<TenantArticlesPage />} />
          <Route path="/:tenantCode/articles/:slug" element={<ArticleDetailsPage />} />
          <Route path="/admin/:tenantCode/articles" element={<AdminArticlesPage />} />
        </Routes>
      </div>
    </>
  );
}

export default App;