package org.bitart.twotabstest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalFragment extends Fragment {

    private final static String PARCELABLE_ARRAY = "animal_array";
    private final static String RECYCLER_POSITION = "scroll_position";

    private String mAnimalName;
    private ArrayList<Kot3Animal> mAnimalArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private AnimalAdapter mAnimalAdapter;

    public AnimalFragment(){
        super();
    }

    public AnimalFragment(String animalName) {
        super();
        mAnimalName = animalName;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARCELABLE_ARRAY, mAnimalArrayList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mAnimalArrayList = savedInstanceState.getParcelableArrayList(PARCELABLE_ARRAY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animal_fragment, container, false);

        if (mAnimalArrayList.size() == 0) {
            Singleton.getInstance(getContext()).getApi().getData(mAnimalName).enqueue(new Callback<Kot3Response>() {
                @Override
                public void onResponse(Call<Kot3Response> call, Response<Kot3Response> response) {
                    if (response.body() == null) {
                        Toast.makeText(getContext(), "Response null", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mAnimalArrayList.addAll(response.body().getData());
                    mAnimalAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<Kot3Response> call, Throwable t) {
                    try {
                        Log.d("Retrofit", t.getMessage());
                    } catch (NullPointerException ignored) {
                    }
                    Toast.makeText(getContext(), "Retrofit error", Toast.LENGTH_SHORT).show();
                }
            });
        }


        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAnimalAdapter = new AnimalAdapter();
        mRecyclerView.setAdapter(mAnimalAdapter);

        return view;
    }

    public class AnimalHolder extends RecyclerView.ViewHolder {

        private Kot3Animal mAnimal;

        private final ImageView mImageView;
        private final TextView mTextViewNumber;
        private final TextView mTextViewDescription;

        public AnimalHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.animal_item, parent, false));

            mImageView = this.itemView.findViewById(R.id.imageView);
            mTextViewNumber = this.itemView.findViewById(R.id.textViewNumber);
            mTextViewDescription = this.itemView.findViewById(R.id.textViewDescription);
            mTextViewDescription.setText(parent.getContext().getString(R.string.some_text));

            this.itemView.setOnClickListener(view -> {
                startActivity(AnimalDetailsActivity.newIntent(getContext(), mAnimal));
            });
        }

        public void bind(Kot3Animal animal){
            mAnimal = animal;
            mTextViewNumber.setText(animal.getTitle());
            Picasso.get().load(animal.getUrl()).into(mImageView);
        }

        public void recycle() {
            Picasso.get().cancelRequest(mImageView);
        }
    }

    public class AnimalAdapter extends RecyclerView.Adapter<AnimalHolder> {

        @NonNull
        @Override
        public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new AnimalHolder(inflater, parent);
        }

        @Override
        public void onViewRecycled(@NonNull AnimalHolder holder) {
            holder.recycle();
            super.onViewRecycled(holder);
        }

        @Override
        public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {
            holder.bind(mAnimalArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return mAnimalArrayList.size();
        }
    }
}
