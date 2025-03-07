package iu.b590.spring2025.midtermsection6
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import iu.b590.spring2025.midtermsection6.databinding.NoteItemBinding
import iu.b590.spring2025.midtermsection6.model.Notes


class NoteHolder(
    private val binding: NoteItemBinding,
    private val onDelete: (Int) -> Unit,
    private val navController: NavController
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(notes: Notes, position: Int) {
        binding.tvTitle.text = notes.Title
        binding.tvTitle.setOnClickListener{
            navController.navigate(R.id.navigate_to_EditFragment)
        }
        binding.ivDelete.setOnClickListener {
            WarningDialog {
                onDelete(position) // Call delete function on confirmation
            }.show((binding.root.context as AppCompatActivity).supportFragmentManager, "deleteDialog")
        }
    }
}

class NotesAdapter(
    private var notesList: MutableList<Notes>,
    private val navController: NavController
) : RecyclerView.Adapter<NoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteHolder(binding, { position -> deleteNote(position) }, navController)
    }

    override fun getItemCount(): Int = notesList.size

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(notesList[position], position)
    }

    fun updateNotes(newNotes: List<Notes>) {
        notesList.clear()
        notesList.addAll(newNotes)
        notifyDataSetChanged() // Ensure UI refreshes
    }

    private fun deleteNote(position: Int) {
        if (position in notesList.indices) {
            notesList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, notesList.size)
        }
    }
}


