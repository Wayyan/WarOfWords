package com.team12.warofwords.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team12.warofwords.AlterActivity;
import com.team12.warofwords.DB.AppDatabase;
import com.team12.warofwords.R;
import com.team12.warofwords.entity.Intermediate;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntermediatesAdapter extends RecyclerView.Adapter<IntermediatesAdapter.MyViewHolder> {

    private Context mContext;
    private List<Intermediate> intermediateList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.star_layout)
        LinearLayout lStar;

        @BindView(R.id.imgLock)
        ImageView imgLock;

        @BindView(R.id.imgViewMore)
        ImageView imgViewMore;

        @BindView(R.id.star1)
        ImageView imgStar1;

        @BindView(R.id.star2)
        ImageView imgStar2;

        @BindView(R.id.star3)
        ImageView imgStar3;

        @BindView(R.id.mission)
        TextView tLevel;

        @BindView(R.id.question)
        TextView tQuestion;

        @BindView(R.id.score)
        TextView tScore;

        @BindView(R.id.diamond)
        TextView tDiamond;


        private Intermediate Intermediate;
        private Intent i;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Intermediate.getOn()) {
                        i = new Intent(mContext.getApplicationContext(), AlterActivity.class);
                        i.putExtra("levelID", Intermediate.getLevelID());
                        i.putExtra("isNormal", false);
                        mContext.startActivity(i);
                    } else if (Intermediate.getLevelID() == 4)
                        Toast.makeText(mContext.getApplicationContext(), "အသစ် ထပ်မံ မထွက်ရှိသေးပါ", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mContext.getApplicationContext(), "သော့ခတ်ထားပါသည်", Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void bindData(Intermediate Intermediate) {
            this.Intermediate = Intermediate;
            if (Intermediate.getLevelID() == 4) {
                tLevel.setText("View More");
                tQuestion.setText("You Can DownLoad \n Next Mission Games");
                tScore.setVisibility(View.INVISIBLE);
                tDiamond.setVisibility(View.GONE);
            } else {
                tLevel.setText("Misssion " + Intermediate.getLevelID());


                if (Intermediate.getOn()) {
                    tQuestion.setText("Total Question :" + Intermediate.getQuestions() + "      ");
                    tScore.setVisibility(View.VISIBLE);
                    tDiamond.setVisibility(View.VISIBLE);
                    tScore.setText(" :  " + Intermediate.getScores());
                    tDiamond.setText(" :  " + Intermediate.getDiamonds());
                } else {
                    tQuestion.setText("Total Question :" + Intermediate.getQuestions() + "\n Can\'t play mission now");
                    tScore.setVisibility(View.INVISIBLE);
                    tDiamond.setVisibility(View.GONE);
                }
            }

        }

        public void setVisibility() {
            if (Intermediate.getLevelID() >= AppDatabase.getAppDatabase(mContext).intermediateDAO().countIntermediates()) {

                lStar.setVisibility(View.GONE);
                imgLock.setVisibility(View.GONE);
                imgViewMore.setVisibility(View.VISIBLE);

            } else {

                if (Intermediate.getOn()) {
                    lStar.setVisibility(View.VISIBLE);
                    imgLock.setVisibility(View.GONE);
                } else {
                    lStar.setVisibility(View.GONE);
                    imgLock.setVisibility(View.VISIBLE);
                }
                imgViewMore.setVisibility(View.GONE);

            }


        }

        public void setStarLevel() {
            if (Intermediate.getScores() >= (Intermediate.getQuestions() / 5) * 4) {
                imgStar1.setImageResource(R.drawable.ic_star_unlock);
                imgStar2.setImageResource(R.drawable.ic_star_unlock);
                imgStar3.setImageResource(R.drawable.ic_star_unlock);


            } else if (Intermediate.getScores() >= Intermediate.getQuestions() / 2) {
                imgStar1.setImageResource(R.drawable.ic_star_unlock);
                imgStar2.setImageResource(R.drawable.ic_star_unlock);
                imgStar3.setImageResource(R.drawable.ic_star_lock);


            } else if (Intermediate.getScores() >= Intermediate.getQuestions() / 3) {
                imgStar1.setImageResource(R.drawable.ic_star_unlock);
                imgStar2.setImageResource(R.drawable.ic_star_lock);
                imgStar3.setImageResource(R.drawable.ic_star_lock);
            } else resetStar();
        }

        public void resetStar() {
            imgStar1.setImageResource(R.drawable.ic_star_lock);
            imgStar2.setImageResource(R.drawable.ic_star_lock);
            imgStar3.setImageResource(R.drawable.ic_star_lock);
        }


    }

    public IntermediatesAdapter(Context mContext, List<Intermediate> intermediateList) {
        this.mContext = mContext;
        this.intermediateList = intermediateList;
    }

    public void swipeData(List<Intermediate> data) {
        this.intermediateList = data;
        notifyDataSetChanged();
    }

    @Override
    public IntermediatesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.level_card, parent, false);

        return new IntermediatesAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final IntermediatesAdapter.MyViewHolder holder, int position) {
        Intermediate Intermediate = intermediateList.get(position);
        holder.bindData(Intermediate);

        holder.setVisibility();
        holder.resetStar();
        holder.setStarLevel();


    }

    public int getItemCount() {
        return intermediateList.size();
    }

}