/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

//import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dany
 */
public class Conexion{
    //DATOS PARA LA CONEXION 
    private String bd;
    private String login;
    private String password;
    private String url;
    private Connection con = null;
    public ResultSet rs;
    public Statement stm;
    public PreparedStatement pstm;

    public Conexion(){
        bd="KRATOS";
        login="dminaya";
        password="dminaya";
        url="jdbc:mariadb://localhost:3306/"+bd;
        stm=null;
    }


    public void conectar(){
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            con = DriverManager.getConnection(url,login,password);

            if (con != null){
                System.out.println("base de datos "+bd+" conectada!!");
                stm = con.createStatement();
            }

        }catch (SQLException e){
            System.out.println(e);
        }catch (Exception e){
            System.out.println("no se conectó correctamente"+e);
        }
    }	

    public Connection getConexion(){
        return con;
    }

    public void desconectar(){
//        con = null;
//        System.out.println("Desconectado de la base de datos!!");
        boolean result = true;
        try{
            con.close();
            System.out.println("Desconectado :D");
        }
        catch(SQLException cerrar){
            JOptionPane.showMessageDialog(null,"No es posible cerrar la conexión: "+cerrar);
            result = false;
        }
    }
    
    //consultas
    public void QuerySQL(String sql){
        try{           
            stm = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs  = stm.executeQuery(sql);
        }
        catch(SQLException erroSQL){
             JOptionPane.showMessageDialog(null,"Un comando SQL no funciona: "+erroSQL+",  SQL passado: "+sql);
        }
    }
    
    public String [][] select(String table, String fields, String where){
        int registros=0;
        String colname[] = fields.split(","); //quitar espacios en blanco

        //COnsultas Sql
        String q= "SELECT "+ fields + " FROM "+ table;
        String q2= "SELECT count(*) as Total FROM "+table;

        if(where != null){
            q+= " WHERE "+	where;
            q2+= " WHERE "+ where;
        }

        try{
            PreparedStatement pstm = con.prepareStatement(q2);
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("Total");
            res.close();
        }catch(SQLException e){
            System.out.println(e);
        }


        ///se crea una matrix con tantas filas y columnas que necesite
        String[][] data = new String[registros][fields.split(",").length];

        //realizamos la consulta sql y llenamos los datos en la matriz "OBJECT"
        try{
            PreparedStatement pstm = con.prepareStatement(q);
            ResultSet res = pstm.executeQuery();
            int i = 0;

            while(res.next()){
                for(int j=0; j<= fields.split(",").length-1;j++){
                    data[i][j] = res.getString(colname[j].trim());
                }

                i++;
            }

            res.close();

        }catch(SQLException e){
            System.out.println(e);
        }

        return data;

    }


    //consultar
    public ResultSet Consultar(String SQL){
        ResultSet rs = null;

        try{
            rs = stm.executeQuery(SQL);

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "No se logro establecer la conexion a la bd "+bd,"Error",JOptionPane.ERROR_MESSAGE);

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se logro establecer la conexion a la bd "+bd,"Error",JOptionPane.ERROR_MESSAGE);
        }
        
        return rs;
    }

    //procedure detpartabla
    public ResultSet listDetpartabla(int IdCabpartabla, String IdEstado){
        ResultSet rs = null;
        rs = this.Consultar("SELECT * FROM DETPARTABLA WHERE IDCABPARTABLA ="+IdCabpartabla+" AND IDESTADO LIKE '"+IdEstado+"';");
        return rs;
    }

    //actualizar
    public void actualizar(String SQL){		
        try{
            stm.executeUpdate(SQL);

            JOptionPane.showMessageDialog(null,"Se actualizo correctamente..");

        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "No se logro establecer la conexion a la bd "+bd,"Error",JOptionPane.ERROR_MESSAGE);

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "No se logro establecer la conexion a la bd "+bd,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }



    public Timestamp fechaServer(){
        java.util.Date fecha = new java.util.Date(); //fecha actual
        long lnMilisegundos = fecha.getTime();
        Timestamp tiempoActual = new Timestamp(lnMilisegundos);

        return tiempoActual;
    }


    public int ultimoRegistro(String column, String table, String where){
        ResultSet rs = null;
        int Idmax = 0;

        String sql_max_id ="SELECT IFNULL(MAX("+column.toUpperCase()+"),0) AS MAXIMO FROM "+table.toUpperCase();
        if (where.equals("")==false){
            sql_max_id += " WHERE "+where.toUpperCase();
        }
        Statement st;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql_max_id);

            while(rs.next()){
                Idmax =  rs.getInt("MAXIMO");
            }
        } catch (SQLException e1) {
            System.out.println("Error al consultar el ultimo registro de la tabla "+table.toUpperCase()+ " - "+e1.toString());
        }

        return Idmax;
    }

    public int cantidadRegistros(String table){
        ResultSet rs = null;
        long cant = 0;

        String sql_max_id ="SELECT COUNT(*) AS CANTIDAD FROM "+table.toUpperCase();
        Statement st;
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql_max_id);

            while(rs.next()){
                    cant = rs.getLong("CANTIDAD");
            }
        } catch (SQLException e1) {
            System.out.println("Error al consultar la cantidad de registros de la tabla "+table.toUpperCase()+ " - "+e1.toString());
        } catch (Exception e){
            System.out.println("error al ejecutar "+e);
        }

        return (int) cant;
    }


    public Integer ingresar(String user, String contra){
        this.QuerySQL("SELECT * FROM USUARIO WHERE LOGIN='"+user+"' AND PASSWD='"+contra+"'");
        try{
            if (this.rs.next()){
                Integer idEstado = this.rs.getInt("IDESTADO");
                if(idEstado == 1){
                    return this.rs.getInt("IDUSUARIO");
                }else{
                    //JOptionPane.showMessageDialog(null, "Usuario Deshabilitado..");
                    return -1;
                }
            }else{
                //JOptionPane.showMessageDialog(null, "Usuario y contraseña Invalidos. Digite nuevamente");
                return -1;
            }
        }
            catch(SQLException erro){
            //JOptionPane.showMessageDialog(null,"Error: "+erro);
        }
        return -1;
    }

    public Timestamp convertStringToTimestamp(String str_date) {
        try {
          DateFormat formatter;
          formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:00");
           // you can change format of date
          java.util.Date date = (Date) formatter.parse(str_date);
          java.sql.Timestamp timeStampDate = new Timestamp(date.getTime());

          return timeStampDate;
        } catch (ParseException e) {
          System.out.println("Exception :" + e);
          return null;
        }
    }
    
    public String FunDetPartabla(int IdDetpartabla, int Opcion){
        String strRetorno="";

        Object[][]  res = this.select("DETPARTABLA", "DESCRIPCION, ABREV","IDDETPARTABLA ="+IdDetpartabla);

        if(res.length>0){
            switch (Opcion){
                case 1: strRetorno = res[0][0].toString() ; break; //DESCRIPCION
                case 2: strRetorno = res[0][1].toString() ; break; //ABREV
                default: System.out.println("no existe la opción");
            }
        }

        return strRetorno;
    }
    
    public String FunUbigeo(String codubigeo){
        String strRetorno="";

        Object[][]  res = this.select("UBIGEO", "CODUBIGEO, UBIGEO","CODUBIGEO ='"+codubigeo+"'");

        if(res.length>0){
            strRetorno = res[0][1].toString() ; //UBIGEO
        }

        return strRetorno;
    }
    
    public java.sql.Date convertJavaDateToSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }
    
    public void limpiarTabla(JTable tabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int filas = tabla.getRowCount();

        if (filas>0){
            for(int i=0; i< filas; i++){
                    modelo.removeRow(0);
            }
        }
    }
}
