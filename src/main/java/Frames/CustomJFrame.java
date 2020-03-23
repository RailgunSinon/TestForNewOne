package Frames;

import FileSearcher.TreadAnalyser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CustomJFrame extends JFrame {

    private JFileChooser fileChooser = new JFileChooser();
    private JTextField search = new JTextField();
    private JTextField fileExtension = new JTextField("log");
    private JTabbedPane tabbedPane = new JTabbedPane();
    private int width = 800;
    private int height = 600;
    private File selectedFile;
    private JButton searchButton = new JButton("Search");
    private JButton directoryButton = new JButton("Choose a directory");
    private JLabel fileExtensionLabel = new JLabel("File extension");
    private JLabel fileChooserValue = new JLabel("No directory selected");
    private JLabel message = new JLabel();
    private JLabel searchingLabel = new JLabel("What should i find?");
    private JLabel buttonLabel = new JLabel("Where should i search this?");

    private class JFileChooserActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setCurrentDirectory(new File("/var/log"));

            int response = fileChooser.showOpenDialog(CustomJFrame.this);
            message.setVisible(false);

            switch (response)
            {
                case JFileChooser.APPROVE_OPTION:
                    fileChooserValue.setText(fileChooser.getSelectedFile().toString());
                    selectedFile = fileChooser.getSelectedFile();
                    break;
                case JFileChooser.ERROR_OPTION:
                    message.setText("An error has occurred");
                    message.setVisible(true);
                    break;
            }
        }
    }

    private class JButtonSearchActionListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(selectedFile != null)
            {
                Thread thread ;
                thread = new TreadAnalyser(search.getText(),selectedFile.toString(), fileExtension.getText(),
                                          width,height,tabbedPane);
                thread.start();
            }
        }
    }

    public CustomJFrame(){
        super();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(groupLayout);
        groupLayout.setAutoCreateGaps(true);
        groupLayout.setAutoCreateContainerGaps(true);

        message.setVisible(false);
        search.setMaximumSize(new Dimension(width,20));
        directoryButton.setMaximumSize(new Dimension(width/2,20));
        ActionListener actionListener = new JFileChooserActionListener();
        directoryButton.addActionListener(actionListener);
        fileExtension.setMaximumSize(new Dimension(width,25));
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        searchButton.addActionListener(new JButtonSearchActionListener());

        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup()
                           .addComponent(message)
                           .addGroup(
                                   groupLayout.createSequentialGroup()
                                              .addComponent(searchingLabel)
                                              .addGap(20)
                                              .addComponent(search)
                           ).addGroup(
                                   groupLayout.createSequentialGroup()
                                              .addComponent(buttonLabel)
                                              .addGap(20)
                                              .addComponent(fileChooserValue)
                                              .addComponent(directoryButton)
                           ).addGroup(
                                   groupLayout.createSequentialGroup()
                                              .addComponent(fileExtensionLabel)
                                              .addGap(20)
                                              .addComponent(fileExtension)
                                              .addComponent(searchButton)
                           ).addGroup(
                                   groupLayout.createSequentialGroup()
                                              .addComponent(tabbedPane)
                )
        );

        groupLayout.setVerticalGroup(
                groupLayout.createSequentialGroup()
                           .addComponent(message)
                           .addGroup(
                                   groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                              .addComponent(searchingLabel)
                                              .addComponent(search)
                                   ).addGroup(
                                           groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                      .addComponent(buttonLabel)
                                                      .addComponent(fileChooserValue)
                                                      .addComponent(directoryButton)
                                   ).addGroup(
                                           groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                      .addComponent(fileExtensionLabel)
                                                      .addComponent(fileExtension)
                                                      .addComponent(searchButton)
                                   ).addGroup(
                                           groupLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                      .addComponent(tabbedPane)
                )
        );

        setSize(width, height);

    }
}
