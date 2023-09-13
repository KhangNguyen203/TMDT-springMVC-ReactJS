import { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import Apis, { authApis, endpoints } from "../configs/Apis";
import MySpinner from "../layout/MySpinner";
import { MyCartContext, MyUserContext } from "../App";
import { Button, Col, Form, Image, ListGroup, Row } from "react-bootstrap";
import Moment from "react-moment";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronLeft, faChevronRight, faMessage, faShop } from "@fortawesome/free-solid-svg-icons";
import ProductsOfShop from "./ProductDetail/ProductsOfShop";
import cookie, { remove } from "react-cookies";

const ProductDetails = () => {
    const [user,] = useContext(MyUserContext);
    const [, cartDispatch] = useContext(MyCartContext);
    const { productId } = useParams();
    const [product, setProduct] = useState(null);
    const [comments, setComments] = useState(null);
    const [content, setContent] = useState();
    const [noti, setNoti] = useState();
    const [currentPage, setCurrentPage] = useState(1);
    const [numbers, setNumbers] = useState([]);
    const [, setRecords] = useState([]);
    const recordsPerPage = 5;
    const lastIndex = recordsPerPage * currentPage;
    const firstIndex = lastIndex - recordsPerPage;
    let compare = cookie.load("compare") || {};

    useEffect(() => {
        const loadProduct = async () => {
            let { data } = await Apis.get(endpoints['details'](productId));
            setProduct(data);
        }

        const loadComments = async () => {
            let res = await Apis.get(endpoints['comments-pro'](productId));

            const slicedRecords = res.data.slice(firstIndex, lastIndex);
            const nPage = Math.ceil(res.data.length / recordsPerPage);
            const updatedNumbers = Array.from({ length: nPage }, (_, index) => index + 1);
            setComments(slicedRecords);
            setRecords(slicedRecords);
            setNumbers(updatedNumbers);
        }

        loadProduct();
        loadComments();

    }, [productId, lastIndex, firstIndex]);

    const addComment = () => {
        const process = async () => {
            let { data } = await authApis().post(endpoints['add-comment-pro'], {
                "content": content,
                "productId": product.id
            });
            setContent("")
            setComments([data, ...comments]);
        }

        process();
    }

    const order = (p) => {
        cartDispatch({
            "type": "inc",
            "payload": 1
        });

        let cart = cookie.load("cart") || null;
        if (cart === null)
            cart = {};

        if (p.id in cart) {
            cart[p.id]["quantity"] += 1;
        } else {
            cart[p.id] = {
                "id": p.id,
                "image": p.image,
                "name": p.name,
                "unitPrice": p.price,
                "quantity": 1
            }
        }

        cookie.save("cart", cart);
    }

    const compareProduct = (p) => {
        const existingCategoryId = Object.values(compare).find(
            (product) => product.categoryName === p.categoryId.id
        );

        if (!existingCategoryId) {
            remove('compare');
            compare = {
                [p.id]: {
                    id: p.id,
                    name: p.name,
                    description: p.description,
                    price: p.price,
                    image: p.image,
                    categoryName: p.categoryId.id,
                },
            };
        } else {
            if (Object.keys(compare).length < 3) {
                compare[p.id] = {
                    id: p.id,
                    name: p.name,
                    description: p.description,
                    price: p.price,
                    image: p.image,
                    categoryName: p.categoryId.id,
                };
            }
            else {
                setNoti("Bạn chỉ có thể so sánh cùng lúc 3 sản phẩm cùng loại.");
                setTimeout(() => {
                    setNoti(null);
                }, 2500);
                return;
            }
        }

        cookie.save('compare', compare);
    };

    const changePage = (id, e) => {
        e.preventDefault();
        setCurrentPage(id);
    }

    function nextPage(e) {
        e.preventDefault();
        if (currentPage !== numbers.length)
            setCurrentPage(currentPage + 1)
    }

    function prePage(e) {
        e.preventDefault();
        if (currentPage !== 1)
            setCurrentPage(currentPage - 1)
    }

    if (product === null || comments === null)
        return <MySpinner />;

    let h = `/store/${product.storeId.id}`;
    let url = `/login?next=/products/${productId}`;

    return <>
        <div style={{ backgroundColor: "#f0f0f2", marginTop: "-0.5rem" }}>
            <div className="container py-4">
                <div style={{ backgroundColor: "white", borderRadius: "5px", padding: "2rem" }}>
                    <Row>
                        <Col md={5} xs={6}>
                            <Image style={{ width: "100%", height: "40rem" }} src={product.image} rounded fluid />
                        </Col>
                        <Col className="mx-5" md={5} xs={6}>
                            <h2 className="fs-1">{product.name}</h2>
                            <h4 className="text-danger">{product.price.toFixed(2)} VNĐ</h4>
                            <div className="d-flex">
                                <Button className="me-4" onClick={() => compareProduct(product)}>So sánh</Button>
                                <Button variant="success" onClick={() => order(product)}>Thêm vào giỏ</Button>
                            </div>
                            {Object.keys(compare).length < 3 ? <h3> </h3> : <span className="text-danger mb-2">{noti}</span>}
                            <h4>{product.description}</h4>
                        </Col>
                    </Row>
                </div>

                <div className="mt-4">
                    <div class="d-flex align-items-center my-3" style={{ backgroundColor: "white", borderRadius: "5px", padding: "3px 0 10px 0" }}>
                        <div className="m-3">
                            <img src={product.storeId.userId.avatar}
                                width="70" height="70" className="rounded-circle" alt="logo" />
                        </div>

                        <div className="my-4">
                            <h4>{product.storeId.userId.username}</h4>
                            <div class="d-flex justify-content-center">
                                <div className="me-2" style={{ borderStyle: "groove", borderRadius: "5px", padding: "5px" }}>
                                    <FontAwesomeIcon icon={faMessage} className="me-1" />
                                    <Link variant="primary">Chat ngay</Link>
                                </div>
                                <div style={{ borderStyle: "groove", borderRadius: "5px", padding: "5px" }}>
                                    <FontAwesomeIcon icon={faShop} className="me-1" />
                                    <Link to={h} variant="primary">Xem shop</Link>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div className="my-4" style={{ backgroundColor: "white", borderRadius: "5px", padding: "3px 0 10px 0" }}>
                        <div>
                            <h4 className="m-4">Đánh giá sản phẩm</h4>
                            <div className="ms-3">
                                {user === null ? <p>Vui lòng <Link to={url}>đăng nhập</Link> để bình luận! </p> :
                                    <div className="w-50">
                                        <Form.Control as="textarea" aria-label="With textarea" value={content} onChange={e => setContent(e.target.value)} placeholder="Nội dung bình luận" />
                                        <Button onClick={addComment} className="mt-2" variant="info">Bình luận</Button>
                                    </div>}

                                <hr className="me-4" />

                                {comments.map(c =>
                                    <ListGroup.Item id={c.id}>
                                        <div className="align-items-center" style={{ position: "relative", display: "flex" }}>
                                            <div className="me-2">
                                                <img src={c.userId.avatar} width="30" height="30" alt="avatar" />
                                            </div>

                                            <h3>{c.userId.username}</h3>

                                            <div style={{ position: "absolute", marginLeft: "70rem" }}>
                                                <Moment style={{ right: "1px" }} locale="vi" fromNow>{c.createdAt}</Moment>
                                            </div>
                                        </div>
                                        <div className="ms-5 mt-2">{c.content}</div>
                                        <hr className="me-4" />
                                    </ListGroup.Item>)
                                }
                                <div className="pagination mt-4" >
                                    <button onClick={prePage} className="page-item pagination-products" disabled={currentPage === 1}>
                                        <FontAwesomeIcon icon={faChevronLeft} />
                                    </button>
                                    {numbers.map((n, i) => (
                                        <button key={i} className="page-item" >
                                            <a onClick={(e) => changePage(n, e)} className={`page-link ${currentPage === n ? 'active-btn' : ''}`} href="/">{n}
                                            </a>
                                        </button>
                                    ))}
                                    <button onClick={nextPage} className="page-item pagination-products" disabled={currentPage === numbers.length}>
                                        <FontAwesomeIcon icon={faChevronRight} />
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="my-2" style={{ backgroundColor: "white", borderRadius: "5px", padding: "3px 0 10px 0" }}>
                    <ProductsOfShop number={product.storeId.id} />
                </div>
            </div>
        </div>
    </>
}

export default ProductDetails;