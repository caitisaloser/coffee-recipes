package gui.screens;

import gui.MainScreen;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.nio.file.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.*;
import javax.swing.plaf.metal.*;

import data.AppData;
import data.RecipeInfo;

@SuppressWarnings("serial")
public class Recipe extends JPanel {

    private MainScreen parent;
    private boolean isCreatingNew = false;
    private boolean isUpdatingCombo = false;
    private File pendingImageFile = null;

    private static final int MAIN_SCREEN_WIDTH = MainScreen.MainScreenWidth;
    private static final int MAIN_SCREEN_HEIGHT = MainScreen.MainScreenHeight;
    private static final Dimension MAIN_SCREEN_SIZE = MainScreen.MainScreenSize;

    private static final int MAIN_TO_INNER_GAP = 50;
    private static final int INNER_SCREEN_WIDTH = MAIN_SCREEN_WIDTH - MAIN_TO_INNER_GAP;
    private static final int INNER_SCREEN_HEIGHT = MAIN_SCREEN_HEIGHT - MAIN_TO_INNER_GAP;
    private static final Dimension INNER_SCREEN_SIZE = new Dimension(INNER_SCREEN_WIDTH, INNER_SCREEN_HEIGHT);

    private static final int TOP_PANEL_WIDTH = INNER_SCREEN_WIDTH;
    private static final int TOP_PANEL_COMBO_WIDTH = 270;
    private static final int TOP_PANEL_HEIGHT = 70;
    private static final Dimension TOP_PANEL_SIZE = new Dimension(TOP_PANEL_WIDTH, TOP_PANEL_HEIGHT);
    private static final Dimension TOP_PANEL_COMBO_SIZE = new Dimension(TOP_PANEL_COMBO_WIDTH, TOP_PANEL_HEIGHT);
    private static final Dimension TOP_PANEL_BTN_SIZE = new Dimension(TOP_PANEL_HEIGHT, TOP_PANEL_HEIGHT);

    private static final int INFO_PANEL_WIDTH = INNER_SCREEN_WIDTH;
    private static final int INFO_PANEL_DISPLAY_HEIGHT = 220;
    private static final int INFO_PANEL_EDIT_HEIGHT = 185;
    private static final Dimension INFO_PANEL_DISPLAY_SIZE = new Dimension(INFO_PANEL_WIDTH, INFO_PANEL_DISPLAY_HEIGHT);
    private static final Dimension INFO_PANEL_EDIT_SIZE = new Dimension(INFO_PANEL_WIDTH, INFO_PANEL_EDIT_HEIGHT);

    private static final int BOTTOM_PANEL_WIDTH = INNER_SCREEN_WIDTH;
    private static final int BOTTOM_PANEL_HEIGHT = 50;
    private static final Dimension BOTTOM_PANEL_SIZE = new Dimension(BOTTOM_PANEL_WIDTH, BOTTOM_PANEL_HEIGHT);

    private static final int INNER_PANEL_GAP = 20;
    private static final int INNER_PANEL_HGAP_WIDTH = INNER_SCREEN_WIDTH;
    private static final Dimension INNER_PANEL_HGAP_SIZE = new Dimension(INNER_PANEL_HGAP_WIDTH, INNER_PANEL_GAP);
    private static final Dimension TOP_PANEL_VGAP_SIZE = new Dimension(INNER_PANEL_GAP, TOP_PANEL_HEIGHT);
    private static final Dimension BOTTOM_PANEL_VGAP_SIZE = new Dimension(INNER_PANEL_GAP, BOTTOM_PANEL_HEIGHT);

    private static final int SECOND_PANEL_HALF_WIDTH = 215;
    private static final Dimension SECOND_PANEL_HALF_DISPLAY_SIZE = new Dimension(SECOND_PANEL_HALF_WIDTH, INFO_PANEL_DISPLAY_HEIGHT);
    private static final Dimension SECOND_PANEL_HALF_EDIT_SIZE = new Dimension(SECOND_PANEL_HALF_WIDTH, INFO_PANEL_EDIT_HEIGHT);

