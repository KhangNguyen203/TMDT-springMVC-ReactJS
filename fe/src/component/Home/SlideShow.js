import React, { useState } from 'react'
import slideImage from "./images/slide1.png";
import slideImage2 from "./images/slide2.png";
import slideImage3 from "./images/slide3.png";
import slideImage4 from "./images/slide4.png";
import { Button } from 'react-bootstrap';
export const SlideShow = () => {

    const [slidIndex, setSlidIndex] = useState(1);

    const slides = [
        { image: slideImage, caption: 'Slide 1' },
        { image: slideImage2, caption: 'Slide 2' },
        { image: slideImage3, caption: 'Slide 3' },
        { image: slideImage4, caption: 'Slide 4' },
    ];
    const handleNextSlide = () => {
        setSlidIndex((prevIndex) => (prevIndex + 1) % slides.length);
    };
    const handlePreviousSlide = () => {
        setSlidIndex((prevIndex) => (prevIndex + 1) % slides.length);
    };
    return (
        <div className="my-5">
            <div style={{ position: "relative" }}>
                <div >
                    {slides.map((slide, index) => (
                        <div
                            key={index}
                            style={{ display: index === slidIndex ? 'block' : 'none' }}
                        >
                            <img style={{ width: "100%", height: "300px" }} src={slide.image} alt={slide.caption} />
                        </div>
                    ))}
                </div>
                <Button id="location_button_left" onClick={handlePreviousSlide}>&#10094;</Button>
                <Button id="location_button_right" onClick={handleNextSlide}>&#10095;</Button>
            </div>
        </div>
    )
}
