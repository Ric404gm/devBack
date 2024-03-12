package com.asp.eiyu.ldap.dto;

import lombok.Data;

@Data
public class LoginLdapRequest {
    
    private String usuario;
    private String contrasena;
    private String idUsuario;
    private String idSesion;
    private String estatus;
    private String idAplicativo;
    private String ipUsuario;
}
