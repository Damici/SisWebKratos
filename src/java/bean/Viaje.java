/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import javax.swing.JOptionPane;

/**
 *
 * @author dany
 */
public class Viaje extends Conexion{
    Integer intIdViaje, intIdVehiculo, intNumero, intIdTrabajador, intItem, intIdTipoGasto, intIdGasto;
    Timestamp dateFecIni, dateFecFin, dateFecCierre=null;
    Time timeHoraSalida=null;
    Double dblImpCapital=0.00, dblImpGasto=0.00, dblImpVenta=0.00, dblImpNeto=0.00, dblImpCompra=0.00;
    Double dblImpGanancia=0.00, dblImpUni, dblCantidad, dblTotal;
    String strUsuarioCierre=null, strObservacion=null;
    Integer intIdEstado;
    String strUsuario;
    Timestamp dateFechaReg;
    
    public void nuevoViaje(){
        try {
            //this.conectar();
            intIdViaje = this.ultimoRegistro("IDVIAJE","VIAJE","");
            intIdViaje++;
        
            dateFechaReg = this.fechaServer(); //Fecha y hora del Servidor
            
            PreparedStatement pstm = this.getConexion().prepareStatement ("INSERT INTO "+
                "VIAJE (IDVIAJE, IDVEHICULO, NUMERO, FECHAINICIO, HORASALIDA, FECHAFIN, "+
                "IMPCAPITAL, IMPGASTO, IMPCOMPRA, IMPVENTA, IMPNETO, IMPGANANCIA, FECHACIERRE, "+
                "USUARIOCIERRE, IDESTADO, USUARIO, FECHAREGISTRO) "+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            
            pstm.setInt(1, intIdViaje);
            pstm.setInt(2, intIdVehiculo);
            pstm.setInt(3, intNumero);
            pstm.setTimestamp(4, dateFecIni);
            pstm.setTime(5, timeHoraSalida);
            pstm.setTimestamp(6, dateFecFin);
            pstm.setDouble(7, dblImpCapital);
            pstm.setDouble(8, dblImpGasto);
            pstm.setDouble(9, dblImpCompra);
            pstm.setDouble(10, dblImpVenta);
            pstm.setDouble(11, dblImpNeto);
            pstm.setDouble(12, dblImpGanancia);
            pstm.setTimestamp(13, dateFecCierre);
            pstm.setString(14, strUsuarioCierre);
            pstm.setInt(15, intIdEstado);
            pstm.setString(16, strUsuario);
            pstm.setTimestamp(17, dateFechaReg); 
                                
            pstm.execute();
            pstm.close();
            
            JOptionPane.showMessageDialog(null,"El VIAJE se registró correctamente..");
            //this.desconectar();
        }catch(SQLException e){
            System.out.println("No se grabaron los datos VIAJE SQL.. "+e);
        } catch (Exception e) {
            System.out.println("No se grabaron los datos VIAJE.. "+e);
        }
    }
    
    public void datosViaje(int idviaje){
        ResultSet rs = null;
	String Query = "SELECT * FROM VIAJE WHERE IDVIAJE="+idviaje;
	//this.conectar();
	rs = this.Consultar(Query);
	
	try {
            while(rs.next()){
                this.intIdViaje         = rs.getInt("IDVIAJE");
                this.intIdVehiculo      = rs.getInt("IDVEHICULO");
                this.intNumero          = rs.getInt("NUMERO");
                this.dateFecIni         = rs.getTimestamp("FECHAINICIO");
                this.timeHoraSalida     = rs.getTime("HORASALIDA");
                this.dateFecFin         = rs.getTimestamp("FECHAFIN");
                this.dblImpCapital      = rs.getDouble("IMPCAPITAL");
                this.dblImpGasto        = rs.getDouble("IMPGASTO");
                this.dblImpCompra       = rs.getDouble("IMPCOMPRA");
                this.dblImpVenta        = rs.getDouble("IMPVENTA");
                this.dblImpNeto         = rs.getDouble("IMPNETO");
                this.dblImpGanancia     = rs.getDouble("IMPGANANCIA");
                this.dateFecCierre      = rs.getTimestamp("FECHACIERRE");
                this.strUsuarioCierre   = rs.getString("USUARIOCIERRE");
                this.intIdEstado        = rs.getInt("IDESTADO");
                this.dateFechaReg       = rs.getTimestamp("FECHAREGISTRO");
                this.strUsuario         = rs.getString("USUARIO");
            }
            rs.close();
        //    this.desconectar();
	} catch (SQLException e) {
            System.out.println("Error al consultar los datos del VIAJE "+ intIdVehiculo);
	}
    }
    
    public void nuevoTrabajador(){
        try {
            dateFechaReg = this.fechaServer(); //Fecha y hora del Servidor
            
            PreparedStatement pstm = this.getConexion().prepareStatement ("INSERT INTO "+
                "VIAJETRABAJADOR (IDVIAJE, IDPERSONA, IDESTADO, USUARIO, FECHAREGISTRO) "+
                "VALUES(?,?,?,?,?)");
            
            pstm.setInt(1, intIdViaje);
            pstm.setInt(2, intIdTrabajador);
            pstm.setInt(3, intIdEstado);
            pstm.setString(4, strUsuario);
            pstm.setTimestamp(5, dateFechaReg); 
                                
            pstm.execute();
            pstm.close();
            
//            JOptionPane.showMessageDialog(null,"El Trabajador se registró correctamente..");
            //this.desconectar();
        }catch(SQLException e){
            System.out.println("No se grabaron los datos Trabajador VIAJE SQL.. "+e);
        } catch (Exception e) {
            System.out.println("No se grabaron los datos Trabajador VIAJE.. "+e);
        }
    }
    
    public void nuevoGasto(){
        try {
            //this.conectar();
            intItem = this.ultimoRegistro("ITEM","VIAJEGASTO","IDVIAJE="+this.intIdViaje);
            intItem++;
            
            dateFechaReg = this.fechaServer(); //Fecha y hora del Servidor
            
            PreparedStatement pstm = this.getConexion().prepareStatement ("INSERT INTO "+
                "VIAJEGASTO (IDVIAJE, ITEM, IDTIPOGASTO, IDGASTO, IMPUNITARIO, CANTIDAD, "+
                "IMPGASTO, OBSERVACION, IDESTADO, USUARIO, FECHAREGISTRO) "+
                "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            
            pstm.setInt(1, intIdViaje);
            pstm.setInt(2, intItem);
            pstm.setInt(3, intIdTipoGasto);
            pstm.setInt(4, intIdGasto);
            pstm.setDouble(5, dblImpUni);
            pstm.setDouble(6, dblCantidad);
            pstm.setDouble(7, dblTotal);
            pstm.setString(8, strObservacion);
            pstm.setInt(9, intIdEstado);
            pstm.setString(10, strUsuario);
            pstm.setTimestamp(11, dateFechaReg); 
                                
            pstm.execute();
            pstm.close();
            
            JOptionPane.showMessageDialog(null,"El Gasto se registró correctamente..");
            //this.desconectar();
        }catch(SQLException e){
            System.out.println("No se grabaron los datos gasto VIAJE SQL.. "+e);
        } catch (Exception e) {
            System.out.println("No se grabaron los datos gasto VIAJE.. "+e);
        }
    }
   
    
    public int FunUltimoViaje(Integer idvehiculo){
        int Idmax = 0;
        
        try{
            ResultSet rs = null;
            rs = this.Consultar("SELECT IFNULL(MAX(NUMERO),0) AS MAXIMO FROM VIAJE WHERE IDVEHICULO="+idvehiculo);
            
            while (rs.next()) {
                Idmax = rs.getInt("MAXIMO"); //descripcion
            }

            rs.close();
            } catch (SQLException e) {
                    System.out.println("Error BASE DE DATOS.");
            } catch (Exception e) {
                System.out.println("Error IDE Java.");
            }
        
            return Idmax;
    }
    
    public String FunNombreVehiculo(int IdVehiculo){
        String strRetorno="";

        Object[][]  res = this.select("VEHICULO", "DESCRIPCION","IDVEHICULO ="+IdVehiculo);

        if(res.length>0){
            strRetorno = res[0][0].toString() ;//DESCRIPCION
        }

        return strRetorno;
    }
    
    public Integer getIntIdViaje() {
        return intIdViaje;
    }

    public void setIntIdViaje(Integer intIdViaje) {
        this.intIdViaje = intIdViaje;
    }

    public Integer getIntIdVehiculo() {
        return intIdVehiculo;
    }

    public void setIntIdVehiculo(Integer intIdVehiculo) {
        this.intIdVehiculo = intIdVehiculo;
    }

    public Integer getIntNumero() {
        return intNumero;
    }

    public void setIntNumero(Integer intNumero) {
        this.intNumero = intNumero;
    }

    public Timestamp getDateFecIni() {
        return dateFecIni;
    }

    public void setDateFecIni(Timestamp dateFecIni) {
        this.dateFecIni = dateFecIni;
    }

    public Timestamp getDateFecFin() {
        return dateFecFin;
    }

    public void setDateFecFin(Timestamp dateFecFin) {
        this.dateFecFin = dateFecFin;
    }

    public Time getTimeHoraSalida() {
        return timeHoraSalida;
    }

    public void setTimeHoraSalida(Time timeHoraSalida) {
        this.timeHoraSalida = timeHoraSalida;
    }

    public Double getDblImpCapital() {
        return dblImpCapital;
    }

    public void setDblImpCapital(Double dblImpCapital) {
        this.dblImpCapital = dblImpCapital;
    }

    public Double getDblImpGasto() {
        return dblImpGasto;
    }

    public void setDblImpGasto(Double dblImpGasto) {
        this.dblImpGasto = dblImpGasto;
    }

    public Double getDblImpVenta() {
        return dblImpVenta;
    }

    public void setDblImpVenta(Double dblImpVenta) {
        this.dblImpVenta = dblImpVenta;
    }

    public Double getDblImpNeto() {
        return dblImpNeto;
    }

    public void setDblImpNeto(Double dblImpNeto) {
        this.dblImpNeto = dblImpNeto;
    }

    public void setIntIdEstado(Integer intIdEstado) {
        this.intIdEstado = intIdEstado;
    }

    public void setStrUsuario(String strUsuario) {
        this.strUsuario = strUsuario;
    }

    public Integer getIntIdEstado() {
        return intIdEstado;
    }

    public String getStrUsuario() {
        return strUsuario;
    }

    public Timestamp getDateFechaReg() {
        return dateFechaReg;
    }
    
    public void setIntIdTrabajador(Integer intIdTrabajador) {
        this.intIdTrabajador = intIdTrabajador;
    }

    public void setIntIdTipoGasto(Integer intIdTipoGasto) {
        this.intIdTipoGasto = intIdTipoGasto;
    }

    public void setIntIdGasto(Integer intIdGasto) {
        this.intIdGasto = intIdGasto;
    }

    public void setDblImpUni(Double dblImpUni) {
        this.dblImpUni = dblImpUni;
    }

    public void setDblCantidad(Double dblCantidad) {
        this.dblCantidad = dblCantidad;
    }

    public void setDblTotal(Double dblTotal) {
        this.dblTotal = dblTotal;
    }

    public void setStrObservacion(String strObservacion) {
        this.strObservacion = strObservacion;
    }

    public Double getDblImpCompra() {
        return dblImpCompra;
    }

    public Double getDblImpGanancia() {
        return dblImpGanancia;
    }
    
    
}
