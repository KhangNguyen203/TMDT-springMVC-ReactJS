

<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<section style = "border: 1px solid;" class="container__form">
    <c:choose>
        <c:when test="${categories.id != null}">
            <h1 class="text-center text-info mt-1">CẬP NHẬT THỂ LOẠI</h1>
        </c:when>
        <c:otherwise>
            <h1 class="text-center text-info mt-1">THÊM THỂ LOẠI</h1>
        </c:otherwise>
    </c:choose> 

    <c:url value="/admin/add-categories" var="action" />
    <form:form modelAttribute="categories" method="post" action="${action}">
        <form:hidden path="id" />
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="name" id="name" 
                        placeholder="Tên thể loại" name="name" />
            <label for="name">Tên thể loại</label>
            <form:errors path="name" element="div" class="text text-danger" />

        </div>
        <div class="form-floating mb-3 mt-3">
            <button class="btn btn-info mt-1" type="submit">
                <c:choose>
                    <c:when test="${categories.id != null}">
                        Cập nhật thể loại
                    </c:when>
                    <c:otherwise>
                        Thêm thể loại
                    </c:otherwise>
                </c:choose>
            </button>
        </div>
    </form:form>
</section