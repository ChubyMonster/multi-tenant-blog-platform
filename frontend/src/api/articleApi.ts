import axios from "axios";
import {type Article, type ArticleRequest} from '../types/article';
import { type Tenant } from '../types/tenant';

const API = axios.create({
    baseURL: "http://localhost:8080/api",
});

export const getTenants = () => 
    API.get<Tenant[]>("/tenants");

export const getArticlesByTenant = (tenantCode: string) =>
    API.get<Article[]>(`/tenants/${tenantCode}/articles`);

export const getArticleBySlug = (tenantCode: string, slug: string) =>
    API.get<Article>(`/tenants/${tenantCode}/articles/${slug}`);

export const createArticle = (tenantCode: string, payload: ArticleRequest) =>
    API.post(`/tenants/${tenantCode}/articles`, payload);

export const updateArticle = (
    tenantCode: string,
    slug: string,
    payload: ArticleRequest
) =>
    API.put(`/tenants/${tenantCode}/articles/${slug}`, payload);

export const deleteArticle = (tenantCode: string, slug: string) =>
    API.delete(`/tenants/${tenantCode}/articles/${slug}`);