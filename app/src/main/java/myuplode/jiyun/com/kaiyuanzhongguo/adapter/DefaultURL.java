package myuplode.jiyun.com.kaiyuanzhongguo.adapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class DefaultURL extends DefaultHandler {
    private String strName;
    private String mUrl = null;

    public String getmUrl() {
        return mUrl;

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        strName = qName;

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        strName = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String str = new String(ch, start, length);
        if (strName.equals("url")) {
            mUrl = str;
        }
    }
}
