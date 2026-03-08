<<<<<<< HEAD
# MI UTC - Aplicación Universitaria

## Descripción del proyecto
MI UTC es una plataforma móvil integral diseñada para la comunidad universitaria de la UTC. Facilita el acceso a servicios estudiantiles, gestión académica, salud, y seguridad en tiempo real (SOS). La aplicación está construida con las tecnologías más modernas de Android para garantizar una experiencia fluida, segura y profesional, cumpliendo con los más altos estándares de calidad y seguridad.

## Características Principales
- **Autenticación Estricta:** Acceso seguro mediante matrícula o correo institucional con validaciones en tiempo real.
- **Gestión Académica:** Consulta de materias por cuatrimestre, horarios y catálogo de cursos.
- **Servicios Estudiantiles:** Solicitud de constancias, reposición de credenciales y trámites administrativos con formularios validados.
- **Módulo de Salud:** Agendado de citas médicas y soporte psicológico.
- **Seguridad SOS:** Botón de pánico con rastreo de ubicación en tiempo real y notificaciones push para administradores.
- **UI Adaptativa y Segura:** Todas las pantallas respetan los insets del sistema (Status Bar/Navigation Bar) y usan componentes validados.

## Stack Tecnológico
- **Lenguaje:** Kotlin 1.9.24
- **UI:** Jetpack Compose (Material 3) con soporte para Edge-to-Edge.
- **Arquitectura:** MVVM (Model-View-ViewModel) + Inyección de Dependencias (Hilt).
- **Networking Seguro:** Retrofit 2 + OkHttp 4 con Certificate Pinning y timeouts configurados.
- **Seguridad:** 
  - Almacenamiento local cifrado (EncryptedSharedPreferences).
  - Ofuscación y optimización mediante R8 (ProGuard).
  - Protección contra ataques MITM mediante SSL Pinning y Network Security Config.
  - Validación y sanitización de todas las entradas de usuario.

## Prerrequisitos y Configuración del Entorno
1. **Android Studio:** Ladybug o superior.
2. **JDK:** Versión 17.
3. **SDK de Android:** API 34 (Android 14).
4. **Firebase:** Configurar `google-services.json` en la carpeta `app/`.

## Instrucciones para Compilar y Ejecutar
### Configuración de Secretos
Crea un archivo `local.properties` en la raíz del proyecto y añade:
```properties
MAPS_API_KEY="TU_CLAVE_DE_GOOGLE_MAPS"
```

### Ejecución en Debug
1. Conecta un dispositivo físico o emulador.
2. Haz clic en **Run 'app'**.
3. El entorno usará el servidor de desarrollo y permitirá inspección.

### Ejecución en Release (Modo Producción)
1. Genera un APK firmado: **Build > Generate Signed Bundle / APK**.
2. La versión de release activa automáticamente **R8** para ofuscar el código y eliminar recursos no utilizados, dificultando la ingeniería inversa.

## Seguridad y Privacidad
- **Cifrado en Reposo:** Los datos sensibles jamás se guardan en texto plano; se utiliza el Android Keystore System.
- **Comunicaciones Seguras:** Se prohíbe el tráfico HTTP no cifrado. Todas las peticiones REST se realizan mediante HTTPS centralizado en Retrofit.
- **Integridad de Entradas:** Los campos de texto (`ValidatedTextField`) implementan límites de longitud y tipos de teclado específicos para prevenir desbordamientos o inyecciones.
- **Respeto al Sistema:** El diseño UI utiliza `AppScaffold` para garantizar que el contenido sea siempre visible y no se superponga con elementos del sistema.
=======
# MI UTC - Aplicación Universitaria Integral

## Descripción del Proyecto
**MI UTC** es una plataforma móvil diseñada para modernizar la interacción entre los estudiantes y la Universidad Tecnológica de Coahuila (UTC). La aplicación centraliza servicios críticos como gestión académica, trámites administrativos, servicios de salud (médico y psicológico) y un sistema de seguridad SOS con rastreo en tiempo real.

Desarrollada bajo los estándares más modernos de Android, la app ofrece una interfaz intuitiva (Material 3), seguridad robusta mediante *Certificate Pinning* y una arquitectura escalable que garantiza la integridad de los datos de la comunidad universitaria.

## Stack Tecnológico
- **UI:** Jetpack Compose con arquitectura *Edge-to-Edge*.
- **Arquitectura:** MVVM (Model-View-ViewModel) + Inyección de Dependencias (Hilt).
- **Base de Datos y Auth:** Firebase Auth, Firestore y Storage.
- **Networking:** Retrofit 2 + OkHttp 4 con soporte para SSL/TLS y Pinning.
- **Seguridad:** Cifrado local con *EncryptedSharedPreferences* y ofuscación de código con R8.

---

## Prerrequisitos y Configuración del Entorno
Para compilar y ejecutar este proyecto, asegúrate de contar con el siguiente entorno:

1. **Android Studio:** Ladybug | 2024.2.1 o superior.
2. **JDK:** Java Development Kit versión 17.
3. **Android SDK:** API Level 34 (Android 14.0).
4. **Gradle:** Versión 8.7 (o superior).

### Configuración del Emulador (Importante)
Para una correcta visualización y funcionamiento de Maps, SOS y Firebase Auth, el emulador **debe** cumplir con:
- **Dispositivo:** Pixel 6 o superior.
- **Sistema Operativo:** Android 11.0 (API 30) como mínimo, **recomendado API 34**.
- **Servicios de Google:** Debe ser una imagen con **Google Play Store** (icono de la bolsa en la lista de dispositivos).
- **RAM:** Mínimo 2GB.
- **Configuración:** `Graphics: Hardware - GLES 2.0` para un rendimiento fluido de los mapas.

---

## Instrucciones Exactas para Compilar y Ejecutar

### 1. Configuración de Firebase y Maps
1. Coloca el archivo `google-services.json` en la ruta `app/`.
2. Crea un archivo `local.properties` en la raíz y añade:
   ```properties
   MAPS_API_KEY="TU_API_KEY_DE_GOOGLE"
   ```

### 2. Compilación
1. Abre el proyecto y espera la sincronización de Gradle.
2. Selecciona el emulador configurado según las especificaciones anteriores.
3. Haz clic en **Run 'app'** (`Shift + F10`).

### 3. Login de Prueba (Evaluación)
Si existen problemas de red con los servicios de Google Play en su entorno, puede usar el acceso de emergencia:
- **Usuario:** `admin@utc.edu.mx`
- **Contraseña:** `admin123`

---

## Seguridad y Cumplimiento
- **Tráfico Seguro:** La aplicación prohíbe el tráfico de red no cifrado (HTTP) mediante una política estricta en `network_security_config.xml`.
- **Certificados:** Se implementa *SSL Pinning* para dominios críticos de mapas y rutas para prevenir ataques de intermediario (MITM).
- **Privacidad:** No se almacenan credenciales en texto plano. Se utiliza el sistema de llaves de Android (*Keystore*) para proteger la sesión del usuario.
>>>>>>> ff9f7f7 (fix(app): ajusta flujo de alumno y autenticación, corrige navegación principal y consolida soporte de red, seguridad y utilidades base del sistema)
