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
