
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<c:choose>
    <c:when test="${user.userRole == 'ROLE_STORE'}">
        <h1 class="text-center">Quản lí cửa hàng</h1>
        <div class="">
            <div 
                class="m-5 dashboard">Số lượng sản phẩm của cửa hàng: ${countProduct}  
                <a style = "font-size:12px;" href="<c:url value="/store/products?page=1"/>"><i class="fa-solid fa-chevron-right"></i></a>
            </div>
            <div             
                <c:forEach items="${statsRevenueByStore}" var="c">
                    class="m-5 dashboard" style="border-left: 5px solid red;">Doanh thu của cửa hàng: ${c[0]}  
                </c:forEach>

            </div>
            <h1>${c[0]}</h1>

            <div class="d-flex align-self-stretch" style="justify-content: space-evenly;">
                <div>
                    <div style="height: 490px; width: 450px; background-color: white;">
                        <h2 class="text-center py-2">Số lượng sản phẩm theo thể loại</h2>
                        <canvas class="p-4" id="chartProduct"></canvas>
                    </div>
                </div>
                <div>
                    <div style="height: 490px; width: 650px; background-color: white;">
                        <h2 class="text-center py-2">Doanh thu theo tháng</h2>
                        <canvas class="p-4" id="lineChartStore"></canvas>
                    </div>   
                </div>    
            </div>
        </div>
    </c:when>
    <c:when test="${user.userRole == 'ROLE_STAFF'}">
        <div class="text-center mt-2 text">Quản lí nhân viên</div>
    </c:when>

    <c:when test="${user.userRole == 'ROLE_ADMIN'}">
        <h1 class="text-center">Quản lí cửa hàng</h1>
        <div class="">
            <div 
                class="m-5 dashboard">Tổng số sản phẩm của sàn: ${count}
            </div>    

            <div 
                class="m-5 dashboard" style="border-left: 5px solid red;">Tổng số thể loại của sàn: ${countCategory}
            </div> 

            <div class="d-flex align-self-stretch" style="justify-content: space-evenly;">
                <div>
                    <div style="height: 490px; width: 450px; background-color: white;">
                        <h2 class="text-center py-2">Số lượng sản phẩm theo thể loại</h2>
                        <canvas class="p-4" id="chartPie"></canvas>
                    </div>
                </div>
                <div>
                    <div style="height: 490px; width: 650px; background-color: white;">
                        <h2 class="text-center py-2">Doanh thu của các cửa hàng</h2>
                        <canvas id="lineChart"></canvas>
                    </div>   
                </div>    
            </div>
        </div>

    </c:when> 
</c:choose>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    let r, g, b;

    let labels = [];
    let data = [];
    let colors = [];
    let borderColors = [];

    let labels1 = [];
    let data1 = [];
    let colors1 = [];
    let borderColors1 = [];

    let labels2 = [];
    let data2 = [];
    let colors2 = [];
    let borderColors2 = [];

    let labels4 = [];
    let data4 = [];
    let colors4 = [];

    <c:forEach items="${countProductByCate}" var="c">
    labels.push('${c[1]}');
    data.push(${c[2]});
    r = Math.floor(Math.random() * 256);
    g = Math.floor(Math.random() * 256);
    b = Math.floor(Math.random() * 256);
    colors.push('rgba(' + r + ',' + g + ',' + b + ',0.5)');
    borderColors.push('rgba(' + r + ',' + g + ',' + b + ',1)');
    </c:forEach>

    <c:forEach items="${statsRevenueInEachStore}" var="c">
    labels1.push('${c[0]}');
    data1.push(${c[1]});
    r = Math.floor(Math.random() * 256);
    g = Math.floor(Math.random() * 256);
    b = Math.floor(Math.random() * 256);
    colors1.push('rgba(' + r + ',' + g + ',' + b + ',0.5)');
    borderColors1.push('rgba(' + r + ',' + g + ',' + b + ',1)');
    </c:forEach>

    <c:forEach items="${statsByMonthInStore}" var="c">
    labels2.push('${c[0]}');
    data2.push(${c[1]});
    r = Math.floor(Math.random() * 256);
    g = Math.floor(Math.random() * 256);
    b = Math.floor(Math.random() * 256);
    colors2.push('rgba(' + r + ',' + g + ',' + b + ',0.5)');
    borderColors2.push('rgba(' + r + ',' + g + ',' + b + ',1)');
    </c:forEach>

    <c:forEach items="${statsProductByCate}" var="c">
    labels4.push('${c[0]}');
    data4.push(${c[1]});
    r = Math.floor(Math.random() * 256);
    g = Math.floor(Math.random() * 256);
    b = Math.floor(Math.random() * 256);
    colors4.push('rgba(' + r + ',' + g + ',' + b + ',0.5)');

    </c:forEach>

    window.onload = function () {
        let ctx = document.getElementById('chartProduct');
        let ctx1 = document.getElementById('lineChart');
        let ctx2 = document.getElementById('lineChartStore');
        let ctx4 = document.getElementById('chartPie');

        chartStatsProductByCate(ctx4, labels4, data4, 'pie', colors4);
        loadChart(ctx, labels, data, 'doughnut', colors, borderColors);
        loadChartStore(ctx1, labels1, data1, 'line', colors1, borderColors1);
        lineChartStore1(ctx2, labels2, data2, 'line', colors2, borderColors2);

    };

    function chartStatsProductByCate(ctx4, labels4, data4, type, colors4) {
        const chartProduct = new Chart(ctx4, {
            type: type,
            data: {
                labels: labels4,
                datasets: [{
                        label: 'Số lượng sản phẩm theo thể loại',
                        data: data4,
                        backgroundColor: colors4,
                        borderWidth: 1
                    }]
            }
        });
    }

    function loadChart(ctx, labels, data, type, colors, borderColors) {
        const chartProduct = new Chart(ctx, {
            type: type,
            data: {
                labels: labels,
                datasets: [{
                        label: 'Số lượng sản phẩm theo thể loại',
                        data: data,
                        backgroundColor: colors,
                        borderColor: borderColors,
                        borderWidth: 1
                    }]
            }
        });
    }


    function loadChartStore(ctx1, labels1, data1, type, colors1, borderColors1) {
        const chartProduct = new Chart(ctx1, {
            type: type,
            data: {
                labels: labels1,
                datasets: [{
                        label: 'Thống kê doanh thu từng cửa hàng',
                        data: data1,
                        backgroundColor: colors1,
                        borderColor: borderColors1,
                        borderWidth: 1,
                        fill: false,
                        tension: 0.5
                    }]
            },
            options: {
                scales: {
                    y: {
                        stacked: true
                    }
                }
            }
        });
    }

    function lineChartStore1(ctx2, labels2, data2, type, colors2, borderColors2) {
        const chartProduct = new Chart(ctx2, {
            type: type,
            data: {
                labels: labels2,
                datasets: [{
                        label: '',
                        data: data2,
                        backgroundColor: colors2,
                        borderColor: borderColors2,
                        borderWidth: 1,
                        fill: false,
                        tension: 0.5
                    }]
            },
            options: {
                scales: {
                    y: {
                        stacked: true
                    }
                }
            }
        });
    }
</script>