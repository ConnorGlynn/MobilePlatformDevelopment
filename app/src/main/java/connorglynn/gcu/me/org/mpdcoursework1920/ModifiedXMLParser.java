package connorglynn.gcu.me.org.mpdcoursework1920;
// Connor Glynn S1626555
import android.util.Log;
import android.util.Xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

import org.xml.sax.Parser;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


public class ModifiedXMLParser {
    public static ArrayList<RssFeedModelIncidents> parse(String url) {
        ArrayList<RssFeedModelIncidents> IncidentsList = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
           stream = new URL(url).openConnection().getInputStream();
            parser.setInput(stream, null);

            // Initially tried to use string passed into method. However this was throwing a low level java error (unexpected token error).
            // I suspect this was something to do with the encoding of the string but tried various approaces and could not get working.
            // decided to use a stream directly in the parser to progress.

        /*   parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(xml));*/
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
                            else if (name.equalsIgnoreCase("Georss:point")) {
                                String[] latlong = parser.nextText().split(" ");
                                Float lat = Float.parseFloat(latlong[0]);
                                Float longitude = Float.parseFloat(latlong[1]);
                                Incidents.setLat(lat);
                                Incidents.setLong(longitude);
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




    public static ArrayList<RssFeedModelRoadworks> parseRoadworks(String url) {
        ArrayList<RssFeedModelRoadworks> RoadworksList = new ArrayList<>();
        XmlPullParser parser = Xml.newPullParser();
        InputStream stream = null;
        try {
            // auto-detect the encoding from the stream
            stream = new URL(url).openConnection().getInputStream();
            parser.setInput(stream, null);

            // Initially tried to use string passed into method. However this was throwing a low level java error (unexpected token error).
            // I suspect this was something to do with the encoding of the string but tried various approaces and could not get working.
            // decided to use a stream directly in the parser to progress.

        /*   parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new StringReader(xml));*/
            int eventType = parser.getEventType();
            boolean done = false;
            RssFeedModelRoadworks Roadworks = null;
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("Item")) {
                            Log.i("new item", "Create new item");
                            Roadworks = new RssFeedModelRoadworks();
                        } else if (Roadworks != null) {
                            if (name.equalsIgnoreCase("Title")) {
                                Log.i("Attribute", "setLink");
                                Roadworks.setTitle(parser.nextText());
                            }

                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        Log.i("End tag", name);
                        if (name.equalsIgnoreCase("Item") && Roadworks  != null) {

                            RoadworksList.add(Roadworks );
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
        return RoadworksList;
    }
}
