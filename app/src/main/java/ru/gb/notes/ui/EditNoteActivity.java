package ru.gb.notes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ru.gb.notes.R;
import ru.gb.notes.data.Constants;
import ru.gb.notes.data.InMemoryRepoImpl;
import ru.gb.notes.data.Note;
import ru.gb.notes.data.Repo;

public class EditNoteActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private Button saveNote;
    private int id = -1;
    private Note note;
    private Repo repository = InMemoryRepoImpl.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        title = findViewById(R.id.edit_note_title);
        description = findViewById(R.id.edit_note_description);
        saveNote = findViewById(R.id.edit_note_update);

        Intent intent = getIntent();
        if (intent != null) {
            Note note = (Note) intent.getSerializableExtra(Constants.NOTE);
            id = note.getId();
            title.setText(note.getTitle());
            description.setText(note.getDescription());

            saveNote.setOnClickListener(v -> {
                String updateTitle = title.getText().toString();
                String updateDescription = description.getText().toString();
                Intent intent1 = new Intent(this, NotesListActivity.class);
                note.setTitle(updateTitle);
                note.setDescription(updateDescription);
                if (note.getId() == null) {
                    repository.create(note);
                } else {
                    repository.update(note);
                }
                intent1.putExtra(Constants.NOTE, note);
                onBackPressed();
                finish();

            });
        }
    }
}