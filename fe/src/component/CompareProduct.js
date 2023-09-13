import React, { useState } from 'react';
import { Card } from 'react-bootstrap';
import cookie from 'react-cookies';
import MySpinner from '../layout/MySpinner';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export const CompareProduct = () => {
    const [compares, setCompares] = useState(cookie.load('compare') || {});

    const handleRemoveItem = (itemId) => {
        const updatedCompares = { ...compares };
        delete updatedCompares[itemId];
        setCompares(updatedCompares);
        cookie.save('compare', updatedCompares);
    }

    if (compares === null) {
        return <MySpinner />;
    }

    return (
        <>
            <div className="grid__auto">
                <div className="compare__products m-4">
                    <h2>So sánh sản phẩm</h2>
                    {Object.values(compares).length === 0 ? (
                        <h2 style={{ margin: '0 auto', }} className="text-center text">Hiện chưa có sản phẩm</h2>
                    ) : (
                        Object.values(compares).map((compare) => {
                            return (
                                <>
                                    <div className="compare__product mx-4" key={compare.id}>
                                        <div className="compare__product-text">
                                            <div className="compare__product-img">
                                                <Card className="" style={{ margin: '15px auto' }}>
                                                    <Card.Img style={{height: "250px"}} className="w-100" variant="top" src={compare.image} />
                                                </Card>
                                            </div>
                                        </div>
                                        <div>
                                            <h1>{compare.name}</h1>
                                            <p className="fs-2 text-danger">{compare.price} VNĐ</p>
                                            <p className="fs-3">{compare.description}</p>
                                            <div className="compare-close" onClick={() => handleRemoveItem(compare.id)}>
                                                <FontAwesomeIcon icon={faXmark} />
                                            </div>
                                        </div>
                                    </div>
                                </>
                            );
                        })
                    )}
                </div>
            </div>
        </>
    );
}