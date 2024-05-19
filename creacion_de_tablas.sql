CREATE TABLE Usuarios (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    tipo_de_usuario BOOLEAN NOT NULL
);

CREATE TABLE Directivos (
    directivo_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)
);

CREATE TABLE Empleados (
    empleado_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    calificacion INT,
    FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)
);

CREATE TABLE Metas (
    meta_id INT PRIMARY KEY AUTO_INCREMENT,
    empleado_id INT,
    titulo VARCHAR(255) NOT NULL,
    descripcion VARCHAR(255),
    fecha_inicio DATE,
    fecha_fin DATE,
    progreso INT,
    estado BOOLEAN,
    FOREIGN KEY (empleado_id) REFERENCES Empleados(empleado_id)
);
