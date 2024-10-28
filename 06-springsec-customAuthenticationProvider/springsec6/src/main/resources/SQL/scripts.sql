CREATE DATABASE  IF NOT EXISTS `easybank`;
USE `easybank`;

CREATE TABLE users(
username varchar(50) NOT NULL PRIMARY KEY,
password varchar(500) NOT NULL,
enabled boolean NOT NULL
);

CREATE TABLE authorities(
username varchar(50) NOT NULL,
authority varchar(50) NOT NULL,
constraint fk_authorities_users foreign key(username) references users(username)
);

CREATE unique index ix_auth_username on authorities(username, authority);

INSERT IGNORE INTO `users` VALUES ('user', '{noop}Trek@12345', '1');
INSERT IGNORE INTO `authorities` VALUES ('user', 'read');
-- Trek@54321
INSERT IGNORE INTO `users` VALUES ('admin', '{bcrypt}$2a$12$nx2mwqASdWKVjVT.ck6qzuVuQMfpq2.dpTiJkbaWLMpGw9BYenYNC', '1');
INSERT IGNORE INTO `authorities` VALUES ('admin', 'admin');



CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `pwd` varchar(200) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
);
INSERT  INTO `customer` (`email`, `pwd`, `role`) VALUES ('happy@example.com', '{noop}Trek@12345', 'read');
-- Trek@54321
INSERT  INTO `customer` (`email`, `pwd`, `role`) VALUES ('admin@example.com', '{bcrypt}$2a$12$nx2mwqASdWKVjVT.ck6qzuVuQMfpq2.dpTiJkbaWLMpGw9BYenYNC', 'admin');

-- luke@dunphy.com Trek@12345