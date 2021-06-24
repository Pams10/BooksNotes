package it.pams.booksnotes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import static java.security.AccessController.getContext;

public class AdapterBookAerea extends FirestoreRecyclerAdapter<Book,AdapterBookAerea.BookViewHolder> {

 private Activity activity;
public AdapterBookAerea (@NonNull FirestoreRecyclerOptions<Book> options) {
        super(options);
        }

@Override
protected void onBindViewHolder(@NonNull BookViewHolder holder, int position, @NonNull Book model) {

        holder.mNomeLibro.setText(model.getName());
        holder.mAutore.setText(model.getAuthor());
        holder.mPrezzo.setText(model.getPrice());
        Glide.with(holder.mImgLibroView.getContext()).load(model.getPhoto()).into(holder.mImgLibroView);
        holder.mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSnapshots().getSnapshot(position).getReference().delete();
                Toast.makeText(activity ," Cancellazione Succes !",Toast.LENGTH_SHORT).show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {

        }
        });

        }
@NonNull
@Override
public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).
        inflate(R.layout.item_book_aerea,parent,false);
        activity = (FragmentActivity)(parent.getContext());
        return new BookViewHolder(layout);

        }

public class BookViewHolder extends RecyclerView.ViewHolder{
    TextView mAutore,mNomeLibro,mPrezzo;
    Button mbtnCancel;
    ImageView mImgLibroView;
    public BookViewHolder(@NonNull View itemView) {
        super(itemView);
        mNomeLibro= itemView.findViewById(R.id.itemLibroNomeAerea) ;
        mAutore = itemView.findViewById(R.id.itemLibroAutoreAerea)  ;
        mPrezzo = itemView.findViewById(R.id.itemLibroPrezzoAerea) ;
        mImgLibroView=itemView.findViewById(R.id.imgLibroAerea);
        mbtnCancel = itemView.findViewById(R.id.btnCancel);
    }
}

}
