package com.online.order.ui.screens.home.address

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.online.order.R
import com.online.order.ui.components.AppLargeTextView
import com.online.order.ui.components.AppMediumTextView
import com.online.order.ui.components.AppSmallTextView
import com.online.order.data.Filter
import com.online.order.database.address.AddressEntity
import com.online.order.theme.AppTheme
import com.online.order.theme.orangeNavItem
import com.online.order.theme.textErrorColor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(navController: NavController, addressViewModel: AddressViewModelContract, isFromProfileScreen : Boolean) {
    val context = LocalContext.current
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val scope = rememberCoroutineScope()
    var showSheet by remember {
        mutableStateOf(false)
    }

    val addressList by addressViewModel.addressList.collectAsState(initial = emptyList())


    val editAddress by addressViewModel.editAddress.collectAsState()


    var addressSelected by remember {
        mutableStateOf<AddressEntity?>(null)
    }





    LaunchedEffect(Unit) {
        addressViewModel.dismissBottomSheet.collect {
            if (it) {
                Toast.makeText(context, "Address updated successfully", Toast.LENGTH_SHORT).show()
                showSheet = false
            }
        }

    }

    LaunchedEffect(editAddress) {
        if (editAddress != null) {
            showSheet = true
            bottomSheetState.show()
        }
    }



    Box(modifier = Modifier.then(if(isFromProfileScreen) Modifier.safeContentPadding() else Modifier)) {
        Column(modifier = Modifier.fillMaxSize()) {
            AddressToolBar(navController)
            LazyColumn(modifier = Modifier.padding(8.dp)) {
                items(addressList) { address ->
                    AddressBookItem(addressViewModel,address, isExpanded = address == addressSelected, onMenuClick = {
                        addressEntity ->
                        addressSelected = addressEntity

                    },

                        onEditClick = {
                            editAddressfromList ->
                            if (editAddressfromList != null) {
                                addressViewModel.getAddressById(editAddressfromList)
                            }
                        },
                        onItemClick = {

                        })
                }
            }


        }
        FloatingActionButton(
            onClick = {
                scope.launch {
                    showSheet = true
                    bottomSheetState.show()
                }

            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        if (showSheet) {

            ModalBottomSheet(onDismissRequest = {
                showSheet = false
                addressViewModel.resetEditAddress()
            }, sheetState = bottomSheetState, modifier = Modifier
                .wrapContentHeight()
                .padding(8.dp)) {
                ShowBottomSheet(addressViewModel)
            }
        }
    }


}



@Composable
fun AddressBookItem(addressViewModel : AddressViewModelContract, address: AddressEntity, isExpanded : Boolean, onMenuClick: (AddressEntity?) -> Unit, onEditClick :(AddressEntity?) -> Unit, onItemClick :(Int) -> Unit){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable {
                onItemClick(address.id)
            }

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row (horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.4f)
                    .padding(start = 8.dp, end = 8.dp), verticalArrangement = Arrangement.Center) {
                    Icon(
                        painter = painterResource(id = getImage(address.locationType)),
                        contentDescription = "",
                        modifier = Modifier.size(30.dp)
                        , tint = MaterialTheme.colorScheme.tertiary
                    )
                    AppSmallTextView(
                        text = "1.4KM",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                }
                Column(modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1.8f), horizontalAlignment = Alignment.Start) {
                    AppMediumTextView(
                        text = address.locationType,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.SemiBold,
                        sp = 18.sp
                    )
                    AppMediumTextView(
                        text = address.name,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        sp = 16.sp,
                        lineHeight = 14.sp
                    )

                    AppMediumTextView(
                        text = address.fullAddress,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        sp = 14.sp,
                        lineHeight = 14.sp
                    )
                    AppMediumTextView(
                        text = "Phone number : +91- " + address.phoneNumber,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Normal,
                        sp = 12.sp,
                        lineHeight = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )


                }
                Box(modifier = Modifier
                    .padding(start = 8.dp)
                    .align(Alignment.Bottom), contentAlignment = Alignment.BottomEnd) {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Column {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "food",
                                modifier = Modifier
                                    .size(20.dp)
                                    .rotate(90f)
                                    .clickable {

                                        onMenuClick(address)

                                    },
                                tint = orangeNavItem
                            )

                        }

                        Spacer(modifier = Modifier.width(12.dp))
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "food",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {

                                },
                            tint = orangeNavItem
                        )


                    }
                }

                    Box {
                        DropdownMenu(expanded = isExpanded , onDismissRequest = {   onMenuClick(null) }, modifier = Modifier.align(Alignment.TopEnd)) {
                            DropdownMenuItem(text = { AppMediumTextView(text = "Edit") }, onClick = {
                                onEditClick(address)
                                onMenuClick(null)
                            })

                            DropdownMenuItem(text = { AppMediumTextView(text = "Delete") }, onClick = {
                                addressViewModel.deleteAddress(address)
                                onMenuClick(null)

                            })

                        }
                    }



            }




        }

    }

}



