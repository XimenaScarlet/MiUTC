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
