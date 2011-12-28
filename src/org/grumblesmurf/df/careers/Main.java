package org.grumblesmurf.df.careers;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.max;

public class Main
{
    private static JFrame frame;
    private static DwarvesListModel dwarvesList = new DwarvesListModel();
    private static MainForm form;

    public static void main(String[] args)
        throws ClassNotFoundException, UnsupportedLookAndFeelException, IllegalAccessException, InstantiationException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        frame = new JFrame("DFCareers");
        form = new MainForm();
        frame.setContentPane(form.contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public static void readDwarvesFrom(File file) {
        dwarvesList.clear();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            Document doc = dbFactory.newDocumentBuilder().parse(file);
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

    public static void displayDwarf(int index) {
        Dwarf dwarf = dwarvesList.getElementAt(index);
        HTMLDocument document = (HTMLDocument) form.dwarfInfo.getDocument();
        try {
            javax.swing.text.Element body = newBodyElement(document);
            dwarf.renderTo(body);
        } catch (BadLocationException e) {
            reportError(e);
        } catch (IOException e) {
            reportError(e);
        }
    }

    private static javax.swing.text.Element newBodyElement(HTMLDocument document)
        throws BadLocationException, IOException {
        javax.swing.text.Element body = document.getElement("body");
        document.setOuterHTML(body, "<body id='body'></body>");
        body = document.getElement("body");
        return body;
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
