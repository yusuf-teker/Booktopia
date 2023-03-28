package com.example.bookfinder.util

import android.util.Log
import androidx.compose.runtime.internal.liveLiteral
import androidx.compose.ui.graphics.Color
import com.example.bookfinder.R
import com.example.bookfinder.model.*
import com.example.bookfinder.screens.common.BookItem
val harryPotter = createVolumeInfo("https://m.media-amazon.com/images/I/51mFoFmu0EL._SX335_BO1,204,203,200_.jpg","Harry Potter and the Chamber of Secrets")
val mathBook = createVolumeInfo("http://books.google.com/books/content?id=kYYJLhL2arwC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api")
val mockItems =

    mutableListOf<Book>(
        Book("1","kind1", SearchInfo("searchInfo1"),null, mathBook, false),
        Book("2","kind2", SearchInfo("searchInfo2"),null, harryPotter,true),
        Book("3","kind3", SearchInfo("searchInfo3"),null, null),
        Book("4","kind4", SearchInfo("searchInfo4"),null, mathBook,true),
        Book("5","kind5", SearchInfo("searchInfo5"),null, harryPotter),
        Book("6","kind6", SearchInfo("searchInfo6"),null, harryPotter),
        Book("1","kind1", SearchInfo("searchInfo1"),null, harryPotter,false),
        Book("2","kind2", SearchInfo("searchInfo2"),null, mathBook,true),
        Book("3","kind3", SearchInfo("searchInfo3"),null, mathBook),
        Book("4","kind4", SearchInfo("searchInfo4"),null, harryPotter,true),
        Book("5","kind5", SearchInfo("searchInfo5"),null, mathBook),
        Book("6","kind6", SearchInfo("searchInfo6"),null, harryPotter),
    )

fun createVolumeInfo(link: String, title: String= "Tilte of the book"): VolumeInfo {
    return VolumeInfo(listOf("Name Surname","Name1 Surname1"),listOf("",""),"Mathematics plays a key role in computer science, some researchers would consider computers as nothing but the physical embodiment of mathematical systems. And whether you are designing a digital circuit, a computer program or a new programming language, you need mathematics to be able to reason about the design -- its correctness, robustness and dependability. This book covers the foundational mathematics necessary for courses in computer science. The common approach to presenting mathematical concepts and operators is to define them in terms of properties they satisfy, and then based on these definitions develop ways of computing the result of applying the operators and prove them correct. This book is mainly written for computer science students, so here the author takes a different approach: he starts by defining ways of calculating the results of applying the operators and then proves that they satisfy various properties. After justifying his underlying approach the author offers detailed chapters covering propositional logic, predicate calculus, sets, relations, discrete structures, structured types, numbers, and reasoning about programs. The book contains chapter and section summaries, detailed proofs and many end-of-section exercises -- key to the learning process. The book is suitable for undergraduate and graduate students, and although the treatment focuses on areas with frequent applications in computer science, the book is also suitable for students of mathematics and engineering.", ImageLinks("",link),"","",0,"","","","",title)
}
val colors2 = listOf(Color.Blue, Color.Cyan, Color.Green, Color.Yellow, Color.Magenta, Color.Red, Color.LightGray, Color.White)

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
val categoryNames = listOf<String>(
    "Bilgisayar ve Teknoloji",
    "Bilim Kurgu ve Fantastik",
    "Çizgi Romanlar",
    "Çocuk Kitaplar",
    "Din ve İnanç",
    "Eğitim",
    "Gizem ve Macera",
    "İş ve Yatırım",
    "Kurgu ve Edebiyat",
    "Romantik",
    "Sağlık, Zihin ve Beden",
    "Sanat ve Eğlence",
    "Tarih"
)
var categories
= listOf(
    Category(0, categoryNames[0], R.drawable.computer_and_technology, categoryColors[0]),
    Category(1, categoryNames[1], R.drawable.science_fiction, categoryColors[1]),
    Category(2, categoryNames[2], R.drawable.comic, categoryColors[2]),
    Category(3, categoryNames[3], R.drawable.child_books, categoryColors[3]),
    Category(4, categoryNames[4], R.drawable.religion_belief, categoryColors[4]),
    Category(5, categoryNames[5], R.drawable.education, categoryColors[5]),
    Category(6, categoryNames[6], R.drawable.adventure, categoryColors[6]),
    Category(7, categoryNames[7], R.drawable.business, categoryColors[7]),
    Category(8 ,categoryNames[8], R.drawable.fiction_literature, categoryColors[8]),
    Category(9, categoryNames[9], R.drawable.romantic, categoryColors[9]),
    Category(10, categoryNames[10], R.drawable.health_mind_body, categoryColors[10]),
    Category(11, categoryNames[11], R.drawable.art_entertainment, categoryColors[11]),
    Category(12, categoryNames[12], R.drawable.history, categoryColors[12]),
)