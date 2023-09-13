import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faListUl } from "@fortawesome/free-solid-svg-icons";
import Apis, { endpoints } from "../../configs/Apis";

const Category = () => {
    const [category, setCategory] = useState([]);

    useEffect(() => {
        const loadCategory = async () => {
            try {
                let e = endpoints['categories'];
                const res = await Apis.get(e);
                setCategory(res.data);
            } catch (ex) {
                console.error(ex);
            }
        };
        loadCategory();

    }, []);

    let all = true;
    let p = `/?all=${all}`;

    return (
        <>
            <div>
                <nav className="category">
                    <h3 className="category_heading">
                        <FontAwesomeIcon icon={faListUl} style={{ marginRight: "5px" }} />
                        Danh mục
                    </h3>
                    <ul className="category-list">
                        <li className="category-items">
                            <Link to={p} className="category-items-text" >Tất cả</Link>
                        </li>
                        {category.map(c => {
                            let h = `/?cateId=${c.id}`;
                            return (
                                <li className="category-items">
                                    <Link to={h} className="category-items-text"
                                        key={c.id} >{c.name}</Link>
                                </li>)
                        })}

                    </ul>
                </nav>
            </div>
        </>

    )
}
export default Category;