<%-- 
    Document   : Test
    Created on : Oct 6, 2015, 7:29:26 PM
    Author     : Sely
--%>

<%@page import="lychee.dote.server.accountcreation.AccountCreatorBean"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%

        AccountCreatorBean acb = new AccountCreatorBean();
        System.out.println(acb.isPhoneNumberAvailable("3545"));
        %>
    </body>
</html>