@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowBottomSheet(addressViewModel: AddressViewModelContract) {

    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf(false) }

    var phoneNumber by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf(false) }


    val locationType by addressViewModel.locationType.collectAsState()


    var fullAddress by remember { mutableStateOf("") }
    var fullAddressError by remember { mutableStateOf(false) }


    var floor by remember {
        mutableStateOf("")
    }
    var floorError by remember { mutableStateOf(false) }


    var landmark by remember { mutableStateOf("") }
    var landmarkError by remember { mutableStateOf(false) }

    var locationTypeSelected by remember {
        mutableStateOf("")
    }






    val scrollState = rememberScrollState()

    val imeVisible = WindowInsets.ime.getBottom(LocalDensity.current) > 0

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val sheetHeight = screenHeight * 0.7f


    LaunchedEffect(addressViewModel.editAddress.value) {
        if (addressViewModel.editAddress.value != null) {
            name = addressViewModel.editAddress.value?.name ?: ""
            phoneNumber = addressViewModel.editAddress.value?.phoneNumber ?: ""
            //locationType = addressViewModel.editAddress.value!!.locationType
            fullAddress = addressViewModel.editAddress.value?.fullAddress?: ""
            floor = addressViewModel.editAddress.value?.floor?: ""
            landmark = addressViewModel.editAddress.value?.landmark?: ""
        }
    }
        Column(
            modifier = Modifier
                .height(sheetHeight)
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState)

            ) {
                AppLargeTextView(
                    "Enter Complete Address",
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedTextField(value = name,
                    onValueChange = { it ->
                        val filtered = it.filter { it.isLetterOrDigit() }
                        name = filtered
                        nameError = false
                    },
                    singleLine = true, label = {
                        AppMediumTextView(
                            "Receiver's name",
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = textErrorColor
                    ),
                    isError = nameError,
                    trailingIcon = {
                        if (nameError) {
                            IconButton(onClick = { nameError = !nameError }) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "Error",
                                    tint = MaterialTheme.colorScheme.onBackground
                                )
                            }
                        }
                    }
                )
                if (nameError) {
                    AppSmallTextView(
                        text = "Address is required",
                        color = textErrorColor,
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                    )
                }
                OutlinedTextField(
                    value = phoneNumber, onValueChange = { it ->
                        val filtered = it.filter { it.isDigit() }
                        phoneNumber = filtered
                        phoneError = false
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ), label = {

                        AppMediumTextView(
                            "Receiver's contact",
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground
                        )

                    },
                    singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = textErrorColor
                    ),
                    isError = phoneError
                )
                if (phoneError) {
                    AppSmallTextView(
                        text = "Receiver's contact is required",
                        color = textErrorColor,
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                    )
                }
                AppMediumTextView(
                    "Location Type", modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp), color = MaterialTheme.colorScheme.onBackground
                )


                LazyRow( modifier = Modifier
                    .padding(vertical = 4.dp)
                    .fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(locationType){
                        filter ->
                        LocationTypeItem(filter,locationTypeSelected, onItemClick = {
                                name ->
                            locationTypeSelected = name

                        })

                    }
                }
                OutlinedTextField(value = fullAddress, onValueChange = { it ->
                    val filtered =
                        it.filter { it.isLetterOrDigit() || it == ',' || it == '.' || it == ' ' }
                    fullAddress = filtered
                    fullAddressError = false
                }, label = {
                    AppMediumTextView(
                        "Complete Address *",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                    singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = textErrorColor
                    ),
                    isError = fullAddressError
                )
                if (fullAddressError) {
                    AppSmallTextView(
                        text = "Complete Address is required",
                        color = textErrorColor,
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                    )
                }

                OutlinedTextField(
                    value = floor, onValueChange = { it ->
                        val filtered =
                            it.filter { it.isLetterOrDigit() || it == ',' || it == '.' || it == ' ' }
                        floor = filtered
                        floorError = false
                    },
                    singleLine = true, label = {
                        AppMediumTextView(
                            "Floor (optional)",
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = textErrorColor
                    ),
                    isError = floorError
                )
                if (floorError) {
                    AppSmallTextView(
                        text = "Floor is required",
                        color = textErrorColor,
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                    )
                }

                OutlinedTextField(value = landmark, onValueChange = { it ->
                    val filtered =
                        it.filter { it.isLetterOrDigit() || it == ',' || it == '.' || it == ' ' }
                    landmark = filtered
                    landmarkError = false
                }, label = {
                    AppMediumTextView(
                        "Landmark (optional)",
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                    singleLine = true, modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp), colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedBorderColor = Color.LightGray,
                        errorBorderColor = textErrorColor
                    ),
                    isError = landmarkError
                )
                if (landmarkError) {
                    AppSmallTextView(
                        text = "Landmark is required",
                        color = textErrorColor,
                        modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                    )
                }
            }

          //  Box (modifier = Modifier.align(Alignment.End)) {
            AnimatedVisibility(visible = !imeVisible) {
                Button(
                    onClick = {

                        if (name.isEmpty()) {
                            nameError = true
                        } else if (phoneNumber.isEmpty()) {
                            phoneError = true
                        } else if (fullAddress.isEmpty()) {
                            fullAddressError = true
                        } else if (floor.isEmpty()) {
                            floorError = true
                        } else if (landmark.isEmpty()) {
                            landmarkError = true
                        }else if(locationTypeSelected.isEmpty()){
                            Toast.makeText(context, "Please select location type", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "All entries are fine", Toast.LENGTH_SHORT)
                                .show()


                            if(addressViewModel.editAddress.value != null){
                                addressViewModel.updateAddress(
                                    AddressEntity(
                                        id = addressViewModel.editAddress.value!!.id,
                                        name = name,
                                        phoneNumber = phoneNumber,
                                        fullAddress = fullAddress,
                                        floor = floor,
                                        landmark = landmark,
                                        locationType = locationTypeSelected
                                    )
                                )
                            }else{
                                addressViewModel.insertAddress(
                                    AddressEntity(
                                        name = name,
                                        phoneNumber = phoneNumber,
                                        fullAddress = fullAddress,
                                        floor = floor,
                                        landmark = landmark,
                                        locationType = locationTypeSelected
                                    )
                                )
                            }

                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    AppMediumTextView(
                        "CONFIRM ADDRESS",
                        color = MaterialTheme.colorScheme.background,
                        sp = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                //  }
            }



        }



}


@Composable
fun LocationTypeItem(filter: Filter,locationTypeSelected : String, onItemClick : (String) -> Unit){
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (locationTypeSelected.isNotBlank() && locationTypeSelected == filter.filterName) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surface)
            .clickable {
                onItemClick(filter.filterName)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = filter.filterImage),
            contentDescription = "",
            modifier = Modifier.size(15.dp),
            tint = if(locationTypeSelected.isNotBlank() && locationTypeSelected == filter.filterName) Color.White else MaterialTheme.colorScheme.onBackground
        )
        AppMediumTextView(
            filter.filterName,
            modifier = Modifier.padding(start = 8.dp),
            color = if(locationTypeSelected.isNotBlank() && locationTypeSelected == filter.filterName) Color.White else MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewLightDark
@Composable
fun ShowBottomSheetPreview() {
    AppTheme {
        ShowBottomSheet(FakeAddressViewModel())
    }

}

@Composable
fun AddressToolBar(navController: NavController) {
    Column(modifier = Modifier) {

        Row(
            modifier = Modifier
                .height(45.dp)
                .padding(start = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft,
                contentDescription = "food",
                modifier = Modifier
                    .size(30.dp)
                    .clickable(indication = null, interactionSource = remember {
                        MutableInteractionSource()
                    }) {
                        navController.navigateUp()
                    },
                tint = MaterialTheme.colorScheme.onBackground
            )
            AppMediumTextView(
                text = "Address",
                modifier = Modifier.padding(start = 6.dp),
                fontWeight = FontWeight.Bold,
                sp = 18.sp,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.weight(1f))




            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "food",
                modifier = Modifier
                    .size(20.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )


        }


    }


}


fun getImage(name: String) : Int{
   return when(name){
        "Home" -> R.drawable.ic_home
        "Work" -> R.drawable.ic_office
        "Hotel" -> R.drawable.ic_hotel
        "Other" -> R.drawable.ic_park
       else -> R.drawable.ic_home
    }

}

class FakeAddressViewModel: AddressViewModelContract {
    val allAddresses: Flow<List<AddressEntity>> =
        flowOf(
            listOf(
                AddressEntity(
                    id = 1,
                    name = "John Doe",
                    phoneNumber = "1234567890",
                    fullAddress = "123 Main St",
                    floor = "2nd Floor",
                    landmark = "Near Park",
                    "location"
                )
            )
        )
    override val addressList: StateFlow<List<AddressEntity>>
         = MutableStateFlow(emptyList())
    override val locationType  = MutableStateFlow(
        listOf(
            Filter("Home", R.drawable.ic_home),
            Filter("Work", R.drawable.ic_office)
        )
    )
    override val editAddress = MutableStateFlow<AddressEntity?>(null)
    override var dismissBottomSheet: SharedFlow<Boolean> = MutableSharedFlow<Boolean>()
    override fun insertAddress(address: AddressEntity) {

    }

    override fun deleteAddress(address: AddressEntity) {

    }

    override fun getAddressById(address: AddressEntity) {

    }

    override fun updateAddress(address: AddressEntity) {

    }

    override fun resetEditAddress() {

    }
}

@PreviewLightDark
@Composable
fun AddressScreenPreview() {
    AppTheme {
        AddressScreen(rememberNavController(), FakeAddressViewModel(), true)
    }
}