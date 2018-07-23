begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// TODO: temporarily removed, LibreOffice, Java 9
end_comment

begin_comment
comment|//package org.jabref.gui.openoffice;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import java.awt.BorderLayout;
end_comment

begin_comment
comment|//import java.awt.Dimension;
end_comment

begin_comment
comment|//import java.awt.event.ActionEvent;
end_comment

begin_comment
comment|//import java.io.FileNotFoundException;
end_comment

begin_comment
comment|//import java.io.IOException;
end_comment

begin_comment
comment|//import java.lang.reflect.InvocationTargetException;
end_comment

begin_comment
comment|//import java.lang.reflect.Method;
end_comment

begin_comment
comment|//import java.net.URL;
end_comment

begin_comment
comment|//import java.net.URLClassLoader;
end_comment

begin_comment
comment|//import java.nio.file.Path;
end_comment

begin_comment
comment|//import java.nio.file.Paths;
end_comment

begin_comment
comment|//import java.util.ArrayList;
end_comment

begin_comment
comment|//import java.util.List;
end_comment

begin_comment
comment|//import java.util.Optional;
end_comment

begin_comment
comment|//import java.util.stream.Collectors;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import javax.swing.AbstractAction;
end_comment

begin_comment
comment|//import javax.swing.Action;
end_comment

begin_comment
comment|//import javax.swing.ButtonGroup;
end_comment

begin_comment
comment|//import javax.swing.Icon;
end_comment

begin_comment
comment|//import javax.swing.JButton;
end_comment

begin_comment
comment|//import javax.swing.JCheckBoxMenuItem;
end_comment

begin_comment
comment|//import javax.swing.JDialog;
end_comment

begin_comment
comment|//import javax.swing.JFrame;
end_comment

begin_comment
comment|//import javax.swing.JMenuItem;
end_comment

begin_comment
comment|//import javax.swing.JPanel;
end_comment

begin_comment
comment|//import javax.swing.JPopupMenu;
end_comment

begin_comment
comment|//import javax.swing.JRadioButtonMenuItem;
end_comment

begin_comment
comment|//import javax.swing.JTextField;
end_comment

begin_comment
comment|//import javax.swing.SwingUtilities;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import org.jabref.Globals;
end_comment

begin_comment
comment|//import org.jabref.gui.BasePanel;
end_comment

begin_comment
comment|//import org.jabref.gui.DialogService;
end_comment

begin_comment
comment|//import org.jabref.gui.JabRefFrame;
end_comment

begin_comment
comment|//import org.jabref.gui.desktop.JabRefDesktop;
end_comment

begin_comment
comment|//import org.jabref.gui.desktop.os.NativeDesktop;
end_comment

begin_comment
comment|//import org.jabref.gui.help.HelpAction;
end_comment

begin_comment
comment|//import org.jabref.gui.icon.IconTheme;
end_comment

begin_comment
comment|//import org.jabref.gui.undo.NamedCompound;
end_comment

begin_comment
comment|//import org.jabref.gui.undo.UndoableKeyChange;
end_comment

begin_comment
comment|//import org.jabref.gui.util.BackgroundTask;
end_comment

begin_comment
comment|//import org.jabref.gui.util.DefaultTaskExecutor;
end_comment

begin_comment
comment|//import org.jabref.gui.util.DirectoryDialogConfiguration;
end_comment

begin_comment
comment|//import org.jabref.gui.util.FileDialogConfiguration;
end_comment

begin_comment
comment|//import org.jabref.logic.bibtexkeypattern.BibtexKeyGenerator;
end_comment

begin_comment
comment|//import org.jabref.logic.bibtexkeypattern.BibtexKeyPatternPreferences;
end_comment

begin_comment
comment|//import org.jabref.logic.help.HelpFile;
end_comment

begin_comment
comment|//import org.jabref.logic.l10n.Localization;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.OOBibStyle;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.OpenOfficePreferences;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.StyleLoader;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.UndefinedParagraphFormatException;
end_comment

begin_comment
comment|//import org.jabref.logic.util.OS;
end_comment

begin_comment
comment|//import org.jabref.logic.util.io.FileUtil;
end_comment

begin_comment
comment|//import org.jabref.model.Defaults;
end_comment

begin_comment
comment|//import org.jabref.model.database.BibDatabase;
end_comment

begin_comment
comment|//import org.jabref.model.database.BibDatabaseContext;
end_comment

begin_comment
comment|//import org.jabref.model.entry.BibEntry;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import com.jgoodies.forms.builder.ButtonBarBuilder;
end_comment

begin_comment
comment|//import com.jgoodies.forms.builder.FormBuilder;
end_comment

begin_comment
comment|//import com.jgoodies.forms.layout.FormLayout;
end_comment

begin_comment
comment|//import com.sun.star.beans.IllegalTypeException;
end_comment

begin_comment
comment|//import com.sun.star.beans.NotRemoveableException;
end_comment

begin_comment
comment|//import com.sun.star.beans.PropertyExistException;
end_comment

begin_comment
comment|//import com.sun.star.beans.PropertyVetoException;
end_comment

begin_comment
comment|//import com.sun.star.beans.UnknownPropertyException;
end_comment

begin_comment
comment|//import com.sun.star.comp.helper.BootstrapException;
end_comment

begin_comment
comment|//import com.sun.star.container.NoSuchElementException;
end_comment

begin_comment
comment|//import com.sun.star.lang.WrappedTargetException;
end_comment

begin_comment
comment|//import org.slf4j.Logger;
end_comment

begin_comment
comment|//import org.slf4j.LoggerFactory;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|///**
end_comment

begin_comment
comment|// * Pane to manage the interaction between JabRef and OpenOffice.
end_comment

begin_comment
comment|// */
end_comment

begin_comment
comment|//public class OpenOfficePanel {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final Logger LOGGER = LoggerFactory.getLogger(OpenOfficePanel.class);
end_comment

begin_comment
comment|//    private final DialogService dialogService;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private JPanel content;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private JDialog diag;
end_comment

begin_comment
comment|//    private final JButton connect;
end_comment

begin_comment
comment|//    private final JButton manualConnect;
end_comment

