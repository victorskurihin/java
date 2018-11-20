package ru.otus.gwt.client.aside;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;

public class Valute
{
    private boolean isElementNode;
    private boolean isAMD = false;
    private boolean isGBP = false;
    private boolean isUSD = false;
    private String value;

    public static boolean checkElementNode(Node node) {
        return null != node && node.getNodeType() == Node.ELEMENT_NODE;
    }

    public Valute(Node node) {
        isElementNode = checkElementNode(node);
        if (isElementNode) {
            NodeList nl = node.getChildNodes();

            for (int i = 0; i < nl.getLength(); ++i) {
                Node n = nl.item(i);
                if (checkElementNode(n)) {
                    if ("AMD".equals(n.getFirstChild().getNodeValue())) {
                        isAMD = true;
                    }
                    if ("GBP".equals(n.getFirstChild().getNodeValue())) {
                        isGBP = true;
                    }
                    if ("USD".equals(n.getFirstChild().getNodeValue())) {
                        isUSD = true;
                    }
                    if ((isAMD || isGBP || isUSD) && "Value".equals(n.getNodeName())) {
                        value = n.getFirstChild().getNodeValue();
                    }
                }
            }
        }
    }

    public boolean isElementNode()
    {
        return isElementNode;
    }

    public boolean isAMD()
    {
        return isAMD;
    }

    public boolean isGBP()
    {
        return isGBP;
    }

    public boolean isUSD()
    {
        return isUSD;
    }

    public String getValue()
    {
        return value;
    }
}
