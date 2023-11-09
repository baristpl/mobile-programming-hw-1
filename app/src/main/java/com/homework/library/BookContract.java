package com.homework.library;

import android.provider.BaseColumns;

public class BookContract {
    private BookContract() {}

    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "books";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_TITLE = "authorName";
        public static final String COLUMN_COVER_IMAGE_URL = "cover_image_url";
        public static final String COLUMN_COVER_IMAGE_RES_ID = "cover_image_res_id";
        public static final String COLUMN_DESCRIPTION = "description";
    }

}
