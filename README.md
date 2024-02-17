# Rutas de la Aplicación

## Rutas de Partida

- `POST /iniciarTablero/{id}`: Esta ruta inicia un tablero para un usuario específico. El ID del usuario se pasa como parámetro en la ruta.

- `GET /mostrarCasillasTablero/{id_tablero}`: Esta ruta devuelve las casillas de un tablero específico. El ID del tablero se pasa como parámetro en la ruta.

- `GET /tablerosJugador/{id}`: Esta ruta devuelve todos los tableros de un jugador específico. El ID del jugador se pasa como parámetro en la ruta.

- `PUT /destaparCasilla/{id_tablero}/{cDestapada}`: Esta ruta destapa una casilla en un tablero específico. El ID del tablero y la casilla a destapar se pasan como parámetros en la ruta.

## Rutas de Usuario

- `POST /login`: Esta ruta se utiliza para iniciar sesión en la aplicación. Se espera que el cuerpo de la solicitud contenga los detalles del usuario.

- `GET /getAllUsers`: Esta ruta devuelve todos los usuarios en la base de datos.

- `GET /getOneUser/{id}`: Esta ruta devuelve un usuario específico. El ID del usuario se pasa como parámetro en la ruta.

- `POST /insertUser`: Esta ruta inserta un nuevo usuario en la base de datos. Se espera que el cuerpo de la solicitud contenga los detalles del nuevo usuario.

- `DELETE /deleteUser/{id}`: Esta ruta elimina un usuario específico. El ID del usuario se pasa como parámetro en la ruta.

- `PUT /updateUser/{id}`: Esta ruta actualiza los detalles de un usuario específico. El ID del usuario se pasa como parámetro en la ruta y se espera que el cuerpo de la solicitud contenga los nuevos detalles del usuario.
