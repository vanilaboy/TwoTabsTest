package org.bitart.twotabstest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class AnimalDetailsActivity extends AppCompatActivity {

    private static final String REQUIRED_ANIMAL = "required_animal";

    private ImageView mImageView;
    private TextView mTextViewNumber;
    private TextView mTextViewDescription;

    private Kot3Animal mAnimal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);

        mImageView = findViewById(R.id.imageViewDetails);
        mTextViewNumber = findViewById(R.id.textViewDetailsNumber);
        mTextViewDescription = findViewById(R.id.textViewDetailsDescription);

        mAnimal = getIntent().getParcelableExtra(REQUIRED_ANIMAL);
        if(mAnimal != null) {
            Picasso.get().load(mAnimal.getUrl()).into(mImageView);
            mTextViewNumber.setText(mAnimal.getTitle());
        }
        mTextViewDescription.setText(getString(R.string.some_text));
    }

    public static Intent newIntent(Context context, Kot3Animal animal) {
        Intent intent = new Intent(context, AnimalDetailsActivity.class);
        intent.putExtra(REQUIRED_ANIMAL, animal);
        return intent;
    }
}
