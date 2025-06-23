import { Image } from "./image";

export interface Product {
    id: number;
    title: string;
    description: string;
    price: number;
    stockQuantity: number;
    createdAt: Date;
    updatedAt: Date;
    enabled: boolean;
    brand: string;
    category: string;
    images: Image[];
}