    private static final int INFO_LABEL_HEIGHT = 40;
    private static final int INFO_TEXT_DISPLAY_HEIGHT = INFO_PANEL_DISPLAY_HEIGHT - INFO_LABEL_HEIGHT;
    private static final int INFO_TEXT_EDIT_HEIGHT = INFO_PANEL_EDIT_HEIGHT - INFO_LABEL_HEIGHT;
    private static final Dimension INGREDIENT_LABEL_SIZE = new Dimension(SECOND_PANEL_HALF_WIDTH, INFO_LABEL_HEIGHT);
    private static final Dimension INGREDIENT_TEXT_DISPLAY_SIZE = new Dimension(SECOND_PANEL_HALF_WIDTH, INFO_TEXT_DISPLAY_HEIGHT);
    private static final Dimension INGREDIENT_TEXT_EDIT_SIZE = new Dimension(SECOND_PANEL_HALF_WIDTH, INFO_TEXT_EDIT_HEIGHT);
    private static final Dimension INSTRUCTION_LABEL_SIZE = new Dimension(INFO_PANEL_WIDTH, INFO_LABEL_HEIGHT);
    private static final Dimension INSTRUCTION_TEXT_DISPLAY_SIZE = new Dimension(INFO_PANEL_WIDTH, INFO_TEXT_DISPLAY_HEIGHT);
    private static final Dimension INSTRUCTION_TEXT_EDIT_SIZE = new Dimension(INFO_PANEL_WIDTH, INFO_TEXT_EDIT_HEIGHT);

    private static final int BOTTOM_PANEL_BTN_WIDTH = 120;
    private static final Dimension BOTTOM_PANEL_BTN_SIZE = new Dimension(BOTTOM_PANEL_BTN_WIDTH, BOTTOM_PANEL_HEIGHT);

    private static final Color MAIN_BG_COLOR = MainScreen.bgColor;
    private static final Color TEXT_AREAS_BG_COLOR = Color.white;
    private static final Color TEXT_LABEL_BG_COLOR = new Color(0xd9ccad);
    private static final Color DEFAULT_BTN_BG_COLOR = new Color(0x53411c);
    private static final Color CLICKED_BTN_BG_COLOR = new Color(0x382c12);
    private static final Color TEXT_SELECTION_COLOR = new Color(0xf0e9da);
    private static final Color TEXT_AREAS_FG_COLOR = new Color(0x261f10);
    private static final Color TEXT_LABEL_FG_COLOR = new Color(0x453920);
    private static final Color DEFAULT_BTN_FG_COLOR = TEXT_SELECTION_COLOR;
    private static final Color HOVER_BTN_BORDER_COLOR = new Color(0xd9ccad);

    private static final String ALL_FONT_NAME = "Consolas";
    private static final int TEXT_FIELD_FONT_SIZE = 25;
    private static final int TEXT_AREA_FONT_SIZE = 15;
    private static final int TEXT_LABEL_FONT_SIZE = 20;
    private static final int BTN_FONT_SIZE = 15;
    private static final int IMAGE_BTN_FONT_SIZE = 40;

    private JPanel innerScreen;
    private JPanel topPanel;
    private JPanel topPanelGap1;
    private JPanel topPanelGap2;
    private JTextField nameTextField;
    private JComboBox<RecipeInfo> nameComboBox;
    private JButton editButton;
    private JButton newButton;

    private JPanel ingredientImagePanel;
    private JPanel ingredientPanel;
    private JLabel ingredientTitleLabel;
    private JTextArea ingredientTextArea;
    private JScrollPane ingredientTextAreaScroll;
    private JButton imageButton;

    private JPanel instructionPanel;
    private JLabel instructionTitleLabel;
    private JTextArea instructionTextArea;
    private JScrollPane instructionTextAreaScroll;

    private JPanel lastInnerScreenGap;
    private JPanel bottomPanel;
    private JButton saveButton;
    private JButton deleteButton;

    public Recipe(MainScreen parent) {
        this.parent = parent;
        setupMainScreen();
        initializeComponents();
        populateComponents();
        updateComboBox(0);
        setDisplayVisible();
    }

    private void setupMainScreen() {
        setPreferredSize(MAIN_SCREEN_SIZE);
        setBackground(MAIN_BG_COLOR);
        setLayout(new GridBagLayout());
    }

