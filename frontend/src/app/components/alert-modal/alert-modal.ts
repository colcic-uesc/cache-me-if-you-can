import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DialogModule } from 'primeng/dialog';
import { AlertResponse } from '../../domain/dto/alert-response';

@Component({
  selector: 'app-alert-edit-modal',
  standalone: true,
  imports: [CommonModule, DialogModule],
  templateUrl: './alert-modal.html',
  styleUrl: './alert-modal.scss'
})
export class AlertModal{
  @Input() visible = false;
  @Input() alert: AlertResponse | null = null;
  @Input() isEditing = false;
  @Output() visibleChange = new EventEmitter<boolean>();
  @Output() save = new EventEmitter<number>();
  @Output() cancel = new EventEmitter<void>();

  desiredPrice = 0;

  ngOnChanges() {
    if (this.alert) {
      this.desiredPrice = this.alert.desiredPrice;
    }
  }

  formatPrice(price: number): string {
    return `R$ ${price.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
  }

  onPriceInput(event: any) {
    const value = event.target.value.replace(/[^\d,]/g, '').replace(',', '.');
    const numericValue = parseFloat(value);
    if (!isNaN(numericValue) && numericValue > 0) {
      this.desiredPrice = numericValue;
    }
  }

  validatePrice() {
    if (this.desiredPrice <= 0) {
      this.desiredPrice = 1;
    }
  }

  decreasePrice() {
    if (this.desiredPrice > 1) {
      this.desiredPrice = Math.max(1, this.desiredPrice - 1);
    }
  }

  increasePrice() {
    this.desiredPrice += 1;
  }

  onSave() {
    this.save.emit(this.desiredPrice);
    this.onCancel();
  }

  onCancel() {
    this.visible = false;
    this.visibleChange.emit(false);
    this.cancel.emit();
  }
}
