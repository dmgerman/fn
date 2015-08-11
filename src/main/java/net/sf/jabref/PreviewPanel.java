begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|print
operator|.
name|PrinterException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyChangeEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyVetoException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|VetoableChangeListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|print
operator|.
name|attribute
operator|.
name|HashPrintRequestAttributeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|print
operator|.
name|attribute
operator|.
name|PrintRequestAttributeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|print
operator|.
name|attribute
operator|.
name|standard
operator|.
name|JobName
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|HyperlinkEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|HyperlinkListener
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|Layout
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|LayoutHelper
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|ExportFormats
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|PreviewPanelTransferHandler
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * Displays an BibtexEntry using the given layout format.  */
end_comment

begin_class
DECL|class|PreviewPanel
specifier|public
class|class
name|PreviewPanel
extends|extends
name|JPanel
implements|implements
name|VetoableChangeListener
implements|,
name|SearchTextListener
implements|,
name|EntryContainer
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|/**      * The bibtex entry currently shown      */
DECL|field|entry
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
comment|/**      * If a database is set, the preview will attempt to resolve strings in the      * previewed entry using that database.      */
DECL|field|database
specifier|private
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|layout
specifier|private
name|Layout
name|layout
decl_stmt|;
DECL|field|layoutFile
specifier|private
name|String
name|layoutFile
decl_stmt|;
DECL|field|previewPane
specifier|private
specifier|final
name|JEditorPane
name|previewPane
decl_stmt|;
DECL|field|scrollPane
specifier|private
specifier|final
name|JScrollPane
name|scrollPane
decl_stmt|;
DECL|field|pdfPreviewPanel
specifier|private
specifier|final
name|PdfPreviewPanel
name|pdfPreviewPanel
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
comment|/**      * @param database      *            (may be null) Optionally used to resolve strings.      * @param entry      *            (may be null) If given this entry is shown otherwise you have      *            to call setEntry to make something visible.      * @param panel      *            (may be null) If not given no toolbar is shown on the right      *            hand side.      * @param metaData      *            (must be given) Used for resolving pdf directories for links.      * @param layoutFile      *            (must be given) Used for layout      */
DECL|method|PreviewPanel (BibtexDatabase database, BibtexEntry entry, BasePanel panel, MetaData metaData, String layoutFile)
specifier|public
name|PreviewPanel
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|layoutFile
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|entry
argument_list|,
name|panel
argument_list|,
name|metaData
argument_list|,
name|layoutFile
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param database      *            (may be null) Optionally used to resolve strings.      * @param entry      *            (may be null) If given this entry is shown otherwise you have      *            to call setEntry to make something visible.      * @param panel      *            (may be null) If not given no toolbar is shown on the right      *            hand side.      * @param metaData      *            (must be given) Used for resolving pdf directories for links.      * @param layoutFile      *            (must be given) Used for layout      * @param withPDFPreview if true, a PDF preview is included in the PreviewPanel      */
DECL|method|PreviewPanel (BibtexDatabase database, BibtexEntry entry, BasePanel panel, MetaData metaData, String layoutFile, boolean withPDFPreview)
specifier|public
name|PreviewPanel
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|layoutFile
parameter_list|,
name|boolean
name|withPDFPreview
parameter_list|)
block|{
name|this
argument_list|(
name|panel
argument_list|,
name|metaData
argument_list|,
name|layoutFile
argument_list|,
name|withPDFPreview
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|setEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param panel      *            (may be null) If not given no toolbar is shown on the right      *            hand side.      * @param metaData      *            (must be given) Used for resolving pdf directories for links.      * @param layoutFile      *            (must be given) Used for layout      */
DECL|method|PreviewPanel (BasePanel panel, MetaData metaData, String layoutFile)
specifier|public
name|PreviewPanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|layoutFile
parameter_list|)
block|{
name|this
argument_list|(
name|panel
argument_list|,
name|metaData
argument_list|,
name|layoutFile
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      *       * @param panel      *            (may be null) If not given no toolbar is shown on the right      *            hand side.      * @param metaData      *            (must be given) Used for resolving pdf directories for links.      * @param layoutFile      *            (must be given) Used for layout      * @param withPDFPreview if true, a PDF preview is included in the PreviewPanel.       * The user can override this setting by setting the config setting JabRefPreferences.PDF_PREVIEW to false.      */
DECL|method|PreviewPanel (BasePanel panel, MetaData metaData, String layoutFile, boolean withPDFPreview)
specifier|private
name|PreviewPanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|layoutFile
parameter_list|,
name|boolean
name|withPDFPreview
parameter_list|)
block|{
name|super
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|withPDFPreview
operator|=
name|withPDFPreview
operator|&&
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PDF_PREVIEW
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|layoutFile
operator|=
name|layoutFile
expr_stmt|;
name|this
operator|.
name|previewPane
operator|=
name|createPreviewPane
argument_list|()
expr_stmt|;
if|if
condition|(
name|withPDFPreview
condition|)
block|{
name|this
operator|.
name|pdfPreviewPanel
operator|=
operator|new
name|PdfPreviewPanel
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|pdfPreviewPanel
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
block|{
comment|// dropped files handler only created for main window
comment|// not for Windows as like the search results window
name|this
operator|.
name|previewPane
operator|.
name|setTransferHandler
argument_list|(
operator|new
name|PreviewPanelTransferHandler
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|this
argument_list|,
name|this
operator|.
name|previewPane
operator|.
name|getTransferHandler
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Set up scroll pane for preview pane
name|scrollPane
operator|=
operator|new
name|JScrollPane
argument_list|(
name|previewPane
argument_list|,
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
name|scrollPane
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
comment|/*          * If we have been given a panel and the preference option          * previewPrintButton is set, show the tool bar          */
if|if
condition|(
name|panel
operator|!=
literal|null
operator|&&
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_PRINT_BUTTON
argument_list|)
condition|)
block|{
name|add
argument_list|(
name|createToolBar
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|LINE_START
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|withPDFPreview
condition|)
block|{
name|JSplitPane
name|splitPane
init|=
operator|new
name|JSplitPane
argument_list|(
name|JSplitPane
operator|.
name|HORIZONTAL_SPLIT
argument_list|,
name|scrollPane
argument_list|,
name|pdfPreviewPanel
argument_list|)
decl_stmt|;
name|splitPane
operator|.
name|setOneTouchExpandable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// int oneThird = panel.getWidth()/3;
name|int
name|oneThird
init|=
literal|400
decl_stmt|;
comment|// arbitrarily set as panel.getWidth() always
comment|// returns 0 at this point
name|splitPane
operator|.
name|setDividerLocation
argument_list|(
name|oneThird
operator|*
literal|2
argument_list|)
expr_stmt|;
comment|// Provide minimum sizes for the two components in the split pane
comment|//			Dimension minimumSize = new Dimension(oneThird * 2, 50);
comment|//			scrollPane.setMinimumSize(minimumSize);
comment|//			minimumSize = new Dimension(oneThird, 50);
comment|//			pdfScrollPane.setMinimumSize(minimumSize);
name|add
argument_list|(
name|splitPane
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|add
argument_list|(
name|scrollPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|PrintAction
class|class
name|PrintAction
extends|extends
name|AbstractAction
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
DECL|method|PrintAction ()
specifier|public
name|PrintAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Print Preview"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"psSmall"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Print Preview"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|//DocumentPrinter printerService;
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent arg0)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|arg0
parameter_list|)
block|{
comment|// Background this, as it takes a while.
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|PrintRequestAttributeSet
name|pras
init|=
operator|new
name|HashPrintRequestAttributeSet
argument_list|()
decl_stmt|;
name|pras
operator|.
name|add
argument_list|(
operator|new
name|JobName
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|print
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|,
name|pras
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|PrinterException
name|e
parameter_list|)
block|{
comment|// Inform the user... we don't know what to do.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PreviewPanel
operator|.
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not print preview"
argument_list|)
operator|+
literal|".\n"
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Printing Entry Preview"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
DECL|field|printAction
specifier|private
name|Action
name|printAction
decl_stmt|;
DECL|method|getPrintAction ()
specifier|private
name|Action
name|getPrintAction
parameter_list|()
block|{
if|if
condition|(
name|printAction
operator|==
literal|null
condition|)
block|{
name|printAction
operator|=
operator|new
name|PrintAction
argument_list|()
expr_stmt|;
block|}
return|return
name|printAction
return|;
block|}
DECL|class|CloseAction
class|class
name|CloseAction
extends|extends
name|AbstractAction
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
DECL|method|CloseAction ()
specifier|public
name|CloseAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close window"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"close"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close window"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|panel
operator|.
name|hideBottomComponent
argument_list|()
expr_stmt|;
block|}
block|}
DECL|field|closeAction
specifier|private
name|Action
name|closeAction
decl_stmt|;
DECL|field|wordsToHighlight
specifier|private
name|ArrayList
argument_list|<
name|String
argument_list|>
name|wordsToHighlight
init|=
literal|null
decl_stmt|;
DECL|method|getCloseAction ()
specifier|private
name|Action
name|getCloseAction
parameter_list|()
block|{
if|if
condition|(
name|closeAction
operator|==
literal|null
condition|)
block|{
name|closeAction
operator|=
operator|new
name|CloseAction
argument_list|()
expr_stmt|;
block|}
return|return
name|closeAction
return|;
block|}
DECL|method|createPopupMenu ()
specifier|private
name|JPopupMenu
name|createPopupMenu
parameter_list|()
block|{
name|JPopupMenu
name|menu
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|getPrintAction
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|panel
operator|!=
literal|null
condition|)
block|{
name|menu
operator|.
name|add
argument_list|(
name|panel
operator|.
name|frame
operator|.
name|switchPreview
argument_list|)
expr_stmt|;
block|}
return|return
name|menu
return|;
block|}
DECL|method|createToolBar ()
specifier|private
name|JToolBar
name|createToolBar
parameter_list|()
block|{
name|JToolBar
name|tlb
init|=
operator|new
name|JToolBar
argument_list|(
name|SwingConstants
operator|.
name|VERTICAL
argument_list|)
decl_stmt|;
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|Action
name|printAction
init|=
name|getPrintAction
argument_list|()
decl_stmt|;
name|Action
name|closeAction
init|=
name|getCloseAction
argument_list|()
decl_stmt|;
name|tlb
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
comment|// The toolbar carries all the key bindings that are valid for the whole
comment|// window.
name|ActionMap
name|am
init|=
name|tlb
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|tlb
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close entry preview"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|im
operator|.
name|put
argument_list|(
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Print entry preview"
argument_list|)
argument_list|,
literal|"print"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"print"
argument_list|,
name|printAction
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// Add actions (and thus buttons)
name|tlb
operator|.
name|add
argument_list|(
name|closeAction
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|printAction
argument_list|)
expr_stmt|;
name|Component
index|[]
name|comps
init|=
name|tlb
operator|.
name|getComponents
argument_list|()
decl_stmt|;
for|for
control|(
name|Component
name|comp
range|:
name|comps
control|)
block|{
operator|(
operator|(
name|JComponent
operator|)
name|comp
operator|)
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
return|return
name|tlb
return|;
block|}
DECL|method|createPreviewPane ()
specifier|private
name|JEditorPane
name|createPreviewPane
parameter_list|()
block|{
name|JEditorPane
name|previewPane
init|=
operator|new
name|JEditorPane
argument_list|()
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Override
specifier|public
name|Dimension
name|getPreferredScrollableViewportSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
block|}
decl_stmt|;
name|previewPane
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|)
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|setComponentPopupMenu
argument_list|(
name|createPopupMenu
argument_list|()
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|setDragEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// this has an effect only, if no custom transfer handler is registered. We keep the statement if the transfer handler is removed.
name|previewPane
operator|.
name|setContentType
argument_list|(
literal|"text/html"
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|addHyperlinkListener
argument_list|(
operator|new
name|HyperlinkListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|hyperlinkUpdate
parameter_list|(
name|HyperlinkEvent
name|hyperlinkEvent
parameter_list|)
block|{
if|if
condition|(
name|hyperlinkEvent
operator|.
name|getEventType
argument_list|()
operator|==
name|HyperlinkEvent
operator|.
name|EventType
operator|.
name|ACTIVATED
condition|)
block|{
try|try
block|{
name|String
name|address
init|=
name|hyperlinkEvent
operator|.
name|getURL
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Util
operator|.
name|openExternalViewer
argument_list|(
name|PreviewPanel
operator|.
name|this
operator|.
name|metaData
argument_list|,
name|address
argument_list|,
literal|"url"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|previewPane
return|;
block|}
DECL|method|setDatabase (BibtexDatabase db)
specifier|public
name|void
name|setDatabase
parameter_list|(
name|BibtexDatabase
name|db
parameter_list|)
block|{
name|database
operator|=
name|db
expr_stmt|;
block|}
DECL|method|setMetaData (MetaData metaData)
specifier|public
name|void
name|setMetaData
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
block|}
DECL|method|readLayout (String layoutFormat)
specifier|public
name|void
name|readLayout
parameter_list|(
name|String
name|layoutFormat
parameter_list|)
throws|throws
name|Exception
block|{
name|layoutFile
operator|=
name|layoutFormat
expr_stmt|;
name|readLayout
argument_list|()
expr_stmt|;
block|}
DECL|method|readLayout ()
specifier|private
name|void
name|readLayout
parameter_list|()
throws|throws
name|Exception
block|{
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|layoutFile
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|layout
operator|=
operator|new
name|LayoutHelper
argument_list|(
name|sr
argument_list|)
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
expr_stmt|;
block|}
DECL|method|setLayout (Layout layout)
specifier|public
name|void
name|setLayout
parameter_list|(
name|Layout
name|layout
parameter_list|)
block|{
name|this
operator|.
name|layout
operator|=
name|layout
expr_stmt|;
block|}
DECL|method|setEntry (BibtexEntry newEntry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibtexEntry
name|newEntry
parameter_list|)
block|{
if|if
condition|(
name|newEntry
operator|!=
name|entry
condition|)
block|{
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|removePropertyChangeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
name|newEntry
operator|.
name|addPropertyChangeListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
name|entry
operator|=
name|newEntry
expr_stmt|;
try|try
block|{
name|readLayout
argument_list|()
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|ex
parameter_list|)
block|{
throw|throw
name|ex
throw|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getEntry ()
specifier|public
name|BibtexEntry
name|getEntry
parameter_list|()
block|{
return|return
name|this
operator|.
name|entry
return|;
block|}
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|ExportFormats
operator|.
name|entryNumber
operator|=
literal|1
expr_stmt|;
comment|// Set entry number in case that is included in the preview layout.
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|wordsToHighlight
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|previewPane
operator|.
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|revalidate
argument_list|()
expr_stmt|;
comment|// Scroll to top:
specifier|final
name|JScrollBar
name|bar
init|=
name|scrollPane
operator|.
name|getVerticalScrollBar
argument_list|()
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|bar
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// update pdf preview
if|if
condition|(
name|pdfPreviewPanel
operator|!=
literal|null
condition|)
block|{
name|pdfPreviewPanel
operator|.
name|updatePanel
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|hasEntry ()
specifier|public
name|boolean
name|hasEntry
parameter_list|()
block|{
return|return
name|entry
operator|!=
literal|null
return|;
block|}
comment|/**      * The PreviewPanel has registered itself as an event listener with the      * currently displayed BibtexEntry. If the entry changes, an event is      * received here, and we can update the preview immediately.      */
annotation|@
name|Override
DECL|method|vetoableChange (PropertyChangeEvent evt)
specifier|public
name|void
name|vetoableChange
parameter_list|(
name|PropertyChangeEvent
name|evt
parameter_list|)
throws|throws
name|PropertyVetoException
block|{
comment|// TODO updating here is not really necessary isn't it?
comment|// Only if we are visible.
name|update
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|searchText (ArrayList<String> words)
specifier|public
name|void
name|searchText
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
parameter_list|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|HIGH_LIGHT_WORDS
argument_list|)
condition|)
block|{
name|this
operator|.
name|wordsToHighlight
operator|=
name|words
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|this
operator|.
name|wordsToHighlight
operator|!=
literal|null
condition|)
block|{
comment|// setting of JabRefPreferences.HIGH_LIGHT_WORDS seems to have changed.
comment|// clear all highlights and remember the clearing (by wordsToHighlight = null)
name|this
operator|.
name|wordsToHighlight
operator|=
literal|null
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