    private void initializeComponents() {
        innerScreen = createPanel(INNER_SCREEN_SIZE, new FlowLayout(FlowLayout.LEADING, 0, 0));
        topPanel = createPanel(TOP_PANEL_SIZE, new FlowLayout(FlowLayout.LEADING, 0, 0));
        ingredientImagePanel = createPanel(INFO_PANEL_DISPLAY_SIZE, new GridLayout(1, 2, 20, 0));
        ingredientPanel = createPanel(SECOND_PANEL_HALF_DISPLAY_SIZE, new FlowLayout(FlowLayout.LEADING, 0, 0));
        instructionPanel = createPanel(INFO_PANEL_DISPLAY_SIZE, new FlowLayout(FlowLayout.LEADING, 0, 0));
        bottomPanel = createPanel(BOTTOM_PANEL_SIZE, new FlowLayout(FlowLayout.LEADING, 0, 0));

        topPanelGap1 = createGapPanel(TOP_PANEL_VGAP_SIZE);
        topPanelGap2 = createGapPanel(TOP_PANEL_VGAP_SIZE);
        lastInnerScreenGap = createGapPanel(INNER_PANEL_HGAP_SIZE);

        nameTextField = new JTextField();
        nameComboBox = new JComboBox<>();
        editButton = new JButton();
        newButton = new JButton();

        ingredientTitleLabel = new JLabel("INGREDIENTS");
        ingredientTextArea = new JTextArea();
        ingredientTextAreaScroll = new JScrollPane(ingredientTextArea);
        imageButton = new JButton("+");

        instructionTitleLabel = new JLabel("INSTRUCTIONS");
        instructionTextArea = new JTextArea();
        instructionTextAreaScroll = new JScrollPane(instructionTextArea);

        saveButton = new JButton("SAVE");
        deleteButton = new JButton();

        styleComponents();
    }

    private void populateComponents() {
        add(innerScreen);
        innerScreen.add(topPanel);
        innerScreen.add(createGapPanel(INNER_PANEL_HGAP_SIZE));
        innerScreen.add(ingredientImagePanel);
        innerScreen.add(createGapPanel(INNER_PANEL_HGAP_SIZE));
        innerScreen.add(instructionPanel);
        innerScreen.add(lastInnerScreenGap);
        innerScreen.add(bottomPanel);

        topPanel.add(nameTextField);
        topPanel.add(nameComboBox);
        topPanel.add(topPanelGap1);
        topPanel.add(editButton);
        topPanel.add(topPanelGap2);
        topPanel.add(newButton);

        ingredientImagePanel.add(ingredientPanel);
        ingredientImagePanel.add(imageButton);
        ingredientPanel.add(ingredientTitleLabel);
        ingredientPanel.add(ingredientTextAreaScroll);

        instructionPanel.add(instructionTitleLabel);
        instructionPanel.add(instructionTextAreaScroll);

        bottomPanel.add(saveButton);
        bottomPanel.add(createGapPanel(BOTTOM_PANEL_VGAP_SIZE));
        bottomPanel.add(deleteButton);
    }

    private JPanel createPanel(Dimension size, LayoutManager layout) {
        JPanel panel = new JPanel();
        panel.setPreferredSize(size);
        panel.setLayout(layout);
        panel.setBackground(MAIN_BG_COLOR);
        return panel;
    }

    private JPanel createGapPanel(Dimension size) {
        return createPanel(size, null);
    }

    private void styleComponents() {
        nameTextField.setPreferredSize(TOP_PANEL_SIZE);
        nameComboBox.setPreferredSize(TOP_PANEL_COMBO_SIZE);
        editButton.setPreferredSize(TOP_PANEL_BTN_SIZE);
        newButton.setPreferredSize(TOP_PANEL_BTN_SIZE);
        ingredientTitleLabel.setPreferredSize(INGREDIENT_LABEL_SIZE);
        instructionTitleLabel.setPreferredSize(INSTRUCTION_LABEL_SIZE);
        saveButton.setPreferredSize(BOTTOM_PANEL_BTN_SIZE);
        deleteButton.setPreferredSize(BOTTOM_PANEL_BTN_SIZE);

        editButton.addActionListener(e -> editView());
        newButton.addActionListener(e -> newView());
        saveButton.addActionListener(e -> saveView());
        deleteButton.addActionListener(e -> deleteView());
        imageButton.addActionListener(e -> chooseImage());

        styleTextField(nameTextField);
        styleTextArea(ingredientTextArea);
        styleTextArea(instructionTextArea);
        styleScrollPane(ingredientTextAreaScroll);
        styleScrollPane(instructionTextAreaScroll);
        styleLabel(ingredientTitleLabel);
        styleLabel(instructionTitleLabel);

        setAppButtonIcons();

        styleButton(editButton, BTN_FONT_SIZE);
        styleButton(newButton, BTN_FONT_SIZE);
        styleImageButton(imageButton);
        styleButton(saveButton, BTN_FONT_SIZE);
        styleButton(deleteButton, BTN_FONT_SIZE);

        styleComboBox(nameComboBox);
    }

