package com.team12.warofwords.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team12.warofwords.R;
import com.team12.warofwords.SharePreferences.SharePreferenceStorage;
import com.team12.warofwords.model.WordStoreModel;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Way yan on 10/16/2018.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {
    private List<WordStoreModel> wordStoreModels;
    public static String[] first = {};
    public static String[] second = {};
    public static String[] third = {};
    private static Context context;
    public static Typeface font;
    public int[] partnerAvatas = {R.drawable.user_lite, R.drawable.iron_man_avatar, R.drawable.spider_man_avatar, R.drawable.hulk_avatar, R.drawable.ant_man, R.drawable.dontknow_avata};
    private SharePreferenceStorage mShp;
    public static int partaner;

    public MyRecyclerAdapter(Context context, List<WordStoreModel> wordStoreModels) {
        this.context = context;
        this.wordStoreModels = wordStoreModels;
        mShp=new SharePreferenceStorage(this.context);
        partaner=partnerAvatas[mShp.getPartner()];
        font = Typeface.createFromAsset(context.getAssets(), "samsung.ttf");
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_holder, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bindView(wordStoreModels.get(i));

    }

    @Override
    public int getItemCount() {
        return wordStoreModels.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtWord, txtTranslate, txtAvata;
        private LinearLayout lnBackground, lnGravity;

        //private TextView tvUni;
        public MyViewHolder(View itemView) {
            super(itemView);
            txtWord = itemView.findViewById(R.id.txt_word);
            lnBackground = itemView.findViewById(R.id.ln_background_changeable_holder);
            lnGravity = itemView.findViewById(R.id.ln_gravcity_changable_holder);
            txtTranslate = itemView.findViewById(R.id.tv_translate);
            txtAvata = itemView.findViewById(R.id.tv_avata);

            txtTranslate.setTypeface(font);
        }

        public void bindView(WordStoreModel tm) {
            txtWord.setText(tm.getWord());
            if (tm.isIsleft()) {
                txtWord.setTextColor(Color.parseColor("#000000"));//"#424242"));
                lnBackground.setBackground(context.getResources().getDrawable(R.drawable.left_compititor_design));
                lnGravity.setGravity(Gravity.LEFT);
                txtAvata.setVisibility(View.VISIBLE);
                txtAvata.setBackgroundResource(partaner);
                txtTranslate.setTextColor(Color.parseColor("#606060"));//"#808080"));
                txtTranslate.setText("translated:" + getShotWord(tm.getMyanmrWord()));
            } else {
                txtWord.setTextColor(Color.WHITE);
                lnBackground.setBackground(context.getResources().getDrawable(R.drawable.right_compititor_design));
                lnGravity.setGravity(Gravity.RIGHT);
                txtAvata.setVisibility(View.GONE);
                // txtTranslate.setTextColor(Color.WHITE);
                txtTranslate.setTextColor(Color.parseColor("#fbfbfb"));
                txtTranslate.setText("translated: " + getShotWord(tm.getMyanmrWord()));
            }

        }

        private String getShotWord(String word) {
            String sword;
            String[] shortStrings = word.split("။");
            if (shortStrings.length > 1)
                return shortStrings[0];
            else
                return word;
//                sword = shortStrings[0];
//                String[] tmp = sword.split(Pattern.quote("("));
//                int i = tmp.length;
//                if (1 < i) {
//                    return tmp[0];
//                } else {
//                    Log.d("debugg", shortStrings[0] + "");
//                    return sword;
//                }
//            } else {
//                String[] tmp = word.split(Pattern.quote("("));
//                int i = tmp.length;
//                if (1 < i) {
//                    return tmp[0];
//                } else {
//                    return word;
//                }
            //          }
        }
    }
}
