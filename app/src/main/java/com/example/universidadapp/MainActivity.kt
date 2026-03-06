package com.example.universidadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.universidadapp.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UniversidadApp()
        }
    }
}

@Composable
fun UniversidadApp() {
    val navController = rememberNavController()
    MaterialTheme {
        Surface {
            AppNavHost(navController = navController)
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginAsAlumno = { navController.navigate("alumno_dashboard") },
                onLoginAsMaestro = { navController.navigate("maestro_dashboard") },
                onLoginAsDirectivo = { navController.navigate("directivo_dashboard") },
                onLoginAsTransporte = { navController.navigate("transporte_dashboard") },
            )
        }

        // Alumno
        composable("alumno_dashboard") { AlumnoDashboardScreen(navController) }
        composable("alumno_horario") { AlumnoHorarioScreen(navController) }
        composable("alumno_materias") { AlumnoMateriasScreen(navController) }
        composable("alumno_tareas") { AlumnoTareasScreen(navController) }
        composable("alumno_calificaciones") { AlumnoCalificacionesScreen(navController) }
        composable("alumno_transporte") { AlumnoTransporteScreen(navController) }
        composable("alumno_anuncios") { AlumnoAnunciosScreen(navController) }
        composable("alumno_salud") { AlumnoSaludScreen(navController) }
        composable("alumno_perfil") { AlumnoPerfilScreen(navController) }
        composable("alumno_config") { AlumnoConfigScreen(navController) }

        // Maestro
        composable("maestro_dashboard") { MaestroDashboardScreen(navController) }
        composable("maestro_horario") { MaestroHorarioScreen(navController) }
        composable("maestro_grupos") { MaestroGruposScreen(navController) }
        composable("maestro_tareas") { MaestroTareasScreen(navController) }
        composable("maestro_anuncios") { MaestroAnunciosScreen(navController) }
        composable("maestro_perfil") { MaestroPerfilScreen(navController) }
        composable("maestro_config") { MaestroConfigScreen(navController) }

        // Directivo
        composable("directivo_dashboard") { DirectivoDashboardScreen(navController) }
        composable("directivo_alumnos") { DirectivoAlumnosScreen(navController) }
        composable("directivo_maestros") { DirectivoMaestrosScreen(navController) }
        composable("directivo_carreras") { DirectivoCarrerasScreen(navController) }
        composable("directivo_grupos") { DirectivoGruposScreen(navController) }
        composable("directivo_anuncios") { DirectivoAnunciosScreen(navController) }
        composable("directivo_transporte") { DirectivoTransporteScreen(navController) }

        // Transporte
        composable("transporte_dashboard") { TransporteDashboardScreen(navController) }
        composable("transporte_scan") { TransporteScanScreen(navController) }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    UniversidadApp()
}