    private void styleTextField(JTextField field) {
        field.setBackground(TEXT_AREAS_BG_COLOR);
        field.setForeground(TEXT_AREAS_FG_COLOR);
        field.setSelectionColor(TEXT_SELECTION_COLOR);
        field.setFont(new Font(ALL_FONT_NAME, Font.PLAIN, TEXT_FIELD_FONT_SIZE));
        field.setBorder(new EmptyBorder(20, 20, 20, 20));
    }

    private void styleTextArea(JTextArea area) {
        area.setBackground(TEXT_AREAS_BG_COLOR);
        area.setForeground(TEXT_AREAS_FG_COLOR);
        area.setSelectionColor(TEXT_SELECTION_COLOR);
        area.setFont(new Font(ALL_FONT_NAME, Font.PLAIN, TEXT_AREA_FONT_SIZE));
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
    }

    private void styleScrollPane(JScrollPane scroll) {
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scroll.getVerticalScrollBar().setUnitIncrement(10);
    }

    private void styleLabel(JLabel label) {
        label.setBackground(TEXT_LABEL_BG_COLOR);
        label.setForeground(TEXT_LABEL_FG_COLOR);
        label.setOpaque(true);
        label.setFont(new Font(ALL_FONT_NAME, Font.BOLD, TEXT_LABEL_FONT_SIZE));
        label.setBorder(new EmptyBorder(15, 10, 10, 10));
    }

    private void setAppButtonIcons() {
        String editFilePath = "/image/edit.png";
        String newFilePath = "/image/new.png";

        Image editImage = new ImageIcon(getClass().getResource(editFilePath)).getImage();
        Image newImage = new ImageIcon(getClass().getResource(newFilePath)).getImage();

        Image scaledEdit = editImage.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        Image scaledNew = newImage.getScaledInstance(30, 30, Image.SCALE_SMOOTH);

        editButton.setIcon(new ImageIcon(scaledEdit));
        newButton.setIcon(new ImageIcon(scaledNew));
    }

    private void styleButton(JButton btn, int fontSize) {
        btn.setBackground(DEFAULT_BTN_BG_COLOR);
        btn.setForeground(DEFAULT_BTN_FG_COLOR);
        btn.setFont(new Font(ALL_FONT_NAME, Font.BOLD, fontSize));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        Border defaultBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);
        Border hoverBorder = BorderFactory.createLineBorder(HOVER_BTN_BORDER_COLOR, 2);
        btn.setBorder(defaultBorder);

        btn.getModel().addChangeListener(e -> {
            ButtonModel model = btn.getModel();
            if (model.isPressed()) {
                btn.setBackground(CLICKED_BTN_BG_COLOR);
                btn.setBorder(hoverBorder);
            } else if (model.isRollover()) {
                btn.setBackground(DEFAULT_BTN_BG_COLOR);
                btn.setBorder(hoverBorder);
            } else {
                btn.setBackground(DEFAULT_BTN_BG_COLOR);
                btn.setForeground(DEFAULT_BTN_FG_COLOR);
                btn.setBorder(defaultBorder);
            }
        });

