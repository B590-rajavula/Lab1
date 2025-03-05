package iu.b590.spring2025.practicum20.network

import android.util.Xml.Encoding
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path


interface GitHubApi {
    @PUT("repos/{owner}/{repo}/contents/{path}")
    suspend fun uploadFile(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String,
        @Body body: GitHubFile
    ): Response<GitHubResponse>
    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getFileContent(
        @Header("Authorization") token: String,
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String
    ): Response<GitHubFileResponse>

}
data class GitHubFile(
    val message: String,
    val content: String,
    val branch: String = "main"
)
data class GitHubResponse(

    val content: Content
)

data class GitHubFileResponse(
    val content: String,
    val encoding: String,
    val download_url: String
)
data class Content (
val path: String,
val sha: String
)



