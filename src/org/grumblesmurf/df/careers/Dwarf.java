package org.grumblesmurf.df.careers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Dwarf implements Comparable<Dwarf>
{
    final String name;
    private final String nickName;
    final String sex;
    private final Map<String, Integer> attributes;
    private final String displayName;

    public Dwarf(String name, String nickName, String sex, Map<String, Integer> attributes) {
        this.name = name;
        this.nickName = nickName;
        this.sex = sex;
        this.attributes = attributes;

        if (nickName.isEmpty()) {
            this.displayName = name;
        } else {
            this.displayName = String.format("%s (%s)", name, nickName);
        }
    }

    public static Dwarf from(Element creature) {
        String name = textContentOfFirstChildNamed("Name", creature);
        String nickName = textContentOfFirstChildNamed("Nickname", creature);
        String sex = textContentOfFirstChildNamed("Sex", creature);

        Element attributesElement = firstChildNamed("Attributes", creature);
        NodeList childNodes = attributesElement.getChildNodes();
        Map<String, Integer> attributes = new LinkedHashMap<String, Integer>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item instanceof Element) {
                attributes.put(item.getNodeName(), Integer.valueOf(item.getTextContent()));
            }
        }
        return new Dwarf(name, nickName, sex, attributes);
    }

    private static Element firstChildNamed(String name, Element parent) {
        return (Element) parent.getElementsByTagName(name).item(0);
    }

    private static String textContentOfFirstChildNamed(String name, Element parent) {
        return firstChildNamed(name, parent).getTextContent();
    }

    @Override
    public int compareTo(Dwarf o) {
        return displayName.compareTo(o.displayName);
    }

    @Override
    public String toString() {
        return displayName;
    }

    public void renderTo(javax.swing.text.Element body)
        throws IOException, BadLocationException {
        HTMLDocument d = (HTMLDocument) body.getDocument();
        d.insertBeforeEnd(body, String.format("<h1>%s</h1>", displayName));
        d.insertBeforeEnd(body, attributesTable());
    }

    private String attributesTable() {
        StringBuilder sb = new StringBuilder("<table style='float: left;'>");
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            sb.append("<tr>")
                .append("<th>").append(entry.getKey()).append("</th>")
                .append("<td>").append(entry.getValue()).append("</td>")
                .append("</tr>");
        }

        return sb.append("</table>").toString();
    }
}
