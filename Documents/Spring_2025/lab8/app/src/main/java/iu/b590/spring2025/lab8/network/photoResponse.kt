package iu.b590.spring2025.lab8.network
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class photoResponse(
    @Json(name = "photo") val galleryItems: List<GalleryItem>
)
