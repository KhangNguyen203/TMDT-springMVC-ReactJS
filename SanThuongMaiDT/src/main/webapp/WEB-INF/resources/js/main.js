function deleteProduct(path) {
    if (confirm("Bạn có chắc xóa") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}

function deleteCategory(path) {
    if (confirm("Bạn có chắc xóa") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}

function deleteUser(path) {
    if (confirm("Bạn có chắc xóa") === true) {
        fetch(path, {
            method: "delete"
        }).then(res => {
            if (res.status === 204)
                location.reload();
            else
                alert("Something wrong!!!");
        });
    }
}
//let pageItem = document.querySelectorAll(".page-item");
//
//pageItem.forEach((item) => {
//    item.addEventListener("click", () => {
//        item.classList.add("active");
//    });
//});
