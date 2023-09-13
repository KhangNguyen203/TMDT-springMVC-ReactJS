
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${param.accessDenied != null}">
    <div class="alert alert-danger ">
        Bạn không có quyền truy cập
    </div>
</c:if>


<section style = "border: 1px solid;" class="container__form">
    <c:url value="/login" var="action" />
    <form method="post" action="${action}">        
        <h1>Đăng nhập</h1>

        <div class="form-floating mb-3 mt-3">
            <input type="text" class="form-control" id="name" placeholder="Tên đăng nhập..." name="username" />
            <label for="name">Tên đăng nhập</label>
        </div>
        <div class="form-floating mt-3 mb-3">
            <input type="password" class="form-control" id="pwd" placeholder="Mật khẩu..." name="password" />
            <label for="pwd">Mật khẩu</label>
        </div>

        <input class="btn" type="submit" value="Đăng nhập" />
        <div class="signup_link">Bạn chưa có tài khoản? <a href="<c:url value="/register"/>">Đăng ký</a></div>
    </form>
</section>
