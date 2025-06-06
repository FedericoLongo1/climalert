package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Clima;
import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.models.repositories.IClimaRepository;
import ar.utn.ba.ddsi.mailing.services.IAlertasService;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.Arrays;
import java.util.List;

@Service
public class AlertasService implements IAlertasService {
    private static final Logger logger = LoggerFactory.getLogger(AlertasService.class);
    private static final double TEMPERATURA_ALERTA = 35.0;
    private static final int HUMEDAD_ALERTA = 60;

    private final IClimaRepository climaRepository;
    private final String remitente;
    private final List<String> destinatarios;
    private final IEmailService emailService;

    public AlertasService(
            IClimaRepository climaRepository,
            IEmailService emailService,
            @Value("${email.alertas.remitente}") String remitente,
            @Value("${email.alertas.destinatarios}") String destinatarios) {
        this.climaRepository = climaRepository;
        this.emailService = emailService;
        this.remitente = remitente;
        this.destinatarios = Arrays.asList(destinatarios.split(","));
    }


    @Override
    public Mono<Void> generarAlertasYAvisar() {
        return Mono.fromCallable(() -> climaRepository.findByProcesado(false))
            .flatMap(climas -> {
                logger.info("Procesando {} registros de clima no procesados", climas.size());
                return Mono.just(climas);
            })
            .flatMap(climas -> {
                climas.stream()
                    .filter(this::cumpleCondicionesAlerta)
                    .forEach(this::generarEmail);
                
                // Marcar todos como procesados
                climas.forEach(clima -> {
                    clima.setProcesado(true);
                    climaRepository.save(clima);
                });
                
                return Mono.empty();
            })
            .onErrorResume(e -> {
                logger.error("Error al procesar alertas: {}", e.getMessage());
                return Mono.empty();
            })
            .then();
    }

    private boolean cumpleCondicionesAlerta(Clima clima) {
        return clima.getTemperatura().getTemperaturaCelsius() > TEMPERATURA_ALERTA &&
               clima.getHumedad() > HUMEDAD_ALERTA;
    }


    private void generarEmail(Clima clima) {
        String ciudad = clima.getUbicacion().getCiudad().getNombre();
        String asunto = "Alerta de Clima - Condiciones Extremas";
        String mensaje = String.format(
                "ALERTA: Condiciones climáticas extremas detectadas en %s\n\n" +
                        "Temperatura: %.1f°C\n" +
                        "Humedad: %d%%\n" +
                        "Condición: %s\n" +
                        "Velocidad del viento: %.1f km/h\n\n" +
                        "Se recomienda tomar precauciones.",
                ciudad,
                clima.getTemperatura().getTemperaturaCelsius(),
                clima.getHumedad(),
                clima.getCondicion(),
                clima.getVelocidadVientoKmh()
        );

        for (String destinatario : destinatarios) {
            Email email = new Email(destinatario, remitente, asunto, mensaje);
            email.setEnviado(false); //Pongo el mail en pendiente para que se envíe al momento de procesar pendientes
            emailService.crearEmail(email);
        }

        logger.info("Email de alerta generado para {} - Enviado a {} destinatarios", ciudad, destinatarios.size());
    }

} 