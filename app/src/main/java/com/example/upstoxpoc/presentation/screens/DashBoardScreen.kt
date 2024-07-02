package com.example.upstoxpoc.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.upstoxpoc.R
import com.example.upstoxpoc.data.model.CryptoItem
import com.example.upstoxpoc.domain.utils.ApiState
import com.example.upstoxpoc.domain.utils.FilterType
import com.example.upstoxpoc.domain.utils.Utils
import com.example.upstoxpoc.domain.utils.filterCategories
import com.example.upstoxpoc.presentation.FilterBottomSheet
import com.example.upstoxpoc.presentation.ShowLoading
import com.example.upstoxpoc.presentation.SpacerWidth
import com.example.upstoxpoc.presentation.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(
    navHostController: NavHostController,
    mainViewModel: MainViewModel = hiltViewModel()
) {
    var functionCalled by remember { mutableStateOf(false) }
    var showBottomSheet by remember { mutableStateOf(false) }
    val selectedCategories = remember { mutableStateListOf<String>() }
    val filters = remember { mutableStateListOf<FilterType>() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showBottomSheet = true
                selectedCategories.clear()
                filters.clear()
            }) {
                Icon(Icons.Default.FilterList, contentDescription = "Filter")
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            CallApi(mainViewModel)
            if (showBottomSheet) {
                FilterBottomSheet(
                    filterCategories = filterCategories,
                    onCategorySelected = { category ->
                        if (category in selectedCategories) {
                            selectedCategories.remove(category)
                        } else {
                            selectedCategories.add(category)
                        }
                        functionCalled = true
                    },
                    onDismissRequest = { showBottomSheet = false }
                )
            }

            LaunchedEffect(functionCalled) {
                if (functionCalled) {
                    mainViewModel.getFilterListByOption(
                        Utils.convertToFilterTypeList(selectedCategories.toList())
                    )
                    functionCalled = false // Reset the flag
                }
            }
        }
    }
}

@Composable
fun CallApi(mainViewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpacerWidth(30.dp)

        LaunchedEffect(Unit) {
            mainViewModel.callCryptoApi()
        }
        when (val result = mainViewModel.response.value) {
            is ApiState.Loading -> {
                ShowLoading()
            }

            is ApiState.Success -> {
                if (result.data.code() == 200) {
                    result.data.body()?.let {
                        ShowListPreview(mainViewModel, it)
                    }
                } else {
                    Text(
                        text = "${stringResource(id = R.string.something_went_wrong)} ${result.data.code()}",
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
            }

            is ApiState.Failure -> {
                Text(
                    text = "${result.msg}",
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Red,
                        fontWeight = FontWeight.Normal
                    )
                )
            }

            is ApiState.Empty -> {
                //This is Ideal Case
            }
        }

    }
}

@Composable
fun ListItem(cryptoItem: CryptoItem) {
    val trailingImage = Utils.getCrytoTypeIcon(cryptoItem)

    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 4.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(text = cryptoItem.name, fontSize = 20.sp, color = Color.Black)
                Text(text = cryptoItem.symbol, fontSize = 16.sp, color = Color.Gray)
            }

            Box {
                if (cryptoItem.isNew) {
                    Icon(
                        painter = painterResource(id = R.drawable.new_image),
                        contentDescription = "",
                        modifier = Modifier
                            .size(25.dp)
                            .align(Alignment.TopEnd),
                        tint = Color.Unspecified
                    )
                }
                Image(
                    painter = painterResource(id = trailingImage),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 10.dp, end = 10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop
                )

            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            content()
        }
    }
}

@Composable
fun ShowListPreview(mainViewModel: MainViewModel, list: List<CryptoItem>) {
    val viewState by mainViewModel.viewState.collectAsState()
    val searchItem = remember { mutableStateOf("") }
    MyApp {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            item {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .background(color = Color.White),
                    value = searchItem.value,
                    onValueChange = { searchText ->
                        searchItem.value = searchText
                        mainViewModel.getFilterListByName(list, searchText)

                    },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "")
                    })
            }
            items(items = viewState.filterList) { item ->
                item?.let {
                    ListItem(cryptoItem = it)
                }
            }
        }
    }
}
