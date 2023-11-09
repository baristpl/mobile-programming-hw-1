package com.homework.library;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<List<Book>> booksLiveData;

    public LiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }

    private DatabaseHelper databaseHelper;
    private BookRepository bookRepository;

    private int selectedItemId;

    public MainViewModel(@NonNull Application application) {
        super(application);

        databaseHelper = new DatabaseHelper(application);
        bookRepository = new BookRepository(databaseHelper);

        booksLiveData = new MutableLiveData<>();
    }

    public void selectItem(int itemId) {
        selectedItemId = itemId;
    }

    public void deleteItem() {
        if (selectedItemId != 0) {
            deleteItemFromDb();
            updateBooks();
        }
    }

    public void updateBooks() {
        List<Book> books = bookRepository.getAllBooks();

        if (books.isEmpty()) {
            bookRepository.fillDummy();
            updateBooks();
        }

        booksLiveData.setValue(books);
    }

    private void deleteItemFromDb() {
        bookRepository.deleteBook(selectedItemId);
    }


    public Book getBook(int bookId) {
        return bookRepository.getBookById(bookId);
    }

    public void addBook(String name, String authorName, String description, String coverUrl) {
        Book book = new Book(0, name, authorName, coverUrl, -1, description);
        bookRepository.insertBook(book);
    }
}
