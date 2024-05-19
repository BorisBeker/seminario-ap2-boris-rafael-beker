SELECT * FROM Usuarios;

SELECT * FROM Directivos;

SELECT e.*, u.username, u.email, u.tipo_de_usuario 
FROM Empleados e 
INNER JOIN Usuarios u ON e.user_id = u.user_id;

SELECT * FROM Metas WHERE estado = TRUE;