import { ProductDetail } from './../../pages/product-detail/product-detail';
import { Brand } from "../models/brand";
import { Category } from "../models/category";

export interface ProductDetailedResponse {
  title: string;
  description: string;
  released: Date;
  brand: Brand;
  category: Category;
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
