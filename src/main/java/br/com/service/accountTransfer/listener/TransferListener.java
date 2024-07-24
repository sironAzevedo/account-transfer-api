package br.com.service.accountTransfer.listener;

import br.com.service.accountTransfer.infra.cloud.event.EventsConfig;
import br.com.service.accountTransfer.models.dtos.NotificacaoRequestDTO;
import br.com.service.accountTransfer.models.dtos.TransferenciaRequestDTO;
import br.com.service.accountTransfer.models.event.TransferenciaEvent;
import br.com.service.accountTransfer.service.IBacenService;
import io.awspring.cloud.messaging.core.NotificationMessagingTemplate;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class TransferListener {

    private final EventsConfig config;
    private final IBacenService bacenService;
    private final NotificationMessagingTemplate notificationTemplate;

    @Async
    @EventListener(TransferenciaEvent.class)
    public void notify(@NonNull TransferenciaEvent event) {

        notificarBacen(event.transfer());
        notifyTipic(event.transfer());
    }

    private void notificarBacen(@NonNull TransferenciaRequestDTO transfer) {

        log.info("INICIO - notificando para o Bacen");
        var notification = NotificacaoRequestDTO.builder()
                .valor(transfer.getValor())
                .conta(transfer.getConta())
                .build();

        bacenService.notification(notification);
        log.info("FIM - notificando para o Bacen");
    }

    private void notifyTipic(TransferenciaRequestDTO transfer) {
        log.info("INICIO - Enviando informação da transferencia para o topico {}", config.getTopic());
        notificationTemplate.convertAndSend(config.getTopic(), transfer, Map.of("transaction_type", transfer.getTipoTransferencia().getDescricao()));
        log.info("FIM - Enviando informação da transferencia para o topico {}", config.getTopic());
    }

}
