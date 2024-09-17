package med.voll.api.domain.direccion;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    private String calle;
    private String distrito;
    private String ciudad;
    private Integer numero;
    private String complemento;

    public Direccion(DatosDireccion datosDireccion){
        this.calle=datosDireccion.calle();
        this.distrito=datosDireccion.distrito();
        this.ciudad =datosDireccion.ciudad();
        this.numero=Integer.valueOf(datosDireccion.numero());
        this.complemento=datosDireccion.complemento();
    }

    public Direccion actualizarDireccion(DatosDireccion datos){
        this.calle=datos.calle();
        this.distrito=datos.distrito();
        this.ciudad=datos.ciudad();
        this.numero= Integer.valueOf(datos.numero());
        this.complemento=datos.complemento();
        return this;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
}
