export interface Article {
    id: number;
    title: string;
    slug : string;
    content : string;
    author : string;
    tenantCode : string;
}

export interface ArticleRequest {
    title: string;
    slug: string;
    content: string;
    author: string;
}