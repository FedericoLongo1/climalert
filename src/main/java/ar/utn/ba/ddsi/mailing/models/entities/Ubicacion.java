package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;

@Getter
public class Ubicacion {
    private Ciudad ciudad;
    private Region region;
    private Pais pais;

    //TODO comparaciones en cada una de las entidades
    //Esto sería para que Argentina y aRgEnTiNa sean el mismo país.

    public Ubicacion(Ciudad ciudad, Region region, Pais pais) {
        this.ciudad = ciudad;
        this.region = region;
        this.pais = pais;
    }
}
