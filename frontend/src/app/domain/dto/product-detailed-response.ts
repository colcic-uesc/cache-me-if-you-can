import { ProductDetail } from './../../pages/product-detail/product-detail';
import { Brand } from "../models/brand";
import { CategoryResponse } from "../models/category";

export interface ProductDetailedResponse {
  id: number;
  title: string;
  description: string;
  released: Date;
  brand: Brand;
  category: CategoryResponse;
  bestPrice: number;
  images: string[];
  offers: ProductDetailedOfferResponse[];
  history: any[];
}

export interface ProductDetailedOfferResponse {
  sellerId: number;
  sellerName: string;
  sellerImage: string;
  price: number;
}

export interface ProductDetailedHistoryResponse{
  price:number;
  date: Date;
}
