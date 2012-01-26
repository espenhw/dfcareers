package org.grumblesmurf.df.careers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.html.HTMLDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class MainForm
{
    private JTabbedPane tabbedPane1;
    private JTextField filename;
    private JButton browse;
    private JList dwarves;
    JTextPane dwarfInfo;
    private JList jobs;
    JTextPane jobInfo;
    JPanel contentPane;
    JCheckBox showOnlyPositive;
    private JPanel dwarvesPanel;
    private JPanel jobsPanel;
    JTextPane embarkHelp;
    private JButton reload;

    private JFileChooser chooser = new JFileChooser(new File("."));
    private File file;

    public MainForm() {
        chooser.setDialogTitle("Open XML file");
        chooser.setFileFilter(new XmlFileFilter());
        chooser.addChoosableFileFilter(new XmlFileFilter());

        browse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    file = chooser.getSelectedFile();
                    filename.setText(file.getPath());
                    reload.setEnabled(true);
                    Main.readDwarvesFrom(file);
                }
            }
        });
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
        showOnlyPositive.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tabbedPane1.getSelectedComponent() == dwarvesPanel) {
                    Main.displayDwarf(dwarves.getSelectedIndex());
                } else {
                    Main.displayJob(jobs.getSelectedValue().toString());
                }
            }
        });
        reload.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.readDwarvesFrom(file);
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

    private static class XmlFileFilter extends FileFilter
    {
        @Override
        public boolean accept(File f) {
            return f.isDirectory() || f.getName().endsWith(".xml");
        }

        @Override
        public String getDescription() {
            return "*.xml";
        }
    }
}
