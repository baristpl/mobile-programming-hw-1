package com.homework.library;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class BookListAdapter extends ListAdapter<Book, BookListAdapter.BookViewHolder> {

    private OnItemClickListener onItemClickListener;

    protected BookListAdapter(@NonNull DiffUtil.ItemCallback<Book> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = getItem(position);
        holder.bind(book, onItemClickListener);
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView authorNameTextView;
        private final ImageView coverImageView;

        public BookViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.book_name);
            authorNameTextView = itemView.findViewById(R.id.author_name);
            coverImageView = itemView.findViewById(R.id.book_cover);
        }

        public void bind(Book book, final OnItemClickListener listener) {
            nameTextView.setText(book.getName());
            authorNameTextView.setText(book.getAuthorName());
            int bookId = (int) book.getId();

            itemView.setOnClickListener(view -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemClick(bookId);
                }
            });

            itemView.setOnLongClickListener(view -> {
                if (listener != null && getAdapterPosition() != RecyclerView.NO_POSITION) {
                    listener.onItemLongClick(bookId);
                    view.setSelected(true);
                    return true;
                }
                return false;
            });

            if (book.getCoverImageResId() != -1) {
                Glide.with(coverImageView.getContext())
                        .load(book.getCoverImageResId())
                        .centerCrop()
                        .into(coverImageView);
            } else {
                Glide.with(coverImageView.getContext())
                        .load(book.getCoverImageUrl())
                        .centerCrop()
                        .into(coverImageView);
            }
        }
    }

    static class BookDiff extends DiffUtil.ItemCallback<Book> {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int itemId);
        void onItemLongClick(int itemId);
    }



    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}