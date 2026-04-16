import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getTenants } from "../api/articleApi";
import {type  Tenant } from "../types/tenant";

function HomePage() {
  const [tenants, setTenants] = useState<Tenant[]>([]);
  const [error, setError] = useState("");

  useEffect(() => {
    loadTenants();
  }, []);

  const loadTenants = async () => {
    try {
      const response = await getTenants();
      setTenants(response.data);
    } catch {
      setError("Failed to load tenants");
    }
  };

  return (
    <div>
      <h1>Tenants</h1>
      {error && <p className="error">{error}</p>}

      <div className="grid">
        {tenants.map((tenant) => (
          <div key={tenant.id} className="card">
            <h3>{tenant.name}</h3>
            <p>{tenant.code}</p>

            <Link to={`/${tenant.code}/articles`}>
              View Articles
            </Link>

            <br />

            <Link to={`/admin/${tenant.code}/articles`}>
              Admin Panel
            </Link>
          </div>
        ))}
      </div>
    </div>
  );
}

export default HomePage;