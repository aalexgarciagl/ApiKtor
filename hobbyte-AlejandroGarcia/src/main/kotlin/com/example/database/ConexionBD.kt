package com.example.database


import utils.Constantes
import com.example.models.*
import java.sql.*


object ConexionBD {

    var conexion: Connection? = null
    var sentenciaSQL: Statement? = null
    var registros: ResultSet? = null
    var conn: Connection? = null


    fun abrirConexion(): Int {
        var cod = 0
        try {


            // Cargar el driver/controlador JDBC de MySql
            val controlador = "com.mysql.cj.jdbc.Driver"
            val URL_BD = "jdbc:mysql://" + Constantes.ip+":"+Constantes.puerto+"/" + Constantes.bbdd


            Class.forName(controlador)

            // Realizamos la conexión a una BD con un usuario y una clave.
            conexion = DriverManager.getConnection(URL_BD, Constantes.usuario, Constantes.passwd)
            sentenciaSQL = ConexionBD.conexion!!.createStatement()
            println("Conexion realizada con éxito")
        } catch (e: Exception) {
            System.err.println("Exception: " + e.message)
            cod = -1
        }
        return cod
    }

    fun cerrarConexion(): Int {
        var cod = 0
        try {
            conexion!!.close()
            println("Desconectado de la Base de Datos") // Opcional para seguridad
        } catch (ex: SQLException) {
            cod = -1
        }
        return cod
    }

