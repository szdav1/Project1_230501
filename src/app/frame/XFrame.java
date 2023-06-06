package app.frame;

import app.components.complex.editors.PouleEditor;
import app.components.complex.frameparts.CenterPanel;
import app.components.complex.frameparts.ContentPanel;
import app.components.complex.frameparts.TitleBar;
import support.appdata.AppearanceData;
import support.constants.PositionConstants;
import support.framework.appearances.BasicAppearance;
import support.framework.builders.CustomAppearanceBuilder;

import javax.swing.JComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

public final class XFrame extends AbstractXFrame {
    // Frame parts
    // Content panel
    private final ContentPanel contentPanel;
    private final TitleBar titleBar;
    private final CenterPanel centerPanel;

    // Editors
    private final PouleEditor pouleEditor;

    // Competition panel
    private final CompetitionPanel competitionPanel;

    public XFrame(Image iconImage, String title) {
        super(iconImage, title);
        this.setLocationRelativeTo(null);

        // Frame parts
        // Content panel
        this.contentPanel = new ContentPanel(this, BasicAppearance.BLACK);
        // Set the contentPanel as the content pane of the frame
        this.setContentPane(this.contentPanel);

        this.titleBar = new TitleBar(null, "", this, BasicAppearance.BLACK);
        this.centerPanel = new CenterPanel(this, BasicAppearance.BLACK);

        // Poule editor
        this.pouleEditor = new PouleEditor(this,
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addBorder(AppearanceData.RED_BORDER)
                .build());

        // Competition panel
        this.competitionPanel = new CompetitionPanel(this,
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addBorder(AppearanceData.RED_BORDER)
                .build());

        // Add components to the frame
        this.insertComponent(this.competitionPanel);

        // Set the visibility of the frame
        this.setVisible(true);
    }

    public void togglePouleEditor() {
        if (this.getFrameState() == XFrameConstants.NORMAL) {
            this.setFrameState(XFrameConstants.EDITOR_OPENED);
            this.insertComponent(this.pouleEditor, PositionConstants.TOP_POS);
        }
    }

    public void closePouleEditor() {
        this.setFrameState(XFrameConstants.NORMAL);
        this.extractComponent(this.pouleEditor);
    }

    public void insertComponent(JComponent component, PositionConstants positionConstants) {
        this.centerPanel.addComponent(component, positionConstants);
        this.repaint();
    }

    public void insertComponent(JComponent component) {
        this.insertComponent(component, PositionConstants.MID_POS);
        this.repaint();
    }

    public void extractComponent(JComponent component) {
        this.centerPanel.removeComponent(component);
    }

    @Override
    public void addComponent(JComponent component, PositionConstants positionConstants) {
        this.add(component, positionConstants.getzIndex());
        this.repaint();
    }

    @Override
    public void addComponent(JComponent component, String borderLayoutPosition) {
        if (!(this.getLayout() instanceof BorderLayout)) {
            return;
        }

        this.add(component, borderLayoutPosition);
        this.repaint();
    }

    @Override
    public void addComponent(JComponent component) {
        this.addComponent(component, PositionConstants.MID_POS);
        this.repaint();
    }

    @Override
    public JComponent removeComponent(JComponent component) {
        this.remove(component);
        this.repaint();
        return component;
    }
}
