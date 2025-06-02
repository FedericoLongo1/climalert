package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.services.IEmailSenderService;
import ar.utn.ba.ddsi.mailing.services.IEmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements IEmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

    @Override
    public void enviar(Email email) {
        logger.info("Simulación de envío de mail a {}", email.getDestinatario());
    }
}

