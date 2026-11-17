package com.example.animalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.animalsapp.ui.theme.*

@Composable
fun AnimalDetailScreen(
    animal: Animal?,
    onBack: () -> Unit
) {
    if (animal == null) {
        Box(Modifier.fillMaxSize().background(DarkBackground), contentAlignment = Alignment.Center) {
            Text("Animal no encontrado", color = TextWhite)
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
        // Barra superior con botón regresar
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
                    text = animal.name,
                    color = TextWhite,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        // Imagen principal
        item {
            AsyncImage(
                model = animal.image,
                contentDescription = animal.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .clip(RoundedCornerShape(24.dp))
            )
        }

        // Tarjeta de Descripción (estilo NeonYellow)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(NeonYellow)
                    .padding(18.dp)
            ) {
                Text("Descripción", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(6.dp))
                Text(animal.description, color = TextDark, fontSize = 14.sp, lineHeight = 20.sp)
            }
        }

        // Tarjeta de Hechos Interesantes (estilo PastelLilac)
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(PastelLilac)
                    .padding(18.dp)
            ) {
                Text("Hechos Interesantes", color = TextDark, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
                Spacer(modifier = Modifier.height(10.dp))
                animal.facts.forEach { fact ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(top = 6.dp)
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(DarkPill)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(fact, color = TextDark, fontSize = 14.sp, lineHeight = 19.sp)
                    }
                }
            }
        }

        // Galería de Imágenes (Carrusel horizontal)
        if (animal.imageGallery.isNotEmpty()) {
            item {
                Column {
                    Text(
                        text = "Galería de Imágenes",
                        color = TextWhite,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(animal.imageGallery) { photoUrl ->
                            AsyncImage(
                                model = photoUrl,
                                contentDescription = "Galería de ${animal.name}",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .width(220.dp)
                                    .height(150.dp)
                                    .clip(RoundedCornerShape(20.dp))
                            )
                        }
                    }
                }
            }
        }
    }
}