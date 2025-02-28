CREATE TABLE Employee
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    /* dni VARCHAR(20) UNIQUE NOT NULL,*/
    name     VARCHAR(100),
    password VARCHAR(255),
    token    VARCHAR(255)
);
ENGINE
=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='tabla de empleados';

INSERT INTO Employee (id, name, password, token)
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

