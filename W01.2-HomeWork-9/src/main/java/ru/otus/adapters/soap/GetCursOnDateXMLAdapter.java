/*
 * GetCursOnDateXMLAdapter.java
 * This file was last modified at 2018.12.03 20:05 by Victor N. Skurikhin.
 * $Id$
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 */

package ru.otus.adapters.soap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.otus.models.cbr.Currency;
import ru.otus.soap.wsclient.cbr.DailyInfo;
import ru.otus.soap.wsclient.cbr.DailyInfoSoap;
import ru.otus.soap.wsclient.cbr.GetCursOnDateXMLResponse;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;

public class GetCursOnDateXMLAdapter
{
    private final List<Currency> list = new ArrayList<>();

    public GetCursOnDateXMLAdapter(XMLGregorianCalendar date)
    {
        DailyInfoSoap service = new DailyInfo().getDailyInfoSoap();
        GetCursOnDateXMLResponse.GetCursOnDateXMLResult xmlResult = service.getCursOnDateXML(date);
        convertGetCursOnDateXMLResult(xmlResult);
    }

    public List<Currency> getList()
    {
        return list;
    }

    public String getDataString(Node node)
    {
        return node.getFirstChild().getNodeValue();
    }

    public Currency convetToCurrency(Node node)
    {
        Currency result = new Currency();

        while (node != null) {
            node.getNodeName();

            switch (node.getNodeName().toUpperCase()) {
                case "VCHCODE":
                    result.setVChCode(getDataString(node));
                    break;
                case "VNAME":
                    result.setVName(getDataString(node).trim());
                    break;
                case "VNOM":
                    result.setVNom(getDataString(node));
                    break;
                case "VCURS":
                    result.setVCurs(getDataString(node));
                    break;
                case "VCODE":
                    result.setVCode(getDataString(node));
                    break;
            }

            node = node.getNextSibling();
        }

        return result;
    }

    private void convertGetCursOnDateXMLResult(GetCursOnDateXMLResponse.GetCursOnDateXMLResult result)
    {
        Element element = (org.w3c.dom.Element) result.getContent().get(0);
        NodeList nodes = element.getChildNodes();

        for (int i = 0; i < nodes.getLength(); ++i) {
            Node node = nodes.item(i);
            list.add(convetToCurrency(node.getFirstChild()));
        }
    }
}

/* vim: syntax=java:fileencoding=utf-8:fileformat=unix:tw=78:ts=4:sw=4:sts=4:et
 */
//EOF
