<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="compress" uri="http://htmlcompressor.googlecode.com/taglib/compressor" %>

<compress:html removeIntertagSpaces="true">
    <!DOCTYPE HTML>
    <html lang="ru">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="author" content="Yurii Salimov https://www.linkedin.com/in/yurii-salimov">
        <meta name="robots" content="noindex,nofollow">
        <meta name="title" content="Заказы || Alex Coffee">
        <title>Заказы || Alex Coffee</title>
        <link rel="shortcut icon" href="<c:url value="/resources/img/favicon.ico"/>" type="image/x-icon">
        <link rel="icon" href="<c:url value="/resources/img/favicon.ico"/>" type="image/x-icon">
        <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/resources/css/animate.css"/>" rel="stylesheet" type="text/css">
        <link href="<c:url value="/resources/css/style.min.css"/>" rel="stylesheet" type="text/css">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet"
              type="text/css">
    </head>
    <body>
    <jsp:include page="/WEB-INF/views/manager/template/manager_navbar.jsp"/>
    <div class="container-fluid">
        <section id="orders">
            <div class="row admin-page">
                <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-10 col-md-offset-1 col-lg-10 col-lg-offset-1 col-xl-10 col-xl-offset-1">
                    <div class="row section-name text-shadow">
                        <b>
                            <span class="color-brown">Заказы</span>
                            <c:if test="${fn:length(orders) eq 0}"><span class="color-red"> - список пуст!</span></c:if>
                        </b>
                    </div>
                </div>
                <c:if test="${fn:length(orders) gt 0}">
                    <div class="col-xs-12 col-sm-10 col-sm-offset-1 col-md-10 col-md-offset-1 col-lg-10 col-lg-offset-1 col-xl-10 col-xl-offset-1 full-cart">
                        <table class="table">
                            <tr>
                                <th>Номер</th>
                                <th>Статус</th>
                                <th class="hidden-xs">Дата</th>
                                <th>Действие</th>
                            </tr>
                            <c:forEach items="${orders}" var="order">
                                <tr>
                                    <td>${order.number}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status eq status_new}">
                                                <span class="color-green">${order.status.description}</span>
                                            </c:when>
                                            <c:otherwise>${order.status.description}</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="hidden-xs">${order.date}</td>
                                    <td>
                                        <a href="<c:url value="/managers/order/view/${order.id}"/>"
                                           title="Смотреть заказ ${order.number}">
                                            <button class="btn btn-info" type="submit">Смотреть</button>
                                        </a>
                                        <c:if test="${(order.status eq status_new) or (order.manager eq auth_user)}">
                                            <a href="<c:url value="/managers/order/edit/${order.id}"/>"
                                               title="Редактировать заказ ${order.number}">
                                                <button class="btn btn-success" type="submit">Редактировать</button>
                                            </a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </div>
                </c:if>
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
