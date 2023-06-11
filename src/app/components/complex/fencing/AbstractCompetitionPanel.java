package app.components.complex.fencing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import app.components.buttons.FCXButton;
import app.components.panels.AbstractXPanel;
import app.components.panels.XPanel;
import app.components.scrollpanels.XScrollPanel;
import app.frame.XFrame;
import support.appdata.AppearanceData;
import support.appdata.SizeData;
import support.constants.PositionConstants;
import support.framework.appearances.BasicAppearance;
import support.framework.builders.CustomAppearanceBuilder;
import support.framework.interfaces.Appearance;

public abstract class AbstractCompetitionPanel extends AbstractXPanel {
    // List for the poules
    protected final List<Poule> pouleList = new LinkedList<>();

    // Scroll panel
    protected final XScrollPanel scrollPanel;

    // Inner container for the bottom section
    private final XPanel bottomSection;

    // Buttons
    private final FCXButton calcAllButton;
    private final FCXButton finishButton;
    private final FCXButton closeButton;

    public AbstractCompetitionPanel(XFrame frame, Appearance appearance) {
        super(0, SizeData.BORDER_SIZE, SizeData.COMPETITION_PANEL_WIDTH,
            SizeData.COMPETITION_PANEL_HEIGHT, new BorderLayout(), frame, appearance);

        // Scroll panel
        this.scrollPanel = new XScrollPanel(new Dimension(this.getWidth(), this.getHeight()),
            new FlowLayout(FlowLayout.CENTER, SizeData.GAP, SizeData.GAP), this.frame,
            new CustomAppearanceBuilder()
                .addMainBackground(this.getAppearance().getMainBackground())
                .addBorder(this.getAppearance().getBorder())
                .build(),
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addMainForeground(Color.red)
                .build());

        // Inner container for the bottom section
        this.bottomSection = new XPanel(new Dimension(this.getWidth(), SizeData.BUTTON_HEIGHT),
            new FlowLayout(FlowLayout.LEADING, 0, 0), this.frame, BasicAppearance.BLACK);

        // Buttons
        this.calcAllButton = new FCXButton(SizeData.BUTTON_DIMENSION, "Calculate All", this.frame,
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addMainForeground(Color.white)
                .addSecondaryForeground(Color.red)
                .addBorder(AppearanceData.RED_BORDER)
                .build(),
            new CustomAppearanceBuilder()
                .addMainForeground(Color.red)
                .addSecondaryForeground(Color.yellow)
                .build());

        this.finishButton = new FCXButton(SizeData.BUTTON_DIMENSION, "Finish All", this.frame,
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addMainForeground(Color.white)
                .addSecondaryForeground(Color.red)
                .addBorder(AppearanceData.RED_BORDER)
                .build(),
            new CustomAppearanceBuilder()
                .addMainForeground(Color.red)
                .addSecondaryForeground(Color.yellow)
                .build());

        this.closeButton = new FCXButton(SizeData.BUTTON_DIMENSION, "Close", this.frame,
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addMainForeground(Color.white)
                .addSecondaryForeground(Color.red)
                .addBorder(AppearanceData.RED_BORDER)
                .build(),
            new CustomAppearanceBuilder()
                .addMainForeground(Color.red)
                .addSecondaryForeground(Color.yellow)
                .build());
        this.closeButton.addActionListener(e -> this.frame.closeCompetitionPanel());

        // Add components to the bottom section
        this.bottomSection.addComponent(this.calcAllButton);
        this.bottomSection.addComponent(this.finishButton);
        this.bottomSection.addComponent(this.closeButton);

        // Add the scroll panel to the panel
        this.addComponent(this.scrollPanel, BorderLayout.CENTER);
        this.addComponent(this.bottomSection, BorderLayout.SOUTH);
    }

    public void generatePoules(List<String> valueList) {
        // Collect the data from the list
        final String round = valueList.get(0);
        final String pouleName = valueList.get(1);
        final int fencersPoule = valueList.get(2).isBlank() ? 7 : Integer.parseInt(valueList.get(2));
        int numberOfFencers = Integer.parseInt(valueList.get(3));
        final String referee = valueList.get(4);
        final String date = valueList.get(5);

        // Calculations and Generations
        // Number of poules with the preferred amount of fencers
        // When the preferred size is bigger than the total amount of the fencers
        if (numberOfFencers <= fencersPoule) {
            final Poule poule = new Poule(this.frame, numberOfFencers, BasicAppearance.BLACK_BORDERED);
            this.pouleList.add(poule);
            this.insertComponent(poule);
        }
        // Generate the poules normally
        else {
            int numberOfPoules = numberOfFencers / fencersPoule;
            numberOfFencers -= numberOfPoules * fencersPoule;
            for (int i = 0; i < numberOfPoules; i++) {
                final Poule poule = new Poule(this.frame, fencersPoule, BasicAppearance.BLACK_BORDERED);
                this.pouleList.add(poule);
                this.insertComponent(poule);
            }

            // Generate the last poule from the remaining fencers
            if (numberOfFencers > numberOfPoules && numberOfFencers >= 4) {
                final Poule poule = new Poule(this.frame, numberOfFencers, BasicAppearance.BLACK_BORDERED);
                this.pouleList.add(poule);
                this.insertComponent(poule);
            }
            else if (numberOfFencers > numberOfPoules) {
                this.pouleList.get(0).reConstruct(this.pouleList.get(0).getAmount() - 1);
                numberOfFencers++;
                final Poule poule = new Poule(this.frame, numberOfFencers, BasicAppearance.BLACK_BORDERED);
                this.pouleList.add(poule);
                this.insertComponent(poule);
            }
            else if (numberOfFencers == 1) {
                this.pouleList.get(0).reConstruct(this.pouleList.get(0).getAmount() - 3);
                numberOfFencers += 3;
                final Poule poule = new Poule(this.frame, numberOfFencers, BasicAppearance.BLACK_BORDERED);
                this.pouleList.add(poule);
                this.insertComponent(poule);
            }
            else {
                int index = 0;
                while (numberOfFencers > 0) {
                    if (this.pouleList.get(index).getAmount() < 8) {
                        this.pouleList.get(index).reConstruct(this.pouleList.get(index).getAmount() + 1);

                        if (++index >= this.pouleList.size()) {
                            index = 0;
                        }
                        numberOfFencers--;
                    }
                }
            }
        }

        this.scrollPanel.getViewPanel().setPreferredSize(new Dimension(
            this.scrollPanel.getViewPanel().getPreferredSize().width,
            this.pouleList.size() * SizeData.POULE_HEIGHT + SizeData.GAP
        ));
        this.repaintFrame();
    }

    public void insertComponent(JComponent component, PositionConstants positionConstants) {
        this.scrollPanel.addComponent(component, positionConstants);
    }

    public void insertComponent(JComponent component) {
        this.insertComponent(component, PositionConstants.MID_POS);
    }

    public JComponent extractComponent(JComponent component) {
        return this.scrollPanel.removeComponent(component);
    }

    public void clearAll() {
        this.pouleList.forEach(this::extractComponent);
        this.pouleList.clear();
    }
}