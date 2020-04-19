package org.me.gcu.mobile_platformcoursework;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {
    private TextView rawDataDisplay;
    private String result;
    private SearchView Search;

    //Traffic Scotland URLs
    private String urlSource1 = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    private String urlSource2 = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";
    private String urlSource3 = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rawDataDisplay = (TextView) findViewById(R.id.rawDataDisplay);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(this);

        List<String> Items = new ArrayList<String>();
        Items.add ("Choose a Feed");
        Items.add("Roadworks");
        Items.add("Planned Roadworks");
        Items.add("Current Incidents");

        ArrayAdapter<String> Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Items);

        Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(Adapter);

    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       //IF Statement for XML Links
        String item = parent.getItemAtPosition(position).toString();


            if (item == "Roadworks") {
                String urlSource = urlSource1;
                startProgress(urlSource);
            }
            else if (item == "Planned Roadworks"){
                String urlSource = urlSource2;
                startProgress(urlSource);
            }
            else if (item == "Current Incidents"){
                String urlSource = urlSource3;
                startProgress(urlSource);
            }
        }

    public void onNothingSelected(AdapterView<?> arg0)  {

    }


    public void startProgress(String urlSource) {
        new Thread(new Task(urlSource)).start();
    }

    private class Task implements Runnable {
        public String url;
        private LinkedList<Roadworks> alist;

        public Task(String aurl) {
            url = aurl;
        }

        @Override
        public void run() {

            URL aurl = null;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";



            try {

                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                result = in.readLine();
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                    Log.e("MyTag", inputLine);

                }
                in.close();
            } catch (IOException ae) {
                Log.e("MyTag", "ioexception");
            }

            Roadworks roadworks = new Roadworks();
            alist = null;
            try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(result));
                    int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    // Found a start tag
                    if (eventType == XmlPullParser.START_TAG) {

                        // Check which Tag we have
                        if (xpp.getName().equalsIgnoreCase("channel")) {

                            alist = new LinkedList<Roadworks>();

                        } else if (xpp.getName().equalsIgnoreCase("item")) {

                            roadworks = new Roadworks();

                        }
                        else if (xpp.getName().equalsIgnoreCase("title")) {

                            String temp = xpp.nextText();
                            roadworks.setRoad(temp);

                        } else if (xpp.getName().equalsIgnoreCase("description")) {

                            String temp = xpp.nextText();
                            roadworks.setDescription(temp);

                        }else if (xpp.getName().equalsIgnoreCase("Link")){

                            String temp = xpp.nextText();
                            roadworks.setLink(temp);

                        }else if (xpp.getName().equalsIgnoreCase("point")) {

                            String temp = xpp.nextText();
                            roadworks.Separate_Coordinates(temp);

                        }else if (xpp.getName().equalsIgnoreCase("pubDat")){

                            String temp = xpp.nextText();
                            roadworks.setPublished(temp);
                        }

                    } else if (eventType == XmlPullParser.END_TAG) {

                        if (xpp.getName().equalsIgnoreCase("item")) {

                            Log.e("MyTag", "item is " + roadworks.toString());
                            alist.add(roadworks);

                        }
                    }
                    // Get the next event
                    eventType = xpp.next();

                } // End of while
            } catch (XmlPullParserException ae1) {
                Log.e("MyTag", "Parsing error" + ae1.toString());
            } catch (IOException ae1) {
                Log.e("MyTag", "IO error during parsing");
            }

            Search = (SearchView) findViewById(R.id.searchview);

            Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    String Filter ="";

                    for (Roadworks roadworks:alist){


                        if (roadworks.toString().contains(query)){

                            Filter += roadworks.toString();
                        }

                        }
                    rawDataDisplay.setText(Filter);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

            MainActivity.this.runOnUiThread(new Runnable() {
                public void run() {
                    Log.d("UI thread", "I am the UI thread"); //Tag for Testing (REMOVE)

                    String data = "";

                    for(Roadworks roadworks:alist) {
                        roadworks.toString();

                        data += roadworks.toString();
                    }
                    rawDataDisplay.setText(data);
                }
            });
        }
    }


} // End of MainActivity