

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<table style ="margin-left: 85px;
       width: 1000px;" class="mt-4 container table table-hover">
    <tr class="table-primary">
        <th>Tên cửa hàng</th>
        <th></th>
    </tr>
    <c:forEach items="${pending}" var = "p">
        <tr>
            <td>${p.name}</td>
            <td>
                <a class="btn btn-info" href="<c:url value="/staff/confirm/${p.id}"/>"/>Xem</a>
            </td>
        </tr>
    </c:forEach>
<c:forEach items="${store}" var = "c">
            ${c.name}
 </c:forEach>
</table>