begin_comment
comment|//    private final JButton selectDocument;
end_comment

begin_comment
comment|//    private final JButton setStyleFile = new JButton(Localization.lang("Select style"));
end_comment

begin_comment
comment|//    private final JButton pushEntries = new JButton(Localization.lang("Cite"));
end_comment

begin_comment
comment|//    private final JButton pushEntriesInt = new JButton(Localization.lang("Cite in-text"));
end_comment

begin_comment
comment|//    private final JButton pushEntriesEmpty = new JButton(Localization.lang("Insert empty citation"));
end_comment

begin_comment
comment|//    private final JButton pushEntriesAdvanced = new JButton(Localization.lang("Cite special"));
end_comment

begin_comment
comment|//    private final JButton update;
end_comment

begin_comment
comment|//    private final JButton merge = new JButton(Localization.lang("Merge citations"));
end_comment

begin_comment
comment|//    private final JButton manageCitations = new JButton(Localization.lang("Manage citations"));
end_comment

begin_comment
comment|//    private final JButton exportCitations = new JButton(Localization.lang("Export cited"));
end_comment

begin_comment
comment|//    private final JButton settingsB = new JButton(Localization.lang("Settings"));
end_comment

begin_comment
comment|//    private final JButton help = new HelpAction(Localization.lang("OpenOffice/LibreOffice integration"),
end_comment

begin_comment
comment|//            HelpFile.OPENOFFICE_LIBREOFFICE).getHelpButton();
end_comment

begin_comment
comment|//    private OOBibBase ooBase;
end_comment

begin_comment
comment|//    private final JabRefFrame frame;
end_comment

begin_comment
comment|//    private OOBibStyle style;
end_comment

begin_comment
comment|//    private StyleSelectDialog styleDialog;
end_comment

begin_comment
comment|//    private boolean dialogOkPressed;
end_comment

begin_comment
comment|//    private final OpenOfficePreferences preferences;
end_comment

begin_comment
comment|//    private final StyleLoader loader;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public OpenOfficePanel(JabRefFrame jabRefFrame) {
end_comment

begin_comment
comment|//        Icon connectImage = IconTheme.JabRefIcons.CONNECT_OPEN_OFFICE.getSmallIcon();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        connect = new JButton(connectImage);
end_comment

begin_comment
comment|//        manualConnect = new JButton(connectImage);
end_comment

begin_comment
comment|//        connect.setToolTipText(Localization.lang("Connect"));
end_comment

begin_comment
comment|//        manualConnect.setToolTipText(Localization.lang("Manual connect"));
end_comment

begin_comment
comment|//        connect.setPreferredSize(new Dimension(24, 24));
end_comment

begin_comment
comment|//        manualConnect.setPreferredSize(new Dimension(24, 24));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        selectDocument = new JButton(IconTheme.JabRefIcons.OPEN.getSmallIcon());
end_comment

begin_comment
comment|//        selectDocument.setToolTipText(Localization.lang("Select Writer document"));
end_comment

begin_comment
comment|//        selectDocument.setPreferredSize(new Dimension(24, 24));
end_comment

begin_comment
comment|//        update = new JButton(IconTheme.JabRefIcons.REFRESH.getSmallIcon());
end_comment

begin_comment
comment|//        update.setToolTipText(Localization.lang("Sync OpenOffice/LibreOffice bibliography"));
end_comment

begin_comment
comment|//        update.setPreferredSize(new Dimension(24, 24));
end_comment

begin_comment
comment|//        preferences = Globals.prefs.getOpenOfficePreferences();
end_comment

begin_comment
comment|//        loader = new StyleLoader(preferences,
end_comment

begin_comment
comment|//                Globals.prefs.getLayoutFormatterPreferences(Globals.journalAbbreviationLoader),
end_comment

begin_comment
comment|//                Globals.prefs.getDefaultEncoding());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        this.frame = jabRefFrame;
end_comment

begin_comment
comment|//        initPanel();
end_comment

begin_comment
comment|//        dialogService = frame.getDialogService();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public JPanel getContent() {
end_comment

begin_comment
comment|//        return content;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void initPanel() {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        connect.addActionListener(e -> connectAutomatically());
end_comment

begin_comment
comment|//        manualConnect.addActionListener(e -> connectManually());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        selectDocument.setToolTipText(Localization.lang("Select which open Writer document to work on"));
end_comment

begin_comment
comment|//        selectDocument.addActionListener(e -> {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                ooBase.selectDocument();
end_comment

begin_comment
comment|//                frame.output(Localization.lang("Connected to document") + ": "
end_comment

begin_comment
comment|//                        + ooBase.getCurrentDocumentTitle().orElse(""));
end_comment

begin_comment
comment|//            } catch (UnknownPropertyException | WrappedTargetException | IndexOutOfBoundsException |
end_comment

begin_comment
comment|//                    NoSuchElementException | NoDocumentException ex) {
end_comment

begin_comment
comment|//                LOGGER.warn("Problem connecting", ex);
end_comment

begin_comment
comment|//                dialogService.showErrorDialogAndWait(ex);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        setStyleFile.addActionListener(event -> {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            if (styleDialog == null) {
end_comment

begin_comment
comment|//                styleDialog = new StyleSelectDialog(frame, preferences, loader);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            styleDialog.setVisible(true);
end_comment

begin_comment
comment|//            styleDialog.getStyle().ifPresent(selectedStyle -> {
end_comment

begin_comment
comment|//                style = selectedStyle;
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    style.ensureUpToDate();
end_comment

begin_comment
comment|//                } catch (IOException e) {
end_comment

begin_comment
comment|//                    LOGGER.warn("Unable to reload style file '" + style.getPath() + "'", e);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//                frame.setStatus(Localization.lang("Current style is '%0'", style.getName()));
end_comment

begin_comment
comment|//            });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        pushEntries.setToolTipText(Localization.lang("Cite selected entries between parenthesis"));
end_comment

begin_comment
comment|//        pushEntries.addActionListener(e -> pushEntries(true, true, false));
end_comment

begin_comment
comment|//        pushEntriesInt.setToolTipText(Localization.lang("Cite selected entries with in-text citation"));
end_comment

