package ru.otus.gwt.client.aside;

import com.google.gwt.http.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.google.gwt.xml.client.Document;

public class CBRValutes
{
    private String xmlText;
    private final RequestCallback callback = new RequestCallback()
    {
        @SuppressWarnings({"GwtToHtmlReferences", "GWTStyleCheck"})
        @Override
        public void onResponseReceived(Request request, Response response)
        {
            xmlText = response.getText();
            Document doc = XMLParser.parse(xmlText);
            if (null == doc) {
                return;
            }
            NodeList nl = doc.getDocumentElement().getChildNodes();
            if (null == nl) {
                return;
            }
            for (int i = 0; i < nl.getLength(); ++i) {
                Valute valute = new Valute(nl.item(i));
                if (valute.isAMD()) {
                    RootPanel id = RootPanel.get("AMD");
                    id.add(new Label("AMD: " + valute.getValue()));
                }
                if (valute.isGBP()) {
                    RootPanel id = RootPanel.get("GBP");
                    id.add(new Label("GBP: " + valute.getValue()));
                }
                if (valute.isUSD()) {
                    RootPanel id = RootPanel.get("USD");
                    id.add(new Label("USD: " + valute.getValue()));
                }
            }
        }

        @Override
        public void onError(Request request, Throwable exception)
        { /* None */ }
    };

    public CBRValutes(String url) {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, url);

        try {
            requestBuilder.sendRequest(null, callback);
        } catch (RequestException e) {
            Window.alert("Could not parse XML document." + e);
        }
    }
}
