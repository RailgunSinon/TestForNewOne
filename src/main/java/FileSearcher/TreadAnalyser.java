package FileSearcher;

import Frames.ClossableJPanel;
import Frames.JTreeEntity;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;

public class TreadAnalyser extends Thread {

    private String path = "";
    private String extension = "";
    private String search = "";
    private int width;
    private int height;
    private int page;
    private File file = null;
    private boolean isLoaded = false;
    private JTabbedPane jTabbedPane;
    private JTextArea textArea = new JTextArea();

    public TreadAnalyser(String search, String path, String extension,
                         int width, int height, JTabbedPane jTabbedPane){

        this.search = search;
        this.path = path;
        this.extension = extension;
        this.width = width;
        this.height = height;
        this.jTabbedPane = jTabbedPane;
    }

    private void handleAnException(JComponent jComponent,Exception ex) {
        jComponent.add( new JLabel("An error has occurred: " + ex.getMessage()));
    }

    private void loadNextPage() throws Exception{
        if(!isLoaded){
            List<String> lines = FileReader.readPage(file,page);

            if(lines.size() > 0)
            {
                for (String line : lines){
                    textArea.append(line + "\n");
                }
                page++;
            } else {
                isLoaded = true;
            }
        }
    }

    @Override
    public void start(){

        JPanel tabPanel = new JPanel();
        tabPanel.setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        tabPanel.add(contentPanel);

        try {
            FileAnalyser fileAnalyser = new FileAnalyser();
            List<File> inputdata = fileAnalyser.tree(path,extension,search);

            JTreeEntity treeEntity = new JTreeEntity(path,inputdata);
            JTree tree = new JTree(treeEntity);

            tree.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    TreePath treePath = tree.getPathForLocation(e.getX(),e.getY());

                    if(treePath != null){
                        String path = treePath.getLastPathComponent().toString();

                        if(path.endsWith("." + extension)){
                            File infile = new File(path);

                            if(file != infile && infile.exists() && infile.isFile()){
                                textArea.setText("");
                                contentPanel.removeAll();
                                page = 0;
                                isLoaded = false;
                                file = infile;

                                try{
                                    DefaultCaret caret = (DefaultCaret) textArea.getCaret();
                                    caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
                                    JScrollPane scrollPane = new JScrollPane(textArea);

                                    AdjustmentListener adjustmentListener = e1 -> {
                                        if(scrollPane.getViewport().getHeight() + e1.getAdjustable().getValue()
                                          == e1.getAdjustable().getMaximum()){
                                            try{
                                                loadNextPage();
                                            }
                                            catch (Exception ex){
                                                handleAnException(contentPanel,ex);
                                            }
                                        }
                                    };
                                    scrollPane.getVerticalScrollBar().addAdjustmentListener(adjustmentListener);
                                    contentPanel.add(scrollPane);

                                    tabPanel.validate();
                                    tabPanel.repaint();
                                }catch (Exception ex){
                                    handleAnException(contentPanel,ex);
                                }
                            }
                        }
                    }
                }
            });

            tabPanel.add(tree,BorderLayout.WEST);
        } catch (Exception e) {
            handleAnException(tabPanel,e);
        }

        jTabbedPane.add(tabPanel);
        jTabbedPane.setTabComponentAt(jTabbedPane.indexOfComponent(tabPanel), ClossableJPanel.getTitlePanel(jTabbedPane,tabPanel,"Result"));
    }


}
