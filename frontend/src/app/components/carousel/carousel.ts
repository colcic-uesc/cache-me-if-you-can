import { Component } from '@angular/core';
import { Carousel as PCarousel } from 'primeng/carousel';

@Component({
  selector: 'app-carousel',
  imports: [PCarousel],
  templateUrl: './carousel.html',
  styleUrl: './carousel.scss',
})
export class Carousel {
  banners: string[] = ['banner.png', 'banner-1.png', 'banner-2.png'];

  responsiveOptions = [
    {
      breakpoint: '1400px',
      numVisible: 2,
      numScroll: 1,
    },
    {
      breakpoint: '1199px',
      numVisible: 3,
      numScroll: 1,
    },
    {
      breakpoint: '767px',
      numVisible: 2,
      numScroll: 1,
    },
    {
      breakpoint: '575px',
      numVisible: 1,
      numScroll: 1,
    },
  ];
}