        btn.setUI(new MetalButtonUI() {
            @Override
            protected Color getDisabledTextColor() { return DEFAULT_BTN_FG_COLOR; }
        });
    }

    private void styleImageButton(JButton btn) {
        btn.setBackground(DEFAULT_BTN_BG_COLOR);
        btn.setForeground(DEFAULT_BTN_FG_COLOR);
        btn.setFont(new Font(ALL_FONT_NAME, Font.BOLD, IMAGE_BTN_FONT_SIZE));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Border frameBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(TEXT_LABEL_BG_COLOR, 4),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        Border hoverFrameBorder = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(HOVER_BTN_BORDER_COLOR, 4),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        );

        btn.setBorder(frameBorder);

        btn.getModel().addChangeListener(e -> {
            ButtonModel model = btn.getModel();
            if (model.isPressed()) {
                btn.setBackground(CLICKED_BTN_BG_COLOR);
                btn.setBorder(hoverFrameBorder);
            } else if (model.isRollover()) {
                btn.setBackground(DEFAULT_BTN_BG_COLOR);
                btn.setBorder(hoverFrameBorder);
            } else {
                btn.setBackground(DEFAULT_BTN_BG_COLOR);
                btn.setBorder(frameBorder);
            }
        });
    }

    private void styleComboBox(JComboBox<RecipeInfo> box) {
        box.setEditable(true);
        box.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton btn = new JButton("▼");
                btn.setFont(new Font(ALL_FONT_NAME, Font.PLAIN, BTN_FONT_SIZE));
                btn.setBackground(DEFAULT_BTN_BG_COLOR);
                btn.setForeground(DEFAULT_BTN_FG_COLOR);
                btn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
                btn.setContentAreaFilled(false);
                btn.setFocusPainted(false);
                btn.setOpaque(true);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                return btn;
            }

            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = new BasicComboPopup(comboBox) {
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = super.createScroller();
                        scroller.getViewport().setBackground(Color.white);
                        scroller.setBorder(BorderFactory.createLineBorder(DEFAULT_BTN_BG_COLOR));
                        return scroller;
                    }
                };
                popup.getList().setBackground(Color.white);
                return popup;
            }

            @Override
            protected LayoutManager createLayoutManager() {
                return new ComboBoxLayoutManager() {
                    @Override
                    public void layoutContainer(Container parent) {
                        super.layoutContainer(parent);
                        int buttonWidth = TOP_PANEL_HEIGHT / 2;
                        int boxWidth = comboBox.getWidth();
                        int boxHeight = comboBox.getHeight();

                        if (arrowButton != null) {
                            arrowButton.setBounds(boxWidth - buttonWidth, 0, buttonWidth, boxHeight);
                        }
                        if (editor != null) {
                            editor.setBounds(0, 0, boxWidth - buttonWidth, boxHeight);
                        }
                    }
                };
            }

            @Override
            public void installUI(JComponent c) {
                super.installUI(c);
                box.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        JTextField editField = (JTextField) box.getEditor().getEditorComponent();
        editField.setBackground(TEXT_AREAS_BG_COLOR);
        editField.setBorder(BorderFactory.createEmptyBorder(25, 15, 20, 15));
        editField.setFont(new Font(ALL_FONT_NAME, Font.BOLD, TEXT_FIELD_FONT_SIZE));
        editField.setSelectionColor(TEXT_SELECTION_COLOR);

        box.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setFont(new Font(ALL_FONT_NAME, Font.PLAIN, TEXT_AREA_FONT_SIZE));
                label.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));

                if (isSelected) {
                    label.setBackground(TEXT_LABEL_BG_COLOR);
                    label.setForeground(TEXT_LABEL_FG_COLOR);
                } else {
                    label.setBackground(Color.white);
                    label.setForeground(TEXT_LABEL_FG_COLOR);
                }
                return label;
            }
        });

        editField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                box.setPopupVisible(true);
            }
        });

        editField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String input = editField.getText().toLowerCase();
                    for (int i = 0; i < box.getItemCount(); i++) {
                        String item = box.getItemAt(i).name.toLowerCase();
                        if (item.contains(input)) {
                            box.setSelectedIndex(i);
                            box.setPopupVisible(false);
                            editField.setCaretPosition(editField.getText().length());
                            break;
                        }
                    }
                }
            }
        });

        box.addActionListener(e -> updateTextAreas());
    }

    private void updateComboBox(int targetIndex) {
        isUpdatingCombo = true;
        nameComboBox.removeAllItems();

        for (RecipeInfo r : parent.appData.recipes) {
            nameComboBox.addItem(r);
        }

        if (nameComboBox.getItemCount() > 0) {
            targetIndex = Math.max(0, Math.min(targetIndex, nameComboBox.getItemCount() - 1));
            nameComboBox.setSelectedIndex(targetIndex);
        }

        isUpdatingCombo = false;
        updateTextAreas();
    }

    private void updateTextAreas() {
        if (isUpdatingCombo) return;

        RecipeInfo selectedRecipe = (RecipeInfo) nameComboBox.getSelectedItem();

        if (selectedRecipe == null) {
            if (!isCreatingNew) {
                nameTextField.setText("");
                ingredientTextArea.setText("");
                instructionTextArea.setText("");
            }
        } else {
            nameTextField.setText(selectedRecipe.name);
            ingredientTextArea.setText(selectedRecipe.ingredients);
            instructionTextArea.setText(selectedRecipe.instructions);

            nameTextField.setCaretPosition(0);
            ingredientTextArea.setCaretPosition(0);
            instructionTextArea.setCaretPosition(0);
        }

        if (selectedRecipe != null) {
            editButton.setEnabled(true);
            updateImageDisplay(null, selectedRecipe.imagePath);
        } else {
            editButton.setEnabled(false);
            updateImageDisplay(null, null);
        }
    }

    private void editView() {
        deleteButton.setText("DELETE");
        setEditVisible();
    }

    private void newView() {
        pendingImageFile = null;
        updateImageDisplay(pendingImageFile, null);
        isCreatingNew = true;
        nameTextField.setText("");
        ingredientTextArea.setText("");
        instructionTextArea.setText("");
        deleteButton.setText("CANCEL");
        setEditVisible();
    }

    private void saveView() {
        String savedName = nameTextField.getText().trim();
        String savedIngredient = ingredientTextArea.getText();
        String savedInstruction = instructionTextArea.getText();

        if (savedName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "bffr theres literally no name", "bruh", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RecipeInfo currentlySelected = (RecipeInfo) nameComboBox.getSelectedItem();

        for (RecipeInfo r : parent.appData.recipes) {
            if (r.name.equalsIgnoreCase(savedName)) {
                if (!isCreatingNew && r == currentlySelected) {
                    continue;
                }
                JOptionPane.showMessageDialog(this, "that name already exists dumbass", "r u a bobo", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String finalImageName = (currentlySelected != null) ? currentlySelected.imagePath : "";
		
		if (pendingImageFile != null) {
			try {
				File imgFolder = new File(AppData.APP_DIR, "images");
				if (!imgFolder.exists()) imgFolder.mkdirs();
				
				String sanitizedName = savedName.replaceAll("[\\\\/:*?\"<>|]", "_");
				finalImageName = sanitizedName + getFileExtension(pendingImageFile);
				File destination = new File(imgFolder, finalImageName);
						
				Files.copy(pendingImageFile.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
						
			} catch (Exception e) {
				System.err.println("Could not save image: " + e.getMessage());
			}
		}

        int targetIndex = 0;

        if (isCreatingNew) {
            RecipeInfo newRecipe = new RecipeInfo(savedName, savedIngredient, savedInstruction, finalImageName);
            parent.appData.recipes.add(newRecipe);
            targetIndex = parent.appData.recipes.size() - 1;
        } else {
            if (currentlySelected != null) {
                currentlySelected.name = savedName;
                currentlySelected.ingredients = savedIngredient;
                currentlySelected.instructions = savedInstruction;
                currentlySelected.imagePath = finalImageName;
            }
            targetIndex = nameComboBox.getSelectedIndex();
        }

        isCreatingNew = false;
        pendingImageFile = null;

        updateComboBox(targetIndex);
        setDisplayVisible();
    }

    private void deleteView() {
		pendingImageFile = null;
		
		if (isCreatingNew) {
			isCreatingNew = false;
			updateTextAreas();
			setDisplayVisible();
			return;
		}
		
		int selectedIndex = nameComboBox.getSelectedIndex();
		if (selectedIndex != -1) {
			RecipeInfo targetRecipe = parent.appData.recipes.get(selectedIndex);
			
			if (targetRecipe.imagePath != null && !targetRecipe.imagePath.isEmpty()) {
				try {
					File imgFolder = new File(AppData.APP_DIR, "images");
					File imgFile = new File(imgFolder, targetRecipe.imagePath);
					
					if (imgFile.exists()) {
						imgFile.delete();
					}
				} catch (Exception e) {
					System.err.println("Failed to delete image file: " + e.getMessage());
				}
			}
			
			parent.appData.recipes.remove(selectedIndex);
		}
		
		int targetIndex = Math.max(0, selectedIndex - 1);
		
		updateComboBox(targetIndex);
		setDisplayVisible();
	}

    private void setDisplayVisible() {
        AppData.saveRecipes(parent.appData.recipes);
        isCreatingNew = false;

        nameTextField.setVisible(false);
        lastInnerScreenGap.setVisible(false);
        bottomPanel.setVisible(false);

        topPanelGap1.setVisible(true);
        topPanelGap2.setVisible(true);
        nameComboBox.setVisible(true);
        editButton.setVisible(true);
        newButton.setVisible(true);

        ingredientImagePanel.setPreferredSize(INFO_PANEL_DISPLAY_SIZE);
        instructionPanel.setPreferredSize(INFO_PANEL_DISPLAY_SIZE);
        ingredientTextAreaScroll.setPreferredSize(INGREDIENT_TEXT_DISPLAY_SIZE);
        imageButton.setPreferredSize(SECOND_PANEL_HALF_DISPLAY_SIZE);
        instructionTextAreaScroll.setPreferredSize(INSTRUCTION_TEXT_DISPLAY_SIZE);

        imageButton.setEnabled(false);
        ingredientTextArea.setEditable(false);
        instructionTextArea.setEditable(false);
        ingredientTextArea.setFocusable(false);
        instructionTextArea.setFocusable(false);
    }

    private void setEditVisible() {
        topPanelGap1.setVisible(false);
        topPanelGap2.setVisible(false);
        nameComboBox.setVisible(false);
        editButton.setVisible(false);
        newButton.setVisible(false);

        nameTextField.setVisible(true);
        lastInnerScreenGap.setVisible(true);
        bottomPanel.setVisible(true);

        ingredientImagePanel.setPreferredSize(INFO_PANEL_EDIT_SIZE);
        instructionPanel.setPreferredSize(INFO_PANEL_EDIT_SIZE);
        ingredientTextAreaScroll.setPreferredSize(INGREDIENT_TEXT_EDIT_SIZE);
        imageButton.setPreferredSize(SECOND_PANEL_HALF_EDIT_SIZE);
        instructionTextAreaScroll.setPreferredSize(INSTRUCTION_TEXT_EDIT_SIZE);

        imageButton.setEnabled(true);
        ingredientTextArea.setEditable(true);
        instructionTextArea.setEditable(true);
        ingredientTextArea.setFocusable(true);
        instructionTextArea.setFocusable(true);

        if (ingredientTextArea.getText().length() > 0) ingredientTextArea.setCaretPosition(0);
        if (instructionTextArea.getText().length() > 0) instructionTextArea.setCaretPosition(0);
    }

    private void chooseImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Recipe Image");
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Images (JPG, PNG)", "jpg", "jpeg", "png");
        chooser.addChoosableFileFilter(filter);

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            pendingImageFile = chooser.getSelectedFile();
            updateImageDisplay(pendingImageFile, null);
        }
    }

    private void updateImageDisplay(File rawFile, String savedFileName) {
		imageButton.setText("");
		imageButton.setIcon(null);
		imageButton.setDisabledIcon(null);
		
		try {
			Image img = null;
			
			if (rawFile != null) {
				img = ImageIO.read(rawFile);
			} else if (savedFileName != null && !savedFileName.isEmpty()) {
				File imgFolder = new File(AppData.APP_DIR, "images");
				File f = new File(imgFolder, savedFileName);
				if (f.exists()) {
					img = ImageIO.read(f);
				}
			}
			
			if (img != null) {
				int targetWidth = SECOND_PANEL_HALF_WIDTH - 32; 
				int targetHeight = INFO_PANEL_EDIT_HEIGHT - 32; 
				
				Image scaledImg = img.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
				imageButton.setIcon(new ImageIcon(scaledImg));
				imageButton.setDisabledIcon(new ImageIcon(scaledImg));
			} else {
				imageButton.setText("+");
			}
			
		} catch (Exception e) {
			System.err.println("Failed to load image.");
			imageButton.setText("+");
		}
	}

    private String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return name.substring(lastIndexOf);
    }
}