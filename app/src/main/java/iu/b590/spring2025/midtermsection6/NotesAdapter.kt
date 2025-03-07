package iu.b590.spring2025.midtermsection6


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iu.b590.spring2025.midtermsection6.databinding.NoteItemBinding
import iu.b590.spring2025.midtermsection6.model.Note


class NoteHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(note: Note, onNoteClicked: (Note) -> Unit, onDeleteClicked: (Note) -> Unit) {
        binding.noteTitleTextView.text = note.title

        // Set click listener for the entire item (tapping the title)
        binding.root.setOnClickListener {
            onNoteClicked(note)
        }

        binding.deleteButton.setOnClickListener {
            onDeleteClicked(note)
        }
    }
}

class NoteAdapter(
    private val notes: List<Note>,
    private val onNoteClicked: (Note) -> Unit, // Callback for note tap
    private val onDeleteClicked: (Note) -> Unit  // Callback for delete button tap
) : RecyclerView.Adapter<NoteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteHolder(binding)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]
        holder.bind(note, onNoteClicked, onDeleteClicked)
    }
}