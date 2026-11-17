package com.example.animalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.ui.theme.*
import com.example.animalsapp.ui.viewmodel.AnimalsUiState

@Composable
fun AnimalsListScreen(
    uiState: AnimalsUiState,
    onAnimalClick: (String) -> Unit,
    onRetry: () -> Unit
) {
    val cardColors = listOf(NeonYellow, PastelLilac, CoralOrange)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(NeonYellow),
                contentAlignment = Alignment.Center
            ) {
                Text("🐾", fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Explora Fauna", color = TextWhite, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("${uiState.animals.size} especies disponibles", color = TextGray, fontSize = 13.sp)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text("Animales", color = TextWhite, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(14.dp))

        when {
            uiState.isLoading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = NeonYellow)
                }
            }
            uiState.errorMessage != null -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(uiState.errorMessage, color = CoralOrange)
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onRetry,
                        colors = ButtonDefaults.buttonColors(containerColor = NeonYellow)
                    ) {
                        Text("Reintentar", color = TextDark, fontWeight = FontWeight.Bold)
                    }
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    itemsIndexed(uiState.animals) { index, animal ->
                        AnimalCardItem(
                            animal = animal,
                            backgroundColor = cardColors[index % cardColors.size],
                            onClick = { onAnimalClick(animal.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimalCardItem(
    animal: Animal,
    backgroundColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(14.dp)
    ) {
        Text(
            text = animal.name,
            color = TextDark,
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.height(10.dp))
        AsyncImage(
            model = animal.image,
            contentDescription = animal.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .clip(RoundedCornerShape(18.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(DarkPill)
                .padding(horizontal = 12.dp, vertical = 5.dp)
        ) {
            Text("Ver ficha →", color = TextWhite, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}