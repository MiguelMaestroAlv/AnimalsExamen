package com.example.animalsapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.example.animalsapp.data.model.Environment
import com.example.animalsapp.ui.theme.*
import com.example.animalsapp.ui.viewmodel.AnimalsUiState

@Composable
fun EnvironmentsListScreen(
    uiState: AnimalsUiState,
    onEnvironmentClick: (String) -> Unit
) {
    val cardColors = listOf(PastelLilac, CoralOrange, NeonYellow)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text("Ambientes", color = TextWhite, fontSize = 26.sp, fontWeight = FontWeight.ExtraBold)
        Text("Ecosistemas y hábitats naturales", color = TextGray, fontSize = 14.sp)
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = PastelLilac)
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(14.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                itemsIndexed(uiState.environments) { index, env ->
                    EnvironmentCardItem(
                        environment = env,
                        backgroundColor = cardColors[index % cardColors.size],
                        onClick = { onEnvironmentClick(env.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun EnvironmentCardItem(
    environment: Environment,
    backgroundColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = environment.name,
                color = TextDark,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(DarkPill)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text("Explorar", color = TextWhite, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        AsyncImage(
            model = environment.image,
            contentDescription = environment.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(18.dp))
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = environment.description,
            color = TextDark.copy(alpha = 0.85f),
            fontSize = 13.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}