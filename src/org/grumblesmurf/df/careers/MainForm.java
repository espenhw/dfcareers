package org.grumblesmurf.df.careers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.html.HTMLDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

public class MainForm
{
    private JTabbedPane tabbedPane1;
    private JList dwarves;
    JTextPane dwarfInfo;
    private JList jobs;
    JTextPane jobInfo;
    JPanel contentPane;
    private JPanel dwarvesPanel;
    private JPanel jobsPanel;
    JTextPane embarkHelp;
    private JButton load;

    public MainForm() {
        dwarves.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Main.displayDwarf(dwarves.getSelectedIndex());
                }
            }
        });
        jobs.addListSelectionListener(new ListSelectionListener()
        {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Main.displayJob(jobs.getSelectedValue().toString());
                }
            }
        });
        load.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.readDwarves();
                } catch (Exception e1) {
                    Main.reportError(e1);
                }
            }
        });
    }

    private void createUIComponents() {
        dwarves = new JList(Main.dwarvesModel());
        dwarves.setCellRenderer(new DwarfRenderer());

        dwarfInfo = newHtmlPane();

        Position[] values = Position.values();
        Arrays.sort(values, new Comparator<Position>()
        {
            @Override
            public int compare(Position o1, Position o2) {
                return o1.name().compareTo(o2.name());
            }
        });
        jobs = new JList(values);

        jobInfo = newHtmlPane();

        embarkHelp = newHtmlPane();
    }

    private JTextPane newHtmlPane() {
        JTextPane tmp = new JTextPane(new HTMLDocument());
        tmp.setContentType("text/html");
        tmp.setText("<html><head><style>th { text-align: right; }</style></head><body id='body'></body></html>");
        return tmp;
    }
}
