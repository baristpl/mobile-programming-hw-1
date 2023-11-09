package com.homework.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookRepository {
    private DatabaseHelper dbHelper;

    public BookRepository(DatabaseHelper databaseHelper) {
        dbHelper = databaseHelper;
    }

    public long insertBook(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_NAME, book.getName());
        values.put(BookContract.BookEntry.COLUMN_TITLE, book.getAuthorName());
        values.put(BookContract.BookEntry.COLUMN_COVER_IMAGE_URL, book.getCoverImageUrl());
        values.put(BookContract.BookEntry.COLUMN_COVER_IMAGE_RES_ID, book.getCoverImageResId());
        values.put(BookContract.BookEntry.COLUMN_DESCRIPTION, book.getDescription());

        long id = db.insert(BookContract.BookEntry.TABLE_NAME, null, values);
        db.close();
        return id;
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME,
                BookContract.BookEntry.COLUMN_TITLE,
                BookContract.BookEntry.COLUMN_COVER_IMAGE_URL,
                BookContract.BookEntry.COLUMN_COVER_IMAGE_RES_ID,
                BookContract.BookEntry.COLUMN_DESCRIPTION
        };
        Cursor cursor = db.query(BookContract.BookEntry.TABLE_NAME, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Book book = new Book(
                        cursor.getLong(cursor.getColumnIndex(BookContract.BookEntry._ID)),
                        cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_COVER_IMAGE_URL)),
                        cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_COVER_IMAGE_RES_ID)),
                        cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_DESCRIPTION))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    public Book getBookById(long bookId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                BookContract.BookEntry._ID,
                BookContract.BookEntry.COLUMN_NAME,
                BookContract.BookEntry.COLUMN_TITLE,
                BookContract.BookEntry.COLUMN_COVER_IMAGE_URL,
                BookContract.BookEntry.COLUMN_COVER_IMAGE_RES_ID,
                BookContract.BookEntry.COLUMN_DESCRIPTION
        };
        String selection = BookContract.BookEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};

        Cursor cursor = db.query(
                BookContract.BookEntry.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        Book book = null;
        if (cursor.moveToFirst()) {
            book = new Book(
                    cursor.getLong(cursor.getColumnIndex(BookContract.BookEntry._ID)),
                    cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_COVER_IMAGE_URL)),
                    cursor.getInt(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_COVER_IMAGE_RES_ID)),
                    cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_DESCRIPTION))
            );
        }
        cursor.close();
        db.close();
        return book;
    }

    public int deleteBook(long bookId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = BookContract.BookEntry._ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};

        int deletedRows = db.delete(BookContract.BookEntry.TABLE_NAME, selection, selectionArgs);
        db.close();
        return deletedRows;
    }

    public void fillDummy() {
        List<Book> books = Arrays.asList(new Book(
                        1,
                        "To Kill a Mockingbird",
                        "Harper Lee",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4f/To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg/220px-To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg",
                        R.drawable.mockingbird_cover,
                        "To Kill a Mockingbird is a novel by the American author Harper Lee. It was published in 1960 and was instantly successful. In the United States, it is widely read in high schools and middle schools. To Kill a Mockingbird has become a classic of modern American literature; a year after its release, it won the Pulitzer Prize. The plot and characters are loosely based on Lee's observations of her family, her neighbors and an event that occurred near her hometown of Monroeville, Alabama, in 1936, when she was ten."
                ),
                new Book(
                        2,
                        "The Great Gatsby",
                        "F. Scott Fitzgerald",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/The_Great_Gatsby_Cover_1925_Retouched.jpg/220px-The_Great_Gatsby_Cover_1925_Retouched.jpg",
                        R.drawable.gatsby_cover,
                        "The Great Gatsby is a 1925 novel by American writer F. Scott Fitzgerald. Set in the Jazz Age on Long Island, near New York City, the novel depicts first-person narrator Nick Carraway's interactions with mysterious millionaire Jay Gatsby and Gatsby's obsession to reunite with his former lover, Daisy Buchanan."
                ),
                new Book(
                        3,
                        "Fahrenheit 451",
                        "\tRay Bradbury",
                        "https://upload.wikimedia.org/wikipedia/en/thumb/d/db/Fahrenheit_451_1st_ed_cover.jpg/220px-Fahrenheit_451_1st_ed_cover.jpg",
                        R.drawable.fahrenheit_cover,
                        "Fahrenheit 451 is a 1953 dystopian novel by American writer Ray Bradbury.[4] It presents an American society where books have been personified and outlawed and \"firemen\" burn any that are found.[5] The novel follows in the viewpoint of Guy Montag, a fireman who soon becomes disillusioned with his role of censoring literature and destroying knowledge, eventually quitting his job and committing himself to the preservation of literary and cultural writings."
                ), new Book(
                        4,
                        "The Book Thief",
                        "Markus Zusak",
                        "https://upload.wikimedia.org/wikipedia/en/thumb/8/8f/The_Book_Thief_by_Markus_Zusak_book_cover.jpg/220px-The_Book_Thief_by_Markus_Zusak_book_cover.jpg",
                        R.drawable.book_thief_cover,
                        "The Book Thief is a historical fiction novel by the Australian author Markus Zusak, set in Nazi Germany during World War II. Published in 2006, The Book Thief became an international bestseller and was translated into 63 languages and sold 17 million copies. It was adapted into the 2013 feature film, The Book Thief."
                ), new Book(
                        5,
                        "1984",
                        "George Orwell",
                        "https://upload.wikimedia.org/wikipedia/en/5/51/1984_first_edition_cover.jpg",
                        R.drawable._984_cover,
                        "Nineteen Eighty-Four (also published as 1984) is a dystopian novel and cautionary tale by English writer George Orwell. It was published on 8 June 1949 by Secker & Warburg as Orwell's ninth and final book completed in his lifetime. Thematically, it centres on the consequences of totalitarianism, mass surveillance and repressive regimentation of people and behaviours within society.[2][3] Orwell, a democratic socialist, modelled the authoritarian state in the novel on the Soviet Union in the era of Stalinism, and Nazi Germany.[4] More broadly, the novel examines the role of truth and facts within societies and the ways in which they can be manipulated."
                ), new Book(
                        6,
                        "The Lord of the Rings",
                        "J. R. R. Tolkien",
                        "https://upload.wikimedia.org/wikipedia/en/thumb/e/e9/First_Single_Volume_Edition_of_The_Lord_of_the_Rings.gif/220px-First_Single_Volume_Edition_of_The_Lord_of_the_Rings.gif",
                        R.drawable.lotr_cover,
                        "The Lord of the Rings is an epic[1] high-fantasy novel[a] by the English author and scholar J. R. R. Tolkien. Set in Middle-earth, the story began as a sequel to Tolkien's 1937 children's book The Hobbit, but eventually developed into a much larger work. "
                ), new Book(
                        7,
                        "A Tale of Two Cities",
                        "Charles Dickens",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/3/3c/Tales_serial.jpg/220px-Tales_serial.jpg",
                        R.drawable.tale_of_two_cities_cover,
                        "A Tale of Two Cities is a historical novel published in 1859 by Charles Dickens, set in London and Paris before and during the French Revolution. The novel tells the story of the French Doctor Manette, his 18-year-long imprisonment in the Bastille in Paris, and his release to live in London with his daughter Lucie whom he had never met. The story is set against the conditions that led up to the French Revolution and the Reign of Terror."
                ), new Book(
                        8,
                        "The Alchemist",
                        "Paulo Coelho",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c4/TheAlchemist.jpg/220px-TheAlchemist.jpg",
                        R.drawable.the_alchemist_cover,
                        "The Alchemist (Portuguese: O Alquimista) is a novel by Brazilian author Paulo Coelho which was first published in 1988. Originally written in Portuguese, it became a widely translated international bestseller.[1][2] An allegorical novel, The Alchemist follows a young Andalusian shepherd in his journey to the pyramids of Egypt, after having a recurring dream of finding a treasure there."
                ), new Book(
                        9,
                        "Heidi",
                        "Johanna Spyri",
                        "https://upload.wikimedia.org/wikipedia/commons/thumb/1/1a/Spyri_Heidi_Cover_1887.jpg/220px-Spyri_Heidi_Cover_1887.jpg",
                        R.drawable.heidi_cover,
                        "Heidi (/ˈhaɪdi/; German: [ˈhaɪdi]) is a work of children's fiction published between 1880 and 1881 by Swiss author Johanna Spyri, originally published in two parts as Heidi: Her Years of Wandering and Learning[1] (German: Heidis Lehr- und Wanderjahre) and Heidi: How She Used What She Learned[2] (German: Heidi kann brauchen, was es gelernt hat).[3] It is a novel about the events in the life of a 5-year-old girl in her paternal grandfather's care in the Swiss Alps. It was written as a book \"for children and those who love children\" (as quoted from its subtitle)."
                ), new Book(
                        10,
                        "Charlotte's Web",
                        "E. B. White",
                        "https://upload.wikimedia.org/wikipedia/en/thumb/f/fe/CharlotteWeb.png/220px-CharlotteWeb.png",
                        R.drawable.charlotte_web_cover,
                        "Charlotte's Web is a book of children's literature by American author E. B. White and illustrated by Garth Williams; it was published on October 15, 1952, by Harper & Brothers. The novel tells the story of a livestock pig named Wilbur and his friendship with a barn spider named Charlotte. When Wilbur is in danger of being slaughtered by the farmer, Charlotte writes messages in her web praising Wilbur, such as \"Some Pig\" and \"Humble\", to persuade the farmer to let him live."
                )
        );

        for (Book book : books) {
            insertBook(book);
        }
    }
}
