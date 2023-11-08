package com.example.myapplicationdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.myapplicationdemo.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.sql.Struct;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Uri emojiUri;
//    FirebaseStorage storageReference;
    StorageReference storageReference;
    DatabaseReference reference;
    StorageTask storageTask;
//    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storageReference= FirebaseStorage.getInstance().getReference("uploads");
                reference= FirebaseDatabase.getInstance().getReference("uploads");

                if(storageTask!= null && storageTask.isInProgress()){
                    Toast.makeText(MainActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();

                }else {
                    uploaddata();
                }




            }
        });
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectEmoji();
            }
        });



    }
    private String getFileExtension(Uri uri){
        ContentResolver contentResolver= getContentResolver();
        MimeTypeMap mime= MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(emojiUri));
    }

    private void uploaddata() {
        if(emojiUri!= null){
            StorageReference storageReference1= storageReference.child("uploads/"+System.currentTimeMillis()+"."+ getFileExtension(emojiUri));
            storageTask=storageReference1.putFile(emojiUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ModelClass modelClass = new ModelClass(binding.editText.getText().toString().trim(),taskSnapshot.toString());
                    String upload= reference.push().getKey();
                    reference.child(upload).setValue(modelClass);

                    binding.editText.setText("");


                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress=(100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());

                }
            });

        }else {
            Toast.makeText(this, "no file selected", Toast.LENGTH_SHORT).show();
        }
    }


    private void selectEmoji() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==100 &&data != null&&  data.getData()!=null){
            emojiUri= data.getData();
            binding.imageView.setImageURI(emojiUri);

        }
    }
}