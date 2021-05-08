package it.pams.booksnotes;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.ArrayList;

public class AdapterBook extends FirestoreRecyclerAdapter<Book,AdapterBook.BookViewHolder> {
    private Context context1 ;

    public AdapterBook(@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {

        holder.mNomeLibro.setText(model.getName());
        holder.mAutore.setText(model.getAuthor());
        holder.mPrezzo.setText(model.getPrice());
        Glide.with(holder.mImgLibroView.getContext()).load(model.getPhoto()).into(holder.mImgLibroView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               PopUpBook popUpBook = new PopUpBook(context1);
                popUpBook.getBook(model);
                popUpBook.show();
            }
        });

    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             context1=parent.getContext();
        View layout = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_book,parent,false);
        return new BookViewHolder(layout);

    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView mAutore,mNomeLibro,mPrezzo;

        ImageView mImgLibroView;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            mNomeLibro= itemView.findViewById(R.id.itemLibroNome) ;
            mAutore = itemView.findViewById(R.id.itemLibroAutore)  ;
            mPrezzo = itemView.findViewById(R.id.itemLibroPrezzo) ;
            mImgLibroView=itemView.findViewById(R.id.imgLibroView);
        }
    }

}
