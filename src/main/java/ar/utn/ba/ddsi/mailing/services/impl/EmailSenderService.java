package ar.utn.ba.ddsi.mailing.services.impl;

import ar.utn.ba.ddsi.mailing.models.entities.Email;
import ar.utn.ba.ddsi.mailing.services.IEmailSenderService;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService implements IEmailSenderService {

    @Override
    public void enviar(Email email){
        //TODO
    }
}
