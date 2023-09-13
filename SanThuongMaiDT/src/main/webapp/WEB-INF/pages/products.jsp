<%-- 
    Document   : store-product
    Created on : 25 Aug 2023, 10:50:24 pm
    Author     : dat98
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<a class="btn btn-info mt-4" href="<c:url value="/store/add-products"/>"/>Thêm sản phẩm </a>

<div class="grid__column-10">
    <div class="products">
        <div class="grid__row" id="product">
            <c:forEach items="${storeProduct}" var = "p">
                <div class="grid__column-2-4">
                    <div class="product-item">
                        <div class="product-item-img" style="background-image:url('${p[1]}');"></div>
                        <h3 class="product-item-name">${p[2]}</h3>
                        <p class="product-item-price">${p[3]}</p>
                        <div class="product-flex">
                            <c:url value="/api/products/${p[0]}" var = "api" />
                            <div class="product-flex-detail">
                                <a href="<c:url value="/store/add-products/${p[0]}"/>"/>Cập nhật</a>
                            </div>
                            <button class="btn btn-danger" onclick="deleteProduct('${api}')">Xóa</button>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>

    <ul class="btn-page pagination">
        <c:forEach begin="1" end="${counter}" var = "i">   
            <c:url value="/store/products" var="pageAction">
                <c:param name="page" value="${i}" />
            </c:url>
            <li class ="page-item">
                <a class="page-link " href="${pageAction}">${i}</a>
            </li>
        </c:forEach>
    </ul>
</div>
<script src="<c:url value="/js/main.js"/>"></script>