package com.example.univapp.ui.nav

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

// Auth / Login
import com.example.univapp.ui.AuthViewModel
import com.example.univapp.ui.LoginScreen

// Admin
import com.example.univapp.ui.admin.*

// Mapas
import com.example.univapp.ui.RouteMapScreen
import com.example.univapp.ui.routesel.RoutesSelectorScreen

// Alumno
import com.example.univapp.ui.HomeScreen
import com.example.univapp.ui.GradesScreen
import com.example.univapp.ui.ProfileScreen
import com.example.univapp.ui.HealthScreen
import com.example.univapp.ui.SettingsScreen
import com.example.univapp.ui.SubjectsScreen
import com.example.univapp.ui.SubjectDetailScreen
import com.example.univapp.ui.AnnouncementsScreen
import com.example.univapp.ui.TimetableScreen

// Transportista
import com.example.univapp.transporter.TransporterScanScreen

object Routes {
    const val LOGIN = "login"
    const val HOME = "home"
    const val GRADES = "grades"
    const val PROFILE = "profile"
    const val TIMETABLE = "timetable"
    const val ROUTES = "routes"
    const val ROUTE_MAP = "routeMap/{routeId}"
    const val HEALTH = "health"
    const val SUBJECTS = "subjects"
    const val SUBJECT_DETAIL = "subjectDetail/{term}/{subjectId}"
    const val SETTINGS = "settings"
    const val ANNOUNCEMENTS = "announcements"
    const val PSYCH_SUPPORT = "psychSupport"
    const val MEDICAL_SUPPORT = "medicalSupport"
    const val PSYCH_APPOINT = "psychSupport/appointment"
    // Admin
    const val ADMIN_HOME = "admin_home"
    const val ADMIN_ACTIVITY = "admin_activity"
    const val ADMIN_ALUMNOS = "admin_alumnos"
    const val ADMIN_EDIT_ALUMNO = "admin_edit_alumno/{alumnoId}"
    const val ADMIN_ADD_ALUMNO = "admin_add_alumno"
    const val ADMIN_IMPORT_ALUMNOS = "admin_import_alumnos"
    const val ADMIN_MATERIAS = "admin_materias"
    const val ADMIN_MATERIA_DETAIL = "admin_materia_detail/{materiaId}"
    const val ADMIN_ADD_MATERIA = "admin_add_materia/{carreraId}/{grupoId}"
    const val ADMIN_IMPORT_MATERIAS = "admin_import_materias"
    const val ADMIN_GRUPOS   = "admin_grupos"
    const val ADMIN_GROUP_DETAIL = "admin_group_detail/{groupId}"
    const val ADMIN_ADD_GROUP_DETAILS = "admin_add_group_details/{carreraId}"
    const val ADMIN_ASSIGN_STUDENTS = "admin_assign_students/{groupName}/{programType}/{tutorId}/{carreraId}"
    const val ADMIN_PROFESORES = "admin_profesores"
    const val ADMIN_ADD_PROFESOR = "admin_add_profesor/{carreraId}"
    const val ADMIN_HORARIOS   = "admin_horarios"
    const val ADMIN_ADD_HORARIO = "admin_add_horario/{carreraId}/{grupoId}"
    const val ADMIN_IMPORT_HORARIOS = "admin_import_horarios"
    // Transportista
    const val TRANSPORTER_SCAN = "transporter_scan/{routeId}/{busName}/{phone}"
}

