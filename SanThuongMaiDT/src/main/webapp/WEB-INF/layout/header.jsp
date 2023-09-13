<%-- 
Document   : header
Created on : 25 Jul 2023, 9:14:25 pm
Author     : dat98
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div class="container-fluid">
    <div class="row flex-nowrap">
        <div class="col-auto col-md-3 col-xl-2 px-sm-2 px-0" style="background-color: #3c4b64;width: 300px;">
            <div class="d-flex flex-column align-items-center align-items-sm-start px-3 pt-2 text-white min-vh-100">
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal.name != null}">
                        <div class="dropdown pb-4 mt-2" style="margin: 0 auto;">
                            <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="${user.avatar}"/>" width="40" height="40" class="rounded-circle" alt="logo">
                                <span class="d-none d-sm-inline mx-1">${pageContext.request.userPrincipal.name}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                                <li class="header__navbar-item">
                                    <a class="nav-link text-danger fs-4" href="<c:url value="/logout" />">Đăng xuất</a>
                                </li>
                            </ul>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="dropdown pb-4 mt-2" style="margin: 0 auto;">
                            <a href="#" class="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="/img/user.jpg"/>" width="40" height="40" class="rounded-circle" alt="logo">
                                <span class="d-none d-sm-inline mx-1">${pageContext.request.userPrincipal.name}</span>
                            </a>
                            <ul class="dropdown-menu dropdown-menu-dark text-small shadow">
                                <li><a class="dropdown-item text-white fs-3" href="<c:url value="/login" />">Đăng nhập</a></li>
                                <li><a class="dropdown-item text-white fs-3" href="<c:url value="/register" />">Đăng ký</a></li>
                            </ul>                        
                        </div>
                    </c:otherwise>
                </c:choose>

                <ul class="nav nav-pills flex-column mb-sm-auto mb-0 align-items-center align-items-sm-start" id="menu">
                    <c:choose>
                        <c:when test="${user.userRole == 'ROLE_STORE'}">
                            <h1 style="width: 275px; text-align: center;
                                padding: 15px;
                                border-bottom: 1px solid black;">Trang cửa hàng </h1>

                            <li class="nav-item sidebar-items" >
                                <a href="<c:url value="/"/>" class="admin-menu nav-link align-middle px-0" style="border-radius: 0px;
                                   font-size: 20px;
                                   display: block;
                                   padding: 15px;">
                                    <i class="fa-solid fa-store text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Trang chủ</span>
                                </a>
                            </li>

                            <li class="nav-item sidebar-items" >
                                <a href="<c:url value="/store/info-store"/>" class="admin__menu nav-link align-middle px-0" style="border-radius: 0px;font-size: 20px;
                                   display: block;
                                   padding: 15px;">
                                    <i class="fa-solid fa-info text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Thông tin cửa hàng</span> </a>
                            </li>
                            
                            <li class="nav-item sidebar-items" >
                                <a href="#submenu2" data-bs-toggle="collapse" class="nav-link px-0 align-middle admin-menu">
                                    <i class="fa-solid fa-bars-progress text-white"></i>
                                    <span class="ms-1 d-none d-sm-inline text-white">Thể loại</span>
                                    <i style="margin-left: 8px;
                                       font-size: 16px;" class="text-white fa-solid fa-chevron-down"></i>
                                </a>
                                <ul class="collapse nav flex-column ms-1" id="submenu2" data-bs-parent="#menu">

                                    <c:forEach items="${categoriesByStore}" var = "c">
                                        <c:url value="/store/products" var="cateAction">
                                            <c:param name="cateId" value="${c[0]}" />
                                        </c:url>
                                        <li class="w-200">
                                            <a href="${cateAction}" class="nav-link px-0"> <span class="fs-2 d-none d-sm-inline text-white">${c[1]}</span></a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </li>
                            
                            <c:url value="/store/products" var="pageAction">
                                <c:param name="page" value="1" />
                            </c:url>
                            <li class="nav-item sidebar-items" >
                                <a href="${pageAction}" class="nav-link px-0 align-middle admin-menu" >
                                    <i class="fa-solid fa-database text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Sản phẩm</span> 
                                </a>
                            </li>
                            
                         
                            
                            <li class="nav-item sidebar-items" >
                                <a href="#submenu1" data-bs-toggle="collapse" class="nav-link px-0 align-middle admin-menu">
                                    <i class="fa-solid fa-chart-simple text-white"></i>
                                    <span class="ms-1 d-none d-sm-inline text-white">Thống kê</span>
                                    <i style="margin-left: 8px;
                                       font-size: 16px;" class="text-white fa-solid fa-chevron-down"></i>
                                </a>
                                <ul class="collapse nav flex-column ms-1" id="submenu1" data-bs-parent="#menu">
                                    
                                        <li class="w-200">
                                            <a href="<c:url value="/store/stats-product"/>" class="nav-link px-0"> <span class="fs-2 d-none d-sm-inline text-white">Thống kê doanh thu theo sản phẩm</span></a>
                                        </li>
                                    <li class="w-200">
                                            <a href="<c:url value="/store/stats-categories"/>" class="nav-link px-0"> <span class="fs-2 d-none d-sm-inline text-white">Thống kê doanh thu theo thể loại</span></a>
                                        </li>
                                </ul>
                            </li>
                            
                        </c:when>
                            
                        <c:when test="${user.userRole == 'ROLE_STAFF'}">
                            <h1 style="width: 275px;
                                padding: 15px;
                                border-bottom: 1px solid black;">Trang nhân viên </h1>
                            <li class="nav-item sidebar-items" >
                                <a href="<c:url value="/"/>" class="admin-menu nav-link align-middle px-0" style="border-radius: 0px;font-size: 20px;
                                   display: block;
                                   padding: 15px;">
                                    <i class="fa-solid fa-store text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Trang chủ</span>
                                </a>
                            </li>
                            <li class="nav-item sidebar-items" >
                                <a href="<c:url value="/staff"/>" class="nav-link px-0 align-middle admin-menu " >
                                    <i class="fa-solid fa-shop-lock text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Xác nhận cửa hàng</span> </a>
                            </li>
                        </c:when>
                            
                        <c:when test="${user.userRole == 'ROLE_USER'}">
                            <li class="nav-item sidebar-items" >
                                <a href="<c:url value="/register-store"/>" class="admin-menu nav-link align-middle px-0" >
                                    <i class="fa-solid fa-store text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Tạo cửa hàng</span>
                                </a>
                            </li>                       
                        </c:when>

                        <c:when test="${user.userRole == 'ROLE_ADMIN'}">
                            <li class="nav-item sidebar-items" style="">
                                <a href="<c:url value="/"/>" class="admin-menu nav-link align-middle px-0">
                                    <i class="fa-solid fa-store text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Trang chủ</span>
                                </a>
                            </li>
                            <li class="nav-item sidebar-items" style="">
                                <a href="<c:url value="/admin/categories"/>" class="admin__menu nav-link align-middle px-0" style="border-radius: 0px;font-size: 20px;
                                   display: block;
                                   padding: 15px;">
                                    <i class="fa-solid fa-bars-progress text-white"></i>
                                    <span class="ms-1 d-none d-sm-inline text-white">Quản lý thể loại</span>
                                </a>
                            </li>  
                            <li class="nav-item sidebar-items">
                                <a href="<c:url value="/admin/users"/>" class="admin__menu nav-link align-middle px-0" style="border-radius: 0px;font-size: 20px;
                                   display: block;
                                   padding: 15px;">
                                    <i class="fa-regular fa-user text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Quản lý người dùng</span>
                                </a>
                            </li> 
                            <li class="nav-item sidebar-items">
                                <a href="<c:url value="/admin/stats-admin"/>" class="admin__menu nav-link align-middle px-0" style="border-radius: 0px;font-size: 20px;
                                   display: block;
                                   padding: 15px;">
                                    <i class="fa-solid fa-chart-simple text-white"></i> <span class="ms-1 d-none d-sm-inline text-white">Thống kê doanh thu cửa hàng</span>
                                </a>
                            </li> 
                        </c:when>

                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>
                </ul>


                <hr>

            </div>
        </div>
        