begin_comment
comment|//        pushEntriesInt.addActionListener(e -> pushEntries(false, true, false));
end_comment

begin_comment
comment|//        pushEntriesEmpty.setToolTipText(
end_comment

begin_comment
comment|//                Localization.lang("Insert a citation without text (the entry will appear in the reference list)"));
end_comment

begin_comment
comment|//        pushEntriesEmpty.addActionListener(e -> pushEntries(false, false, false));
end_comment

begin_comment
comment|//        pushEntriesAdvanced.setToolTipText(Localization.lang("Cite selected entries with extra information"));
end_comment

begin_comment
comment|//        pushEntriesAdvanced.addActionListener(e -> pushEntries(false, true, true));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        update.setToolTipText(Localization.lang("Ensure that the bibliography is up-to-date"));
end_comment

begin_comment
comment|//        Action updateAction = new AbstractAction() {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            @Override
end_comment

begin_comment
comment|//            public void actionPerformed(ActionEvent e) {
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    if (style == null) {
end_comment

begin_comment
comment|//                        style = loader.getUsedStyle();
end_comment

begin_comment
comment|//                    } else {
end_comment

begin_comment
comment|//                        style.ensureUpToDate();
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    ooBase.updateSortedReferenceMarks();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    List<BibDatabase> databases = getBaseList();
end_comment

begin_comment
comment|//                    List<String> unresolvedKeys = ooBase.refreshCiteMarkers(databases, style);
end_comment

begin_comment
comment|//                    ooBase.rebuildBibTextSection(databases, style);
end_comment

begin_comment
comment|//                    if (!unresolvedKeys.isEmpty()) {
end_comment

begin_comment
comment|//                        dialogService.showErrorDialogAndWait(Localization.lang("Unable to synchronize bibliography"),
end_comment

begin_comment
comment|//                                Localization.lang("Your OpenOffice/LibreOffice document references the BibTeX key '%0', which could not be found in your current library.",
end_comment

begin_comment
comment|//                                        unresolvedKeys.get(0)));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                } catch (UndefinedCharacterFormatException ex) {
end_comment

begin_comment
comment|//                    reportUndefinedCharacterFormat(ex);
end_comment

begin_comment
comment|//                } catch (UndefinedParagraphFormatException ex) {
end_comment

begin_comment
comment|//                    reportUndefinedParagraphFormat(ex);
end_comment

begin_comment
comment|//                } catch (ConnectionLostException ex) {
end_comment

begin_comment
comment|//                    showConnectionLostErrorMessage();
end_comment

begin_comment
comment|//                } catch (IOException ex) {
end_comment

begin_comment
comment|//                    LOGGER.warn("Problem with style file", ex);
end_comment

begin_comment
comment|//                    dialogService.showErrorDialogAndWait(Localization.lang("No valid style file defined"),
end_comment

begin_comment
comment|//                            Localization.lang("You must select either a valid style file, or use one of the default styles."));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                } catch (BibEntryNotFoundException ex) {
end_comment

begin_comment
comment|//                    LOGGER.debug("BibEntry not found", ex);
end_comment

begin_comment
comment|//                    dialogService.showErrorDialogAndWait(Localization.lang("Unable to synchronize bibliography"), Localization.lang(
end_comment

begin_comment
comment|//                            "Your OpenOffice/LibreOffice document references the BibTeX key '%0', which could not be found in your current library.",
end_comment

begin_comment
comment|//                            ex.getBibtexKey()));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                } catch (com.sun.star.lang.IllegalArgumentException | PropertyVetoException | UnknownPropertyException | WrappedTargetException | NoSuchElementException |
end_comment

begin_comment
comment|//                        CreationException ex) {
end_comment

begin_comment
comment|//                    LOGGER.warn("Could not update bibliography", ex);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        };
end_comment

begin_comment
comment|//        update.addActionListener(updateAction);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        merge.setToolTipText(Localization.lang("Combine pairs of citations that are separated by spaces only"));
end_comment

begin_comment
comment|//        merge.addActionListener(e -> {
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                ooBase.combineCiteMarkers(getBaseList(), style);
end_comment

begin_comment
comment|//            } catch (UndefinedCharacterFormatException ex) {
end_comment

begin_comment
comment|//                reportUndefinedCharacterFormat(ex);
end_comment

begin_comment
comment|//            } catch (com.sun.star.lang.IllegalArgumentException | UnknownPropertyException | PropertyVetoException |
end_comment

begin_comment
comment|//                    CreationException | NoSuchElementException | WrappedTargetException | IOException |
end_comment

begin_comment
comment|//                    BibEntryNotFoundException ex) {
end_comment

begin_comment
comment|//                LOGGER.warn("Problem combining cite markers", ex);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//        settingsB.addActionListener(e -> showSettingsPopup());
end_comment

begin_comment
comment|//        manageCitations.addActionListener(e -> {
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                CitationManager cm = new CitationManager(ooBase, dialogService);
end_comment

begin_comment
comment|//                cm.showDialog();
end_comment

begin_comment
comment|//            } catch (NoSuchElementException | WrappedTargetException | UnknownPropertyException ex) {
end_comment

begin_comment
comment|//                LOGGER.warn("Problem showing citation manager", ex);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        exportCitations.addActionListener(event -> exportEntries());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        selectDocument.setEnabled(false);
end_comment

begin_comment
comment|//        pushEntries.setEnabled(false);
end_comment

begin_comment
comment|//        pushEntriesInt.setEnabled(false);
end_comment

begin_comment
comment|//        pushEntriesEmpty.setEnabled(false);
end_comment

begin_comment
comment|//        pushEntriesAdvanced.setEnabled(false);
end_comment

begin_comment
comment|//        update.setEnabled(false);
end_comment

begin_comment
comment|//        merge.setEnabled(false);
end_comment

begin_comment
comment|//        manageCitations.setEnabled(false);
end_comment

begin_comment
comment|//        exportCitations.setEnabled(false);
end_comment

begin_comment
comment|//        diag = new JDialog((JFrame) null, "OpenOffice/LibreOffice panel", false);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        FormBuilder mainBuilder = FormBuilder.create()
end_comment

begin_comment
comment|//                .layout(new FormLayout("fill:pref:grow", "p,p,p,p,p,p,p,p,p,p,p"));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        FormBuilder topRowBuilder = FormBuilder.create()
end_comment

begin_comment
comment|//                .layout(new FormLayout(
end_comment

begin_comment
comment|//                        "fill:pref:grow, 1dlu, fill:pref:grow, 1dlu, fill:pref:grow, 1dlu, fill:pref:grow, 1dlu, fill:pref",
end_comment

begin_comment
comment|//                        "pref"));
end_comment

begin_comment
comment|//        topRowBuilder.add(connect).xy(1, 1);
end_comment

begin_comment
comment|//        topRowBuilder.add(manualConnect).xy(3, 1);
end_comment

begin_comment
comment|//        topRowBuilder.add(selectDocument).xy(5, 1);
end_comment

begin_comment
comment|//        topRowBuilder.add(update).xy(7, 1);
end_comment

begin_comment
comment|//        topRowBuilder.add(help).xy(9, 1);
end_comment

begin_comment
comment|//        mainBuilder.add(topRowBuilder.getPanel()).xy(1, 1);
end_comment

begin_comment
comment|//        mainBuilder.add(setStyleFile).xy(1, 2);
end_comment

begin_comment
comment|//        mainBuilder.add(pushEntries).xy(1, 3);
end_comment

begin_comment
comment|//        mainBuilder.add(pushEntriesInt).xy(1, 4);
end_comment

begin_comment
comment|//        mainBuilder.add(pushEntriesAdvanced).xy(1, 5);
end_comment

begin_comment
comment|//        mainBuilder.add(pushEntriesEmpty).xy(1, 6);
end_comment

begin_comment
comment|//        mainBuilder.add(merge).xy(1, 7);
end_comment

begin_comment
comment|//        mainBuilder.add(manageCitations).xy(1, 8);
end_comment

begin_comment
comment|//        mainBuilder.add(exportCitations).xy(1, 9);
end_comment

begin_comment
comment|//        mainBuilder.add(settingsB).xy(1, 10);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        content = new JPanel();
end_comment

begin_comment
comment|//        content.setLayout(new BorderLayout());
end_comment

begin_comment
comment|//        content.add(mainBuilder.getPanel(), BorderLayout.CENTER);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        /*
end_comment

begin_comment
comment|//        frame.getTabbedPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
end_comment

begin_comment
comment|//                .put(Globals.getKeyPrefs().getKey(KeyBinding.REFRESH_OO), "Refresh OO");
end_comment

begin_comment
comment|//        frame.getTabbedPane().getActionMap().put("Refresh OO", updateAction);
end_comment

begin_comment
comment|//        */
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void exportEntries() {
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            if (style == null) {
end_comment

begin_comment
comment|//                style = loader.getUsedStyle();
end_comment

begin_comment
comment|//            } else {
end_comment

begin_comment
comment|//                style.ensureUpToDate();
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            ooBase.updateSortedReferenceMarks();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            List<BibDatabase> databases = getBaseList();
end_comment

begin_comment
comment|//            List<String> unresolvedKeys = ooBase.refreshCiteMarkers(databases, style);
end_comment

begin_comment
comment|//            BibDatabase newDatabase = ooBase.generateDatabase(databases);
end_comment

begin_comment
comment|//            if (!unresolvedKeys.isEmpty()) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                dialogService.showErrorDialogAndWait(Localization.lang("Unable to generate new library"),
end_comment

begin_comment
comment|//                        Localization.lang("Your OpenOffice/LibreOffice document references the BibTeX key '%0', which could not be found in your current library.",
end_comment

begin_comment
comment|//                                unresolvedKeys.get(0)));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            Defaults defaults = new Defaults(Globals.prefs.getDefaultBibDatabaseMode());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            BibDatabaseContext databaseContext = new BibDatabaseContext(newDatabase, defaults);
end_comment

begin_comment
comment|//            this.frame.addTab(databaseContext, true);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        } catch (BibEntryNotFoundException ex) {
end_comment

begin_comment
comment|//            LOGGER.debug("BibEntry not found", ex);
end_comment

begin_comment
comment|//            dialogService.showErrorDialogAndWait(Localization.lang("Unable to synchronize bibliography"),
end_comment

begin_comment
comment|//                    Localization.lang("Your OpenOffice/LibreOffice document references the BibTeX key '%0', which could not be found in your current library.",
end_comment

begin_comment
comment|//                            ex.getBibtexKey()));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        } catch (com.sun.star.lang.IllegalArgumentException | UnknownPropertyException | PropertyVetoException |
end_comment

begin_comment
comment|//                UndefinedCharacterFormatException | NoSuchElementException | WrappedTargetException | IOException |
end_comment

begin_comment
comment|//                CreationException e) {
end_comment

begin_comment
comment|//            LOGGER.warn("Problem generating new database.", e);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private List<BibDatabase> getBaseList() {
end_comment

begin_comment
comment|//        List<BibDatabase> databases = new ArrayList<>();
end_comment

begin_comment
comment|//        if (preferences.getUseAllDatabases()) {
end_comment

begin_comment
comment|//            for (BasePanel basePanel : frame.getBasePanelList()) {
end_comment

begin_comment
comment|//                databases.add(basePanel.getDatabase());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            databases.add(frame.getCurrentBasePanel().getDatabase());
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        return databases;
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void connectAutomatically() {
end_comment

begin_comment
comment|//        BackgroundTask
end_comment

begin_comment
comment|//                .wrap(() -> {
end_comment

begin_comment
comment|//                    DetectOpenOfficeInstallation officeInstallation = new DetectOpenOfficeInstallation(diag, preferences, dialogService);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    Boolean installed = officeInstallation.isInstalled().get();
end_comment

begin_comment
comment|//                    if (installed == null || !installed) {
end_comment

begin_comment
comment|//                        throw new IllegalStateException("OpenOffice Installation could not be detected.");
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    return null; // can not use BackgroundTask.wrap(Runnable) because Runnable.run() can't throw exceptions
end_comment

begin_comment
comment|//                })
end_comment

begin_comment
comment|//                .onSuccess(x -> connect())
end_comment

begin_comment
comment|//                .onFailure(ex ->
end_comment

begin_comment
comment|//                        dialogService.showErrorDialogAndWait(Localization.lang("Autodetection failed"), Localization.lang("Autodetection failed"), ex))
end_comment

begin_comment
comment|//                .executeWith(Globals.TASK_EXECUTOR);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void connectManually() {
end_comment

begin_comment
comment|//        showManualConnectionDialog();
end_comment

begin_comment
comment|//        if (!dialogOkPressed) {
end_comment

begin_comment
comment|//            return;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        connect();
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void connect() {
end_comment

begin_comment
comment|//        JDialog progressDialog = null;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            // Add OO JARs to the classpath
end_comment

begin_comment
comment|//            loadOpenOfficeJars(Paths.get(preferences.getInstallationPath()));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            // Show progress dialog:
end_comment

begin_comment
comment|//            progressDialog = new DetectOpenOfficeInstallation(diag, preferences, dialogService)
end_comment

begin_comment
comment|//                    .showProgressDialog(diag, Localization.lang("Connecting"), Localization.lang("Please wait..."));
end_comment

begin_comment
comment|//            JDialog finalProgressDialog = progressDialog;
end_comment

begin_comment
comment|//            BackgroundTask
end_comment

begin_comment
comment|//                    .wrap(this::createBibBase)
end_comment

begin_comment
comment|//                    .onFinished(() -> SwingUtilities.invokeLater(() -> {
end_comment

begin_comment
comment|//                        finalProgressDialog.dispose();
end_comment

begin_comment
comment|//                        diag.dispose();
end_comment

begin_comment
comment|//                    }))
end_comment

begin_comment
comment|//                    .onSuccess(ooBase -> {
end_comment

begin_comment
comment|//                        this.ooBase = ooBase;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                        if (ooBase.isConnectedToDocument()) {
end_comment

begin_comment
comment|//                            frame.output(Localization.lang("Connected to document") + ": " + ooBase.getCurrentDocumentTitle().orElse(""));
end_comment

begin_comment
comment|//                        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                        // Enable actions that depend on Connect:
end_comment

begin_comment
comment|//                        selectDocument.setEnabled(true);
end_comment

begin_comment
comment|//                        pushEntries.setEnabled(true);
end_comment

begin_comment
comment|//                        pushEntriesInt.setEnabled(true);
end_comment

begin_comment
comment|//                        pushEntriesEmpty.setEnabled(true);
end_comment

begin_comment
comment|//                        pushEntriesAdvanced.setEnabled(true);
end_comment

begin_comment
comment|//                        update.setEnabled(true);
end_comment

begin_comment
comment|//                        merge.setEnabled(true);
end_comment

begin_comment
comment|//                        manageCitations.setEnabled(true);
end_comment

begin_comment
comment|//                        exportCitations.setEnabled(true);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    })
end_comment

begin_comment
comment|//                    .onFailure(ex ->
end_comment

begin_comment
comment|//                            dialogService.showErrorDialogAndWait(Localization.lang("Autodetection failed"), Localization.lang("Autodetection failed"), ex))
end_comment

begin_comment
comment|//                    .executeWith(Globals.TASK_EXECUTOR);
end_comment

begin_comment
comment|//            diag.dispose();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        } catch (UnsatisfiedLinkError e) {
end_comment

begin_comment
comment|//            LOGGER.warn("Could not connect to running OpenOffice/LibreOffice", e);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            DefaultTaskExecutor.runInJavaFXThread(() ->
end_comment

begin_comment
comment|//                    dialogService.showErrorDialogAndWait(Localization.lang("Unable to connect. One possible reason is that JabRef "
end_comment

begin_comment
comment|//                            + "and OpenOffice/LibreOffice are not both running in either 32 bit mode or 64 bit mode.")));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        } catch (IOException e) {
end_comment

begin_comment
comment|//            LOGGER.warn("Could not connect to running OpenOffice/LibreOffice", e);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            DefaultTaskExecutor.runInJavaFXThread(() ->
end_comment

begin_comment
comment|//                    dialogService.showErrorDialogAndWait(Localization.lang("Could not connect to running OpenOffice/LibreOffice."),
end_comment

begin_comment
comment|//                            Localization.lang("Could not connect to running OpenOffice/LibreOffice.") + "\n"
end_comment

begin_comment
comment|//                                    + Localization.lang("Make sure you have installed OpenOffice/LibreOffice with Java support.") + "\n"
end_comment

begin_comment
comment|//                                    + Localization.lang("If connecting manually, please verify program and library paths.")
end_comment

begin_comment
comment|//                                    + "\n" + "\n" + Localization.lang("Error message:"),
end_comment

begin_comment
comment|//                            e));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        } finally {
end_comment

begin_comment
comment|//            if (progressDialog != null) {
end_comment

begin_comment
comment|//                progressDialog.dispose();
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void loadOpenOfficeJars(Path configurationPath) throws IOException {
end_comment

begin_comment
comment|//        List<Optional<Path>> filePaths = OpenOfficePreferences.OO_JARS.stream().map(jar -> FileUtil.find(jar, configurationPath)).collect(Collectors.toList());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        if (!filePaths.stream().allMatch(Optional::isPresent)) {
end_comment

begin_comment
comment|//            throw new IOException("(Not all) required Open Office Jars were found inside installation path.");
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        List<URL> jarURLs = new ArrayList<>(OpenOfficePreferences.OO_JARS.size());
end_comment

begin_comment
comment|//        for (Optional<Path> jarPath : filePaths) {
end_comment

begin_comment
comment|//            jarURLs.add((jarPath.get().toUri().toURL()));
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        addURL(jarURLs);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private OOBibBase createBibBase() throws IOException, InvocationTargetException, IllegalAccessException,
end_comment

begin_comment
comment|//            WrappedTargetException, BootstrapException, UnknownPropertyException, NoDocumentException,
end_comment

begin_comment
comment|//            NoSuchElementException, CreationException {
end_comment

begin_comment
comment|//        // Connect
end_comment

begin_comment
comment|//        return new OOBibBase(preferences.getExecutablePath(), true);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static void addURL(List<URL> jarList) throws IOException {
end_comment

begin_comment
comment|//        URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
end_comment

begin_comment
comment|//        Class<URLClassLoader> sysclass = URLClassLoader.class;
end_comment

begin_comment
comment|//        try {
end_comment

begin_comment
comment|//            Method method = sysclass.getDeclaredMethod("addURL", URL.class);
end_comment

begin_comment
comment|//            method.setAccessible(true);
end_comment

begin_comment
comment|//            for (URL anU : jarList) {
end_comment

begin_comment
comment|//                method.invoke(sysloader, anU);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        } catch (SecurityException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException |
end_comment

begin_comment
comment|//                InvocationTargetException e) {
end_comment

begin_comment
comment|//            LOGGER.error("Could not add URL to system classloader", e);
end_comment

begin_comment
comment|//            throw new IOException("Error, could not add URL to system classloader", e);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void showManualConnectionDialog() {
end_comment

begin_comment
comment|//        dialogOkPressed = false;
end_comment

begin_comment
comment|//        final JDialog cDiag = new JDialog((JFrame) null, Localization.lang("Set connection parameters"), true);
end_comment

begin_comment
comment|//        final NativeDesktop nativeDesktop = JabRefDesktop.getNativeDesktop();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        final DialogService dialogService = this.dialogService;
end_comment

begin_comment
comment|//        DirectoryDialogConfiguration dirDialogConfiguration = new DirectoryDialogConfiguration.Builder()
end_comment

begin_comment
comment|//                .withInitialDirectory(nativeDesktop.getApplicationDirectory())
end_comment

begin_comment
comment|//                .build();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        FileDialogConfiguration fileDialogConfiguration = new FileDialogConfiguration.Builder()
end_comment

begin_comment
comment|//                .withInitialDirectory(nativeDesktop.getApplicationDirectory())
end_comment

begin_comment
comment|//                .build();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Path fields
end_comment

begin_comment
comment|//        final JTextField ooPath = new JTextField(30);
end_comment

begin_comment
comment|//        JButton browseOOPath = new JButton(Localization.lang("Browse"));
end_comment

begin_comment
comment|//        ooPath.setText(preferences.getInstallationPath());
end_comment

begin_comment
comment|//        browseOOPath.addActionListener(e -> DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showDirectorySelectionDialog(dirDialogConfiguration))
end_comment

begin_comment
comment|//                .ifPresent(f -> ooPath.setText(f.toAbsolutePath().toString())));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        final JTextField ooExec = new JTextField(30);
end_comment

begin_comment
comment|//        JButton browseOOExec = new JButton(Localization.lang("Browse"));
end_comment

begin_comment
comment|//        ooExec.setText(preferences.getExecutablePath());
end_comment

begin_comment
comment|//        browseOOExec.addActionListener(e -> DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showFileOpenDialog(fileDialogConfiguration))
end_comment

begin_comment
comment|//                .ifPresent(f -> ooExec.setText(f.toAbsolutePath().toString())));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        final JTextField ooJars = new JTextField(30);
end_comment

begin_comment
comment|//        ooJars.setText(preferences.getJarsPath());
end_comment

begin_comment
comment|//        JButton browseOOJars = new JButton(Localization.lang("Browse"));
end_comment

begin_comment
comment|//        browseOOJars.addActionListener(e -> DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showDirectorySelectionDialog(dirDialogConfiguration))
end_comment

begin_comment
comment|//                .ifPresent(f -> ooJars.setText(f.toAbsolutePath().toString())));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        FormBuilder builder = FormBuilder.create()
end_comment

begin_comment
comment|//                .layout(new FormLayout("left:pref, 4dlu, fill:pref:grow, 4dlu, fill:pref", "pref"));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        if (OS.WINDOWS || OS.OS_X) {
end_comment

begin_comment
comment|//            builder.add(Localization.lang("Path to OpenOffice/LibreOffice directory")).xy(1, 1);
end_comment

begin_comment
comment|//            builder.add(ooPath).xy(3, 1);
end_comment

begin_comment
comment|//            builder.add(browseOOPath).xy(5, 1);
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            builder.add(Localization.lang("Path to OpenOffice/LibreOffice executable")).xy(1, 1);
end_comment

begin_comment
comment|//            builder.add(ooExec).xy(3, 1);
end_comment

begin_comment
comment|//            builder.add(browseOOExec).xy(5, 1);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            builder.appendRows("4dlu, pref");
end_comment

begin_comment
comment|//            builder.add(Localization.lang("Path to OpenOffice/LibreOffice library dir")).xy(1, 3);
end_comment

begin_comment
comment|//            builder.add(ooJars).xy(3, 3);
end_comment

begin_comment
comment|//            builder.add(browseOOJars).xy(5, 3);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        builder.padding("5dlu, 5dlu, 5dlu, 5dlu");
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        cDiag.getContentPane().add(builder.getPanel(), BorderLayout.CENTER);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Buttons
end_comment

begin_comment
comment|//        JButton ok = new JButton(Localization.lang("OK"));
end_comment

begin_comment
comment|//        JButton cancel = new JButton(Localization.lang("Cancel"));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        ok.addActionListener(e -> {
end_comment

begin_comment
comment|//            if (OS.WINDOWS || OS.OS_X) {
end_comment

begin_comment
comment|//                preferences.updateConnectionParams(ooPath.getText(), ooPath.getText(), ooPath.getText());
end_comment

begin_comment
comment|//            } else {
end_comment

begin_comment
comment|//                preferences.updateConnectionParams(ooPath.getText(), ooExec.getText(), ooJars.getText());
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            dialogOkPressed = true;
end_comment

begin_comment
comment|//            cDiag.dispose();
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//        cancel.addActionListener(e -> cDiag.dispose());
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        ButtonBarBuilder bb = new ButtonBarBuilder();
end_comment

begin_comment
comment|//        bb.addGlue();
end_comment

begin_comment
comment|//        bb.addRelatedGap();
end_comment

begin_comment
comment|//        bb.addButton(ok);
end_comment

begin_comment
comment|//        bb.addButton(cancel);
end_comment

begin_comment
comment|//        bb.addGlue();
end_comment

begin_comment
comment|//        bb.padding("5dlu, 5dlu, 5dlu, 5dlu");
end_comment

begin_comment
comment|//        cDiag.getContentPane().add(bb.getPanel(), BorderLayout.SOUTH);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Finish and show dirDialog
end_comment

begin_comment
comment|//        cDiag.pack();
end_comment

begin_comment
comment|//        cDiag.setVisible(true);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void pushEntries(boolean inParenthesisIn, boolean withText, boolean addPageInfo) {
end_comment

begin_comment
comment|//        if (!ooBase.isConnectedToDocument()) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showErrorDialogAndWait(
end_comment

begin_comment
comment|//                    Localization.lang("Error pushing entries"), Localization.lang("Not connected to any Writer document. Please"
end_comment

begin_comment
comment|//                            + " make sure a document is open, and use the 'Select Writer document' button to connect to it.")));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            return;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Boolean inParenthesis = inParenthesisIn;
end_comment

begin_comment
comment|//        String pageInfo = null;
end_comment

begin_comment
comment|//        if (addPageInfo) {
end_comment

begin_comment
comment|//            AdvancedCiteDialog citeDialog = new AdvancedCiteDialog(frame);
end_comment

begin_comment
comment|//            citeDialog.showDialog();
end_comment

begin_comment
comment|//            if (citeDialog.canceled()) {
end_comment

begin_comment
comment|//                return;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            if (!citeDialog.getPageInfo().isEmpty()) {
end_comment

begin_comment
comment|//                pageInfo = citeDialog.getPageInfo();
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            inParenthesis = citeDialog.isInParenthesisCite();
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        BasePanel panel = frame.getCurrentBasePanel();
end_comment

begin_comment
comment|//        if (panel != null) {
end_comment

begin_comment
comment|//            final BibDatabase database = panel.getDatabase();
end_comment

begin_comment
comment|//            List<BibEntry> entries = panel.getSelectedEntries();
end_comment

begin_comment
comment|//            if (!entries.isEmpty()&& checkThatEntriesHaveKeys(entries)) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                try {
end_comment

begin_comment
comment|//                    if (style == null) {
end_comment

begin_comment
comment|//                        style = loader.getUsedStyle();
end_comment

begin_comment
comment|//                    }
end_comment

begin_comment
comment|//                    ooBase.insertEntry(entries, database, getBaseList(), style, inParenthesis, withText, pageInfo,
end_comment

begin_comment
comment|//                            preferences.getSyncWhenCiting());
end_comment

begin_comment
comment|//                } catch (FileNotFoundException ex) {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showErrorDialogAndWait(
end_comment

begin_comment
comment|//                            Localization.lang("No valid style file defined"),
end_comment

begin_comment
comment|//                            Localization.lang("You must select either a valid style file, or use one of the default styles.")));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                    LOGGER.warn("Problem with style file", ex);
end_comment

begin_comment
comment|//                } catch (ConnectionLostException ex) {
end_comment

begin_comment
comment|//                    showConnectionLostErrorMessage();
end_comment

begin_comment
comment|//                } catch (UndefinedCharacterFormatException ex) {
end_comment

begin_comment
comment|//                    reportUndefinedCharacterFormat(ex);
end_comment

begin_comment
comment|//                } catch (UndefinedParagraphFormatException ex) {
end_comment

begin_comment
comment|//                    reportUndefinedParagraphFormat(ex);
end_comment

begin_comment
comment|//                } catch (com.sun.star.lang.IllegalArgumentException | UnknownPropertyException | PropertyVetoException |
end_comment

begin_comment
comment|//                        CreationException | NoSuchElementException | WrappedTargetException | IOException |
end_comment

begin_comment
comment|//                        BibEntryNotFoundException | IllegalTypeException | PropertyExistException |
end_comment

begin_comment
comment|//                        NotRemoveableException ex) {
end_comment

begin_comment
comment|//                    LOGGER.warn("Could not insert entry", ex);
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    /**
end_comment

begin_comment
comment|//     * Check that all entries in the list have BibTeX keys, if not ask if they should be generated
end_comment

begin_comment
comment|//     *
end_comment

begin_comment
comment|//     * @param entries A list of entries to be checked
end_comment

begin_comment
comment|//     * @return true if all entries have BibTeX keys, if it so may be after generating them
end_comment

begin_comment
comment|//     */
end_comment

begin_comment
comment|//    private boolean checkThatEntriesHaveKeys(List<BibEntry> entries) {
end_comment

begin_comment
comment|//        // Check if there are empty keys
end_comment

begin_comment
comment|//        boolean emptyKeys = false;
end_comment

begin_comment
comment|//        for (BibEntry entry : entries) {
end_comment

begin_comment
comment|//            if (!entry.getCiteKeyOptional().isPresent()) {
end_comment

begin_comment
comment|//                // Found one, no need to look further for now
end_comment

begin_comment
comment|//                emptyKeys = true;
end_comment

begin_comment
comment|//                break;
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // If no empty keys, return true
end_comment

begin_comment
comment|//        if (!emptyKeys) {
end_comment

begin_comment
comment|//            return true;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        // Ask if keys should be generated
end_comment

begin_comment
comment|//        boolean citePressed = dialogService.showConfirmationDialogAndWait(Localization.lang("Cite"),
end_comment

begin_comment
comment|//                Localization.lang("Cannot cite entries without BibTeX keys. Generate keys now?"),
end_comment

begin_comment
comment|//                Localization.lang("Generate keys"),
end_comment

begin_comment
comment|//                Localization.lang("Cancel"));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        BasePanel panel = frame.getCurrentBasePanel();
end_comment

begin_comment
comment|//        if (citePressed&& (panel != null)) {
end_comment

begin_comment
comment|//            // Generate keys
end_comment

begin_comment
comment|//            BibtexKeyPatternPreferences prefs = Globals.prefs.getBibtexKeyPatternPreferences();
end_comment

begin_comment
comment|//            NamedCompound undoCompound = new NamedCompound(Localization.lang("Cite"));
end_comment

begin_comment
comment|//            for (BibEntry entry : entries) {
end_comment

begin_comment
comment|//                if (!entry.getCiteKeyOptional().isPresent()) {
end_comment

begin_comment
comment|//                    // Generate key
end_comment

begin_comment
comment|//                    new BibtexKeyGenerator(panel.getBibDatabaseContext(), prefs)
end_comment

begin_comment
comment|//                            .generateAndSetKey(entry)
end_comment

begin_comment
comment|//                            .ifPresent(change -> undoCompound.addEdit(new UndoableKeyChange(change)));
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            undoCompound.end();
end_comment

begin_comment
comment|//            // Add all undos
end_comment

begin_comment
comment|//            panel.getUndoManager().addEdit(undoCompound);
end_comment

begin_comment
comment|//            // Now every entry has a key
end_comment

begin_comment
comment|//            return true;
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            // No, we canceled (or there is no panel to get the database from, highly unlikely)
end_comment

begin_comment
comment|//            return false;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void showConnectionLostErrorMessage() {
end_comment

begin_comment
comment|//        DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showErrorDialogAndWait(Localization.lang("Connection lost"),
end_comment

begin_comment
comment|//                Localization.lang("Connection to OpenOffice/LibreOffice has been lost. "
end_comment

begin_comment
comment|//                        + "Please make sure OpenOffice/LibreOffice is running, and try to reconnect.")));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void reportUndefinedParagraphFormat(UndefinedParagraphFormatException ex) {
end_comment

begin_comment
comment|//        DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showErrorDialogAndWait(Localization.lang("Undefined paragraph format"),
end_comment

begin_comment
comment|//                Localization.lang("Your style file specifies the paragraph format '%0', "
end_comment

begin_comment
comment|//                        + "which is undefined in your current OpenOffice/LibreOffice document.",
end_comment

begin_comment
comment|//                        ex.getFormatName())
end_comment

begin_comment
comment|//                        + "\n" +
end_comment

begin_comment
comment|//                        Localization.lang("The paragraph format is controlled by the property 'ReferenceParagraphFormat' or 'ReferenceHeaderParagraphFormat' in the style file.")));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void reportUndefinedCharacterFormat(UndefinedCharacterFormatException ex) {
end_comment

begin_comment
comment|//        DefaultTaskExecutor.runInJavaFXThread(() -> dialogService.showErrorDialogAndWait(Localization.lang("Undefined character format"),
end_comment

begin_comment
comment|//                Localization.lang(
end_comment

begin_comment
comment|//                        "Your style file specifies the character format '%0', "
end_comment

begin_comment
comment|//                                + "which is undefined in your current OpenOffice/LibreOffice document.",
end_comment

begin_comment
comment|//                        ex.getFormatName())
end_comment

begin_comment
comment|//                        + "\n"
end_comment

begin_comment
comment|//                        + Localization.lang("The character format is controlled by the citation property 'CitationCharacterFormat' in the style file.")
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        ));
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void showSettingsPopup() {
end_comment

begin_comment
comment|//        JPopupMenu menu = new JPopupMenu();
end_comment

begin_comment
comment|//        final JCheckBoxMenuItem autoSync = new JCheckBoxMenuItem(
end_comment

begin_comment
comment|//                Localization.lang("Automatically sync bibliography when inserting citations"),
end_comment

begin_comment
comment|//                preferences.getSyncWhenCiting());
end_comment

begin_comment
comment|//        final JRadioButtonMenuItem useActiveBase = new JRadioButtonMenuItem(
end_comment

begin_comment
comment|//                Localization.lang("Look up BibTeX entries in the active tab only"));
end_comment

begin_comment
comment|//        final JRadioButtonMenuItem useAllBases = new JRadioButtonMenuItem(
end_comment

begin_comment
comment|//                Localization.lang("Look up BibTeX entries in all open libraries"));
end_comment

begin_comment
comment|//        final JMenuItem clearConnectionSettings = new JMenuItem(Localization.lang("Clear connection settings"));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        ButtonGroup lookupButtonGroup = new ButtonGroup();
end_comment

begin_comment
comment|//        lookupButtonGroup.add(useActiveBase);
end_comment

begin_comment
comment|//        lookupButtonGroup.add(useAllBases);
end_comment

begin_comment
comment|//        if (preferences.getUseAllDatabases()) {
end_comment

begin_comment
comment|//            useAllBases.setSelected(true);
end_comment

begin_comment
comment|//        } else {
end_comment

begin_comment
comment|//            useActiveBase.setSelected(true);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        autoSync.addActionListener(e -> {
end_comment

begin_comment
comment|//            preferences.setSyncWhenCiting(autoSync.isSelected());
end_comment

begin_comment
comment|//            Globals.prefs.setOpenOfficePreferences(preferences);
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//        useAllBases.addActionListener(e -> {
end_comment

begin_comment
comment|//            preferences.setUseAllDatabases(useAllBases.isSelected());
end_comment

begin_comment
comment|//            Globals.prefs.setOpenOfficePreferences(preferences);
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//        useActiveBase.addActionListener(e -> {
end_comment

begin_comment
comment|//            preferences.setUseAllDatabases(!useActiveBase.isSelected());
end_comment

begin_comment
comment|//            Globals.prefs.setOpenOfficePreferences(preferences);
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//        clearConnectionSettings.addActionListener(e -> {
end_comment

begin_comment
comment|//            preferences.clearConnectionSettings();
end_comment

begin_comment
comment|//            Globals.prefs.setOpenOfficePreferences(preferences);
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        menu.add(autoSync);
end_comment

begin_comment
comment|//        menu.addSeparator();
end_comment

begin_comment
comment|//        menu.add(useActiveBase);
end_comment

begin_comment
comment|//        menu.add(useAllBases);
end_comment

begin_comment
comment|//        menu.addSeparator();
end_comment

begin_comment
comment|//        menu.add(clearConnectionSettings);
end_comment

begin_comment
comment|//        menu.show(settingsB, 0, settingsB.getHeight());
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//}
end_comment

end_unit

