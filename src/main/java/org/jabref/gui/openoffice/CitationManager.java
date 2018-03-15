begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// TODO: has an internal dependency exporting org.w3c.dom, temporarily removed
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
comment|//import java.awt.event.MouseAdapter;
end_comment

begin_comment
comment|//import java.awt.event.MouseEvent;
end_comment

begin_comment
comment|//import java.util.List;
end_comment

begin_comment
comment|//import java.util.Optional;
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
comment|//import javax.swing.BorderFactory;
end_comment

begin_comment
comment|//import javax.swing.JButton;
end_comment

begin_comment
comment|//import javax.swing.JComponent;
end_comment

begin_comment
comment|//import javax.swing.JDialog;
end_comment

begin_comment
comment|//import javax.swing.JFrame;
end_comment

begin_comment
comment|//import javax.swing.JScrollPane;
end_comment

begin_comment
comment|//import javax.swing.JTable;
end_comment

begin_comment
comment|//import javax.swing.JTextField;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import org.jabref.Globals;
end_comment

begin_comment
comment|//import org.jabref.gui.DialogService;
end_comment

begin_comment
comment|//import org.jabref.gui.keyboard.KeyBinding;
end_comment

begin_comment
comment|//import org.jabref.logic.l10n.Localization;
end_comment

begin_comment
comment|//import org.jabref.logic.openoffice.CitationEntry;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import ca.odell.glazedlists.BasicEventList;
end_comment

begin_comment
comment|//import ca.odell.glazedlists.EventList;
end_comment

begin_comment
comment|//import ca.odell.glazedlists.gui.TableFormat;
end_comment

begin_comment
comment|//import ca.odell.glazedlists.swing.DefaultEventTableModel;
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
comment|//import com.sun.star.beans.UnknownPropertyException;
end_comment

begin_comment
comment|//import com.sun.star.container.NoSuchElementException;
end_comment

begin_comment
comment|//import com.sun.star.container.XNameAccess;
end_comment

begin_comment
comment|//import com.sun.star.lang.IllegalArgumentException;
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
comment|// * Dialog for modifying existing citations.
end_comment

begin_comment
comment|// */
end_comment

begin_comment
comment|//class CitationManager {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static final Logger LOGGER = LoggerFactory.getLogger(CitationManager.class);
end_comment

begin_comment
comment|//    private final OOBibBase ooBase;
end_comment

begin_comment
comment|//    private final JDialog diag;
end_comment

begin_comment
comment|//    private final EventList<CitationEntry> list;
end_comment

begin_comment
comment|//    private final JTable table;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private final DefaultEventTableModel<CitationEntry> tableModel;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    public CitationManager(OOBibBase ooBase, DialogService dialogService)
end_comment

begin_comment
comment|//            throws NoSuchElementException, WrappedTargetException, UnknownPropertyException {
end_comment

begin_comment
comment|//        diag = new JDialog((JFrame) null, Localization.lang("Manage citations"), true);
end_comment

begin_comment
comment|//        this.ooBase = ooBase;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        list = new BasicEventList<>();
end_comment

begin_comment
comment|//        XNameAccess nameAccess = ooBase.getReferenceMarks();
end_comment

begin_comment
comment|//        List<String> names = ooBase.getJabRefReferenceMarks(nameAccess);
end_comment

begin_comment
comment|//        for (String name : names) {
end_comment

begin_comment
comment|//            list.add(new CitationEntry(name,
end_comment

begin_comment
comment|//                    "<html>..." + ooBase.getCitationContext(nameAccess, name, 30, 30, true) + "...</html>",
end_comment

begin_comment
comment|//                    ooBase.getCustomProperty(name)));
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//        tableModel = new DefaultEventTableModel<>(list, new CitationEntryFormat());
end_comment

begin_comment
comment|//        table = new JTable(tableModel);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        diag.add(new JScrollPane(table), BorderLayout.CENTER);
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
comment|//        JButton ok = new JButton(Localization.lang("OK"));
end_comment

begin_comment
comment|//        bb.addButton(ok);
end_comment

begin_comment
comment|//        JButton cancel = new JButton(Localization.lang("Cancel"));
end_comment

begin_comment
comment|//        bb.addButton(cancel);
end_comment

