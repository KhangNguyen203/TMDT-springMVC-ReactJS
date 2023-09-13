<%-- 
    Document   : confirm
    Created on : 17 Aug 2023, 8:44:53 pm
    Author     : dat98
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<section style = "border: 1px solid;" class="container__form">
    <c:url value="/staff" var="action" />
    <form:form modelAttribute="store" method="post" action="${action}">
        <form:hidden path="id" />
        <form:hidden path="description" />
        <form:hidden path="name" />
        <form:hidden path="location" />

        <form:hidden path="userId.id" />
        <form:hidden path="userId.firstName" />
        <form:hidden path="userId.lastName" />
        <form:hidden path="userId.email" />
        <form:hidden path="userId.phone" />
        <form:hidden path="userId.avatar" />
        <form:hidden path="userId.username" />
        <form:hidden path="userId.password" />

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="name" id="name" 
                        placeholder="Tên sản phẩm" name="name" disabled="true"/>
            <label for="name">Tên cửa hàng</label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="description" id="description" 
                        placeholder="description" name="description" disabled="true"/>
            <label for="description">Description </label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="location" id="location" 
                        placeholder="location" name="location" disabled="true"/>
            <label for="location">Vị trí </label>
        </div>
        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-success mt-1" type="submit">Xác nhận </button>
        </div>

    </form:form>
</section>
