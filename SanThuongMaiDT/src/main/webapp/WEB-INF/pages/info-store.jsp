<%-- 
    Document   : info-store
    Created on : 4 Sept 2023, 1:38:28 am
    Author     : dat98
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:url value="/store/info-store" var="updateStore" />
<section style = "border: 1px solid;" class="container__form">
    <form:form modelAttribute="storeInfo" method="post" action="${updateStore}" >
        <form:hidden path="id" />
        <form:hidden path="userId" />
        <form:hidden path="status" />
        
        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="name" id="name" 
                        placeholder="Tên cửa hàng" name="name" />
            <label for="name">Tên cửa hàng</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="description" id="description" 
                        placeholder="Mô tả cửa hàng" name="description" />
            <label for="name">Mô tả cửa hàng</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <form:input type="text" class="form-control" path="location" id="location" 
                        placeholder="Vị trí cửa hàng" name="location" />
            <label for="name">Vị trí cửa hàng</label>
        </div>

        <div class="form-floating mb-3 mt-3">
            <button value ="" class="btn btn-info mt-1" type="submit">
                Cập nhật thông tin
            </button>
        </div>
    </form:form>

</section>