# sesionManager
# Gestor de Sesiones de Conferencias

Este proyecto es un sistema para gestionar sesiones de conferencias, permitiendo agregar participantes y asegurando que no tengan sesiones simultáneas.

## 🚀 Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Dazaitan/sesionManager
2. Configurar base de datos:
   * Crear base de datos
        CREATE DATABASE sesionManager;
   * Crear tablas:
     * Tabla sesiones:
          CREATE TABLE sesiones (
          id BIGINT PRIMARY KEY,
          fecha_creacion TIMESTAMP NOT NULL,
          hora_inicio TIMESTAMP NOT NULL,
          hora_fin TIMESTAMP NOT NULL
          );
     * tabla personas:
          CREATE TABLE personas (
          id BIGINT PRIMARY KEY,
          nombre VARCHAR(255) NOT NULL
          );
     * tabla relacional sesiones_personas:
         CREATE TABLE sesiones_personas (
         sesion_id BIGINT REFERENCES sesiones(id) ON DELETE CASCADE,
         persona_id BIGINT REFERENCES personas(id) ON DELETE CASCADE,
         PRIMARY KEY (sesion_id, persona_id)
         );
     * Insertar datos basicos a tabla personas:
        insert into personas (id,nombre) values (1,"Jhon");
        insert into personas (id,nombre) values (2,"Chrystian");
3. Pruebas
   Ejecutar todas las pruebas unitarias comando:
```bash
mvn test