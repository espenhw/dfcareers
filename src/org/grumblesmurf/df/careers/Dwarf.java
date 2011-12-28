package org.grumblesmurf.df.careers;

import org.w3c.dom.Element;

import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import java.io.IOException;

public class Dwarf implements Comparable<Dwarf>
{
    final String name;
    private final String nickName;
    final String sex;
    private final String displayName;

    public Dwarf(String name, String nickName, String sex) {
        this.name = name;
        this.nickName = nickName;
        this.sex = sex;

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
        return new Dwarf(name, nickName, sex);
    }

    private static String textContentOfFirstChildNamed(String name, Element parent) {
        return parent.getElementsByTagName(name).item(0).getTextContent();
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
        
    }
}
