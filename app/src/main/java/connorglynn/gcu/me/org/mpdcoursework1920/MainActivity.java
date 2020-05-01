package connorglynn.gcu.me.org.mpdcoursework1920;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private String routine;
    private TextView rawDataDisplay;
    private String result;
    private Button startButton;
    private ArrayList<RssFeedModelIncidents> incidents;

    private ArrayList<RssFeedModelRoadworks> roadworks;
    // Traffic Scotland URLs
    private String urlSourceRoadworks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String urlSourcePlannedRoadworks = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String urlSourceIncidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);

        Button roadworksbutton= (Button)findViewById(R.id.RoadworksButton);
        roadworksbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                routine = "roadworks";
                // click handling code
                startProgress();
            }
        });



    }

    public void onClick(View aview)
    {

      /*  URL aurl;
        URLConnection yc;
        BufferedReader in = null;
        String inputLine = "";


        Log.e("MyTag","in run");

        try
        {
            Log.e("MyTag","in try");
            aurl = new URL(urlSource);
            yc = aurl.openConnection();
            in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
            //
            // Throw away the first 2 header lines before parsing
            //
            //
            //
            while ((inputLine = in.readLine()) != null)
            {
                result = result + inputLine;
                Log.e("MyTag",inputLine);

            }
            in.close();
        }
        catch (IOException ae)
        {
            Log.e("MyTag", "ioexception");
        }
        incidents = ParseHelper.parseIncidents(result);

        for(RssFeedModelIncidents incident : incidents)
        {
            rawDataDisplay.append(incident.description);
        }*/

        routine = "incidents";

        startProgress();
    }

    public void startProgress()
    {

        String url="";
        if(routine=="roadworks" )
        {
            url = urlSourceRoadworks;
        }
        else if(routine =="incidents")
        {
            url = urlSourceIncidents;
        }
        // Run network access on a separate thread;
        new Thread(new Task(url)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

             /*   URL aurl;
                URLConnection yc;
                BufferedReader in;
                String inputLine;


                try
                {
                    aurl = new URL(url);
                    yc = aurl.openConnection();
                    in = new BufferedReader(new InputStreamReader(yc.getInputStream()));

                    while ((inputLine = in.readLine()) != null)
                    {
                        result = result + inputLine;

                    }
                    in.close();
                }
                catch (IOException ae)
                {
                    Log.e("MyTag", "ioexception");
                }*/


                //   incidents = ParseHelper.parseIncidents(result);

            if(routine=="incidents") {

                incidents = ModifiedXMLParser.parse(url);
            }

            if(routine=="roadworks") {

                roadworks = ModifiedXMLParser.parseRoadworks(url);
            }

            //
            // Now that you have the xml data you can parse it
            //

            // Now update the TextView to display raw XML data
            // Probably not the best way to update TextView
            // but we are just getting started !



            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {

                    rawDataDisplay.setText("");
                    if(routine=="incidents") {
                        for (int i = 0; i < incidents.size(); i++) {
                            RssFeedModelIncidents incident = incidents.get(i);
                            rawDataDisplay.append(incident.getTitle() + "\n");
                        }
                    }
                   else if(routine=="roadworks") {
                        for (int i = 0; i < roadworks.size(); i++) {
                            RssFeedModelRoadworks roadwork = roadworks.get(i);
                            rawDataDisplay.append(roadwork.getTitle() + "\n");
                        }
                    }

                    /*for(RssFeedModelIncidents incident : incidents)
                    {
                       // rawDataDisplay.setText(incident.getDescription());
                        Log.d(incident.getTitle(),incident.getDescription());

                       // rawDataDisplay.append(incident.description);
                    }*/

                    Log.d("UI thread", "I am the UI thread");
                 //   rawDataDisplay.setText(result);
                }
            });
        }

    }
}
