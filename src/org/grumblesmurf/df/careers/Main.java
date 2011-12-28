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
import java.util.*;

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
            dwarf.renderTo(body, form.showOnlyPositive.isSelected());
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

    public static void displayJob(int selectedIndex) {
        Position position = Position.values()[selectedIndex];

        Map<Evaluation, Set<Dwarf>> eval = new EnumMap<Evaluation, Set<Dwarf>>(Evaluation.class);
        for (Dwarf dwarf : dwarvesList) {
            Evaluation evaluation = position.evaluate(dwarf);
            if (form.showOnlyPositive.isSelected() && evaluation.ordinal() > Evaluation.Reasonable.ordinal()) {
                continue;
            }

            if (!eval.containsKey(evaluation)) {
                eval.put(evaluation, new TreeSet<Dwarf>());
            }
            eval.get(evaluation).add(dwarf);
        }

        HTMLDocument document = (HTMLDocument) form.jobInfo.getDocument();
        try {
            javax.swing.text.Element body = newBodyElement(document);

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Evaluation, Set<Dwarf>> e : eval.entrySet()) {
                sb.append("<h2>").append(e.getKey()).append("</h2><ul>");
                for (Dwarf dwarf : e.getValue()) {
                    sb.append("<li>").append(dwarf).append("</li>");
                }
                sb.append("</ul>");
            }
            document.insertBeforeEnd(body, sb.toString());
        } catch (BadLocationException e) {
            reportError(e);
        } catch (IOException e) {
            reportError(e);
        }
    }

    private static class DwarvesListModel extends AbstractListModel implements Iterable<Dwarf>
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

        @Override
        public Iterator<Dwarf> iterator() {
            return dwarves.iterator();
        }
    }
}
