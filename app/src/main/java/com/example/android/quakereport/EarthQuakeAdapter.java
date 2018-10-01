package com.example.android.quakereport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthQuakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthQuakeAdapter(Activity context, ArrayList<Earthquake> Earthquakes) {
        super(context, 0,Earthquakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ListView = convertView;
        final Earthquake currentPosition = getItem(position);

        if(ListView == null) {
            ListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list, parent, false);
        }

        String formattedMagnitude = formatMagnitude(currentPosition.getmMagnitude());

        TextView EarthquakeMagnitude = ListView.findViewById(R.id.earthquake_magnitude);
        EarthquakeMagnitude.setText(formattedMagnitude);

        GradientDrawable magnitudeCircle = (GradientDrawable) EarthquakeMagnitude.getBackground();
        int magnitudeColor = getMagnitudeColor(currentPosition.getmMagnitude());
        magnitudeCircle.setColor(magnitudeColor);

        String location = currentPosition.getmLocation();
        String[] bits = location.split(" ");
        String msg1="";
        String msg2="";
        int i=0;
        int len = bits.length;
        if (len>2) {
            for (i = 3; i < len; i++) {
                msg2 += bits[i] + " ";
            }
            msg1 += bits[0] + " " + bits[1] + " " + bits[2];
        }
        else{
            msg2+=bits[0]+" "+bits[1];
            msg1="Near the";
        }

        TextView EarthquakeLocation_1 = ListView.findViewById(R.id.earthquake_location_1);
        EarthquakeLocation_1.setText(msg1);

        TextView EarthquakeLocation_2 = ListView.findViewById(R.id.earthquake_location_2);
        EarthquakeLocation_2.setText(msg2);

        Date dateObject = new Date(currentPosition.getmTimeInMilliseconds());

        TextView EarthquakeDate = ListView.findViewById(R.id.earthquake_date);
        String formattedDate = formatDate(dateObject);
        EarthquakeDate.setText(formattedDate);

        TextView EarthquakeTime = ListView.findViewById(R.id.earthquake_time);
        String formattedTime = formatTime(dateObject);
        EarthquakeTime.setText(formattedTime);

        ListView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String url = currentPosition.getmURL();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                getContext().startActivity(i);
            }
        });

        return ListView;
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatMagnitude(double magnitude ){
        DecimalFormat magnitudeFormat = new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int mag = (int)Math.floor(magnitude);
        switch (mag){
            case 0:
            case 1:
                magnitudeColorResourceId=R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId=R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId=R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId=R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId=R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId=R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId=R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId=R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId=R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId=R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(),magnitudeColorResourceId);
    }
}
