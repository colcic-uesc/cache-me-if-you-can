import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { ButtonModule } from 'primeng/button';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { ToastModule } from 'primeng/toast';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { MessageService, ConfirmationService } from 'primeng/api';

import { AlertService } from '../../domain/services/alert.service';
import { AlertResponse } from '../../domain/dto/alert-response';
import { AlertRequest } from '../../domain/dto/alert-request';
import { AlertModal } from '../../components/alert-modal/alert-modal';

@Component({
  selector: 'app-alerts',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ButtonModule,
    ProgressSpinnerModule,
    ToastModule,
    ConfirmDialogModule,
    AlertModal,
  ],
  providers: [MessageService, ConfirmationService],
  templateUrl: './alerts.html',
  styleUrl: './alerts.scss',
})
export class Alerts implements OnInit {
  alerts: AlertResponse[] = [];
  isLoading = true;
  showEditModal = false;
  selectedAlert: AlertResponse | null = null;

  constructor(
    private alertService: AlertService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this.loadAlerts();
  }

  loadAlerts(): void {
    this.isLoading = true;
    this.alertService.getAlerts().subscribe({
      next: (alerts) => {
        this.alerts = alerts;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Erro ao carregar alertas:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível carregar os alertas. Tente novamente.',
        });
        this.isLoading = false;
      },
    });
  }

  editAlert(alert: AlertResponse): void {
    this.selectedAlert = alert;
    this.showEditModal = true;
  }


  onSaveAlert(newPrice: number): void {
    if (!this.selectedAlert) {
      return;
    }

    const alertRequest: AlertRequest = {
      productId: this.selectedAlert.productId,
      desiredPrice: newPrice,
    };

    this.alertService.updateAlert(alertRequest).subscribe({
      next: () => {
        this.loadAlerts();

        this.messageService.add({
          severity: 'success',
          summary: 'Alerta Atualizado!',
          detail: `Preço desejado alterado para R$ ${newPrice.toLocaleString(
            'pt-BR',
            { minimumFractionDigits: 2, maximumFractionDigits: 2 }
          )}`,
        });

        this.selectedAlert = null;
      },
      error: (error) => {
        console.error('Erro ao atualizar alerta:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível atualizar o alerta. Tente novamente.',
        });
      },
    });
  }
  onCancelEdit(): void {
    this.selectedAlert = null;
  }

  deleteAlert(alertId: number): void {
    this.alertService.deleteAlert(alertId).subscribe({
      next: () => {
        this.loadAlerts();
        this.messageService.add({
          severity: 'success',
          summary: 'Alerta Excluído!',
          detail: 'O alerta foi removido com sucesso.',
        });
      },
      error: (error) => {
        console.error('Erro ao excluir alerta:', error);
        this.messageService.add({
          severity: 'error',
          summary: 'Erro',
          detail: 'Não foi possível excluir o alerta. Tente novamente.',
        });
      },
    });
  }

}
