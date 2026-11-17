package com.example.animalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.data.model.Environment
import com.example.animalsapp.ui.theme.*

@Composable
fun EnvironmentDetailScreen(
    environment: Environment?,
    animalsInEnvironment: List<Animal>,
    onAnimalClick: (String) -> Unit,
    onBack: () -> Unit
) {
    if (environment == null) {
        Box(Modifier.fillMaxSize().background(DarkBackground), contentAlignment = Alignment.Center) {
            Text("Ambiente no encontrado", color = TextWhite)
        }
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 40.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Barra superior
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(DarkSurface)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = TextWhite)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = environment.name,
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        // Imagen del ambiente
        item {
            AsyncImage(
                model = environment.image,
                contentDescription = environment.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }

        // Descripción en tarjeta CoralOrange
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(CoralOrange)
                    .padding(18.dp)
            ) {
                Text("Sobre este ecosistema", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(environment.description, color = TextDark, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }

        // Título de animales que habitan aquí
        item {
            Text(
                text = "Animales que habitan aquí (${animalsInEnvironment.size})",
                color = TextWhite,
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Lista de animales del ambiente (nombre e imagen principal)
        if (animalsInEnvironment.isEmpty()) {
            item {
                Text("No hay animales registrados en este ambiente.", color = TextGray, fontSize = 14.sp)
            }
        } else {
            items(animalsInEnvironment) { animal ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(DarkSurface)
                        .clickable { onAnimalClick(animal.id) }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = animal.image,
                        contentDescription = animal.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = animal.name,
                            color = TextWhite,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Toca para ver detalles",
                            color = NeonYellow,
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }
    }
}
