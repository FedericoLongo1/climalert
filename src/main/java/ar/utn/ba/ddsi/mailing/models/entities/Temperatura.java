package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;

@Getter
public class Temperatura {
    private Double temperaturaCelsius;

    public double temperaturaFahrenheit(){
        return (temperaturaCelsius*1.8)+32;
    }
    public double temperaturaKelvin(){
        return temperaturaCelsius+273.5;
    }
}
