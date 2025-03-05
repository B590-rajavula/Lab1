package iu.b590.spring2025.practicum20

import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import iu.b590.spring2025.practicum20.databinding.PostItemBinding
import iu.b590.spring2025.practicum20.model.Post

private const val TAG = "PostHolder"

class PostHolder (private val binding: PostItemBinding)
: RecyclerView.ViewHolder (binding.root) {
    fun bind(post: Post) {
        val username = post.user?.username ?: "Unknown User"
        binding.tvUsername.text = username
        binding.tvDescription.text = post.description
        binding.tvRelativeTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMs)

        if(post.imageUrl != "") {
            try {
//                binding.ivPost.load(post.imageUrl) {
//                    placeholder (R.drawable.flower1)
                binding.ivPost.load(post.imageUrl) {
                    crossfade(true)
                    placeholder(R.drawable.flower1)
                    error(R.drawable.flower1)

            }
            }
            catch (e: Exception) {
                Log.e(TAG, e.message?:"")
            }
        }
    }
}


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