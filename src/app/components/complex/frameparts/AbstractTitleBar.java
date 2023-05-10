package app.components.complex.frameparts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;

import app.components.buttons.ICXButton;
import app.components.complex.menuitems.MenuButton;
import app.components.panels.AbstractXPanel;
import app.components.panels.XPanel;
import app.frame.XFrame;
import support.appdata.AssetsData;
import support.appdata.SizeData;
import support.framework.appearances.BasicAppearance;
import support.framework.builders.CustomAppearanceBuilder;
import support.framework.interfaces.Appearance;
import support.util.Util;

public abstract class AbstractTitleBar extends AbstractXPanel {
    // Main buttons
    protected final ICXButton exitButton;
    protected final ICXButton iconifyButton;

    // Inner container
    protected final XPanel innerContainer;

    // Menu buttons
    protected final MenuButton createMenu;

    // "Full" constructor
    protected AbstractTitleBar(ImageIcon icon, String title, XFrame frame, Appearance appearance) {
        super(new Dimension(SizeData.WIDTH, SizeData.BUTTON_HEIGHT), new FlowLayout(FlowLayout.LEADING, 0, 0),
            frame, appearance);

        // Main buttons
        this.exitButton = new ICXButton(SizeData.NARROW_BUTTON_DIMENSION,
            Util.loadBigIcon(AssetsData.BUTTON_ICONS.concat("exit1")),
            Util.loadBigIcon(AssetsData.BUTTON_ICONS.concat("exit2")), this.frame, BasicAppearance.OPAQUE);
        // Close the frame and exit the application
        this.exitButton.addActionListener(e -> {
            this.frame.dispose();
            System.exit(0);
        });

        this.iconifyButton = new ICXButton(SizeData.NARROW_BUTTON_DIMENSION,
            Util.loadBigIcon(AssetsData.BUTTON_ICONS.concat("iconify1")),
            Util.loadBigIcon(AssetsData.BUTTON_ICONS.concat("iconify2")), this.frame, BasicAppearance.OPAQUE);
        // Set the frame's state to iconified
        this.iconifyButton.addActionListener(e -> this.frame.setExtendedState(JFrame.ICONIFIED));

        // Inner container
        this.innerContainer = new XPanel(new Dimension(this.getPreferredSize().width - (SizeData.NARROW_BUTTON_WIDTH * 2),
            this.getPreferredSize().height), this.getLayout(), this.frame, appearance);

        // Menu buttons
        this.createMenu = new MenuButton(SizeData.NARROW_BUTTON_DIMENSION, 0, 0,
            Util.loadBigIcon(AssetsData.BUTTON_ICONS.concat("menu1")),
            Util.loadBigIcon(AssetsData.BUTTON_ICONS.concat("menu2")), this.frame, BasicAppearance.OPAQUE,
            new CustomAppearanceBuilder()
                .addMainBackground(Color.black)
                .addBorder(new LineBorder(Color.red, SizeData.BORDER_SIZE))
                .build());

        // Add components to the innerContainer
        this.innerContainer.addComponent(this.createMenu);

        // Add components to the TitleBar
        this.addComponent(this.innerContainer);
        this.addComponent(this.iconifyButton);
        this.addComponent(this.exitButton);

        // Add the TitleBar to the frame
        this.frame.addComponent(this, BorderLayout.NORTH);
    }
}
