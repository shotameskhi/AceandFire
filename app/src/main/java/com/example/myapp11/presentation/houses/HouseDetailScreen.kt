package com.example.myapp11.presentation.houses

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun HouseDetailScreen(
    houseId: Int,
    viewModel: HouseDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(houseId) {
        viewModel.loadHouse(houseId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = uiState) {
            is HouseDetailUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is HouseDetailUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is HouseDetailUiState.Success -> {
                val house = state.house
                val imageUrl = "https://ui-avatars.com/api/?name=${house.name.replace(" ", "+")}&background=795548&color=fff&size=512"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = house.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = house.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (house.region.isNotBlank()) DetailRow(label = "Region", value = house.region)
                        if (house.coatOfArms.isNotBlank()) DetailRow(label = "Coat of Arms", value = house.coatOfArms)
                        if (house.words.isNotBlank()) DetailRow(label = "Words", value = house.words)
                        if (house.titles.any { it.isNotBlank() }) DetailRow(label = "Titles", value = house.titles.filter { it.isNotBlank() }.joinToString(", "))
                        if (house.seats.any { it.isNotBlank() }) DetailRow(label = "Seats", value = house.seats.filter { it.isNotBlank() }.joinToString(", "))
                        if (house.founded.isNotBlank()) DetailRow(label = "Founded", value = house.founded)
                        if (house.ancestralWeapons.any { it.isNotBlank() }) DetailRow(label = "Ancestral Weapons", value = house.ancestralWeapons.filter { it.isNotBlank() }.joinToString(", "))
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.secondary
        )
        Text(text = value, fontSize = 16.sp)
    }
}
