package com.example.myapp11.presentation.details

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
fun CharacterDetailsScreen(
    characterName: String,
    viewModel: CharacterDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(characterName) {
        viewModel.loadCharacter(583)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val state = uiState) {
            is CharacterDetailUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is CharacterDetailUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            is CharacterDetailUiState.Success -> {
                val character = state.character
                val displayName = character.name.ifBlank { characterName }
                val imageUrl = "https://ui-avatars.com/api/?name=${displayName.replace(" ", "+")}&background=random&size=512"

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = displayName,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = displayName,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Column(modifier = Modifier.fillMaxWidth()) {
                        if (character.gender.isNotBlank()) {
                            DetailRow(label = "Gender", value = character.gender)
                        }
                        if (character.culture.isNotBlank()) {
                            DetailRow(label = "Culture", value = character.culture)
                        }
                        if (character.born.isNotBlank()) {
                            DetailRow(label = "Born", value = character.born)
                        }
                        if (character.titles.any { it.isNotBlank() }) {
                            DetailRow(label = "Titles", value = character.titles.filter { it.isNotBlank() }.joinToString(", "))
                        }
                        if (character.aliases.any { it.isNotBlank() }) {
                            DetailRow(label = "Aliases", value = character.aliases.filter { it.isNotBlank() }.joinToString(", "))
                        }
                        if (character.tvSeries.any { it.isNotBlank() }) {
                            DetailRow(label = "TV Series", value = character.tvSeries.filter { it.isNotBlank() }.joinToString(", "))
                        }
                        if (character.playedBy.any { it.isNotBlank() }) {
                            DetailRow(label = "Played By", value = character.playedBy.filter { it.isNotBlank() }.joinToString(", "))
                        }
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
        Text(
            text = value,
            fontSize = 16.sp
        )
    }
}
