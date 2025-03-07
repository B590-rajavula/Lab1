package iu.b590.spring2025.practicum20

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.imageLoader
import kotlinx.coroutines.withContext
import coil.load
import coil.request.ImageRequest
import iu.b590.spring2025.practicum20.databinding.PostItemBinding
import iu.b590.spring2025.practicum20.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

private const val TAG = "PostHolder"

class PostHolder (private val binding: PostItemBinding)
    : RecyclerView.ViewHolder (binding.root) {
    fun bind(post: Post) {
        val username = post.user?.username ?: "Unknown User"
        binding.tvUsername.text = username
        binding.tvDescription.text = post.description
        binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)

        if (post.imageUrl.isNotEmpty()) {
            // Extract file name from the URL
            fun getImageFileName(url: String): String {
                return url.substringAfterLast("/")
            }

            val fileName = getImageFileName(post.imageUrl)
            Log.d(TAG, "File Name: $fileName")

            val imageUrl = PhotoRepository.get().getImageUrl(fileName)
            Log.d(TAG, "Image URL: $imageUrl")

            // Load the image as a Bitmap and set it as the placeholder
            loadImageAsPlaceholder(imageUrl)

            // Use Coil to load the main image
            binding.ivPost.load(imageUrl) {
                crossfade(true)
                placeholder(R.drawable.flower1)
                error(R.drawable.flower1)
            }
        } else {
            binding.ivPost.setImageResource(R.drawable.flower)  // Fallback if image URL is empty
        }
    }

    private fun loadImageAsPlaceholder(imageUrl: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val bitmap = withContext(Dispatchers.IO) {
                    val inputStream = URL(imageUrl).openStream()
                    BitmapFactory.decodeStream(inputStream)
                }
                binding.ivPost.setImageBitmap(bitmap)  // Set the downloaded image as the placeholder
            } catch (e: Exception) {
                e.printStackTrace()
                binding.ivPost.setImageResource(R.drawable.flower1)  // Set default image if loading fails
            }
        }
    }
}



//        if(post.imageUrl != "") {
//            try {
////                binding.ivPost.load(post.imageUrl) {
////                    placeholder (R.drawable.flower1)
//                binding.ivPost.load(post.imageUrl) {
//                    crossfade(true)
//                    placeholder(R.drawable.flower1)
//                    error(R.drawable.flower1)
//
//            }
//
//                binding.ivPost.setImageBitmap(bitmap)
//            }
//            catch (e: Exception) {
//                Log.e(TAG, e.message?:"")
//            }
//        }
//    }
//}


class PostsAdapter(
    private val posts: List<Post>,
): RecyclerView.Adapter<PostHolder>() {
    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): PostHolder {
        val infaltor = LayoutInflater.from(parent.context)
        val binding = PostItemBinding.inflate(infaltor, parent, false)
        return PostHolder(binding)
    }
    override fun getItemCount(): Int {
        return posts.size
    }


    override fun onBindViewHolder (holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.bind(post)
    }
}