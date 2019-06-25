<%-- 
    Document   : response
    Created on : 16/11/2018, 7:49:38 PM
    Author     : nbnb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
  <body>
    <jsp:useBean id="mybean" scope="session" class="bbclasses.NameHandlers" />
               <jsp:setProperty name="mybean" property="name" />
    <h1>Hello, <jsp:getProperty name="mybean" property="name" />!
               <jsp:getProperty name="mybean" property="sex" />
    </h1>
</body>

</html>
