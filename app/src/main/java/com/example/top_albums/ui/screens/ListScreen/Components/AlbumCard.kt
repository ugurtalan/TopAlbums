package com.example.top_albums.ui.screens.ListScreen.Components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.top_albums.domain.model.MyAlbum

@Composable
fun AlbumCard(album: MyAlbum,sequence:Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),

        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,

        ) {




                   AsyncImage(
                       model = album.imageUrl,
                       contentDescription = "",
                       modifier = Modifier
                           .size(80.dp)
                           .clip(RoundedCornerShape(8.dp)),

                       )

                   Column(
                       modifier = Modifier.padding(start = 16.dp).weight(1f)
                   ) {
                       Text(
                           text = album.name,
                           style = MaterialTheme.typography.titleMedium ,
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )
                       Text(
                           text = album.artist,
                           style = MaterialTheme.typography.bodyMedium,
                           color = MaterialTheme.colorScheme.onSurfaceVariant,
                           maxLines = 1,
                           overflow = TextOverflow.Ellipsis
                       )
                   }

            Spacer(modifier = Modifier.weight(1f))


            Text(
                text = sequence.toString(),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp).fillMaxHeight()
                    .wrapContentHeight(Alignment.CenterVertically).width(24.dp)
            )


        }
    }
}