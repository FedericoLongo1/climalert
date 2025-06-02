package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;

@Getter
public class Region {
    private String nombre;

    public Region(String region) {
        this.nombre = normalizar(region);
    }

    private String normalizar(String nombre) {
        return nombre.trim().toLowerCase();
    }

}
