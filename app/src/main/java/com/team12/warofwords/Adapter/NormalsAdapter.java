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
import com.team12.warofwords.entity.Normal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NormalsAdapter extends RecyclerView.Adapter<NormalsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Normal> normalList;

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


        private Normal Normal;
        private Intent i;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Normal.getOn()) {
                        i = new Intent(mContext.getApplicationContext(), AlterActivity.class);
                        i.putExtra("levelID", Normal.getLevelID());
                        i.putExtra("isNormal", true);
                        mContext.startActivity(i);
                    } else if (Normal.getLevelID() == 4)
                        Toast.makeText(mContext.getApplicationContext(), "အသစ် ထပ်မံ မထွက်ရှိသေးပါ", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(mContext.getApplicationContext(), "သော့ခတ်ထားပါသည်", Toast.LENGTH_SHORT).show();
                }
            });

        }

        public void bindData(Normal Normal) {
            this.Normal = Normal;
            if (Normal.getLevelID() == 4) {
                tLevel.setText("View More");
                tQuestion.setText("You Can DownLoad \n Next Mission Games");
                tScore.setVisibility(View.INVISIBLE);
                tDiamond.setVisibility(View.GONE);
            } else {
                tLevel.setText("Misssion " +Normal.getLevelID());


                if (Normal.getOn()) {
                    tQuestion.setText("Total Question :" + Normal.getQuestions()+"      ");
                    tScore.setVisibility(View.VISIBLE);
                    tDiamond.setVisibility(View.VISIBLE);
                    tScore.setText(" :  " + Normal.getScores());
                    tDiamond.setText(" :  " + Normal.getDiamonds());
                } else {
                    tQuestion.setText("Total Question :" + Normal.getQuestions() + "\n Can\'t play mission now");
                    tScore.setVisibility(View.INVISIBLE);
                    tDiamond.setVisibility(View.GONE);
                }
            }

        }

        public void setVisibility() {
            if (Normal.getLevelID() >= AppDatabase.getAppDatabase(mContext).normalDAO().countNormals()) {

                lStar.setVisibility(View.GONE);
                imgLock.setVisibility(View.GONE);
                imgViewMore.setVisibility(View.VISIBLE);

            } else {

                if (Normal.getOn()) {
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
            if (Normal.getScores() >= (Normal.getQuestions() / 5) * 4) {
                imgStar1.setImageResource(R.drawable.ic_star_unlock);
                imgStar2.setImageResource(R.drawable.ic_star_unlock);
                imgStar3.setImageResource(R.drawable.ic_star_unlock);


            } else if (Normal.getScores() >= Normal.getQuestions() / 2) {
                imgStar1.setImageResource(R.drawable.ic_star_unlock);
                imgStar2.setImageResource(R.drawable.ic_star_unlock);
                imgStar3.setImageResource(R.drawable.ic_star_lock);


            } else if (Normal.getScores() >= Normal.getQuestions() / 3) {
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

    public NormalsAdapter(Context mContext, List<Normal> normalList) {
        this.mContext = mContext;
        this.normalList = normalList;
    }

    public void swipeData(List<Normal> data) {
        this.normalList = data;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.level_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Normal Normal = normalList.get(position);
        holder.bindData(Normal);

        holder.setVisibility();
        holder.resetStar();
        holder.setStarLevel();


    }

    public int getItemCount() {
        return normalList.size();
    }

}
