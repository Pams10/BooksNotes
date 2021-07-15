package it.pams.booksnotes;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;

public class PopUpBook extends Dialog {
    Book model;
    ImageView mpopBookImg , mImag;
    Button mContact;
    Communication com;
    Context c;

    TextView mpopBookName,mpopBookPrice,mpopBookEdition,mpopBookAuthor,mpopBookDescrip;
    public PopUpBook(@NonNull Context context) {
        super(context);
        this.c=context;
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
        // com= (Communication)getContext();
        mContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("setupContact","click com");
                ((MainActivity)c).contactOwner(model.getOwner());
                dismiss();
                cancel();
            }
        });
    }
    private void setupDialog() {
        mpopBookImg = findViewById(R.id.popBookImg);
        Glide.with(getContext()).load(Uri.parse(model.getPhoto())).into(mpopBookImg);
        mImag =findViewById(R.id.imageView3);
        mImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cancel();
            }
        });
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