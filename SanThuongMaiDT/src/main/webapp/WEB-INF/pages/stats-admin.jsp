<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<div class="row mt-4" style="display: flex; justify-content: space-evenly;">
    <div class="col-md-5 col-sm-12">
        <table class="table table-hover">
            <tr class="table-info ">
                <th>Tên cửa hàng</th>
                <th>Doanh thu</th>
            </tr>

            <c:forEach items="${statsRevenueByStoreAdmin}" var="c">
                <tr>
                    <td>${c[0]}</td>
                    <td>${c[1]} VNĐ</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="col-md-5 col-sm-12 bg-white">
        <form>
            <table class="table">
                <h4 class="m-2"><i class="fa-solid fa-filter"></i>Bộ lọc</h4>
                <tr class="">
                    <th><label>Tháng </label></th>
                    <th><input type="number" name="month" placeholder="Nhập tháng..."/></th>
                </tr>

                <tr>
                    <th><label>Quý </label></th>
                    <th><input type="number" name="quarter" placeholder="Nhập quý..."/></th>
                </tr>
                <tr>
                    <th><label>Năm </label></td>
                    <th><input type="number" name="year" placeholder="Nhập năm..."/></th>
                </tr>
            </table>

            <div class="m-3">
                <button type="submit" class="btn btn-info">Tìm kiếm</button>    
            </div>
        </form>
    </div>
</div>
<form>
    <canvas id="chartProduct"></canvas>
</form>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    let labels = [];
    let data = [];
    let colors = [];
    let borderColors = [];
    let r, g, b;

    <c:forEach items="${statsRevenueByStoreAdmin}" var="c">
    labels.push('${c[0]}');
    data.push(${c[1]});

    r = Math.floor(Math.random() * 256);
    g = Math.floor(Math.random() * 256);
    b = Math.floor(Math.random() * 256);

    colors.push('rgba(' + r + ',' + g + ',' + b + ',0.5)');
    borderColors.push('rgba(' + r + ',' + g + ',' + b + ',1)');

    </c:forEach>

    window.onload = function () {
        const ctx = document.getElementById('chartProduct');
        loadChart(ctx, labels, data, 'bar', colors, borderColors);
    };

    function loadChart(ctx, labels, data, type, colors, borderColors) {
        const chartProduct = new Chart(ctx, {
            type: type,
            data: {
                labels: labels,
                datasets: [{
                        label: 'Thống kê doanh thu theo thể loại',
                        data: data,
                        backgroundColor: colors,
                        borderColor: borderColors,
                        borderWidth: 1
                    }]
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
    }

</script>