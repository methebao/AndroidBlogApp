package com.example.androidblogapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.LinearLayoutManager;

import com.example.androidblogapp.Adapters.CommentAdapter;
import com.example.androidblogapp.Helpers.DateHelper;
import com.example.androidblogapp.Models.Comment;
import com.example.androidblogapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PostDetailActivity extends AppCompatActivity {
    private ImageView singelImage;
    private RecyclerView commentRv;
    private EditText commentEdt;
    private TextView singleTitle, singleDesc;
    private TextView authorName, dateCreated;
    String post_key = null;
    private DatabaseReference mDatabase,mCommentDatabase,mUserDatabase;
    private Button deleteBtn;
    private Button addCommentBtn;
    private FirebaseAuth mAuth;
    List<Comment> listComment;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        singelImage = findViewById(R.id.singleImageview);
        commentRv = findViewById(R.id.commentRv);
        singleTitle = findViewById(R.id.singleTitle);
        singleDesc = findViewById(R.id.singleDesc);
        authorName = findViewById(R.id.authorName);
        dateCreated = findViewById(R.id.dateCreated);
        commentEdt = findViewById(R.id.commentEdt);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("posts");
        mCommentDatabase = FirebaseDatabase.getInstance().getReference().child("comments");
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        post_key = getIntent().getExtras().getString("postId");
        deleteBtn = findViewById(R.id.deleteBtn);
        addCommentBtn = findViewById(R.id.addCommentBtn);

        mAuth = FirebaseAuth.getInstance();
        deleteBtn.setVisibility(View.INVISIBLE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.child(post_key).removeValue();
                Intent mainIntent = new Intent(PostDetailActivity.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
        addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCommentBtn.setVisibility(View.INVISIBLE);
                DatabaseReference commentReference = mCommentDatabase.child(post_key).push();
                String comment_content = commentEdt.getText().toString();
                FirebaseUser user = mAuth.getCurrentUser();
                DatabaseReference userDb = mUserDatabase.child(user.getUid());
                String uid = user.getUid();
                String uname = user.getEmail();
                String uimg = user.getPhotoUrl().toString();

                Comment comment = new Comment(comment_content,uid,uimg,uname);


                commentReference.setValue(comment).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(PostDetailActivity.this, "Comment Added", Toast.LENGTH_LONG).show();
                        commentEdt.setText("");
                        addCommentBtn.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PostDetailActivity.this, "Faile to add comment" + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

        mDatabase.child(post_key).addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String post_title = (String) dataSnapshot.child("title").getValue();
                String post_desc = (String) dataSnapshot.child("desc").getValue();
                String post_image = (String) dataSnapshot.child("imageUrl").getValue();
                String post_uid = (String) dataSnapshot.child("uid").getValue();
                String post_authorName = (String) dataSnapshot.child("userName").getValue();
                long post_timeStamp = (long) dataSnapshot.child("timeStamp").getValue();
                singleTitle.setText(post_title);
                singleDesc.setText(post_desc);
                authorName.setText(post_authorName);

                String date = DateHelper.getInstance().timestampToString(post_timeStamp);
                dateCreated.setText(date);
                Picasso.with(PostDetailActivity.this).load(post_image).into(singelImage);
                if (mAuth.getCurrentUser().getUid().equals(post_uid)){
                    deleteBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        setupCommentRV();
    }
    private void setupCommentRV() {

        commentRv.setLayoutManager(new LinearLayoutManager(this));

        DatabaseReference commentRef = mCommentDatabase.child(post_key);
        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComment = new ArrayList<>();
                for (DataSnapshot snap:dataSnapshot.getChildren()) {

                    Comment comment = snap.getValue(Comment.class);
                    listComment.add(comment) ;

                }

                commentAdapter = new CommentAdapter(getApplicationContext(),listComment);
                commentRv.setAdapter(commentAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    };


}
