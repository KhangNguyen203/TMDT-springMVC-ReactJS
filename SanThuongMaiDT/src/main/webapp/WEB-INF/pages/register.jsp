<%-- 
    Document   : register
    Created on : 15 Aug 2023, 4:13:06 pm
    Author     : dat98
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${errMsg != null}">
    <div class="alert alert-danger ">
        ${errMsg}
    </div>
</c:if>

<section style = "border: 1px solid;" class="container__form">
    <c:url value="/register" var="action"/>
    <form:form modelAttribute="user" method="post" action="${action}" enctype="multipart/form-data">

        <h1>Đăng ký</h1>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="firstName" id="firstName" 
                        placeholder="Tên sản phẩm" name="firstName" />
            <label for="firstName">firstName</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="lastName" id="lastName" 
                        placeholder="Tên sản phẩm" name="lastName" />
            <label for="lastName">lastName</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="email" class="form-control" path="email" id="email" 
                        placeholder="Tên sản phẩm" name="email" />
            <label for="email">Email</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="phone" id="phone" 
                        placeholder="Tên sản phẩm" name="phone" />
            <label for="phone">SDT</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="username" id="username" 
                        placeholder="Tên sản phẩm" name="username" />
            <label for="username">Tên đăng nhập</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="password" class="form-control" path="password" id="password" 
                        placeholder="Tên sản phẩm" name="password" />
            <label for="password">Mật khẩu</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="password" class="form-control" path="confirmPassword" id="confirm-password" 
                        placeholder="Tên sản phẩm" name="confirm-password" />
            <label for="confirm-password">Xác nhận mật khẩu</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="file" class="form-control" path="file" id="file" 
                        placeholder="Ảnh" />
            <label for="file">Ảnh</label>
        </div>

        <div class="form-floating mt-3 mb-3">
            <input type="submit" value="Đăng ký" class="btn btn-danger" />
        </div>
    </form:form>
</section>
