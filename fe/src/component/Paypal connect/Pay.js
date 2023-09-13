import React, { useContext, useState } from "react";
import { Button, Table } from "react-bootstrap";
import { authApis, endpoints } from "../../configs/Apis";
import cookie from "react-cookies";
import { MyCartContext } from "../../App";
import PayPalPayment from "./PayPalPayment";
import { useNavigate } from "react-router-dom";


const Pay = (props) => {
    let { data, sum } = props;
    const [cart, setCart] = useState(cookie.load("cart") || null);
    const [, cartDispatch] = useContext(MyCartContext);
    const MyContext = React.createContext();
    const nav = useNavigate("");

    const pay = () => {
        const process = async () => {
            let res = await authApis().post(endpoints['pay'], cart);
            if (res.status === 200) {
                cookie.remove("cart");
                setCart([]);

                cartDispatch({
                    "type": "update",
                    "payload": 0
                });

                alert("Đặt hàng thành công!");
                nav("/");
            }
        }
        process();
    }

    return (
        <MyContext.Provider value={sum}>
            <>
                <div id="hide_div">
                    <div className="container ">
                        <h5 className="text-center text-info mt-2">THANH TOÁN</h5>
                        <form className="row">
                            <div className="col-6">
                                <input type="text" className="form-control" id="hoTen" placeholder="Nhập họ tên" />
                            </div>
                            <div className="col-6">
                                <input type="tel" className="form-control" id="soDienThoai" placeholder="Nhập số điện thoại" />
                            </div>
                            <div className="col-12" style={{ marginTop: "-4rem" }}>
                                {/* <label for="diaChi">Địa Chỉ Nhận Hàng:</label> */}
                                <input type="tel" className="form-control" id="diaChi" placeholder="Nhập địa chỉ nhận hàng" />
                            </div>
                        </form>
                        <div style={{ marginTop: "-2rem" }}>
                            <Table striped hover className="table_border">
                                <thead >
                                    <tr>
                                        <th><h5>Sản Phẩm</h5></th>
                                        <th>Tên sản phẩm</th>
                                        <th>Đơn giá</th>
                                        <th>Số lượng</th>
                                        <th>Số tiền</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {Object.values(data).map(c => {
                                        return <tr key={c.id} >
                                            <td><img src={c.image} width="70" height="70" alt="ImageProduct" /></td>
                                            <td><h5 className="mt-5">{c.name}</h5></td>
                                            <td><p className="mt-5">{(c.unitPrice / c.quantity).toFixed(2)} VNĐ</p></td>
                                            <td>
                                                <h6 className="mt-5">{c.quantity}</h6>
                                            </td>
                                            <td>
                                                {/* <p className="mt-5">{parseInt(c.unitPrice) * parseInt()}VND</p> */}
                                                <p className="mt-5">{c.unitPrice.toFixed(2)} VNĐ</p>
                                            </td>
                                        </tr>
                                    })}
                                </tbody>
                            </Table>
                        </div>
                        <div style={{ marginLeft: "59rem" }}><h4>Tổng thanh toán: <span className="text-danger">{sum}.00</span></h4></div>
                        <div className="custom-border" style={{ paddingTop: "30px" }}>
                            <div className="d-flex justify-content-around" style={{ margin: "0 20px" }}>
                                <div><h6>Phương thức thanh toán:</h6></div>
                                <div style={{ marginTop: "-17px" }}>
                                    {/* <Button onClick={pay} variant="info" className="mt-2 mb-2 ">Tiền mặt</Button> */}
                                    <Button onClick={pay} variant="info" className="mt-2 mb-2 ">Tiền mặt</Button>
                                </div>
                                <div><h6>hoặc </h6></div>
                                <div style={{ marginTop: "-10px" }}>
                                    <PayPalPayment sum={sum} />
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </>
        </MyContext.Provider>
    )
}
export default Pay;