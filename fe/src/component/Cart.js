import { useContext, useEffect, useState } from "react";
import { Alert, Button, Container, Form, Table } from "react-bootstrap";
import cookie from "react-cookies";
import { authApis, endpoints } from "../configs/Apis";
import PayPalPayment from "../component/Paypal connect/PayPalPayment";
import { MyCartContext } from "../App";

const Cart = () => {
    const [, cartDispatch] = useContext(MyCartContext);
    const [cart, setCart] = useState(cookie.load("cart") || null);
    const [totalPrice, setTotalPrice] = useState(0);

    useEffect(() => {
        if (cart) {
            let totalPrice = 0;
            Object.values(cart).forEach(c => {
                totalPrice += parseInt(c.unitPrice) * parseInt(cart[c.id]["quantity"]);
            });
            setTotalPrice(totalPrice);
        }
    }, [cart]);

    const deleteItem = (item) => {
        if (item.id in cart) {
            cartDispatch({
                "type": "dec",
                "payload": item.quantity
            });

            setCart(current => {
                delete current[item.id];
                return current;
            });
            cookie.save("cart", cart);
        }
    };

    const updateItem = () => {
        cookie.save("cart", cart);

        cartDispatch({
            "type": "update",
            "payload": Object.values(cart).reduce((init, current) => init + current["quantity"], 0)
        });
    };

    const pay = () => {
        const process = async () => {
            let res = await authApis().post(endpoints['pay'], cart);
            if (res.status === 200) {
                cookie.remove("cart");
                setCart([]);
                console.info(cart)
                cartDispatch({
                    "type": "update",
                    "payload": 0
                });
            }
        };
        process();
    };

    if (cart === null)
        return <Alert variant="info" className="text-center m-4">
            <h2 className="flex-center">Không có sản phẩm trong giỏ!</h2>
                </Alert>

    if (cart.length === 0)
        return <Alert variant="info"><h2 className="flex-center">Thanh toán thành công!</h2></Alert>;

    return (
        <>
            {Object.values(cart).length === 0 ? (
                <Alert variant="info" className="text-center m-4">
                    <h2 className="flex-center">Không có sản phẩm trong giỏ!</h2>
                </Alert>
            ) : (
                <div style={{ backgroundColor: "#f0f0f2", marginTop: "-0.5rem" }}>
                    <Container>
                        <h1 className="text-center text-info mt-2">GIỎ HÀNG</h1>
                        <Table striped hover className="table_border">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Tên sản phẩm</th>
                                    <th>Đơn giá</th>
                                    <th>Số lượng</th>
                                    <th>Số tiền</th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                {Object.values(cart).map(c => {
                                    return (
                                        <tr key={c.id}>
                                            <td>
                                                <img src={c.image} width="150" height="150" alt="ImageProduct" />
                                            </td>
                                            <td>
                                                <h5 className="mt-5">{c.name}</h5>
                                            </td>
                                            <td>
                                                <p className="mt-5">{c.unitPrice.toFixed(2)} VNĐ</p>
                                            </td>
                                            <td>
                                                <div className="mt-5">
                                                    <Form.Control
                                                        type="number"
                                                        value={cart[c.id]["quantity"]}
                                                        onBlur={updateItem}
                                                        onChange={e =>
                                                            setCart({ ...cart, [c.id]: { ...cart[c.id], "quantity": parseInt(e.target.value) } })
                                                        }
                                                    />
                                                </div>
                                            </td>
                                            <td>
                                                <p className="mt-5">
                                                    {(parseInt(c.unitPrice) * parseInt(cart[c.id]["quantity"])).toFixed(2)}VND
                                                </p>
                                            </td>
                                            <td>
                                                <Button className="mt-5 rounded-circle" variant="danger" onClick={() => deleteItem(c)}>
                                                    &times;
                                                </Button>
                                            </td>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </Table>

                        <div>
                            <div style={{ marginLeft: "59rem" }}>
                                <h4>
                                    Tổng thanh toán: <span className="text-danger">{totalPrice.toFixed(2)}VND</span>
                                </h4>
                            </div>
                            <div className="custom-border" style={{ paddingTop: "30px" }}>
                                <div className="d-flex justify-content-around" style={{ margin: "0 20px" }}>
                                    <div>
                                        <h6>Phương thức thanh toán:</h6>
                                    </div>
                                    <div style={{ marginTop: "-17px" }}>
                                        <Button onClick={pay} variant="info" className="mt-2 mb-2 ">
                                            Tiền mặt
                                        </Button>
                                    </div>
                                    <div>
                                        <h6>hoặc </h6>
                                    </div>
                                    <div style={{ marginTop: "-10px" }}>
                                        <PayPalPayment sum={totalPrice} />
                                    </div>
                                </div>
                            </div>
                        </div>
                    </Container>
                </div>
            )}
        </>
    );
};

export default Cart;