package com.example.desafioglobo.model

import com.google.gson.annotations.SerializedName
import java.sql.Blob

data class Artigos(
    @SerializedName("titulo")
    var titulo : String,
    @SerializedName("texto")
    var texto : String,
    @SerializedName("data")
    var data : String,
    @SerializedName("autor")
    var autor : String,
    @SerializedName("imagens")
    var imagens : List<String>
)