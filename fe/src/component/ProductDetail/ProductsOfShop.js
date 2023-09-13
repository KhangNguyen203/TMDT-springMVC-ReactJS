import { useEffect, useState } from "react";
import Apis, { endpoints } from '../../configs/Apis';
import { Card, Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";


const ProductsOfShop = (props) => {
    const [visibleProducts, setVisibleProducts] = useState(10);
    const [records, setRecords] = useState([]);

    let storeId = props.number;


    useEffect(() => {
        const loadProductsFromStore = async () => {
            try {
                let e = endpoints['store-products'](storeId);
                const res = await Apis.get(e);
                setRecords(res.data);

            } catch (ex) {
                console.error(ex);
            }
        };
        loadProductsFromStore();

    }, [storeId]);

    const handleLoadMore = () => {
        setVisibleProducts(prevVisibleProducts => prevVisibleProducts + 10);
    };

    const scrollToTop = () => {
        window.scrollTo({ top: 0, behavior: 'smooth' });
    };

    return (
        <>
            <div>
                <h3 className="m-4">CÁC SẢN PHẨM KHÁC CỦA SHOP</h3>
                <Row>
                    {records.slice(0, visibleProducts).map(p => {
                        let url = `/products/${p[0]}`;

                        return <Col xs={12} md={3} className="mt-4 px-4" style={{width: "20%"}}>
                            <Card className="">
                                <Link to={url} variant="primary" onClick={scrollToTop}>
                                    <Card.Img variant="top" className="link_hover w-100" src={p[1]} />
                                    <Card.Body>
                                        <Card.Title>
                                            <div className="d-flex justify-content-center">{p[2]}</div>
                                        </Card.Title>
                                        <Card.Text>
                                            <div className="d-flex justify-content-center text-danger">{p[3].toFixed(2)} VNĐ</div>
                                        </Card.Text>
                                    </Card.Body>
                                </Link>
                            </Card>
                        </Col>
                    })}
                </Row>
                <div className="flex-center">
                    {visibleProducts < records.length && (
                        <button className="btn-lazy-loading" onClick={handleLoadMore}>Xem thêm</button>
                    )}
                </div>
            </div>
        </>
    );
};

export default ProductsOfShop;