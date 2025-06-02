package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;

@Getter
public class Ciudad {
    private String nombre;

    public Ciudad(String nombre) {
        this.nombre = normalizar(nombre);
    }

    private String normalizar(String nombre) {
        return nombre.trim().toLowerCase();
    }
}
