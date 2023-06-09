package app.components.complex.inputs;

import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.SwingConstants;

import app.components.labels.XLabel;
import app.components.panels.AbstractXPanel;
import app.components.textcontainers.XTextField;
import app.frame.XFrame;
import support.appdata.SizeData;
import support.framework.builders.CustomAppearanceBuilder;
import support.framework.interfaces.Appearance;

public abstract class AbstractInputField extends AbstractXPanel implements MouseListener, FocusListener, KeyListener {
    protected boolean required;
    protected boolean isErrorPresent;

    // Title label
    protected final XLabel titleLabel;
    // Input field
    protected final XTextField inputField;

    protected AbstractInputField(String title, XFrame frame, Appearance panelAppearance, Appearance inputAppearance) {
        super(SizeData.INPUT_FIELD_DIMENSION, null, frame, panelAppearance);

        this.required = false;
        this.isErrorPresent = false;

        // Title label
        this.titleLabel = new XLabel(0, 0, SizeData.INPUT_FIELD_WIDTH, SizeData.INPUT_FIELD_HEIGHT / 2,
            title, this.frame,
            new CustomAppearanceBuilder()
                .addMainBackground(this.getAppearance().getMainBackground())
                .build());
        this.titleLabel.setHorizontalAlignment(SwingConstants.LEFT);

        // Input field
        this.inputField = new XTextField(0, this.titleLabel.getHeight(), this.titleLabel.getWidth(), this.titleLabel.getHeight(),
            this.frame, inputAppearance);
        this.inputField.addMouseListener(this);
        this.inputField.addFocusListener(this);
        this.inputField.addKeyListener(this);

        // Add the components to the panel
        this.addComponent(this.titleLabel);
        this.addComponent(this.inputField);
    }

    protected AbstractInputField(int x, int y, String title, XFrame frame, Appearance panelAppearance,
        Appearance inputAppearance) {
        this(title, frame, panelAppearance, inputAppearance);
        this.setBounds(x, y, SizeData.INPUT_FIELD_WIDTH, SizeData.INPUT_FIELD_HEIGHT);
    }
}
