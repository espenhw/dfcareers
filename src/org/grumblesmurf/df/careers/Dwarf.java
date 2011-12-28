package org.grumblesmurf.df.careers;

import org.w3c.dom.Element;

public class Dwarf implements Comparable<Dwarf>
{
    public static Dwarf from(Element creature) {
        return new Dwarf();
    }

    @Override
    public int compareTo(Dwarf o) {
        return 0;  // FIXME: Implement
    }
}
