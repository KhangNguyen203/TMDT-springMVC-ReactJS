import { useEffect, useState } from "react";
import Apis, { endpoints } from '../configs/Apis';
import { Alert, Card, Col, Row } from "react-bootstrap";
import MySpinner from "../layout/MySpinner";
import { Link, useSearchParams } from "react-router-dom";
import { SearchRangePrice } from "../component/Home/SearchRangePrice";
import { SlideShow } from "../component/Home/SlideShow";
import Category from "../component/Home/Category";

const Home = () => {
    const [products, setProducts] = useState(null);
    const [q] = useSearchParams();
    const [visibleProducts, setVisibleProducts] = useState(8);

    useEffect(() => {
        const loadProducts = async () => {
            try {
                let e = endpoints['products'];
                let cateId = q.get("cateId")
                let fromPrice = q.get("fromPrice")
                let toPrice = q.get("toPrice")
                let kw = q.get("kw");
                if (kw !== null) {
                    e = `${e}?kw=${kw}`;
                } else if (cateId !== null) {
                    e = `${e}?cateId=${cateId}`
                } else if (fromPrice !== null && toPrice !== null) {
                    e = `${e}?toPrice=${toPrice}&fromPrice=${fromPrice}`
                }

                const res = await Apis.get(e);
                setProducts(res.data);

            } catch (ex) {
                console.error(ex);
            }
        };
        loadProducts();

    }, [q]);

    if (products === null)
        return <MySpinner />

    const handleLoadMore = () => {
        setVisibleProducts(prevVisibleProducts => prevVisibleProducts + 8);
    };


    return (
        <>
            <div style={{ backgroundColor: "#f0f0f2" }}>
                <SlideShow />
                <div className="container d-flex mt-1">
                    <div style={{ width: "20%", marginRight: "5px" }}>
                        <Category />
                    </div>
                    <div style={{ width: "80%" }}>
                        <div className="custom-border pt-2" style={{ backgroundColor: "rgb(240, 219, 219)" }}>
                            <div className="d-flex">
                                <div className="mt-3 ms-5">
                                    <h3>Sắp xếp theo </h3>
                                </div>
                                <div className="mt-1 ms-5">
                                    <SearchRangePrice />
                                </div>
                            </div>
                        </div>
                        {products.length === 0 ? <Alert variant="info" className="mt-5 text-center">Không có sản phẩm nào!</Alert> :
                            <Row>
                                {products.slice(0, visibleProducts).map(p => {
                                    let url = `/products/${p.id}`;

                                    return <Col xs={12} md={3} className="mt-1 p-4">
                                        <Card style={{ width: '22rem', height:"100%"}} >
                                            <Link to={url} variant="primary">
                                                <Card.Img variant="top" className="link_hover" src={p.image} />
                                                <Card.Body>
                                                    <Card.Title>
                                                        <div className="d-flex justify-content-center">{p.name}</div>
                                                    </Card.Title>
                                                    <Card.Text>
                                                        <div className="d-flex justify-content-center text-danger">{p.price} VNĐ</div>
                                                    </Card.Text>
                                                </Card.Body>
                                            </Link>
                                        </Card>
                                    </Col>
                                })}
                            </Row>
                        }

                    </div>
                </div>

                <div className="flex-center">
                    {visibleProducts < products.length && (
                        <button className="btn-lazy-loading" onClick={handleLoadMore}>Xem thêm</button>
                    )}
                </div>
            </div>
        </>
    );
}

export default Home;