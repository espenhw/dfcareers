package org.grumblesmurf.df.careers;

import javax.swing.*;
import java.awt.*;

public class DwarfRenderer implements ListCellRenderer
{
    private final ImageIcon male = createImageIcon("/icons/user.png", "Male");
    private final ImageIcon female = createImageIcon("/icons/user_female.png", "Female");

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {
        Dwarf dwarf = (Dwarf) value;
        JLabel jLabel = new JLabel(value.toString());
        jLabel.setIcon(createImageIconFor(dwarf));
        if (isSelected) {
            jLabel.setOpaque(true);
            jLabel.setBackground(list.getSelectionBackground());
            jLabel.setForeground(list.getSelectionForeground());
        } else {
            jLabel.setBackground(list.getBackground());
            jLabel.setForeground(list.getForeground());
        }
        return jLabel;
    }

    private Icon createImageIconFor(Dwarf dwarf) {
        return "Female".equals(dwarf.sex) ? female : male;
    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        return new ImageIcon(imgURL, description);
    }
}
