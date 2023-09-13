CREATE TABLE `user` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `first_name` varchar(50),
  `last_name` varchar(50),
  `email` varchar(50),
  `phone_number` varchar(50),
  `user_name` varchar(50),
  `password` varchar(50),
  `user_role` varchar(10)
);

CREATE TABLE `store` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `user_id` int,
  `name` varchar(50),
  `description` longtext
);

CREATE TABLE `category` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(50)
);

CREATE TABLE `store_category` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `store_id` int,
  `cate_id` int
);

CREATE TABLE `product` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(100),
  `price` DECIMAL(10,0),
  `image` varchar(500),
  `description` longtext,
  `created_at` datetime,
  `category_id` int,
  `store_id` int
);

CREATE TABLE `orders` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `order_date` datetime,
  `user_id` int
);

CREATE TABLE `order_details` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `order_id` int,
  `product_id` int,
  `price` DECIMAL(10,0),
  `quantity` int
);

CREATE TABLE `comments` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `content` longtext,
  `created_at` datetime,
  `user_id` int,
  `product_id` int
);

CREATE TABLE `review` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `star` int,
  `note` longtext,
  `created_at` datetime,
  `store_id` int,
  `user_id` int
);

ALTER TABLE `product` ADD FOREIGN KEY (`category_id`) REFERENCES `category` (`id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `store` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `comments` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `comments` ADD FOREIGN KEY (`product_id`) REFERENCES `product` (`id`);

ALTER TABLE `store_category` ADD FOREIGN KEY (`store_id`) REFERENCES `category` (`id`);

ALTER TABLE `store_category` ADD FOREIGN KEY (`cate_id`) REFERENCES `store` (`id`);

ALTER TABLE `review` ADD FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);

ALTER TABLE `review` ADD FOREIGN KEY (`store_id`) REFERENCES `store` (`id`);

ALTER TABLE `product` ADD FOREIGN KEY (`store_id`) REFERENCES `store` (`id`);
