package ar.utn.ba.ddsi.mailing.services;

import ar.utn.ba.ddsi.mailing.models.entities.Email;

public interface IEmailSenderService {
    void enviar(Email email);
    //TODO implementar el service para algún tipo de formato de envío
}
