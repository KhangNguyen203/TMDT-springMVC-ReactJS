<%-- 
    Document   : admin
    Created on : 26 Aug 2023, 4:13:16 pm
    Author     : dat98
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1 class="text-center text">Quản lí thể loại</h1>
<a class="btn btn-info mt-2 mb-2" href="<c:url value="/admin/add-categories"/>"/>Thêm thể loại </a>

<table class="table">
    <tr>
        <th>ID</th>
        <th>Tên thể loại</th>
        <th>Hành động</th>
    </tr>

    <c:forEach items="${categories}" var = "c">
        <tr>
            <td>${c.id}</td>
            <td>${c.name}</td>
            <td>
                <c:url value="/api/categories/${c.id}" var = "api" />
                <a class="btn btn-primary" href="<c:url value="/admin/add-categories/${c.id}"/>"/>Cập nhật</a>
                <button class="btn btn-danger" onclick="deleteCategory('${api}')">Xóa</button>
            </td>
        </tr>
    </c:forEach>
</table>

<script src="<c:url value="/js/main.js"/>"></script>