package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import main.AStar;
import main.Node;
import main.main;

/**
 *
 * @author hugop
 */
public class GuiMouseListener implements MouseListener {

    private Node node;
    private Node clickedNode;
    private AStar currentAStarInstance;

    @Override
    public void mouseEntered(MouseEvent e) {
        for (int i = 0; i < main.getGui().getListOfAll().size(); i++) {
            node = main.getGui().getListOfAll().get(i);
            if (main.getGui().getGenerateObstacles().isSelected()
                    && main.getGui().isAllowDrawing()
                    && e.getSource() == node.getNode()) {
                node.setObstacles(true);
                main.getGui().paintObstacles(node, main.getGui().getWidthSelectorSlider().getValue());
            }
            if (main.getGui().getEraseObstacles().isSelected()
                    && main.getGui().isAllowDrawing()
                    && e.getSource() == node.getNode()
                    && (node == main.getSwingWorker().getAStar().getStartNode()) == false
                    && (node == main.getSwingWorker().getAStar().getEndNode()) == false) {

                node.setObstacles(false);
                main.getGui().unpaintObstacles(node, main.getGui().getWidthSelectorSlider().getValue());
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == main.getGui().getStartAlgorithmButton() && main.getGui().getStartAlgorithmButton().isEnabled()) {
            main.start();
        } else if (e.getSource() == main.getGui().getGenerateRandomGrid() && main.getGui().getGenerateRandomGrid().isEnabled()) {
            main.getSwingWorker().setAStar(new AStar());
            main.getSwingWorker().getAStar().generateRandomGrid();
            main.getGui().getResetGrid().setEnabled(true);
            main.getGui().getGenerateRandomGrid().setEnabled(false);
        } else if (e.getSource() == main.getGui().getResetGrid() && main.getGui().getResetGrid().isEnabled()) {
            // Reset differents parameters to keep the validty of our code
            for (int i = 0; i < main.getSwingWorker().getAStarList().size(); i++) {
                currentAStarInstance = main.getSwingWorker().getAStarList().get(i);
                currentAStarInstance.getListOfNodes().clear();
                currentAStarInstance.getNeighbours().clear();
                currentAStarInstance.getOpenList().clear();
                currentAStarInstance.setEndNode(null);
                currentAStarInstance.setStartNode(null);
            }
            main.getSwingWorker().getAStarList().clear();
            main.getSwingWorker().getRunAlgoList().clear();
            main.setEndNodeCount(0);
            main.getGui().getContentPanel().removeAll();
            main.getGui().getListOfAll().clear();
            main.getGui().getGenerateObstacles().setEnabled(true);
            main.getGui().getEraseObstacles().setEnabled(true);
            main.getGui().getChooseStartPos().setEnabled(true);
            main.getGui().getChooseEndPos().setEnabled(true);
            main.getGui().getResetGrid().setEnabled(false);
            main.getGui().getGenerateRandomGrid().setEnabled(true);
            main.getGui().getStartAlgorithmButton().setEnabled(true);
            main.getGui().getWindowMenu().setEnabled(true);
            main.getGui().getGui().repaint();
            main.getGui().createNodes(main.getGui().getMatrixSize());
            main.getGui().getGui().revalidate();
            main.getSwingWorker().setRunAlgo(true);
        } else {
            // Iterate trough all the buttons
            for (int i = 0; i < main.getGui().getListOfAll().size(); i++) {
                clickedNode = main.getGui().getListOfAll().get(i);
                if (main.getGui().getChooseStartPos().isSelected() && e.getSource() == clickedNode.getNode()) {
                    if (main.getSwingWorker().getAStarList().isEmpty()) {
                        JOptionPane.showMessageDialog(main.getGui().getGui(), "First add the end nodes", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Generate the startNode and add it to the openList and paint it on the GUI
                        main.getSwingWorker().getAStarList().get(0).setStartNode(clickedNode);
                        main.getSwingWorker().getOpenSet().add(clickedNode);
                        main.getGui().paintStartNode(main.getSwingWorker().getAStarList().get(0).getStartNode());
                    }
                }
                if (main.getGui().getChooseEndPos().isSelected() && e.getSource() == clickedNode.getNode()) {
                    main.getSwingWorker().getAStarList().add(new AStar(clickedNode));
                    main.getSwingWorker().getRunAlgoList().add(true);
                    main.getGui().paintEndNode(clickedNode);
                    int endNodeCount = main.getEndNodeCount() + 1;
                    main.setEndNodeCount(endNodeCount);
                    System.out.println(main.getEndNodeCount());

                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
