package iu.b590.spring2025.practicum20

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import iu.b590.spring2025.practicum20.network.GitHubApi
import iu.b590.spring2025.practicum20.network.GitHubFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "PhotoRepository"
class PhotoRepository private constructor(
    context: Context?,
    private val coroutineScope: CoroutineScope = GlobalScope
) {

    private val githubApi: GitHubApi
    // Replace with your new personal access token with repo scope.
    private val token = "Bearer rajesh"
    private val owner = "B590-rajavula"           // Your GitHub username/organization
    private val repo = "practicum20-photostore"     // Your private photo store repo
    private val branch = "main"                     // Update branch if your private repo uses "main"

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        githubApi = retrofit.create(GitHubApi::class.java)
    }

    suspend fun saveImage(base64Image: String, filename: String) =
        withContext(Dispatchers.IO) {
            uploadImageToGitHub(base64Image, filename)
        }

    companion object {
        private var INSTANCE: PhotoRepository? = null
        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = PhotoRepository(context)
            }
        }

        fun get(): PhotoRepository {
            if (INSTANCE == null) {
                INSTANCE = PhotoRepository(null)
            }
            return INSTANCE!!
        }
    }

    private suspend fun uploadImageToGitHub(base64Image: String, filename: String) {
        val path = "$filename"
        val file = GitHubFile(
            message = "Add $filename",
            content = base64Image,
            branch = branch // Ensure we target the correct branch
        )
        try {
            val response = githubApi.uploadFile(token, owner, repo, path, file)
            if (response.isSuccessful) {
                Log.d(TAG, "File uploaded successfully: ${response.body()?.content?.path}")
            } else {
                Log.e(TAG, "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    // Returns the URL to access the image from GitHub
    fun getImageUrl(fileName: String): String {
        return "https://raw.githubusercontent.com/${owner}/${repo}/${branch}/${fileName}"
    }





    // Fetch the file from GitHub, decode the base64 content, and return a Bitmap.
    suspend fun fetchAndDecodeImage(filename: String): Bitmap? {
        val path = "$filename"
        try {
            val response = githubApi.getFileContent(token, owner, repo, path)
            if (response.isSuccessful) {
                val fileResponse = response.body()
                if (fileResponse?.encoding == "base64") {
                    val decodedBytes = Base64.decode(fileResponse.content, Base64.DEFAULT)
                    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                }
            } else {
                Log.e("GitHub", "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}
