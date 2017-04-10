package myuplode.jiyun.com.kaiyuanzhongguo.adapter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

import myuplode.jiyun.com.kaiyuanzhongguo.bean.Student;


public class MyDefault extends DefaultHandler {
    private ArrayList<Student> mList = new ArrayList<>();
    private String strName;
    private Student stu;

    public ArrayList<Student> getmList() {
        return mList;

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        strName = qName;
        if (strName.equals("news")) {
            if (stu == null) {
                stu = new Student();
            }
        }


    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (qName.equals("news")) {
            mList.add(stu);
            stu = null;
        }
        strName = "";
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        String str = new String(ch, start, length);
        if (strName.equals("title")) {
            stu.setTitle(str);
        } else if (strName.equals("body")) {
            stu.setBody(str);
        } else if (strName.equals("id")) {
            stu.setId(str);
        }
    }
}
