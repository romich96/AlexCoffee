<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>

<compress:html removeIntertagSpaces="true">
    <!DOCTYPE HTML>
    <html lang="ru">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="author" content="Yurii Salimov https://www.linkedin.com/in/yurii-salimov">
        <meta name="robots" content="noindex,nofollow">
        <meta name="title" content="Авторизация || Alex Coffee">
        <title>Авторизация || Alex Coffee</title>
        <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico"/>" type="image/x-icon">
        <link rel="icon" href="<c:url value="/resources/img/favicon.ico"/>" type="image/x-icon">
        <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/resources/css/animate.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/resources/css/style.min.css"/>" rel="stylesheet" type="text/css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
              type="text/css">
    </head>
    <body>
    <div class="nav-bar">
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <div class="navbar-brand text-shadow">
                        <a href="<c:url value="/home"/>">
                            <span class="nav-text-label color-green">Alex</span>
                            <span class="nav-text-label color-brown">Coffee</span>
                            <img src="<c:url value="/resources/img/main_icon.png"/>"
                                 class="nav-label" alt="Alex Coffee">
                        </a>
                    </div>
                </div>
            </div>
        </nav>
    </div>
    <div class="container-fluid">
        <section id="login">
            <div class="row login">
                <div class="col-xs-6 col-xs-offset-4 col-sm-4 col-sm-offset-4 col-md-4 col-md-offset-4 col-lg-4 col-lg-offset-4 col-xl-4 col-xl-offset-4 text-center">
                    <div class="text-shadow"><span class="authorization">Авторизация</span></div>
                    <c:if test="${param.error ne null}">
                        <div class="alert alert-info" role="alert">Ошибка авторизации</div>
                    </c:if>
                    <c:if test="${param.logout ne null}">
                        <div class="alert alert-info" role="alert">Вы вышли из системы</div>
                    </c:if>
                    <form class="form-signin" action="<c:url value="/login"/>" method="post">
                        <input id="username" class="form-control" type="text" name="username" autofocus required
                               pattern="[A-Za-z0-9_]{5,50}" minlength="5" maxlength="50"
                               placeholder="Введите логин, формат (A-Z, a-z, 0-9, _)" style="margin-top: 25px">
                        <input id="password" class="form-control" type="password" name="password" required
                               pattern="[A-Za-z0-9]{6,50}" style="margin-top: 25px" minlength="6" maxlength="50"
                               placeholder="Введите пароль, формат (A-Z, a-z, 0-9)">
                        <button class="btn btn-success" type="submit" style="margin-top: 25px">Войти</button>
                    </form>
                </div>
            </div>
        </section>
    </div>
    <script src="<c:url value="/resources/js/jquery-1.11.1.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/jquery.appear.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/resources/js/bootstrap.min.js"/>" type="text/javascript"></script>
    </body>
    </html>
</compress:html>

<%-- Yurii Salimov (yuriy.alex.salimov@gmail.com) --%>
