package com.techtitudetribe.aadharshila;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.List;

public class ResultSliderAdapter extends RecyclerView.Adapter<ResultSliderAdapter.SliderViewHolder> {

    private List<resultSliderItem> sliderItems;
    private ViewPager2 viewPager2;
    private Context mContext;
    private DatabaseReference resultRef;
    private FirebaseAuth mAuth;
    private String currentUser;
    private RecyclerView resultListView;

    ResultSliderAdapter(List<resultSliderItem> sliderItems, ViewPager2 viewPager2,Context context) {
        this.sliderItems=sliderItems;
        this.viewPager2 = viewPager2;
        this.mContext=context;

    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.result_viewpager_container,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(sliderItems.get(position));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        resultListView = holder.mView.findViewById(R.id.result_list_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        resultListView.setLayoutManager(linearLayoutManager);

        RelativeLayout bottomView = holder.mView.findViewById(R.id.result_view_more_bottom);
        RelativeLayout topView = holder.mView.findViewById(R.id.result_view_image_background);
        RelativeLayout backgroundView = holder.mView.findViewById(R.id.slider_result_main_container);
        TextView currentStatistics = holder.mView.findViewById(R.id.current_statistic);
        TextView total = holder.mView.findViewById(R.id.slider_total_question);
        TextView correct = holder.mView.findViewById(R.id.slider_correct_question);
        TextView wrong = holder.mView.findViewById(R.id.slider_wrong_question);
        ImageView trophy = holder.mView.findViewById(R.id.trophy);
        //resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results").child("Chemistry");
        resultRef = FirebaseDatabase.getInstance().getReference().child("Results").child("Chemistry");
        final TextView view = holder.mView.findViewById(R.id.view_more_result);
        final ProgressBar progressBar  = holder.mView.findViewById(R.id.show_results_progress_bar);
        final RelativeLayout currentStatistic = holder.mView.findViewById(R.id.current_statistic_layout);
        int colortype=position%5;
        switch (colortype)
        {
            case 0 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red_light));
                //resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results").child("Chemistry");
                currentStatistics.setTextColor(ContextCompat.getColor(mContext,R.color.creative_red));
                total.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                correct.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                wrong.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
                currentStatistic.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red_light));
                trophy.setColorFilter(ContextCompat.getColor(mContext,R.color.creative_red), PorterDuff.Mode.SRC_IN);
                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        view.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        currentStatistic.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 1 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green_light));
                //resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results").child("Chemistry");
                currentStatistics.setTextColor(ContextCompat.getColor(mContext,R.color.creative_green));
                total.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                correct.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                wrong.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green));
                currentStatistic.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_green_light));
                trophy.setColorFilter(ContextCompat.getColor(mContext,R.color.creative_green), PorterDuff.Mode.SRC_IN);
                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        view.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        currentStatistic.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 2 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_light));
                //resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results").child("Chemistry");
                currentStatistics.setTextColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                total.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                correct.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                wrong.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_dark));
                currentStatistic.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_yellow_light));
                trophy.setColorFilter(ContextCompat.getColor(mContext,R.color.creative_yellow_dark), PorterDuff.Mode.SRC_IN);
                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        view.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        currentStatistic.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 3 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue_light));
                //resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results").child("Chemistry");
                currentStatistics.setTextColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                total.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                correct.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                wrong.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue));
                currentStatistic.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_sky_blue_light));
                trophy.setColorFilter(ContextCompat.getColor(mContext,R.color.creative_sky_blue), PorterDuff.Mode.SRC_IN);
                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        view.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        currentStatistic.setVisibility(View.INVISIBLE);
                    }
                });
                break;
            case 4 : topView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                bottomView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                backgroundView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange_light));
                //resultRef = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser).child("Results").child("Chemistry");
                currentStatistics.setTextColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                total.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                correct.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                wrong.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange));
                currentStatistic.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_orange_light));
                trophy.setColorFilter(ContextCompat.getColor(mContext,R.color.creative_orange), PorterDuff.Mode.SRC_IN);
                bottomView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        view.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        currentStatistic.setVisibility(View.INVISIBLE);
                    }
                });
                break;
        }

        displayAllResults();
        //DisplayAllPdfs();

    }

    private void displayAllResults() {
        Query sortResultInDecendingOrder = resultRef.orderByChild("count");
        FirebaseRecyclerAdapter<ResultAdapter,ResultViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ResultAdapter, ResultViewHolder>(
                        ResultAdapter.class,
                        R.layout.result_layout,
                        ResultViewHolder.class,
                        sortResultInDecendingOrder
                ) {
                    @Override
                    protected void populateViewHolder(ResultViewHolder resultViewHolder, ResultAdapter resultAdapter, int i) {

                        //ImageView trophy = resultViewHolder.mView1.findViewById(R.id.trophy);

                        resultViewHolder.setTotal(resultAdapter.getTotal());
                        resultViewHolder.setDate(resultAdapter.getDate());
                        resultViewHolder.setCorrect(resultAdapter.getCorrect());
                        resultViewHolder.setWrong(resultAdapter.getWrong());
                    }
                };
        resultListView.setAdapter(firebaseRecyclerAdapter);
    }
    /*private void DisplayAllPdfs() {

        Query sortPostInDecendingOrder = resultRef.orderByChild("count");
        FirebaseRecyclerAdapter<UpdatePdf, PdfViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UpdatePdf, PdfViewHolder>
                        (
                                UpdatePdf.class,
                                R.layout.result_layout,
                                PdfViewHolder.class,
                                sortPostInDecendingOrder
                        )
                {
                    @Override
                    protected void populateViewHolder(PdfViewHolder viewHolder, final UpdatePdf model, int position) {

                        /*View colorView = viewHolder.mView.findViewById(R.id.pdf_view);
                        TextView pdfTitle = viewHolder.mView.findViewById(R.id.pdf_title);

                        int colorType = position%6;
                        switch (colorType)
                        {
                            case 0 : colorView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.creative_red));
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
                        //progressBar.setVisibility(View.GONE);


                    }
                };
        resultListView.setAdapter(firebaseRecyclerAdapter);
    }*/

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }


    class SliderViewHolder extends RecyclerView.ViewHolder{
            View mView;

            SliderViewHolder(@NonNull View itemView) {
                super(itemView);
                mView=itemView;
            }
            void setImage(resultSliderItem resultSliderItem)
            {
                ImageView imageView = mView.findViewById(R.id.image_slider_result);
                imageView.setImageResource(resultSliderItem.getImage());
            }

    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        View mView1;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            mView1=itemView;
        }

        public void setTotal(String total)
        {
            TextView totalText = (TextView) mView1.findViewById(R.id.slider_total_question);
            totalText.setText(total);
        }

        public void setDate(String date)
        {
            TextView dateText = (TextView) mView1.findViewById(R.id.current_statistic);
            dateText.setText(date);
        }

        public void setCorrect(String correct)
        {
            TextView correctText = (TextView) mView1.findViewById(R.id.slider_correct_question);
            correctText.setText(correct);
        }

        public void setWrong(String wrong)
        {
            TextView wrongText = (TextView) mView1.findViewById(R.id.slider_wrong_question);
            wrongText.setText(wrong);
        }
    }
    /*public static class PdfViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public PdfViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setTitle(String title) {
            TextView pdfTitle = (TextView) mView.findViewById(R.id.current_statistic);
            pdfTitle.setText(title);
        }

        public void setDescription(String description) {
            TextView pdfDescription = (TextView) mView.findViewById(R.id.slider_total_question);
            pdfDescription.setText(description);
        }
    }*/
}
