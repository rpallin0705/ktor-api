CREATE TABLE user
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    email     VARCHAR(100),
    password  VARCHAR(255),
    token     VARCHAR(255),
    image_url VARCHAR(500) NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='tabla de usuarios';

CREATE TABLE restaurant
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(128) UNIQUE NOT NULL,
    address     VARCHAR(255)        NOT NULL,
    phone       VARCHAR(9) UNIQUE   NOT NULL,
    description VARCHAR(255),
    rating      INT CHECK (rating BETWEEN 1 AND 5),
    image       VARCHAR(255)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='Tabla de restaurantes';

CREATE TABLE user_favorites
(
    id            INT AUTO_INCREMENT PRIMARY KEY,
    user_id       INT,
    restaurant_id INT,
    CONSTRAINT fk_id_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_id_restaurant FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = 'Tabla de favoritos';

INSERT INTO user (id, email, password, token)
VALUES (1, 'Juan Pérez', '$2a$12$nTeIMaCnIcvBJlug2YLwGOzVd9gB9bSYN7wZZnZL4/KaOWJqeVkcK', ''),
       (2, 'María López', '$2a$12$jkxtj76TpGMKlhzH/FjX6./f4uZ8EWD.fJunzhWdS1PbYebRSd1P6', ''),
       (3, 'Carlos Sánchez', '$2a$12$h7OELt9pBczNOP/FYnlo4OBJsDyd47CYFVzyTEiFgqc5eER/1fyNq', ''),
       (4, 'Ana García', '$2a$12$2N4jP.Zq9dQGxNmxAZHUH.C3/G1ukWlfMkRYqcbUaJDpvDlRgdDIK', ''),
       (5, 'Luis Torres', '$2a$12$32AvPGspcHV5gal/UYiIIuGH8uKf7eYGhGkHUwLbiDnKSwiHdRAKC', ''),
       (6, 'Lucía Martínez', '$2a$12$9C2gqnL16PXA.PiHApJ4M.ZPl4x0LuC8KjiUf1NJ6pmfQvQ0N7pwi', ''),
       (7, 'Miguel Romero', '$2a$12$iHDO3r.sYslAOvxhWj9ZNOZJf3excUwvl5dVVCKwt9ngVDOI9tWeG', ''),
       (8, 'Sofía Jiménez', '$2a$12$1wor8Zdzb8V6gLoUWDLUa.F7/6jlcYCw0i8yjERGAqAYkjIThWBzy', ''),
       (9, 'Pedro Castillo', '$2a$12$PVhmyBSkwXQKoL3dTfjX2uDNpO4/Tu0HFqea2qavLZQL4viIvZtMu', ''),
       (10, 'Laura Díaz', '$2a$12$crj6TZVB2lZtdjtASzh0mOyOaNwKgRIiap1YHmeVdwcO.ieTMcvpi', ''),
       (11, 'Diego Gómez', '$2a$12$bAW5C3B4t1RpIVNi0/uiqO6.eQ0EDqsGuiEV6HKqvBRbY0djN62Iy', ''),
       (12, 'Natalia Ruiz', '$2a$12$bMwY96mmJHwSZLfCh1c24.VfsnOpjmOMCjtSiazOlZFaORo7KcSQO', ''),
       (13, 'Javier Moreno', '$2a$12$c.oyYsEDm0QYS4tHVcfzOeeF/OU3iilC3rRWreNeqsiUdEwifFbh.', ''),
       (14, 'Elena Vega', '$2a$12$I6xbbL3QU2xfbpzfTufcfew0kmD33aVkpvZDp7bj5.yUvUul5EZsC', ''),
       (15, 'Ricardo Flores', '$2a$12$Jaz5Kh7bxukUOaomkWbRYOotDTu1p2Au.LkhbNvcg/T13CAyuDtSe', ''),
       (16, 'Patricia Navarro', '$2a$12$dV7STyQ475l3jYpoeSy2oOpwwIYyWccrJn5nwqCbbxY7XiVzpUnWm', ''),
       (17, 'Fernando Paredes', '$2a$12$V3ek4s6SJAJTK1nqn3b0O.i2u6tBsJouOx.JWMrqQivlcE2G3fzhu', ''),
       (18, 'Cristina Sáez', '$2a$12$OLO4c91R.K0CFmId7v81j.4VIiOAqn1ABH9aBC3Ac3npb6MWAfVQu', ''),
       (19, 'Raúl Castro', '$2a$12$hDFf6YQwQlv.IVYh64BhA2g3nEXgK46y3uDd1pVZ7hHkXZLNm5bgq', ''),
       (20, 'Inés Sánchez', '$2a$12$zjfHzpz8hzTp8hlUoToCmOkv9gd9/fdScdzgGyjBbr0BFOttds0wA', '');

INSERT INTO restaurant (id, name, address, phone, rating, description)
VALUES (1, 'Sukiyabashi Jiro', 'Calle Gran Vía, 12, 28013 Madrid, España', '911123456', 5,
        'Famoso restaurante de sushi dirigido por el maestro Jiro Ono, conocido por su técnica impecable y su menú omakase.'),
       (2, 'Narisawa', 'Passeig de Gràcia, 45, 08007 Barcelona, España', '932345678', 4,
        'Restaurante innovador que combina técnicas francesas con ingredientes japoneses, destacando la sostenibilidad y la naturaleza.'),
       (3, 'Kyo Aji', 'Avenida de la Constitución, 18, 41004 Sevilla, España', '954987654', 3,
        'Auténtico restaurante kaiseki que ofrece platos de temporada preparados con precisión y maestría.'),
       (4, 'Kitcho Arashiyama', 'Calle Larios, 10, 29005 Málaga, España', '952321789', 5,
        'Experiencia culinaria kaiseki de lujo en un entorno tradicional con vistas serenas.'),
       (5, 'Gion Sasaki', 'Calle Alcalá, 25, 28014 Madrid, España', '910456789', 2,
        'Restaurante moderno de kaiseki que ofrece una mezcla única de creatividad y tradición.'),
       (6, 'Mizai', 'Calle Colón, 34, 46004 Valencia, España', '963258741', 4,
        'Restaurante exclusivo con un menú kaiseki cuidadosamente elaborado y una atmósfera íntima.'),
       (7, 'Sushi Saito', 'Calle Balmes, 15, 08007 Barcelona, España', '934567890', 5,
        'Uno de los mejores restaurantes de sushi, conocido por su pescado fresco y la destreza del chef.'),
       (8, 'Nihonryori Ryugin', 'Paseo del Prado, 8, 28014 Madrid, España', '917654321', 3,
        'Restaurante de alta gastronomía que fusiona la cocina japonesa con técnicas modernas e innovadoras.'),
       (9, 'Ishikawa', 'Calle San Fernando, 15, 41001 Sevilla, España', '955123321', 1,
        'Restaurante kaiseki que ofrece platos delicados y elegantes en una atmósfera tranquila y sofisticada.'),
       (10, 'Sushi Mizutani', 'Calle Mayor, 20, 28005 Madrid, España', '911987654', 4,
        'Restaurante de sushi tradicional, famoso por la precisión del chef y su compromiso con la calidad.');

INSERT INTO user_favorites(user_id, restaurant_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (2, 1),
       (2, 2),
       (3, 3),
       (4, 4);