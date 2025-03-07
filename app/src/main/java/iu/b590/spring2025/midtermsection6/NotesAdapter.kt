package iu.b590.spring2025.midtermsection6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import iu.b590.spring2025.midtermsection6.databinding.NoteItemBinding
import iu.b590.spring2025.midtermsection6.model.Note

// ViewHolder class to hold a reference to the individual note item view
class NoteHolder(private val binding: NoteItemBinding) : RecyclerView.ViewHolder(binding.root) {

    // Bind the note data to the views and set the click listeners
    fun bind(note: Note, onNoteClicked: (Note) -> Unit, onDeleteClicked: (Note) -> Unit) {
        // Set the title of the note in the TextView
        binding.noteTitleTextView.text = note.title

        // Set a click listener for the entire note item (tapping the title)
        binding.root.setOnClickListener {
            onNoteClicked(note)  // Invoke the callback when the note is tapped
        }

        // Set a click listener for the delete button (to delete the note)
        binding.deleteButton.setOnClickListener {
            onDeleteClicked(note)  // Invoke the callback when the delete button is tapped
        }
    }
}

// Adapter class to bind the list of notes to the RecyclerView
class NoteAdapter(
    private val notes: List<Note>,  // List of notes to display
    private val onNoteClicked: (Note) -> Unit,  // Callback for note item tap
    private val onDeleteClicked: (Note) -> Unit  // Callback for delete button tap
) : RecyclerView.Adapter<NoteHolder>() {

    // Create a new ViewHolder for the note item view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        // Inflate the layout for each note item
        val inflater = LayoutInflater.from(parent.context)
        val binding = NoteItemBinding.inflate(inflater, parent, false)
        return NoteHolder(binding)  // Return the created ViewHolder
    }

    // Return the number of items in the list
    override fun getItemCount(): Int {
        return notes.size
    }

    // Bind the data for a specific position in the list to the ViewHolder
    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        val note = notes[position]  // Get the note at the current position
        holder.bind(note, onNoteClicked, onDeleteClicked)  // Bind the note data and set click listeners
    }
}
