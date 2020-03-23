package Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClossableJPanel {
    public static JPanel getTitlePanel(final JTabbedPane tabbedPane,final JPanel jPanel,String title){
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        titlePanel.setOpaque(false);
        JLabel titleJLabel = new JLabel(title);
        titleJLabel.setBorder(BorderFactory.createEmptyBorder(0,0,0,5));
        titlePanel.add(titleJLabel);
        JButton closeButton = new JButton("X");
        closeButton.setBorder(null);

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tabbedPane.remove(jPanel);
            }
        });
        titlePanel.add(closeButton);

        return titlePanel;
    }
}
