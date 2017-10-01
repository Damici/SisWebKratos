<%-- 
    Document   : login
    Created on : 30/09/2017, 07:26:08 PM
    Author     : dany
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Iniciar Sesión</title>
    </head>
    <body>
        <form action="Login" method="post">
            <h1>Sistema Kratos</h1>
            <fieldset>
                
                <table style="margin: auto">
                    <tr>
                        <td>Usuario:</td>
                        <td><input type="text" name="user"></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="passwd"</td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center"><input type="submit" value="Acceder"</td>
                    </tr>
                </table>
                
            </fieldset>
        </form>
    </body>
</html>
