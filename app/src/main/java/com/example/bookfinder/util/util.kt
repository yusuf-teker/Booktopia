package com.example.bookfinder.util

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.bookfinder.R
import com.example.bookfinder.data.model.remote.*


val categoryColors = listOf<Color>(
    Color(0xFF87CEEB), // Gökyüzü mavisi
    Color(0xFF0F52BA), // Kobalt mavisi
    Color(0xFF967BB6), // Lavanta mavisi
    Color(0xFFFF00FF), // Fuşya
    Color(0xFFFF0000), // Kırmızı
    Color(0xFFFFA500), // Turuncu
    Color(0xFFFFFF00), // Sarı
    Color(0xFF008000), // Yeşil
    Color(0xFF556B2F), // Haki yeşili
    Color(0xFF808080), // Gri
    Color(0xFF3E3E3E), //
    Color(0xFFA52A2A), // Kahverengi
    Color(0xFF000000), // Siyah
)

val categoryDrawableList = listOf(
    R.drawable.computer_and_technology,
    R.drawable.science_fiction,
    R.drawable.comic,
    R.drawable.child_books,
    R.drawable.religion_belief,
    R.drawable.education,
    R.drawable.adventure,
    R.drawable.business,
    R.drawable.fiction_literature,
    R.drawable.romantic,
    R.drawable.health_mind_body,
    R.drawable.art_entertainment,
    R.drawable.history,
)

fun createCategory(index: Int, categoryName: String, totalCategorySize: Int): Category {
    return Category(
        index,
        categoryName,
        categoryDrawableList[index],
        categoryColors[index],
        categoryQueries[index],
        totalCategorySize
    )
}

val categoryQueries = listOf<String>(
    "subject:Comic+books+strips+etc&langRestrict=tr",
    "subject:Bilim+subject:Kurgu+subject:Fantastik+subject:Bilim Kurgu ve Fantastik",
    "subject:Çizgi roman",
    "subject:Çocuk kitabı+subject:Çocuk Kitap",
    "subject:Din+subject:İnanç+subject:Din ve İnanç",
    "subject:Eğitim",
    "subject:Gizem+subject:Macera+subject:Gizem ve Macera",
    "subject:İş+subject:Yatırım+subject:İş ve Yatırım",
    "subject:Kurgu+subject:Edebiyat+subject:Kurgu ve Edebiyat",
    "subject:Romantik+subject:Romantizm",
    "subject:Sağlık+subject:Zihin+subject:Sağlık, Zihin ve Beden",
    "subject:Sanat+subject:Eğlence+subject:Sanat ve Eğlence",
    "subject:Tarih"
)

//Extension Function For SHADOW
fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 8.dp,
    blurRadius: Dp = 8.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 2f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    })