@Composable
fun AppNavHost(nav: NavHostController) {
    val authVM: AuthViewModel = viewModel()
    val adminGruposViewModel: AdminGruposViewModel = viewModel()
    val user by authVM.user.collectAsState()
    val offlineSession by authVM.offlineSession.collectAsState()
    val isAdmin by authVM.isAdmin.collectAsState()
    val errText by authVM.error.collectAsState()

    val isLoggedIn = user != null || offlineSession != null

    NavHost(navController = nav, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                vm = authVM,
                errorText = errText,
                onLogin = { id, pass, _ ->
                    authVM.login(id, pass)
                },
                onDismissError = { authVM.clearError() }
            )

            LaunchedEffect(isLoggedIn, isAdmin) {
                if (!isLoggedIn) return@LaunchedEffect
                when (isAdmin) {
                    true -> nav.navigate(Routes.ADMIN_HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                    false -> nav.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                        launchSingleTop = true
                    }
                    null -> Unit
                }
            }

            if (isLoggedIn && isAdmin == null) {
                Box(Modifier.fillMaxSize()) { CircularProgressIndicator() }
            }
        }

        composable(Routes.HOME) {
            val displayName = when {
                user != null -> user!!.email?.substringBefore("@") ?: "Usuario"
                offlineSession != null -> offlineSession!!.email.substringBefore("@")
                else -> ""
            }
            HomeScreen(
                userName          = displayName,
                onGoGrades        = { nav.navigate(Routes.GRADES) },
                onGoProfile       = { nav.navigate(Routes.PROFILE) },
                onGoRoutes        = { nav.navigate(Routes.ROUTES) },
                onGoHealth        = { nav.navigate(Routes.HEALTH) },
                onGoSettings      = { nav.navigate(Routes.SETTINGS) },
                onGoSubjects      = { nav.navigate(Routes.SUBJECTS) },
                onGoAnnouncements = { nav.navigate(Routes.ANNOUNCEMENTS) },
                onGoTimetable     = { nav.navigate(Routes.TIMETABLE) },
                onLogout          = {
                    authVM.logout()
                    nav.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.ADMIN_HOME) {
            AdminDashboard(
                onLogout = {
                    authVM.logout()
                    nav.navigate(Routes.LOGIN) {
                        popUpTo(0) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onOpenAlumnos = { nav.navigate(Routes.ADMIN_ALUMNOS) },
                onOpenMaterias = { nav.navigate(Routes.ADMIN_MATERIAS) },
                onOpenGrupos = { nav.navigate(Routes.ADMIN_GRUPOS) },
                onOpenHorarios = { nav.navigate(Routes.ADMIN_HORARIOS) },
                onOpenProfesores = { nav.navigate(Routes.ADMIN_PROFESORES) },
                onOpenActivity = { nav.navigate(Routes.ADMIN_ACTIVITY) }
            )
        }
        composable(Routes.ADMIN_ALUMNOS)    {
            AdminAlumnosScreen(
                onBack = { nav.popBackStack() },
                onEdit = { alumnoId -> nav.navigate("admin_edit_alumno/$alumnoId") },
                onAddManually = { nav.navigate(Routes.ADMIN_ADD_ALUMNO) },
                onImportExcel = { nav.navigate(Routes.ADMIN_IMPORT_ALUMNOS) }
            )
        }
        composable(Routes.ADMIN_MATERIAS)   {
            val vm: AdminMateriasViewModel = viewModel()
            val uiState by vm.uiState.collectAsState()
            AdminMateriasScreen(
                onBack = { nav.popBackStack() },
                onOpenMateria = { materiaId -> nav.navigate("admin_materia_detail/$materiaId") },
                onAddManually = {
                    val carreraId =uiState.selectedCarrera?.id ?: return@AdminMateriasScreen
                    val grupoId = uiState.selectedGrupo?.id ?: return@AdminMateriasScreen
                    nav.navigate("admin_add_materia/$carreraId/$grupoId")
                },
                onImportExcel = { nav.navigate(Routes.ADMIN_IMPORT_MATERIAS) },
                vm = vm
            )
        }
        composable(Routes.ADMIN_GRUPOS)     {
            val uiState by adminGruposViewModel.uiState.collectAsState()
            AdminGruposScreen(
                onBack = { nav.popBackStack() },
                onGroupClick = { groupId ->
                    nav.navigate("admin_group_detail/$groupId")
                },
                onAddManually = {
                    val carreraId = uiState.selectedCarrera?.id ?: return@AdminGruposScreen
                    nav.navigate("admin_add_group_details/$carreraId")
                 },
                onImportExcel = { /* TODO: Implement import from Excel */ },
                uiState = uiState,
                onCarreraSelected = { adminGruposViewModel.onCarreraSelected(it) }
            )
        }
        composable(Routes.ADMIN_PROFESORES) { backStackEntry ->
            val vm: AdminProfesoresViewModel = viewModel(backStackEntry)
            val uiState by vm.uiState.collectAsState()

            AdminProfesoresScreen(
                onBack = { nav.popBackStack() },
                onAddManually = {
                    val carreraId = uiState.selectedCarrera?.id ?: return@AdminProfesoresScreen
                    nav.navigate("admin_add_profesor/$carreraId")
                },
                onImportExcel = { /* TODO */ },
                vm = vm
            )
        }
        composable(Routes.ADMIN_HORARIOS)   {
            val vm: AdminHorariosViewModel = viewModel()
            val uiState by vm.uiState.collectAsState()
            AdminHorariosScreen(
                onBack = { nav.popBackStack() },
                onAddManually = {
                    val carreraId = uiState.selectedCarrera?.id ?: return@AdminHorariosScreen
                    val grupoId = uiState.selectedGrupo?.id ?: return@AdminHorariosScreen
                    nav.navigate("admin_add_horario/$carreraId/$grupoId")
                },
                onImportExcel = { nav.navigate(Routes.ADMIN_IMPORT_HORARIOS) },
                vm = vm
            )
        }
        composable(Routes.ADMIN_ACTIVITY)   { AdminActivityScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.ADMIN_IMPORT_ALUMNOS) { ImportAlumnosScreen(onBack = { nav.popBackStack() }) }

        composable(Routes.ADMIN_ADD_ALUMNO) {
            AdminAddAlumnoScreen(onBack = { nav.popBackStack() })
        }

        composable(
            route = Routes.ADMIN_ADD_PROFESOR,
            arguments = listOf(navArgument("carreraId") { type = NavType.StringType })
        ) { back ->
            val carreraId = back.arguments?.getString("carreraId") ?: return@composable
            AddProfesorScreen(
                carreraId = carreraId,
                onBack = { nav.popBackStack() }
            )
        }

        composable(
            route = Routes.ADMIN_ADD_MATERIA,
            arguments = listOf(
                navArgument("carreraId") { type = NavType.StringType },
                navArgument("grupoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val carreraId = backStackEntry.arguments?.getString("carreraId") ?: ""
            val grupoId = backStackEntry.arguments?.getString("grupoId") ?: ""
            AddMateriaScreen(
                carreraId = carreraId,
                grupoId = grupoId,
                onBack = { nav.popBackStack() }
            )
        }

        composable(Routes.ADMIN_IMPORT_MATERIAS) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Importar Materias desde Excel (No implementado)")
            }
        }

        composable(
            route = Routes.ADMIN_ADD_HORARIO,
            arguments = listOf(
                navArgument("carreraId") { type = NavType.StringType },
                navArgument("grupoId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val carreraId = backStackEntry.arguments?.getString("carreraId") ?: ""
            val grupoId = backStackEntry.arguments?.getString("grupoId") ?: ""
            AddHorarioScreen(
                carreraId = carreraId,
                grupoId = grupoId,
                onBack = { nav.popBackStack() }
            )
        }

        composable(Routes.ADMIN_IMPORT_HORARIOS) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Importar Horarios desde Excel (No implementado)")
            }
        }

        composable(
            route = Routes.ADMIN_GROUP_DETAIL,
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) { backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId") ?: return@composable
            AdminGroupDetailScreen(
                groupId = groupId,
                onBack = { nav.popBackStack() }
            )
        }

        composable(
            route = Routes.ADMIN_ADD_GROUP_DETAILS,
            arguments = listOf(
                navArgument("carreraId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            AddGroupDetailsScreen(
                onBack = { nav.popBackStack() },
                onNext = { groupName, programType, tutorId ->
                    val carreraId = backStackEntry.arguments?.getString("carreraId") ?: ""
                    val safeGroupName = Uri.encode(groupName)
                    nav.navigate("admin_assign_students/$safeGroupName/$programType/$tutorId/$carreraId")
                },
                vm = adminGruposViewModel
            )
        }

        composable(
            route = Routes.ADMIN_ASSIGN_STUDENTS,
            arguments = listOf(
                navArgument("groupName") { type = NavType.StringType },
                navArgument("programType") { type = NavType.StringType },
                navArgument("tutorId") { type = NavType.StringType },
                navArgument("carreraId") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val groupName = backStackEntry.arguments?.getString("groupName") ?: ""
            val programType = backStackEntry.arguments?.getString("programType") ?: ""
            val tutorId = backStackEntry.arguments?.getString("tutorId") ?: ""
            val carreraId = backStackEntry.arguments?.getString("carreraId") ?: ""
            AssignStudentsScreen(
                onBack = { nav.popBackStack() },
                onSaveGroup = { studentIds ->
                    adminGruposViewModel.createGroup(groupName, tutorId, studentIds, carreraId)
                    nav.navigate(Routes.ADMIN_GRUPOS) {
                        popUpTo(Routes.ADMIN_GRUPOS) { inclusive = true }
                    }
                },
                vm = adminGruposViewModel
            )
        }

        composable(
            route = Routes.ADMIN_MATERIA_DETAIL,
            arguments = listOf(navArgument("materiaId") { type = NavType.StringType })
        ) { backStackEntry ->
            val materiaId = backStackEntry.arguments?.getString("materiaId") ?: ""
            AdminMateriaDetailScreen(
                materiaId = materiaId,
                onBack = { nav.popBackStack() }
            )
        }

        // Composable para la nueva pantalla de edición
        composable(
            route = Routes.ADMIN_EDIT_ALUMNO,
            arguments = listOf(navArgument("alumnoId") { type = NavType.StringType })
        ) { backStackEntry ->
            val alumnoId = backStackEntry.arguments?.getString("alumnoId") ?: ""
            AdminEditAlumnoScreen(
                alumnoId = alumnoId,
                onBack = { nav.popBackStack() }
            )
        }

        /* ------------ Alumno ------------ */
        composable(Routes.GRADES)    { GradesScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.PROFILE)   { ProfileScreen(onBack = { nav.popBackStack() }) }
        composable(Routes.TIMETABLE) { TimetableScreen(onBack = { nav.popBackStack() }) }

        /* ------------ Configuración (logout OK) ------------ */
        composable(Routes.SETTINGS) {
            SettingsScreen(
                onBack = { nav.popBackStack() },
                onLogout = {
                    authVM.logout()
                    nav.navigate(Routes.LOGIN) { popUpTo(0) } // limpia back stack
                }
            )
        }

        /* ------------ Avisos ------------ */
        composable(Routes.ANNOUNCEMENTS) { AnnouncementsScreen() }

        /* ------------ Rutas / Mapas ------------ */
        composable(Routes.ROUTES) {
            RoutesSelectorScreen(
                onBack = { nav.popBackStack() },
                onOpenSaltillo = { nav.navigate("routeMap/Saltillo") },
                onOpenRamos    = { nav.navigate("routeMap/Ramos") }
            )
        }
        composable(Routes.ROUTE_MAP) { back ->
            val routeId = back.arguments?.getString("id") ?: "R5"
            RouteMapScreen(routeId = routeId, onBack = { nav.popBackStack() })
        }

        /* ------------ Transportista ------------ */
        composable(
            route = Routes.TRANSPORTER_SCAN,
            arguments = listOf(
                navArgument("routeId") { type = NavType.StringType },
                navArgument("busName") { type = NavType.StringType },
                navArgument("phone")   { type = NavType.StringType }
            )
        ) { back ->
            val routeId = back.arguments?.getString("routeId") ?: "Ramos"
            val busName = back.arguments?.getString(
                "busName"
            ) ?: "Camión 1"
            val phone   = back.arguments?.getString("phone")   ?: "5218440000000"
            TransporterScanScreen(
                routeId = routeId,
                busName = busName,
                notifyPhoneNumber = phone,
                onBack = { nav.navigate(Routes.LOGIN) { popUpTo(0) } }
            )
        }
    }
}
