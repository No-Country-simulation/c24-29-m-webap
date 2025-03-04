package com.no_country.fichaje.datos.organizacion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrganizacionLoginDTO {
   private Integer numero;

    public OrganizacionLoginDTO(Integer numero, String password) {
        this.numero = numero;
        this.password = password;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
}