begin_comment
comment|//        bb.addGlue();
end_comment

begin_comment
comment|//        bb.getPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
end_comment

begin_comment
comment|//        diag.add(bb.getPanel(), BorderLayout.SOUTH);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        diag.pack();
end_comment

begin_comment
comment|//        diag.setSize(700, 400);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        ok.addActionListener(e -> {
end_comment

begin_comment
comment|//            try {
end_comment

begin_comment
comment|//                storeSettings();
end_comment

begin_comment
comment|//            } catch (UnknownPropertyException | NotRemoveableException | PropertyExistException | IllegalTypeException |
end_comment

begin_comment
comment|//                    IllegalArgumentException ex) {
end_comment

begin_comment
comment|//                LOGGER.warn("Problem modifying citation", ex);
end_comment

begin_comment
comment|//                dialogService.showErrorDialogAndWait(Localization.lang("Problem modifying citation"), ex);
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//            diag.dispose();
end_comment

begin_comment
comment|//        });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        Action cancelAction = new AbstractAction() {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            @Override
end_comment

begin_comment
comment|//            public void actionPerformed(ActionEvent actionEvent) {
end_comment

begin_comment
comment|//                diag.dispose();
end_comment

begin_comment
comment|//            }
end_comment

begin_comment
comment|//        };
end_comment

begin_comment
comment|//        cancel.addActionListener(cancelAction);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        bb.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put
end_comment

begin_comment
comment|//                (Globals.getKeyPrefs().getKey(KeyBinding.CLOSE), "close");
end_comment

begin_comment
comment|//        bb.getPanel().getActionMap().put("close", cancelAction);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        table.getColumnModel().getColumn(0).setPreferredWidth(580);
end_comment

begin_comment
comment|//        table.getColumnModel().getColumn(1).setPreferredWidth(110);
end_comment

begin_comment
comment|//        table.setPreferredScrollableViewportSize(new Dimension(700, 500));
end_comment

begin_comment
comment|//        table.addMouseListener(new TableClickListener());
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private void storeSettings() throws UnknownPropertyException, NotRemoveableException, PropertyExistException,
end_comment

begin_comment
comment|//            IllegalTypeException, IllegalArgumentException {
end_comment

begin_comment
comment|//        for (CitationEntry entry : list) {
end_comment

begin_comment
comment|//            Optional<String> pageInfo = entry.getPageInfo();
end_comment

begin_comment
comment|//            if (entry.pageInfoChanged()&& pageInfo.isPresent()) {
end_comment

begin_comment
comment|//                ooBase.setCustomProperty(entry.getRefMarkName(), pageInfo.get());
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
comment|//    public void showDialog() {
end_comment

begin_comment
comment|//        diag.setLocationRelativeTo(diag.getParent());
end_comment

