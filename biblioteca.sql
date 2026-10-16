-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-10-2026 a las 04:13:56
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `biblioteca`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `libros`
--

CREATE TABLE `libros` (
  `id_libro` int(11) NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `autor` varchar(150) NOT NULL,
  `editorial` varchar(100) DEFAULT NULL,
  `año` int(11) DEFAULT NULL,
  `isbn` varchar(20) DEFAULT NULL,
  `categoria` varchar(50) DEFAULT NULL,
  `disponible` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `libros`
--

INSERT INTO `libros` (`id_libro`, `titulo`, `autor`, `editorial`, `año`, `isbn`, `categoria`, `disponible`) VALUES
(1, 'Cien años de soledad', 'García Márquez', 'Sudamericana', 1967, '978-8437600494', 'Novela', 0),
(2, 'To Kill a Mockingbird', 'Harper Lee', 'Harper', 1960, '978-0061120084', 'Novela', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `multas`
--

CREATE TABLE `multas` (
  `id_multa` int(11) NOT NULL,
  `id_prestamo` int(11) NOT NULL,
  `monto` decimal(10,2) NOT NULL,
  `dias_demora` int(11) NOT NULL,
  `fecha_generacion` date NOT NULL,
  `pagada` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamos`
--

CREATE TABLE `prestamos` (
  `id_prestamo` int(11) NOT NULL,
  `id_socio` int(11) NOT NULL,
  `fecha_prestamo` date NOT NULL,
  `fecha_devolucion_estimada` date NOT NULL,
  `fecha_devolucion_real` date DEFAULT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'activo'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `prestamos`
--

INSERT INTO `prestamos` (`id_prestamo`, `id_socio`, `fecha_prestamo`, `fecha_devolucion_estimada`, `fecha_devolucion_real`, `estado`) VALUES
(1, 2, '2026-10-15', '2026-10-30', NULL, 'activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo_libro`
--

CREATE TABLE `prestamo_libro` (
  `id_prestamo_libro` int(11) NOT NULL,
  `id_prestamo` int(11) NOT NULL,
  `id_libro` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `prestamo_libro`
--

INSERT INTO `prestamo_libro` (`id_prestamo_libro`, `id_prestamo`, `id_libro`) VALUES
(1, 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `socios`
--

CREATE TABLE `socios` (
  `id_socio` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `dni` varchar(20) NOT NULL,
  `domicilio` varchar(200) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `fecha_registro` date NOT NULL,
  `activo` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `socios`
--

INSERT INTO `socios` (`id_socio`, `nombre`, `apellido`, `dni`, `domicilio`, `telefono`, `email`, `fecha_registro`, `activo`) VALUES
(1, 'Ana', 'Gómez', '30123456', 'Av. Siempre Viva 123', '11-5555-1234', 'ana@mail.com', '2026-01-15', 1),
(2, 'Luis', 'Pérez', '28987654', 'Calle Falsa 456', '11-5555-5678', 'luis@mail.com', '2026-02-20', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitudes_compra`
--

CREATE TABLE `solicitudes_compra` (
  `id_solicitud` int(11) NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `cantidad` int(11) NOT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'Pendiente',
  `observaciones` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `solicitudes_compra`
--

INSERT INTO `solicitudes_compra` (`id_solicitud`, `fecha_solicitud`, `cantidad`, `estado`, `observaciones`) VALUES
(1, '2026-10-15', 3, 'Pendiente', 'no');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `libros`
--
ALTER TABLE `libros`
  ADD PRIMARY KEY (`id_libro`),
  ADD UNIQUE KEY `isbn` (`isbn`);

--
-- Indices de la tabla `multas`
--
ALTER TABLE `multas`
  ADD PRIMARY KEY (`id_multa`),
  ADD KEY `id_prestamo` (`id_prestamo`);

--
-- Indices de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD PRIMARY KEY (`id_prestamo`),
  ADD KEY `id_socio` (`id_socio`);

--
-- Indices de la tabla `prestamo_libro`
--
ALTER TABLE `prestamo_libro`
  ADD PRIMARY KEY (`id_prestamo_libro`),
  ADD KEY `id_prestamo` (`id_prestamo`),
  ADD KEY `id_libro` (`id_libro`);

--
-- Indices de la tabla `socios`
--
ALTER TABLE `socios`
  ADD PRIMARY KEY (`id_socio`),
  ADD UNIQUE KEY `dni` (`dni`);

--
-- Indices de la tabla `solicitudes_compra`
--
ALTER TABLE `solicitudes_compra`
  ADD PRIMARY KEY (`id_solicitud`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `libros`
--
ALTER TABLE `libros`
  MODIFY `id_libro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `multas`
--
ALTER TABLE `multas`
  MODIFY `id_multa` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `prestamos`
--
ALTER TABLE `prestamos`
  MODIFY `id_prestamo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `prestamo_libro`
--
ALTER TABLE `prestamo_libro`
  MODIFY `id_prestamo_libro` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `socios`
--
ALTER TABLE `socios`
  MODIFY `id_socio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `solicitudes_compra`
--
ALTER TABLE `solicitudes_compra`
  MODIFY `id_solicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `multas`
--
ALTER TABLE `multas`
  ADD CONSTRAINT `multas_ibfk_1` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamos` (`id_prestamo`);

--
-- Filtros para la tabla `prestamos`
--
ALTER TABLE `prestamos`
  ADD CONSTRAINT `prestamos_ibfk_1` FOREIGN KEY (`id_socio`) REFERENCES `socios` (`id_socio`);

--
-- Filtros para la tabla `prestamo_libro`
--
ALTER TABLE `prestamo_libro`
  ADD CONSTRAINT `prestamo_libro_ibfk_1` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamos` (`id_prestamo`),
  ADD CONSTRAINT `prestamo_libro_ibfk_2` FOREIGN KEY (`id_libro`) REFERENCES `libros` (`id_libro`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
