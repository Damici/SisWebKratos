/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 * @author dany
 */
public class Usuario extends Conexion{
    Integer intIdUsuario, intFlagAdmin, intIdPersona, intIdEstado;
    String strDenominacion, strLogin, strPasswd, strUsuario, strUsuarioBaja;
    Timestamp dateFechaReg, dateFechaBaja;
    
    public void nuevoUsuario(){
        try {
            
            intIdUsuario = this.ultimoRegistro("IDUSUARIO","USUARIO","");
            intIdUsuario++;
        
            dateFechaReg = this.fechaServer(); //Fecha y hora del Servidor
                        
            PreparedStatement pstm = this.getConexion().prepareStatement ("INSERT INTO "+
                "USUARIO (IDUSUARIO, DENOMINACION, LOGIN, PASSWD, FLAGADMIN, IDPERSONA, "+
                "IDESTADO, USUARIO, FECHAREGISTRO, USUARIOBAJA, FECHABAJA) "+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            
            pstm.setInt(1,intIdUsuario);
            pstm.setString(2,strDenominacion);
            pstm.setString(3,strLogin);
            pstm.setString(4,strPasswd);
            pstm.setInt(5,intFlagAdmin);
            pstm.setInt(6,intIdPersona);
            pstm.setInt(7,intIdEstado);
            pstm.setString(8,strUsuario);
            pstm.setTimestamp(9,dateFechaReg);
            pstm.setString(10,strUsuarioBaja);
            pstm.setTimestamp(11,dateFechaBaja);
            
            pstm.execute();
            pstm.close();
            
            System.out.println("El Usuario se registro correctamente.!!");

        }catch(SQLException e){
            System.out.println("No se grabaron los datos USUARIO SQL.. "+e);
        } catch (Exception e) {
            System.out.println("No se grabaron los datos USUARIO.. "+e);
        }
    }
    
    public void datosUsuario(int idusuario){
        ResultSet rs = null;
	String Query = "SELECT * FROM USUARIO WHERE IDUSUARIO="+idusuario;

	rs = this.Consultar(Query);
	
	try {
            while(rs.next()){
                this.intIdUsuario       = rs.getInt("IDUSUARIO");
                this.strDenominacion    = rs.getString("DENOMINACION");
                this.strLogin           = rs.getString("LOGIN");
                this.strPasswd          = rs.getString("PASSWD");
                this.intIdPersona       = rs.getInt("IDPERSONA");
                this.intFlagAdmin       = rs.getInt("FLAGADMIN");
                this.intIdEstado        = rs.getInt("IDESTADO");
                this.dateFechaReg       = rs.getTimestamp("FECHAREGISTRO");
                this.strUsuario         = rs.getString("USUARIO");
                this.strUsuarioBaja     = rs.getString("USUARIOBAJA");
                this.dateFechaBaja      = rs.getTimestamp("FECHABAJA");
            }
            rs.close();
            
	} catch (SQLException e) {
            System.out.println("Error al consultar los datos del USUARIO "+ idusuario);
	}
    }
    
    /*
    IDUSUARIO
    DENOMINACION
    LOGIN
    PASSWD
    FLAGADMIN
    IDPERSONA
    IDESTADO
    USUARIO
    FECHAREGISTRO
    USUARIOBAJA
    FECHABAJA
    */

    public Integer getIntIdUsuario() {
        return intIdUsuario;
    }

    public void setIntIdUsuario(Integer intIdUsuario) {
        this.intIdUsuario = intIdUsuario;
    }

    public Integer getIntFlagAdmin() {
        return intFlagAdmin;
    }

    public void setIntFlagAdmin(Integer intFlagAdmin) {
        this.intFlagAdmin = intFlagAdmin;
    }

    public Integer getIntIdPersona() {
        return intIdPersona;
    }

    public void setIntIdPersona(Integer intIdPersona) {
        this.intIdPersona = intIdPersona;
    }

    public Integer getIntIdEstado() {
        return intIdEstado;
    }

    public void setIntIdEstado(Integer intIdEstado) {
        this.intIdEstado = intIdEstado;
    }

    public String getStrDenominacion() {
        return strDenominacion;
    }

    public void setStrDenominacion(String strDenominacion) {
        this.strDenominacion = strDenominacion;
    }

    public String getStrLogin() {
        return strLogin;
    }

    public void setStrLogin(String strLogin) {
        this.strLogin = strLogin;
    }

    public String getStrPasswd() {
        return strPasswd;
    }

    public void setStrPasswd(String strPasswd) {
        this.strPasswd = strPasswd;
    }

    public String getStrUsuario() {
        return strUsuario;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public String getStrUsuarioBaja() {
        return strUsuarioBaja;
    }

    public void setStrUsuarioBaja(String strUsuarioBaja) {
        this.strUsuarioBaja = strUsuarioBaja;
    }

    public Timestamp getDateFechaBaja() {
        return dateFechaBaja;
    }

    public void setDateFechaBaja(Timestamp dateFechaBaja) {
        this.dateFechaBaja = dateFechaBaja;
    }

    public Timestamp getDateFechaReg() {
        return dateFechaReg;
    }
    
    
}
