package com.example.test.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "food")
public class FoodDataClass
{
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "imageUrl")
    public String imageUrl;

    public FoodDataClass(@NotNull String id,@NotNull String title, @NotNull String imageUrl)
    {
        this.title = title;
        this.imageUrl = imageUrl;
        this.id = id;
    }
}
