INSERT INTO Usuarios (username, password, email, tipo_de_usuario) VALUES
('directivo1', 'password1', 'directivo1@example.com', TRUE),
('directivo2', 'password2', 'directivo2@example.com', TRUE),
('empleado1', 'password3', 'empleado1@example.com', FALSE),
('empleado2', 'password4', 'empleado2@example.com', FALSE);

INSERT INTO Directivos (user_id) VALUES
(1),  -- Asociado al directivo1
(2);  -- Asociado al directivo2

INSERT INTO Empleados (user_id, calificacion) VALUES
(3, 85),  -- Asociado al empleado1
(4, 75);  -- Asociado al empleado2

INSERT INTO Metas (empleado_id, titulo, descripcion, fecha_inicio, fecha_fin, progreso, estado) VALUES
(1, 'Meta 1', 'Descripción de la Meta 1', '2024-01-01', '2024-06-30', 50, FALSE),  -- Meta del empleado1
(2, 'Meta 2', 'Descripción de la Meta 2', '2024-01-01', '2024-06-30', 70, TRUE);

