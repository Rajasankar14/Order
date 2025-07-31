package com.online.order.ui.screens.profileUser

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.theme.AppTheme
import com.online.order.theme.textErrorColor
import com.online.order.ui.components.AppBox
import com.online.order.ui.components.AppIcon
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileUserScreen(navController: NavController) {

    var userName by remember { mutableStateOf("Raja") }
    var userNameError by remember { mutableStateOf(false) }

    var mobile by remember { mutableStateOf("7708703852") }
    var mobileError by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }
    var bottomSheetState = rememberModalBottomSheetState(false)

    var coroutineScope = rememberCoroutineScope()

    var photoOptionList by remember {
        mutableStateOf(
            listOf(
                "Choose from Gallery",
                "Take Photo",
                "Cancel"
            )
        )
    }


    val context = LocalContext.current

    var capturedPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    var capturedImageFile by remember {
        mutableStateOf<File?>(null)
    }

    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {
    }



    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && capturedPhotoUri != null) {
                val inputStream = context.contentResolver.openInputStream(capturedPhotoUri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                capturedBitmap = bitmap
            } else {
                capturedPhotoUri = null
            }
        }


    Box(
        modifier = Modifier
            .padding(8.dp)
            .safeContentPadding()
            .fillMaxSize()
    ) {
        AppIcon(imageVector = Icons.Default.ArrowBack,
            tintColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 8.dp)
                .size(30.dp)
                .clickable {
                    navController.navigateUp()
                })
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Box(modifier = Modifier.clickable {
                coroutineScope.launch {
                    showBottomSheet = true
                    bottomSheetState.show()
                }
            }) {
            if(capturedBitmap == null) {
                    AppBox(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary)

                    ) {

                        AppIcon(
                            imageVector = Icons.Filled.Person,
                            modifier = Modifier
                                .size(70.dp)
                                .align(Alignment.Center)
                        )
                    }
                }else{
                    capturedBitmap?.let {
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = "Captured",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                        )
                    }
                }
                Image(imageVector = Icons.Default.Edit, contentDescription = "", modifier = Modifier.size(25.dp).align(Alignment.BottomEnd), colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)  )
            }

            UserEditText(
                "Name",
                userName,
                userNameError,
                errorMessage = "Name is required",
                onValueChange = {
                    userName = it
                    userNameError = false
                })


            UserEditText(
                "Mobile",
                mobile,
                mobileError,
                errorMessage = "Mobile Number is Required",
                onValueChange = {
                    mobile = it
                    mobileError = false
                })

            UserEditText(
                "Email",
                email,
                emailError,
                errorMessage = "Email is Required",
                onValueChange = {
                    email = it
                    emailError = false
                })
        }

        Button(
            onClick = {
                if (userName.isEmpty()) {
                    userNameError = true
                } else if (mobile.isEmpty()) {
                    mobileError = true
                } else if (email.isEmpty()) {
                    emailError = true
                } else {
                    navController.navigateUp()
                }
            }, modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(horizontal = 8.dp)
                .align(Alignment.BottomCenter),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
        ) {
            AppMediumTextView(
                "Update Profile",
                color = MaterialTheme.colorScheme.background,
                sp = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    coroutineScope.launch {
                        bottomSheetState.hide()
                        showBottomSheet = false
                    }

                }, sheetState = bottomSheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {

                LazyColumn {
                    items(photoOptionList) { name ->
                        Column(modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                if (name.equals("Choose from Gallery", true)) {


                                } else if (name.equals("Take Photo", true)) {


                                    if (ContextCompat.checkSelfPermission(
                                            context,
                                            android.Manifest.permission.CAMERA
                                        ) != PackageManager.PERMISSION_GRANTED
                                    ) {
                                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                                    } else {
                                        coroutineScope.launch {
                                            bottomSheetState.hide()
                                            showBottomSheet = false
                                        }

                                        val file = createImageFile(context)
                                        val uri = FileProvider.getUriForFile(
                                            context,
                                            "${context.packageName}.provider",
                                            file
                                        )
                                        capturedImageFile = file
                                        capturedPhotoUri = uri
                                        launcher.launch(uri)


                                    }


                                } else if (name.equals("Cancel", true)) {
                                    coroutineScope.launch {
                                        bottomSheetState.hide()
                                        showBottomSheet = false
                                    }
                                }
                            }) {
                            AppMediumTextView(text = name, modifier = Modifier.padding(12.dp))
                        }

                    }
                }

            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEditText(
    label: String,
    editTextValue: String,
    error: Boolean,
    errorMessage: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(value = editTextValue, onValueChange = { it ->
        val filtered =
            it.filter { it.isLetterOrDigit() || it == ',' || it == '.' || it == ' ' }
        // name = filtered
        // error = false

        onValueChange(filtered)
    }, label = {
        AppMediumTextView(
            label,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onBackground
        )
    }, singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.secondary,
            unfocusedBorderColor = Color.LightGray,
            errorBorderColor = textErrorColor
        ),
        isError = error
    )

    if (error) {
        AppSmallTextView(
            text = errorMessage,
            color = textErrorColor,
            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
        )
    }
}

fun createImageFile(context: Context) : File{
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)
}

@PreviewLightDark
@Composable
fun ProfileUserScreenPreview() {
    AppTheme {
        ProfileUserScreen(navController = rememberNavController())
    }

}