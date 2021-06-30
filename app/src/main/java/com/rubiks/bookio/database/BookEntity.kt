package com.rubiks.bookio.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")//This is called Anotation it tell compiler what we are creating
data class BookEntity(
    @PrimaryKey val book_id: Int,
    @ColumnInfo(name="book_name") val bookName: String,
    @ColumnInfo(name="book_author")val bookAuthor: String,
    @ColumnInfo(name="book_rating")val bookRating: String,
    @ColumnInfo(name="book_price")val bookPrice: String,
    @ColumnInfo(name="book_img")val bookImage: String
)