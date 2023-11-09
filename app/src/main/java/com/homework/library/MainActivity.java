package com.homework.library;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.homework.library.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private ActivityMainBinding binding;
    private BookListAdapter adapter;

    private ActionMode mActionMode;

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.contextual_toolbar_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.menu_remove) {
                viewModel.deleteItem();
                mode.finish(); // Action picked, so close the CAB
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        View view = binding.getRoot();
        setContentView(view);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        initViews();
        subscribeObserver();
        viewModel.updateBooks();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.updateBooks();
    }

    private void subscribeObserver() {
        viewModel.getBooksLiveData().observe(this, this::submitBookData);
    }

    private void initViews() {
        adapter = new BookListAdapter(new BookListAdapter.BookDiff());
        adapter.setOnItemClickListener(adapterItemListener);

        binding.bookRecycler.setAdapter(adapter);

        binding.toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) item -> {
            if (item.getItemId() == R.id.menu_add) {
                navigateAddBook();
            }
            return true;
        });
    }

    private void navigateAddBook() {
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }


    private void submitBookData(List<Book> books) {
        adapter.submitList(books);
    }

    private final BookListAdapter.OnItemClickListener adapterItemListener = new BookListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(int itemId) {
            navigateDetailActivity(itemId);
        }

        @Override
        public void onItemLongClick(int itemId) {
            activateRemoveMenuItem();
            viewModel.selectItem(itemId);
        }
    };

    private void navigateDetailActivity(int itemId) {
        Intent intent = new Intent(this, BookDetailActivity.class);
        intent.putExtra("book_id", itemId);
        startActivity(intent);
    }

    private void activateRemoveMenuItem() {
        mActionMode = startActionMode(mActionModeCallback);
        mActionMode.setTitle("1 selected item");
    }




}