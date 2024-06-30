package AP4;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexionBaseDeDatos {
    private static Connection conexion;

    public static void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "db123");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void crearTablas() {
        try (Statement stmt = conexion.createStatement()) {
            String sqlUsuarios = "CREATE TABLE IF NOT EXISTS Usuarios (" +
                    "user_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(255)," +
                    "email VARCHAR(255) UNIQUE," +
                    "password VARCHAR(255)," +
                    "tipo_de_usuario BOOLEAN" +
                    ")";
            String sqlDirectivos = "CREATE TABLE IF NOT EXISTS Directivos (" + 
                    "directivo_id INT PRIMARY KEY AUTO_INCREMENT," +
                    "user_id INT, " +
                    "FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)" + 
                    ")";
            String sqlEmpleados = "CREATE TABLE IF NOT EXISTS Empleados (" +
                    "empleado_id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "user_id INT, " +
                    "calificacion INT, " +
                    "FOREIGN KEY (user_id) REFERENCES Usuarios(user_id)" +
                    ")";
            String sqlMetas = "CREATE TABLE IF NOT EXISTS Metas (" +
                    "meta_id INT AUTO_INCREMENT PRIMARY KEY," +
                    "titulo VARCHAR(255)," +
                    "descripcion TEXT," +
                    "fecha_inicio DATE," +
                    "fecha_fin DATE," +
                    "progreso INT," +
                    "estado BOOLEAN," +
                    "empleado_id INT," +
                    "FOREIGN KEY (empleado_id) REFERENCES Empleados(empleado_id)" +
                    ")";
            stmt.executeUpdate(sqlUsuarios);
            stmt.executeUpdate(sqlDirectivos);
            stmt.executeUpdate(sqlEmpleados);
            stmt.executeUpdate(sqlMetas);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarDirectivo(Directivo directivo) {
        try (PreparedStatement pstmtUsuario = conexion.prepareStatement(
                "INSERT INTO Usuarios (username, email, password, tipo_de_usuario) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtDirectivo = conexion.prepareStatement(
                "INSERT INTO Directivos (directivo_id) VALUES (?)")) {

            // Insertar en Usuarios
            pstmtUsuario.setString(1, directivo.getNombre());
            pstmtUsuario.setString(2, directivo.getEmail());
            pstmtUsuario.setString(3, directivo.getClave());
            pstmtUsuario.setBoolean(4, true);
            pstmtUsuario.executeUpdate();

            ResultSet generatedKeys = pstmtUsuario.getGeneratedKeys();
            if (generatedKeys.next()) {
                int directivoId = generatedKeys.getInt(1);

                // Insertar en Directivos
                pstmtDirectivo.setInt(1, directivoId);
                pstmtDirectivo.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarEmpleado(Empleado empleado) {
        try (PreparedStatement pstmtUsuario = conexion.prepareStatement(
                "INSERT INTO Usuarios (username, email, password, tipo_de_usuario) VALUES (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
             PreparedStatement pstmtEmpleado = conexion.prepareStatement(
                "INSERT INTO Empleados (user_id, calificacion) VALUES (?, ?)")) {

            // Insertar en Usuarios
            pstmtUsuario.setString(1, empleado.getNombre());
            pstmtUsuario.setString(2, empleado.getEmail());
            pstmtUsuario.setString(3, empleado.getClave());
            pstmtUsuario.setBoolean(4, false);
            pstmtUsuario.executeUpdate();

            ResultSet generatedKeys = pstmtUsuario.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1); // Obtener el user_id generado

                // Insertar en Empleados
                pstmtEmpleado.setInt(1, userId);
                pstmtEmpleado.setInt(2, empleado.getCalificacion());
                pstmtEmpleado.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertarMeta(int id, String nombreMeta, String descripcion, Date fechaInicio, Date fechaFinEsperado, int progreso, boolean estado, int empleadoId) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "INSERT INTO Metas (titulo, descripcion, fecha_inicio, fecha_fin, progreso, estado, empleado_id) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            pstmt.setString(1, nombreMeta);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(4, new java.sql.Date(fechaFinEsperado.getTime()));
            pstmt.setInt(5, progreso);
            pstmt.setBoolean(6, estado);
            pstmt.setInt(7, empleadoId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Meta> obtenerMetasPorEmpleado(int empleadoId) {
        List<Meta> metas = new ArrayList<>();
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "SELECT * FROM Metas WHERE empleado_id = ?")) {
            pstmt.setInt(1, empleadoId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Meta meta = new Meta(
                        rs.getInt("meta_id"),
                        rs.getString("titulo"),
                        "", // No se necesita el nombre del empleado en esta clase
                        rs.getString("descripcion"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getBoolean("estado"),
                        rs.getInt("progreso")
                );
                metas.add(meta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metas;
    }
    
    public static List<Meta> obtenerTodasLasMetas() {
        List<Meta> metas = new ArrayList<>();
        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM Metas");
            while (rs.next()) {
                Meta meta = new Meta(
                        rs.getInt("meta_id"),
                        rs.getString("titulo"),
                        "", // No se necesita el nombre del empleado en esta clase
                        rs.getString("descripcion"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getBoolean("estado"),
                        rs.getInt("progreso")
                );
                metas.add(meta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return metas;
    }

    public static boolean existeUsuario(String email, String clave) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "SELECT * FROM Usuarios WHERE email = ? AND password = ?")) {
            pstmt.setString(1, email);
            pstmt.setString(2, clave);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Usuario obtenerUsuario(String email) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "SELECT * FROM Usuarios WHERE email = ?")) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Boolean tipo = rs.getBoolean("tipo_de_usuario");
                if (tipo.equals(true)) {
                    Directivo directivo = new Directivo(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                    return directivo;
                } else if (tipo.equals(false)) {
                    Empleado empleado = new Empleado(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                    empleado.setId(rs.getInt("user_id"));
                    return empleado;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Usuario obtenerUsuarioPorId(int id) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "SELECT * FROM Usuarios WHERE user_id = ?")) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tipo = rs.getString("tipo_de_usuario");
                if (tipo.equals("Directivo")) {
                    return new Directivo(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                } else if (tipo.equals("Empleado")) {
                    Empleado empleado = new Empleado(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                    empleado.setId(rs.getInt("user_id"));
                    empleado.setCalificacion(rs.getInt("calificacion"));
                    return empleado;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT u.user_id, u.username, u.email, u.password, e.calificacion " +
                       "FROM Usuarios u " +
                       "JOIN Empleados e ON u.user_id = e.user_id " +
                       "WHERE u.tipo_de_usuario = 0";

        try (Statement stmt = conexion.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Crear objeto Empleado
                Empleado empleado = new Empleado(rs.getString("username"), rs.getString("email"), rs.getString("password"));
                empleado.setId(rs.getInt("user_id"));
                empleado.setCalificacion(rs.getInt("calificacion"));
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public static void actualizarCalificacionEmpleado(int idEmpleado, int nuevaCalificacion) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "UPDATE Empleados SET calificacion = ? WHERE empleado_id = ?")) {
            pstmt.setInt(1, nuevaCalificacion);
            pstmt.setInt(2, idEmpleado);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modificarMeta(int id, String nombreMeta, String descripcion, Date fechaInicio, Date fechaFinEsperado, int progreso, boolean estado) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "UPDATE Metas SET titulo = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, progreso = ?, estado = ? WHERE meta_id = ?")) {
            pstmt.setString(1, nombreMeta);
            pstmt.setString(2, descripcion);
            pstmt.setDate(3, new java.sql.Date(fechaInicio.getTime()));
            pstmt.setDate(4, new java.sql.Date(fechaFinEsperado.getTime()));
            pstmt.setInt(5, progreso);
            pstmt.setBoolean(6, estado);
            pstmt.setInt(7, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void marcarMetaFinalizada(int id) {
        try (PreparedStatement pstmt = conexion.prepareStatement(
                "UPDATE Metas SET estado = ?, progreso = ? WHERE meta_id = ?")) {
            pstmt.setBoolean(1, true);
            pstmt.setInt(2, 100);
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}