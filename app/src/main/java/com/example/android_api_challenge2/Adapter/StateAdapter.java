package com.example.android_api_challenge2.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.example.android_api_challenge2.Activity.MainActivity;
import com.example.android_api_challenge2.R;
import com.example.android_api_challenge2.Model.State;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    private ArrayList<State> states;
    private Context mContext;

    public StateAdapter(Context context, ArrayList<State> states) {
        this.states = states;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.rowlayout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.textViewCountryName.setText(states.get(position).getName());
        holder.textViewNativeName.setText(states.get(position).getNativeName());
        new ImageLoadTask(holder.imageView, states.get(position).getFlag());

        Toast.makeText(mContext, mContext.getPackageName(), Toast.LENGTH_LONG);
        holder.rowlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity ma = (MainActivity) mContext;
                if (ma.GetFlag() == 0) {
                    Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(mContext, R.anim.shake);
                    view.startAnimation(hyperspaceJumpAnimation);
                    State s = states.get(position);
                    ma.LoadSecFragment(s);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return states.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCountryName, textViewNativeName;
        ImageView imageView;
        LinearLayout rowlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCountryName = itemView.findViewById(R.id.textView1);
            textViewNativeName = itemView.findViewById(R.id.textView2);
            imageView = itemView.findViewById(R.id.imageView);
            rowlayout = itemView.findViewById(R.id.rowlayout);
        }
    }

    public ArrayList<State> CostumeFilter(ArrayList<State> states, String text) {
        ArrayList<State> filteredList = new ArrayList<>();

        for (State country : states) {
            if (country.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(country);
            }
        }
        return filteredList;
    }

    private class ImageLoadTask extends AsyncTask<String, Void, Bitmap>
    {
        private ImageView imageView;
        private String url;

        public ImageLoadTask(ImageView imageView, String url) {
            this.imageView = imageView;
            this.url = url;
            this.execute(url);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL urlConnection = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                SVG svg = SVG.getFromInputStream(input);
                PictureDrawable pd = new PictureDrawable(svg.renderToPicture());
                Bitmap bitmap = Bitmap.createBitmap(pd.getIntrinsicWidth(),pd.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                canvas.drawPicture(pd.getPicture());
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SVGParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            imageView.setImageBitmap(bitmap);
        }
    }
}