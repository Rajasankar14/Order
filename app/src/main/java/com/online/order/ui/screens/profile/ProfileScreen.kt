package com.online.order.ui.screens.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.ui.components.AppBox
import com.online.order.ui.components.AppIcon
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.ProfileMenu
import com.online.order.data.ThemeType
import com.online.order.navigation.AuthNavGraph
import com.online.order.preferences.ThemePreferences
import com.online.order.theme.AppTheme
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel) {

    val context = LocalContext.current

    val activity = context as? Activity

    var capturedPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var capturedImageFile by remember {
        mutableStateOf<File?>(null)
    }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }


    var showLogOutDialog by remember {
        mutableStateOf(false)
    }




    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success->
        if (success && capturedPhotoUri != null) {
            val inputStream = context.contentResolver.openInputStream(capturedPhotoUri!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            capturedBitmap = bitmap
        }else{
            capturedPhotoUri = null
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
    }




    val menuItems by profileViewModel.menuItems.collectAsState(emptyList())

    Column (modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
        .padding(
            top = WindowInsets.statusBars
                .asPaddingValues()
                .calculateTopPadding()
        )
        .padding(8.dp)){

        AppIcon(imageVector = Icons.Default.ArrowBack, tintColor = MaterialTheme.colorScheme.onBackground, modifier = Modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .size(30.dp)
            .clickable {
                navController.navigateUp()
            })

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp)

        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (capturedBitmap == null) {
                    AppBox(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {

                                navController.navigate(AuthNavGraph.USEPROFILE.route)
//                                if (ContextCompat.checkSelfPermission(
//                                        context,
//                                        Manifest.permission.CAMERA
//                                    ) != PackageManager.PERMISSION_GRANTED
//                                ) {
//                                    permissionLauncher.launch(Manifest.permission.CAMERA)
//                                } else {
//
////                                    val file = viewModel.createImageFile(context)
////                                    val uri = FileProvider.getUriForFile(
////                                        context,
////                                        "${context.packageName}.provider",
////                                        file
////                                    )
////                                    capturedImageFile = file
////                                    capturedPhotoUri = uri
////                                    launcher.launch(uri)
//                                }

                            }
                    ) {

                        AppIcon(
                            imageVector = Icons.Filled.Person,
                            modifier = Modifier
                                .size(40.dp)
                                .align(Alignment.Center)
                        )
                    }
                } else {
                    capturedBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Captured",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                        )
                    }
                }


                Column(
                    modifier = Modifier.padding(start = 12.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    AppMediumTextView(
                        text = "Raja",
                        modifier = Modifier,
                        fontWeight = FontWeight.Bold,
                        sp = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground

                    )

                    AppSmallTextView(
                        text = "Edit",
                        modifier = Modifier.padding(top = 2.dp),
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground

                    )
                }


            }


        }
        //ProfileTheme()

        ProfileItemList(menuItems, navController, onLogoutClick = {
            showLogOutDialog = true
        }, profileViewModel)
        
        

    }
    LogOutDialog(showDialog = showLogOutDialog, onDismiss = {
        showLogOutDialog = false



    },
        onConfirm = {
            showLogOutDialog = false
            activity?.finish()
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTheme(profileMenu: ProfileMenu, navController: NavController, onLogoutClick : () -> Unit, profileViewModel: ProfileViewModel){



    var showSheet by remember {
        mutableStateOf(false)
    }



    var showSelectedTheme by remember {
        mutableStateOf<ThemeType?>(null)
    }
    val scope = rememberCoroutineScope()

    var bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)



    LaunchedEffect(Unit) {
        showSelectedTheme = profileViewModel.getSavedThemeId()
    }
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                if (profileMenu.isRouteType) {
                    navController.navigate(profileMenu.routeName)
                } else {
                    if (profileMenu.menuName.equals("Log out", false)) {
                        onLogoutClick()
                    } else if (profileMenu.menuName.equals("Appearance", false)) {
                        showSheet = true
                    }
                }

            }

    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

                Image(
                    painterResource(id = profileMenu.menuImage),
                    contentDescription = "",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )

            Column(
                modifier = Modifier.padding(start = 12.dp),
                verticalArrangement = Arrangement.Center
            ) {
                AppMediumTextView(
                    text = profileMenu.menuName,
                    modifier = Modifier,
                    fontWeight = FontWeight.Normal,
                    sp = 12.sp,
                    color = MaterialTheme.colorScheme.onBackground

                )

            }

            Spacer(modifier = Modifier.weight(1f))


            if(showSheet){
                ModalBottomSheet(onDismissRequest = {
                    scope.launch {
                        showSheet = false
                        bottomSheetState.hide()

                    }
                }, sheetState = bottomSheetState) {
                    BottomSheetView(onDismissBottomSheet = { theme->
                        scope.launch {
                            showSheet = false
                            bottomSheetState.hide()
                            showSelectedTheme = theme

                        }

                    }, showSelectedTheme,profileViewModel )

                }
            }

            if (profileMenu.menuName.equals("Appearance", false)) {
                showSelectedTheme?.let { AppMediumTextView(it.themeName, sp = 14.sp, fontWeight = FontWeight.SemiBold) }
                }

            AppIcon(imageVector = Icons.Default.KeyboardArrowRight, tintColor = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@Composable
fun BottomSheetView(onDismissBottomSheet : (ThemeType) -> Unit, showSelectedTheme : ThemeType?, profileViewModel: ProfileViewModel){

    var selectedThemeId by remember {
        mutableIntStateOf(0)
    }

    var selectedThemeType by remember {
        mutableStateOf<ThemeType?>(null)
    }

    var saveSelectedTheme by remember {
        mutableStateOf<ThemeType?>(null)
    }

    val themeList =
        listOf(ThemeType("Light Theme", 1),
            ThemeType("Dark Theme", 2),
            ThemeType("Device Theme", 3))


    LaunchedEffect(Unit) {
        if (showSelectedTheme != null) {
            selectedThemeId = showSelectedTheme.isThemeSelected
            selectedThemeType = showSelectedTheme
        }
    }
    Column(modifier = Modifier.padding(vertical = 12.dp, horizontal = 12.dp)) {
        AppMediumTextView("Choose Theme", sp = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(vertical = 8.dp))

        LazyColumn(
            modifier = Modifier,
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {

            items(themeList){ theme ->

                Row (modifier = Modifier.padding(horizontal = 8.dp), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    AppMediumTextView(theme.themeName, sp = 14.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    RadioButton(selected = selectedThemeId == theme.isThemeSelected , onClick = {
                        selectedThemeId = theme.isThemeSelected
                        selectedThemeType = theme


                    })
                }


            }

        }

        Button(
            onClick = {
                saveSelectedTheme = selectedThemeType
                saveSelectedTheme?.let { profileViewModel.saveThemeId(it) }
                saveSelectedTheme?.let { onDismissBottomSheet(it) }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            AppMediumTextView(
                "Save Changes",
                color = MaterialTheme.colorScheme.background,
                sp = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

}




@Composable
fun ProfileItemList(profileMenuList: List<ProfileMenu>, navController: NavController, onLogoutClick : () -> Unit, profileViewModel: ProfileViewModel){
    LazyColumn {
        items(profileMenuList){ profileItem ->
            ProfileTheme(profileItem, navController, onLogoutClick,profileViewModel)
        }
    }
}


@Composable
fun LogOutDialog(showDialog: Boolean, onDismiss :() -> Unit , onConfirm :() -> Unit){
    if(showDialog){
        AlertDialog(onDismissRequest = {onDismiss() }, confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                AppMediumTextView("Confirm")
            }
        },
            title = {
                AppMediumTextView("Are you sure you want to Log out from Application?", sp = 16.sp, lineHeight = 20.sp)
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    AppMediumTextView("Cancel", )
                }
            },
        ) 
    }


   
}

class FakeSharedPreference(context: Context) : ThemePreferences(context) {

    companion object{
        private const val  KEY_THEME_ID = "SELECTED_THEME_ID"
    }


}






@PreviewLightDark
@Composable
fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(rememberNavController(), ProfileViewModel(FakeSharedPreference(LocalContext.current)))
    }
}