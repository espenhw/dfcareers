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
    private JTextPane jobInfo;
    JPanel contentPane;

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
    }

    private void createUIComponents() {
        dwarves = new JList(Main.dwarvesModel());
        dwarves.setCellRenderer(new DwarfRenderer());

        dwarfInfo = new JTextPane(new HTMLDocument());
        dwarfInfo.setContentType("text/html");
        dwarfInfo.setText("<html><body id='body'></body></html>");
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
