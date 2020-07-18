package com.techtitudetribe.aadharshila;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Fragments.HomeFragment;

public class BookActivity extends AppCompatActivity {

    private RecyclerView pdfListView;
    private DatabaseReference pdfRef,stuRef;
    private String status,currentUser,currentClass;
    private RelativeLayout mssgLayout;
    private FirebaseAuth mAuth;
    private RelativeLayout noInternetLayout;
    private RelativeLayout mToolbar;
    private SearchView bookSearch;
    private SwipeRefreshLayout refreshLayout;
    private String standard;
    private TextView eBookTitle;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_book);


        bookSearch = findViewById(R.id.search_view_book);
        eBookTitle = findViewById(R.id.e_book_title);
        progressBar=  findViewById(R.id.pdf_progress_bar);

        refreshLayout = findViewById(R.id.refresh_pdf);
        refreshLayout.setColorScheme(R.color.creative_red, R.color.creative_sky_blue, R.color.creative_green,R.color.creative_yellow,R.color.creative_violet);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshing(false);
                    }
                }, 5000);
            }
        });


        noInternetLayout = findViewById(R.id.id_no_internet_layout);
        mToolbar =  findViewById(R.id.back_ebook);
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        if(bookSearch!=null)
        {
            bookSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    return true;
                }
            });
        }

        mAuth=FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();

        stuRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser);
        stuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    status = dataSnapshot.child("Subscription").getValue().toString();
                    //currentClass = dataSnapshot.child("Class").getValue().toString();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        currentClass=getIntent().getExtras().getString("class");
        switch (currentClass)
        {
                    case "1": pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 1");
                        break;
                    case "2" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 2");
                        break;
                    case "3" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 3");
                        break;
                    case "4" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 4");
                        break;
                    case "5" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 5");
                        break;
                    case "6" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 6");
                        break;
                    case "7" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 7");
                        break;
                    case "8" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 8");
                        break;
                    case "9" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 9");
                        break;
                    case "10" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 10");
                        break;
                    case "11" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 11");
                        break;
                    case "12" : pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 12");
                        break;
        }

        //pdfRef = FirebaseDatabase.getInstance().getReference().child("Books").child("Class 12");
        pdfListView = (RecyclerView) findViewById(R.id.pdf_list_view);
        pdfListView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        pdfListView.setLayoutManager(linearLayoutManager);

        mssgLayout=findViewById(R.id.subscription_mssg_layout);

        /*UpdatePdf updatePdf = Pdfs.get(position);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(updatePdf.getUrl()));
        startActivity(intent);*/

        if (!isNetworkAvailable()) {
            progressBar.setVisibility(View.GONE);
            noInternetLayout.setVisibility(View.VISIBLE);

        } else  {
            noInternetLayout.setVisibility(View.INVISIBLE);
            DisplayAllPdfs();
        }

    }

    private void search(String s) {
        final Query query = pdfRef.orderByChild("title")
                        .startAt(s)
                        .endAt(s,"\uf8ff");

       /* query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    ArrayList<UpdatePdf> arrayList = new ArrayList<>();
                    arrayList.clear();

                    for (DataSnapshot dss : dataSnapshot.getChildren())
                    {
                        final UpdatePdf categoryItem = dss.getValue(UpdatePdf.class);
                        arrayList.add(categoryItem);
                    }

                    MyAdapter myAdapter = new MyAdapter(getApplicationContext(),arrayList);
                    pdfListView.setAdapter(myAdapter);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
    }


    private void DisplayAllPdfs() {

        Query sortPostInDecendingOrder = pdfRef.orderByChild("count");
        FirebaseRecyclerAdapter<UpdatePdf,PdfViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UpdatePdf, PdfViewHolder>
                        (
                                UpdatePdf.class,
                                R.layout.all_pdf_layout,
                                PdfViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(PdfViewHolder viewHolder, final UpdatePdf model, int position) {

                        View colorView = viewHolder.mView.findViewById(R.id.pdf_view);
                        TextView pdfTitle = viewHolder.mView.findViewById(R.id.pdf_title);

                        int colorType = position%6;
                        switch (colorType)
                        {
                            case 0 : colorView.setBackgroundColor(ContextCompat.getColor(BookActivity.this,R.color.creative_red));
                                    pdfTitle.setTextColor(ContextCompat.getColor(BookActivity.this,R.color.creative_red));
                                break;
                            case 1 : colorView.setBackgroundColor(ContextCompat.getColor(BookActivity.this,R.color.creative_green));
                                pdfTitle.setTextColor(ContextCompat.getColor(BookActivity.this,R.color.creative_green));
                                break;
                            case 2 : colorView.setBackgroundColor(ContextCompat.getColor(BookActivity.this,R.color.creative_yellow));
                                pdfTitle.setTextColor(ContextCompat.getColor(BookActivity.this,R.color.creative_yellow));
                                break;
                            case 3 : colorView.setBackgroundColor(ContextCompat.getColor(BookActivity.this,R.color.creative_sky_blue));
                                pdfTitle.setTextColor(ContextCompat.getColor(BookActivity.this,R.color.creative_sky_blue));
                                break;
                            case 4 : colorView.setBackgroundColor(ContextCompat.getColor(BookActivity.this,R.color.creative_orange));
                                pdfTitle.setTextColor(ContextCompat.getColor(BookActivity.this,R.color.creative_orange));
                                break;
                            case 5 : colorView.setBackgroundColor(ContextCompat.getColor(BookActivity.this,R.color.creative_violet));
                                pdfTitle.setTextColor(ContextCompat.getColor(BookActivity.this,R.color.creative_violet));
                                break;
                            //setBackgroundTint(ContextCompat.getColor(getApplicationContext(), R.color.creative_red)); break;
                        }

                        //final String PostKey = getRef(position).getKey();

                        viewHolder.setTitle(model.getTitle());
                        viewHolder.setDescription(model.getDescription());
                        progressBar.setVisibility(View.GONE);

                        if(status.equals("NO"))
                        {
                            viewHolder.mView.setClickable(false);
                            mssgLayout.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            mssgLayout.setVisibility(View.INVISIBLE);
                            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(BookActivity.this,PdfViewActivity.class);
                                    //intent.setAction(Intent.ACTION_VIEW);
                                    intent.putExtra("title",model.getTitle());
                                    intent.putExtra("url",model.getUrl());
                                    //intent.setData(Uri.parse(model.getUrl()));
                                    //intent.putExtra("Postkey",PostKey);
                                    //intent.setData(Uri.parse(UpdatePdf.getUrl()));
                                    startActivity(intent);
                                }
                            });
                        }

                    }
                };
        pdfListView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class PdfViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PdfViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title) {
            TextView pdfTitle = (TextView) mView.findViewById(R.id.pdf_title);
            pdfTitle.setText(title);
        }

        public void setDescription(String description) {
            TextView pdfDescription = (TextView) mView.findViewById(R.id.pdf_description);
            pdfDescription.setText(description);
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BookActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }



}
