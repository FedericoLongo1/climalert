package ar.utn.ba.ddsi.mailing.models.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Temperatura {
    private Double temperaturaCelsius;

    public Temperatura(Double temperaturaCelcius) {
        temperaturaCelcius = temperaturaCelcius;
    }

    public double temperaturaFahrenheit(){
        return (temperaturaCelsius*1.8)+32;
    }
    public double temperaturaKelvin(){
        return temperaturaCelsius+273.5;
    }
}
