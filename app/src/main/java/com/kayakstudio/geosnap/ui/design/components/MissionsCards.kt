package com.kayakstudio.geosnap.ui.design.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object MissionsCards {
    @Composable
    fun Search(
        title: String,
        subtitle: String,
        price: String,
        date: String,
        duration: String,
        status: String,
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
//            elevation = 4,
            modifier =
            Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            Column(
                modifier =
                Modifier
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.surfaceContainerLowest),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFE0E0E0),
                        modifier = Modifier.size(40.dp),
                    ) {
                        // Add an image or icon here if needed
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                        )
                        Text(text = subtitle, fontSize = 14.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                )
                Text(text = "/Mo", fontSize = 12.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF0F0F0),
                        modifier = Modifier.padding(end = 8.dp),
                    ) {
                        Text(
                            text = date,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFF0F0F0),
                        modifier =
                        Modifier.padding(
                            end = 8.dp,
                        ),
                    ) {
                        Text(
                            text = duration,
                            fontSize = 14.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color(0xFFE1BEE7),
                        modifier =
                        Modifier.padding(
                            end = 8.dp,
                        ),
                    ) {
                        Text(
                            text = status,
                            fontSize = 14.sp,
                            color = Color(0xFF6200EA),
                            modifier = Modifier.padding(8.dp),
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun MissionCard(
        title: String,
        location: String,
        date: String,
        duration: String,
        status: String,
        salary: Int,
        confirmed: Boolean,
        onClick: () -> Unit,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth().clickable { onClick() },
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.surfaceContainerLowest),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier =
                        Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD1C4E9)),
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Text(text = location, fontSize = 14.sp)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$${salary / 1000}K/Mo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(text = date, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = duration, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = status,
                        color = if (confirmed) Color(0xFF4CAF50) else Color(0xFFFFC107),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}
