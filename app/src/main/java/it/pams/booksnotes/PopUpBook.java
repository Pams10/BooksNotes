package it.pams.booksnotes;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

public class PopUpBook extends Dialog {
     Book model;
     ImageView mpopBookImg;
     Button mContact;
     TextView mpopBookName,mpopBookPrice,mpopBookEdition,mpopBookAuthor,mpopBookDescrip;
    public PopUpBook(@NonNull Context context) {
        super(context);
    }
    public Book getBook(Book book){
        model = book;
        return model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pop_up_book);
        setupDialog();
        setupContact();

    }

    private void setupContact() {
        mContact = findViewById(R.id.bookContact);
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void setupDialog() {
        mpopBookImg = findViewById(R.id.popBookImg);
        Glide.with(getContext()).load(Uri.parse(model.getPhoto())).into(mpopBookImg);
        mpopBookName = findViewById(R.id.popBookName);
        mpopBookName.setText(model.getName());

        mpopBookPrice = findViewById(R.id.popBookPriceVal);
        mpopBookPrice.setText(model.getPrice());

        mpopBookEdition = findViewById(R.id.popEditionVal);
        mpopBookEdition.setText(model.getEdition());

        mpopBookAuthor = findViewById(R.id.popBookAuthorVal);
        mpopBookAuthor.setText(model.getAuthor());

        mpopBookDescrip = findViewById(R.id.popBookDescripVal);
        mpopBookDescrip.setText(model.getDescription());
    }


}