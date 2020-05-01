package connorglynn.gcu.me.org.mpdcoursework1920;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import connorglynn.gcu.me.org.mpdcoursework1920.RssFeedModelIncidents;


import static android.content.ContentValues.TAG;

public class ParseHelper {

    public static ArrayList<RssFeedModelIncidents> parseIncidents(String xml) {
        ArrayList<RssFeedModelIncidents> IncidentsList = new ArrayList<>();
        try {
           // xml = "";
            boolean parsingComplete = false;
            RssFeedModelIncidents Incidents = null;
            XmlPullParser xmlParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlParser.setInput(new StringReader(xml));
            int eventType = xmlParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT && !parsingComplete) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = xmlParser.getName();
                        if (name.equalsIgnoreCase("Item")) {
                            Incidents = new RssFeedModelIncidents();
                        } else if (Incidents != null) {
                            if (name.equalsIgnoreCase("Title")) {
                                Incidents.setTitle(xmlParser.nextText());
                            } else if (name.equalsIgnoreCase("Description")) {
                                Incidents.setDescription(xmlParser.nextText());
                            } else if (name.equalsIgnoreCase("Georss:point")) {
                                String[] latlong = xmlParser.nextText().split(" ");
                                Float lat = Float.parseFloat(latlong[0]);
                                Float longitude = Float.parseFloat(latlong[1]);
                                Incidents.setLat(lat);
                                Incidents.setLong(longitude);
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = xmlParser.getName();
                        if (name.equalsIgnoreCase("Item") && Incidents != null) {
                            IncidentsList.add(Incidents);
                        } else if (name.equalsIgnoreCase("Channel")) {
                            parsingComplete = true;
                        }
                        break;
                }
                eventType = xmlParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IncidentsList;
    }

    public static ArrayList<RssFeedModelIncidents> parse(String url) {
        ArrayList<RssFeedModelIncidents> IncidentsList = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
            stream = new URL(url).openConnection().getInputStream();
            parser.setInput(stream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            RssFeedModelIncidents Incidents = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("Item")) {
                            Log.i("new item", "Create new item");
                            Incidents = new RssFeedModelIncidents();
                        } else if (Incidents != null) {
                            if (name.equalsIgnoreCase("Title")) {
                                Log.i("Attribute", "setLink");
                                Incidents.setTitle(parser.nextText());
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase("Item") && Incidents != null) {

                            IncidentsList.add(Incidents);
                        } else if (name.equalsIgnoreCase("Channel")) {
                            done = true;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return IncidentsList;
    }

}
