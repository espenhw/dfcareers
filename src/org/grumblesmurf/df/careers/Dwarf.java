package org.grumblesmurf.df.careers;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;
import java.util.*;

public class Dwarf implements Comparable<Dwarf>
{
    final String name;
    private final String nickName;
    final String sex;
    final Map<String, Integer> attributes;
    final Map<String, Trait> traits;
    private final String displayName;

    public Dwarf(String name, String nickName, String sex, Map<String, Integer> attributes, Map<String, Trait> traits) {
        this.name = name;
        this.nickName = nickName;
        this.sex = sex;
        this.attributes = attributes;
        this.traits = traits;

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

        NodeList traitsElements = creature.getElementsByTagName("Trait");
        Map<String, Trait> traits = new LinkedHashMap<String, Trait>();
        for (int i = 0; i < traitsElements.getLength(); i++) {
            Element trait = (Element) traitsElements.item(i);
            if (trait.hasAttribute("name")) {
                traits.put(trait.getAttribute("name"),
                           new Trait(Integer.parseInt(trait.getAttribute("value")),
                                     trait.getTextContent()));
            } else {
                // TODO: Parse trait text into names and approximate values
                // for at least Honesty, Compromising and Neurosis
                traits.put("Trait" + i,
                           new Trait(-1, trait.getTextContent()));
            }
        }

        return new Dwarf(name, nickName, sex, attributes, traits);
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

    public void renderTo(javax.swing.text.Element body, boolean showOnlyPositive)
        throws IOException, BadLocationException {
        HTMLDocument d = (HTMLDocument) body.getDocument();
        d.insertBeforeEnd(body, nameHeader());
        d.insertBeforeEnd(body, mainLayout());
        d.insertBeforeEnd(d.getElement("left"), attributesTable());
        d.insertBeforeEnd(d.getElement("right"), traitsList());
        d.insertBeforeEnd(d.getElement("right"), positionList(showOnlyPositive));
    }

    private String positionList(boolean showOnlyPositive) {
        Map<Evaluation, Set<Position>> positions = new EnumMap<Evaluation, Set<Position>>(Evaluation.class);

        for (Position position : Position.values()) {
            Evaluation evaluation = position.evaluate(this);
            if (showOnlyPositive && evaluation.ordinal() > Evaluation.Reasonable.ordinal()) {
                continue;
            }

            if (!positions.containsKey(evaluation)) {
                positions.put(evaluation, EnumSet.noneOf(Position.class));
            }
            positions.get(evaluation).add(position);
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Evaluation, Set<Position>> e : positions.entrySet()) {
            sb.append("<h2>").append(e.getKey()).append("</h2>");

            sb.append("<ul>");
            for (Position position : e.getValue()) {
                sb.append("<li>").append(position).append("</li>");
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

    private String mainLayout() {
        return "<table valign='top'><tr><td id='left'></td><td id='right'></td></tr></table>";
    }

    private String nameHeader() {
        return String.format("<h1>%s</h1>", displayName);
    }

    private String attributesTable() {
        StringBuilder sb = new StringBuilder("<table>");
        for (Map.Entry<String, Integer> entry : attributes.entrySet()) {
            sb.append("<tr>")
                .append("<th>").append(entry.getKey()).append("</th>")
                .append("<td>").append(entry.getValue()).append("</td>")
                .append("</tr>");
        }

        return sb.append("</table>").toString();
    }

    private String traitsList() {
        StringBuilder sb = new StringBuilder("<ul>");
        for (Trait trait : traits.values()) {
            if (!trait.description.isEmpty()) {
                sb.append("<li>").append(trait.description).append("</li>");
            }
        }
        return sb.append("</ul><hr />").toString();
    }

    Integer attribute(String key) {
        return attributes.get(key);
    }

    Trait trait(String key) {
        return traits.get(key);
    }

    static class Trait
    {
        final int value;
        private final String description;

        private Trait(int value, String description) {
            this.value = value;
            this.description = description;
        }
    }
}