begin_comment
comment|//        diag.setVisible(true);
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    private static class CitationEntryFormat implements TableFormat<CitationEntry> {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        @Override
end_comment

begin_comment
comment|//        public int getColumnCount() {
end_comment

begin_comment
comment|//            return 2;
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        @Override
end_comment

begin_comment
comment|//        public String getColumnName(int i) {
end_comment

begin_comment
comment|//            if (i == 0) {
end_comment

begin_comment
comment|//                return Localization.lang("Citation");
end_comment

begin_comment
comment|//            } else {
end_comment

begin_comment
comment|//                return Localization.lang("Extra information");
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
comment|//        @Override
end_comment

begin_comment
comment|//        public Object getColumnValue(CitationEntry citEntry, int i) {
end_comment

begin_comment
comment|//            if (i == 0) {
end_comment

begin_comment
comment|//                return citEntry.getContext();
end_comment

begin_comment
comment|//            } else {
end_comment

begin_comment
comment|//                return citEntry.getPageInfo().orElse("");
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
comment|//    private class TableClickListener extends MouseAdapter {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        @Override
end_comment

begin_comment
comment|//        public void mouseClicked(MouseEvent e) {
end_comment

begin_comment
comment|//            if ((e.getButton() == MouseEvent.BUTTON1)&& (e.getClickCount() == 2)) {
end_comment

begin_comment
comment|//                int row = table.rowAtPoint(e.getPoint());
end_comment

begin_comment
comment|//                if (row>= 0) {
end_comment

begin_comment
comment|//                    SingleCitationDialog scd = new SingleCitationDialog(list.get(row));
end_comment

begin_comment
comment|//                    scd.showDialog();
end_comment

begin_comment
comment|//                }
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
comment|//    class SingleCitationDialog {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        private final JDialog singleCiteDialog;
end_comment

begin_comment
comment|//        private final JTextField pageInfo = new JTextField(20);
end_comment

begin_comment
comment|//        private final JButton okButton = new JButton(Localization.lang("OK"));
end_comment

begin_comment
comment|//        private final JButton cancelButton = new JButton(Localization.lang("Cancel"));
end_comment

begin_comment
comment|//        private final CitationEntry entry;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//        public SingleCitationDialog(CitationEntry citEntry) {
end_comment

begin_comment
comment|//            this.entry = citEntry;
end_comment

begin_comment
comment|//            pageInfo.setText(entry.getPageInfo().orElse(""));
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            singleCiteDialog = new JDialog(CitationManager.this.diag, Localization.lang("Citation"), true);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            FormBuilder builder = FormBuilder.create()
end_comment

begin_comment
comment|//                    .layout(new FormLayout("left:pref, 4dlu, fill:150dlu:grow", "pref, 4dlu, pref"));
end_comment

begin_comment
comment|//            builder.add(entry.getContext()).xyw(1, 1, 3);
end_comment

begin_comment
comment|//            builder.add(Localization.lang("Extra information (e.g. page number)")).xy(1, 3);
end_comment

begin_comment
comment|//            builder.add(pageInfo).xy(3, 3);
end_comment

begin_comment
comment|//            builder.padding("10dlu, 10dlu, 10dlu, 10dlu");
end_comment

begin_comment
comment|//            singleCiteDialog.getContentPane().add(builder.getPanel(), BorderLayout.CENTER);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            ButtonBarBuilder bb = new ButtonBarBuilder();
end_comment

begin_comment
comment|//            bb.addGlue();
end_comment

begin_comment
comment|//            bb.addButton(okButton);
end_comment

begin_comment
comment|//            bb.addButton(cancelButton);
end_comment

begin_comment
comment|//            bb.addGlue();
end_comment

begin_comment
comment|//            bb.padding("5dlu, 5dlu, 5dlu, 5dlu");
end_comment

begin_comment
comment|//            singleCiteDialog.add(bb.getPanel(), BorderLayout.SOUTH);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            okButton.addActionListener(e -> {
end_comment

begin_comment
comment|//                if (pageInfo.getText().trim().isEmpty()) {
end_comment

begin_comment
comment|//                    entry.setPageInfo(null);
end_comment

begin_comment
comment|//                } else {
end_comment

begin_comment
comment|//                    entry.setPageInfo(pageInfo.getText().trim());
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//                tableModel.fireTableDataChanged();
end_comment

begin_comment
comment|//                singleCiteDialog.dispose();
end_comment

begin_comment
comment|//            });
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            Action cancelAction = new AbstractAction() {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//                @Override
end_comment

begin_comment
comment|//                public void actionPerformed(ActionEvent actionEvent) {
end_comment

begin_comment
comment|//                    singleCiteDialog.dispose();
end_comment

begin_comment
comment|//                }
end_comment

begin_comment
comment|//            };
end_comment

begin_comment
comment|//            cancelButton.addActionListener(cancelAction);
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//            builder.getPanel().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put
end_comment

begin_comment
comment|//                    (Globals.getKeyPrefs().getKey(KeyBinding.CLOSE), "close");
end_comment

begin_comment
comment|//            builder.getPanel().getActionMap().put("close", cancelAction);
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
comment|//        public void showDialog() {
end_comment

begin_comment
comment|//            singleCiteDialog.pack();
end_comment

begin_comment
comment|//            singleCiteDialog.setLocationRelativeTo(singleCiteDialog.getParent());
end_comment

begin_comment
comment|//            singleCiteDialog.setVisible(true);
end_comment

begin_comment
comment|//        }
end_comment

begin_comment
comment|//    }
end_comment

begin_comment
comment|//}
end_comment

end_unit

