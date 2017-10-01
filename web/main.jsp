<%-- 
    Document   : main
    Created on : 30/09/2017, 08:09:22 PM
    Author     : dany
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pagina Principal</title>
        <link href="jq/jquery-ui.min.css" type="text/css" rel="stylesheet" />
        <link href="jq/menu/menu.css" type="text/css" rel="stylesheet" />
        <link href="css/main.css" type="text/css" rel="stylesheet" />
        
        <script src="jq/jquery-2.1.3.min.js" type="text/javascript" ></script>
        <script src="jq/jquery-ui.min.js" type="text/javascript" ></script>
        <script src="jq/menu/menu.js" type="text/javascript" ></script>
                
    </head>
    <body background="images/fondo.jpg">
        <div id="m_main">
            <div id="m_top">
                
            </div>
            <div id="m_menu">
                <%@include file="WEB-INF/jspf/menu.jspf" %>
            </div>
            <div id="m_body">
                <h3>Bienvenido: ${usuario} - ${nomusuario}</h3>
                <iframe src="#" style="border: none" width="100%" height="100%">
                </iframe>
            </div>
        </div>
    </body>
</html>
