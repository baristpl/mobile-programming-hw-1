package com.homework.library;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.homework.library.databinding.ActivityBookDetailBinding;

public class BookDetailActivity extends AppCompatActivity {
    int bookId;
    private MainViewModel viewModel;

    private ActivityBookDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookDetailBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Intent intent = getIntent();
        bookId = intent.getIntExtra("book_id", -1);


        initViews();
        viewModel.updateBooks();
    }

    private void initViews() {
        Book book = viewModel.getBook(bookId);

        binding.authorName.setText(book.getAuthorName());
        binding.bookName.setText(book.getName());
        binding.bookDescription.setText(book.getDescription());

        if (book.getCoverImageResId() != -1) {
            Glide.with(this)
                    .load(book.getCoverImageResId())
                    .centerCrop()
                    .into(binding.bookCover);
        } else {
            Glide.with(this)
                    .load(book.getCoverImageUrl())
                    .centerCrop()
                    .into(binding.bookCover);
        }


    }
}
