package com.homework.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.homework.library.databinding.ActivityAddBookBinding;

import java.util.Objects;

public class AddBookActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    private ActivityAddBookBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBookBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();
        viewModel.updateBooks();
    }

    private void initViews() {
        binding.addButton.setOnClickListener(view -> {
            addBook();
        });
    }

    private void addBook() {
        try {
            String bookName = Objects.requireNonNull(binding.editText.getText()).toString();
            String authorName = Objects.requireNonNull(binding.editTextAuthor.getText()).toString();
            String description = Objects.requireNonNull(binding.editTextDescription.getText()).toString();
            String coverUrl = Objects.requireNonNull(binding.editTextCover.getText()).toString();

            if (bookName.isEmpty() || authorName.isEmpty() || description.isEmpty()) {
                throw new NullPointerException();
            }

            viewModel.addBook(bookName, authorName, description, coverUrl);
            finish();
        } catch (NullPointerException exception) {
            Toast.makeText(this, "PLEASE FILL ALL THE FIELDS!", Toast.LENGTH_SHORT).show();
        }
    }
}
