export interface AlertResponse {
  productId: number;
  productTitle: string;
  desiredPrice: number;
  bestPrice: number;
  imageContent: string;
  fullfillingOffer: {
    sellerId: number;
    sellerName: string;
    sellerImage: string;
    price: number;
  };
}