    fun sumarVictoria(id_user: Int): Int {
        var cod = 0
        try {
            abrirConexion()
            val sentencia = ("UPDATE " + Constantes.TablaUsuarios + " SET victorias = victorias + 1 WHERE id = " + id_user)
            cod = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cod
    }

    fun actualizarEstadoCasilla(id_casilla: Int, estado: Int): Int {
        var cod = 0
        try {
            abrirConexion()
            val sentencia = ("UPDATE " + Constantes.TablaCasillas + " SET estado = " + estado + " WHERE id = " + id_casilla)
            cod = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cod
    }

    fun actualizarCapacidadHeroe(id_heroe: Int, capacidad: Int): Int {
        var cod = 0
        try {
            abrirConexion()
            val sentencia = ("UPDATE " + Constantes.TablaHeroes + " SET capacidad = " + capacidad + " WHERE id = " + id_heroe)
            cod = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cod
    }


    fun obtenerHeroes(): ArrayList<Heroe> {
        val lp: ArrayList<Heroe> = ArrayList(1)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaHeroes
            registros = sentenciaSQL!!.executeQuery(sentencia)
            while (ConexionBD.registros!!.next()) {
                lp.add(
                    Heroe(
                        ConexionBD.registros!!.getInt("id"),
                        ConexionBD.registros!!.getString("nombre"),
                        ConexionBD.registros!!.getInt("id_prueba"),
                        ConexionBD.registros!!.getInt("capacidad"),
                    )
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return lp
    }

    fun obtenerUser(id_user: Int): Usuario? {
        var p: Usuario? = null
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaUsuarios + " WHERE id = ?"
            val pstmt = conexion!!.prepareStatement(sentencia)
            // pstmt.setInt(1, 30000);
            pstmt.setInt(1, id_user)
            registros = pstmt.executeQuery()
            while (ConexionBD.registros!!.next()) {
                p = Usuario(
                    ConexionBD.registros!!.getInt("id"),
                    ConexionBD.registros!!.getString("nombre"),
                    ConexionBD.registros!!.getInt("victorias"),
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return p
    }


    fun obtenerUsuarios(): ArrayList<Usuario> {
        val lp: ArrayList<Usuario> = ArrayList(1)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaUsuarios
            registros = sentenciaSQL!!.executeQuery(sentencia)
            while (ConexionBD.registros!!.next()) {
                lp.add(
                    Usuario(
                        registros!!.getInt("id"),
                        registros!!.getString("nombre"),
                        registros!!.getInt("victorias")
                    )
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return lp
    }

    fun insertarUser(user: Usuario): Int {
        var cod = 0
        val sentencia = ("INSERT INTO " + Constantes.TablaUsuarios +"(nombre,victorias) VALUES ('" + user.nombre + "','"
                + user.victorias + "')")
        try {
            abrirConexion()
            sentenciaSQL!!.executeUpdate(sentencia)
        } catch (sq: SQLException) {
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }
        return cod
    }

    fun eliminarUser(id_user: Int): Int{
        var cuantos = 0
        val sentencia = "DELETE FROM " + Constantes.TablaUsuarios + " WHERE id = '" + id_user + "'"
        try {
            abrirConexion()
            cuantos = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cuantos
    }

    fun modificarUser(id_user: Int, user: Usuario): Int{
        var cuantos = 0
        try {
            abrirConexion()
            val sentencia = ("UPDATE " + Constantes.TablaUsuarios + " SET nombre = '" + user.nombre
                    + "', victorias = '" + user.victorias + "' WHERE id = '" + id_user + "'")
            cuantos = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cuantos
    }

    fun insertarCasillas(casillas: ArrayList<Casilla>): Int {
        var cod = 0
        try {
            abrirConexion()
            for (casilla in casillas) {
                val sentencia = ("INSERT INTO ${Constantes.TablaCasillas} (id_prueba,id_tablero,estado,capacidad_requerida) VALUES (${casilla.id_prueba}, ${casilla.id_tablero}, ${casilla.estado}, ${casilla.capacidad_requerida})")

                try {
                    sentenciaSQL!!.executeUpdate(sentencia)
                } catch (sq: SQLException) {
                    cod = sq.errorCode
                    break
                }
            }
        } finally {
            cerrarConexion()
        }
        return cod
    }

    fun obtenerPruebas(): ArrayList<Prueba> {
        val lp: ArrayList<Prueba> = ArrayList(1)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaPruebas
            registros = sentenciaSQL!!.executeQuery(sentencia)
            while (ConexionBD.registros!!.next()) {
                lp.add(
                    Prueba(
                        ConexionBD.registros!!.getInt("id"),
                        ConexionBD.registros!!.getString("nombre")
                    )
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return lp
    }

    fun obtenerTableros(): ArrayList<Tablero> {
        val lp: ArrayList<Tablero> = ArrayList(1)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaTableros
            registros = sentenciaSQL!!.executeQuery(sentencia)
            while (ConexionBD.registros!!.next()) {
                lp.add(
                    Tablero(
                        ConexionBD.registros!!.getInt("id"),
                        ConexionBD.registros!!.getInt("estado"),
                        ConexionBD.registros!!.getInt("usuario")
                    )
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return lp
    }

    fun actualizarEstadoTablero(id_tablero: Int, estado: Int): Int {
        var cod = 0
        try {
            abrirConexion()
            val sentencia = ("UPDATE " + Constantes.TablaTableros + " SET estado = " + estado + " WHERE id = " + id_tablero)
            cod = sentenciaSQL!!.executeUpdate(sentencia)
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return cod
    }

    fun obtenerUnTablero(id_tablero: Int?): Tablero{
        var tablero = Tablero(0,0,0)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaTableros + " WHERE id = ?"
            val pstmt = conexion!!.prepareStatement(sentencia)
            pstmt.setInt(1, id_tablero!!.toInt())
            registros = pstmt.executeQuery()
            while (ConexionBD.registros!!.next()) {
                tablero.id = ConexionBD.registros!!.getInt("id")
                tablero.estado = ConexionBD.registros!!.getInt("estado")
                tablero.id_jugador = ConexionBD.registros!!.getInt("usuario")
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return tablero
    }

    fun obtenerTablerosJugador(id_user: Int?): ArrayList<Tablero> {
        val lp: ArrayList<Tablero> = ArrayList(1)
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaTableros + " WHERE usuario = " + id_user
            registros = sentenciaSQL!!.executeQuery(sentencia)
            while (ConexionBD.registros!!.next()) {
                lp.add(
                    Tablero(
                        ConexionBD.registros!!.getInt("id"),
                        ConexionBD.registros!!.getInt("estado"),
                        ConexionBD.registros!!.getInt("usuario")
                    )
                )
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return lp
    }

    fun insertarTablero(id:Int, estado: Int, user: Any): Int {
        var cod = 0
        val sentencia = ("INSERT INTO " + Constantes.TablaTableros + " VALUES (${id},${estado},${user})")
        try {
            abrirConexion()
            sentenciaSQL!!.executeUpdate(sentencia)
        } catch (sq: SQLException) {
            cod = sq.errorCode
        } finally {
            cerrarConexion()
        }
        return cod
    }

    fun obtenerCasillasTablero(id_tablero: Int?): ArrayList<Casilla> {
        var casillas: ArrayList<Casilla> = arrayListOf()
        try {
            abrirConexion()
            val sentencia = "SELECT * FROM " + Constantes.TablaCasillas + " WHERE id_tablero = ?"
            val pstmt = conexion!!.prepareStatement(sentencia)
            // pstmt.setInt(1, 30000);
            pstmt.setInt(1, id_tablero!!.toInt())
            registros = pstmt.executeQuery()
            while (ConexionBD.registros!!.next()) {
                casillas.add(Casilla(
                    ConexionBD.registros!!.getInt("id"),
                    ConexionBD.registros!!.getInt("id_prueba"),
                    ConexionBD.registros!!.getInt("id_tablero"),
                    ConexionBD.registros!!.getInt("capacidad_requerida"),
                    ConexionBD.registros!!.getInt("estado")
                ))
            }
        } catch (ex: SQLException) {
        } finally {
            cerrarConexion()
        }
        return casillas
    }
}