<%-- 
    Document   : admin
    Created on : 26 Aug 2023, 4:13:16 pm
    Author     : dat98
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text mt-2">QUẢN LÍ NGƯỜI DÙNG</h1>

<table class="table">
    <tr>
        <th>ID</th>
        <th>Họ và tên</th>
        <th>Số điện thoại</th>
        <th>Email</th>
        <th>Tên đăng nhập</th>
        <th>Role</th>
        <th>Hành động</th>
    </tr>

    <c:forEach items="${users}" var = "u">
        <c:if test= "${u.userRole != 'ROLE_ADMIN'}">
            <tr>
                <td>${u.id}</td>
                <td>${u.firstName} ${u.lastName} </td>
                <td>${u.phone}</td>
                <td>${u.email}</td>
                <td>${u.username}</td>
                <td>${u.userRole}</td>
                <td>
                    <c:url value="/api/user/${u.id}" var = "api" />
                    <button class="btn btn-danger" onclick="deleteUser('${api}')">Xóa</button>
                </td>
            </tr>
        </c:if>
    </c:forEach>

</table>
<script src="<c:url value="/js/main.js"/>"></script>