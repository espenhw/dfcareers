package org.grumblesmurf.df.careers;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.html.HTMLDocument;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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

    private JFileChooser chooser = new JFileChooser(new File("."));

    public MainForm() {
        chooser.setDialogTitle("Open XML file");
        chooser.setFileFilter(new XmlFileFilter());
        chooser.addChoosableFileFilter(new XmlFileFilter());

        browse.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    filename.setText(file.getPath());
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
                    Main.displayJob(jobs.getSelectedIndex());
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
                    Main.displayJob(jobs.getSelectedIndex());
                }
            }
        });
    }

    private void createUIComponents() {
        dwarves = new JList(Main.dwarvesModel());
        dwarves.setCellRenderer(new DwarfRenderer());

        dwarfInfo = new JTextPane(new HTMLDocument());
        dwarfInfo.setContentType("text/html");
        dwarfInfo.setText("<html><head><style>th { text-align: right; }</style></head><body id='body'></body></html>");

        jobs = new JList(Position.values());

        jobInfo = new JTextPane(new HTMLDocument());
        jobInfo.setContentType("text/html");
        jobInfo.setText("<html><head><style>th { text-align: right; }</style></head><body id='body'></body></html>");

        embarkHelp = new JTextPane(new HTMLDocument());
        embarkHelp.setContentType("text/html");
        embarkHelp.setText("<html><head><style>th { text-align: right; }</style></head><body id='body'></body></html>");
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
