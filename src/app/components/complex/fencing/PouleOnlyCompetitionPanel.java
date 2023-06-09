package app.components.complex.fencing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import app.frame.XFrame;
import app.other.Fencer;
import support.appdata.AppearanceData;
import support.appdata.SizeData;
import support.constants.PositionConstants;
import support.framework.builders.CustomAppearanceBuilder;
import support.framework.interfaces.Appearance;

public final class PouleOnlyCompetitionPanel extends AbstractCompetitionPanel {
    // List for the poules
    private final List<Poule> pouleList = new LinkedList<>();

    public PouleOnlyCompetitionPanel(XFrame frame, Appearance appearance) {
        super(frame, appearance);
    }

    public void generatePoules(List<String> valueList, List<Fencer> fencerList) {
        // Poule number
        int number = 1;
        // Collect the data from the list
        final String round = valueList.get(0);
        int fencersPoule = valueList.get(1).isBlank() ? 5 : Integer.parseInt(valueList.get(1));
        int numberOfFencers = Integer.parseInt(valueList.get(2));
        final String date = valueList.get(3);

        final boolean isDefaultValuesUsed = valueList.get(1).isBlank();

        // Generating poules with the optimal amount of fencers in them
        // This happens when the fencers/poule input field is left empty
        if (isDefaultValuesUsed) {
            if (numberOfFencers <= fencersPoule) {
                final Poule poule = new Poule(this.frame, numberOfFencers,
                    new CustomAppearanceBuilder()
                        .addMainBackground(Color.black)
                        .addBorder(AppearanceData.RED_BORDER)
                        .build());
                poule.setNumber(String.valueOf(number));
                this.pouleList.add(poule);
                this.insertComponent(poule);

                number++;
            }
            // Generate poules normally
            else {
                if (numberOfFencers % fencersPoule == 0) {
                    int numberOfPoules = numberOfFencers / fencersPoule;

                    for (int i = 0; i < numberOfPoules; i++) {
                        final Poule poule = new Poule(this.frame, fencersPoule,
                            new CustomAppearanceBuilder()
                                .addMainBackground(Color.black)
                                .addBorder(AppearanceData.RED_BORDER)
                                .build());
                        poule.setNumber(String.valueOf(number));
                        this.pouleList.add(poule);
                        this.insertComponent(poule);
                        number++;
                    }
                }
                else if (numberOfFencers % 2 == 0) {
                    while (numberOfFencers % fencersPoule != 0 && fencersPoule < 8) {
                        fencersPoule++;
                    }

                    int numberOfPoules = numberOfFencers / fencersPoule;
                    numberOfFencers -= numberOfPoules * fencersPoule;
                    for (int i = 0; i < numberOfPoules; i++) {
                        final Poule poule = new Poule(this.frame, fencersPoule,
                            new CustomAppearanceBuilder()
                                .addMainBackground(Color.black)
                                .addBorder(AppearanceData.RED_BORDER)
                                .build());
                        poule.setNumber(String.valueOf(number));
                        this.pouleList.add(poule);
                        this.insertComponent(poule);
                        number++;
                    }
                }
                else {
                    while (numberOfFencers % fencersPoule < 4 && fencersPoule < 8) {
                        fencersPoule++;
                    }

                    int numberOfPoules = numberOfFencers / fencersPoule;
                    numberOfFencers -= numberOfPoules * fencersPoule;
                    for (int i = 0; i < numberOfPoules; i++) {
                        final Poule poule = new Poule(this.frame, fencersPoule,
                            new CustomAppearanceBuilder()
                                .addMainBackground(Color.black)
                                .addBorder(AppearanceData.RED_BORDER)
                                .build());
                        poule.setNumber(String.valueOf(number));
                        this.pouleList.add(poule);
                        this.insertComponent(poule);
                        number++;
                    }
                }

                // Generate remaining poule
                if (numberOfFencers >= 4 && numberOfFencers <= 8) {
                    final Poule poule = new Poule(this.frame, numberOfFencers,
                        new CustomAppearanceBuilder()
                            .addMainBackground(Color.black)
                            .addBorder(AppearanceData.RED_BORDER)
                            .build());
                    poule.setNumber(String.valueOf(number));
                    this.pouleList.add(poule);
                    this.insertComponent(poule);
                    number++;
                }
            }
        }
        // Generating poules without optimization
        // This happens when the used enters a value for the fencers/poule inut field
        else {
            int numberOfPoules = numberOfFencers / fencersPoule;
            numberOfFencers -= numberOfPoules * fencersPoule;
            for (int i = 0; i < numberOfPoules; i++) {
                final Poule poule = new Poule(this.frame, fencersPoule,
                    new CustomAppearanceBuilder()
                        .addMainBackground(Color.black)
                        .addBorder(AppearanceData.RED_BORDER)
                        .build());
                poule.setNumber(String.valueOf(number));
                this.pouleList.add(poule);
                this.insertComponent(poule);
                number++;
            }

            // Generate the last poule from the remaining fencers
            // 1. When the reminder is enough to make another poule
            if (numberOfFencers >= 4 && numberOfFencers <= 8) {
                final Poule poule = new Poule(this.frame, numberOfFencers,
                    new CustomAppearanceBuilder()
                        .addMainBackground(Color.black)
                        .addBorder(AppearanceData.RED_BORDER)
                        .build());
                poule.setNumber(String.valueOf(number));
                this.pouleList.add(poule);
                this.insertComponent(poule);
                number++;
            }
            // 2. When the reminder is not enough to make another poule
            else if (numberOfFencers < 4) {
                if (this.pouleList.get(0).getAmount() + numberOfFencers <= 8) {
                    this.pouleList.get(0).reConstruct(this.pouleList.get(0).getAmount() + numberOfFencers);
                }
                else if (this.pouleList.get(0).getAmount() + numberOfFencers > 8) {
                    this.pouleList.get(0).reConstruct(this.pouleList.get(0).getAmount() - (4 - numberOfFencers));
                    numberOfFencers += (4 - numberOfFencers);
                    final Poule poule = new Poule(this.frame, numberOfFencers,
                        new CustomAppearanceBuilder()
                            .addMainBackground(Color.black)
                            .addBorder(AppearanceData.RED_BORDER)
                            .build());
                    poule.setNumber(String.valueOf(number));
                    this.pouleList.add(poule);
                    this.insertComponent(poule);
                    number++;
                }
            }
        }

        // Fill the poules with the names if given
        int nameIndex = 0;
        if (!fencerList.isEmpty()) {
            for (Poule poule : pouleList) {
                for (int y = 1; y < poule.getAmount() + 1; y++) {
                    poule.boxArray[y][0].setText(fencerList.get(nameIndex).getName());
                    nameIndex++;
                }
            }
        }

        // Resize the scroll panel to the desired dimensions
        this.scrollPanel.getViewPanel().setPreferredSize(new Dimension(
            this.scrollPanel.getViewPanel().getPreferredSize().width,
            this.pouleList.size() * SizeData.POULE_HEIGHT + SizeData.GAP
        ));
        this.repaintFrame();
    }

    public void clearAll() {
        this.pouleList.forEach(this::extractComponent);
        this.pouleList.clear();
    }

    @Override
    public void addComponent(JComponent component, PositionConstants positionConstants) {
        this.add(component, positionConstants.getzIndex());
        this.repaintFrame();
    }

    @Override
    public void addComponent(JComponent component, String borderLayoutPosition) {
        if (this.getLayout() instanceof BorderLayout) {
            this.add(component, borderLayoutPosition);
            this.repaintFrame();
        }
    }

    @Override
    public void addComponent(JComponent component) {
        this.addComponent(component, PositionConstants.MID_POS);
        this.repaintFrame();
    }

    @Override
    public JComponent removeComponent(JComponent component) {
        this.remove(component);
        this.repaintFrame();
        return component;
    }

    @Override
    public void repaintFrame() {
        if (this.frame != null) {
            this.frame.repaint();
        }
    }

    @Override
    public Appearance getAppearance() {
        return this.appearance;
    }

    @Override
    public XFrame getFrame() {
        return this.frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final Object source = e.getSource();
        if (source.equals(this.finishButton.getButton())) {
            this.pouleList.forEach(Poule::calculateFencerData);
        }
    }
}
