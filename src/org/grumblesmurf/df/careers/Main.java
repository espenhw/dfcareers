package org.grumblesmurf.df.careers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

public class Main
{
    private static JFrame frame;
    private static DwarvesListModel dwarvesList = new DwarvesListModel();

    public static void main(String[] args)
        throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        frame = new JFrame("DFCareers");
        frame.setContentPane(new MainForm().contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    public static void readDwarvesFrom(File file) {
        dwarvesList.clear();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList creatures = doc.getElementsByTagName("Creature");
            List<Dwarf> newDwarves = new LinkedList<Dwarf>();
            for (int i = 0; i < creatures.getLength(); i++) {
                Node creature = creatures.item(i);
                newDwarves.add(Dwarf.from((Element) creature));
            }
            dwarvesList.addAll(newDwarves);
        } catch (Exception e) {
            reportError(e);
        }
    }

    private static void reportError(Exception e) {
        JOptionPane.showMessageDialog(frame, e);
    }

    public static ListModel dwarvesModel() {
        return dwarvesList;
    }

    private static class DwarvesListModel extends AbstractListModel
    {
        private final List<Dwarf> dwarves;

        private DwarvesListModel() {
            dwarves = new ArrayList<Dwarf>();
        }

        public int getSize() {
            return dwarves.size();
        }

        public Dwarf getElementAt(int index) {
            return dwarves.get(index);
        }

        public void clear() {
            int size = dwarves.size();
            dwarves.clear();
            fireIntervalRemoved(this, 0, max(0, size - 1));
        }

        public void addAll(List<Dwarf> newDwarves) {
            dwarves.addAll(newDwarves);
            Collections.sort(dwarves);
            fireIntervalAdded(this, 0, max(0, dwarves.size() - 1));
        }
    }
}
