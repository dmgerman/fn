begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRefExecutorService
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
name|JabRefPreferences
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
name|MetaData
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
name|exporter
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
name|exporter
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
name|exporter
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
name|gui
operator|.
name|desktop
operator|.
name|JabRefDesktop
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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
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
name|logic
operator|.
name|search
operator|.
name|SearchQueryHighlightListener
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_comment
comment|/**  * Displays an BibEntry using the given layout format.  */
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
name|SearchQueryHighlightListener
implements|,
name|EntryContainer
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|PreviewPanel
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The bibtex entry currently shown      */
DECL|field|entry
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entry
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * If a database is set, the preview will attempt to resolve strings in the      * previewed entry using that database.      */
DECL|field|database
specifier|private
name|Optional
argument_list|<
name|BibDatabase
argument_list|>
name|database
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
DECL|field|layout
specifier|private
name|Optional
argument_list|<
name|Layout
argument_list|>
name|layout
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * must not be null, must always be set during constructor, but can change over time      */
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
comment|/**      * must not be null, must always be set during constructor, but can change over time      */
DECL|field|layoutFile
specifier|private
name|String
name|layoutFile
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|Optional
argument_list|<
name|BasePanel
argument_list|>
name|panel
decl_stmt|;
DECL|field|previewPane
specifier|private
name|JEditorPane
name|previewPane
decl_stmt|;
DECL|field|scrollPane
specifier|private
specifier|final
name|JScrollPane
name|scrollPane
decl_stmt|;
DECL|field|printAction
specifier|private
specifier|final
name|PrintAction
name|printAction
decl_stmt|;
DECL|field|closeAction
specifier|private
specifier|final
name|CloseAction
name|closeAction
decl_stmt|;
DECL|field|copyPreviewAction
specifier|private
specifier|final
name|CopyPreviewAction
name|copyPreviewAction
decl_stmt|;
DECL|field|highlightPattern
specifier|private
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
comment|/**      * @param database      *            (may be null) Optionally used to resolve strings.      * @param entry      *            (may be null) If given this entry is shown otherwise you have      *            to call setEntry to make something visible.      * @param panel      *            (may be null) If not given no toolbar is shown on the right      *            hand side.      * @param metaData      *            (must be given) Used for resolving pdf directories for links.      * @param layoutFile      *            (must be given) Used for layout      */
DECL|method|PreviewPanel (BibDatabase database, BibEntry entry, BasePanel panel, MetaData metaData, String layoutFile)
specifier|public
name|PreviewPanel
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|BibEntry
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
name|panel
argument_list|,
name|metaData
argument_list|,
name|layoutFile
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|setEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
comment|/**      *      * @param panel      *            (may be null) If not given no toolbar is shown on the right      *            hand side.      * @param metaData      *            (must be given) Used for resolving pdf directories for links.      * @param layoutFile      *            (must be given) Used for layout      */
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
name|super
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|this
operator|.
name|layoutFile
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|layoutFile
argument_list|)
expr_stmt|;
name|updateLayout
argument_list|()
expr_stmt|;
name|this
operator|.
name|closeAction
operator|=
operator|new
name|CloseAction
argument_list|()
expr_stmt|;
name|this
operator|.
name|printAction
operator|=
operator|new
name|PrintAction
argument_list|()
expr_stmt|;
name|this
operator|.
name|copyPreviewAction
operator|=
operator|new
name|CopyPreviewAction
argument_list|()
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|createPreviewPane
argument_list|()
expr_stmt|;
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
name|this
operator|.
name|panel
operator|.
name|isPresent
argument_list|()
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
name|this
operator|.
name|printAction
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|this
operator|.
name|copyPreviewAction
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|.
name|ifPresent
argument_list|(
name|p
lambda|->
name|menu
operator|.
name|add
argument_list|(
name|p
operator|.
name|frame
argument_list|()
operator|.
name|switchPreview
argument_list|)
argument_list|)
expr_stmt|;
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
name|toolBar
init|=
operator|new
name|OSXCompatibleToolbar
argument_list|(
name|SwingConstants
operator|.
name|VERTICAL
argument_list|)
decl_stmt|;
name|toolBar
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
name|actionMap
init|=
name|toolBar
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|inputMap
init|=
name|toolBar
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|inputMap
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|this
operator|.
name|closeAction
argument_list|)
expr_stmt|;
name|inputMap
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|COPY_PREVIEW
argument_list|)
argument_list|,
literal|"copy"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"copy"
argument_list|,
name|this
operator|.
name|copyPreviewAction
argument_list|)
expr_stmt|;
name|inputMap
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|PRINT_ENTRY_PREVIEW
argument_list|)
argument_list|,
literal|"print"
argument_list|)
expr_stmt|;
name|actionMap
operator|.
name|put
argument_list|(
literal|"print"
argument_list|,
name|this
operator|.
name|printAction
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// Add actions (and thus buttons)
name|toolBar
operator|.
name|add
argument_list|(
name|this
operator|.
name|closeAction
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|this
operator|.
name|copyPreviewAction
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|this
operator|.
name|printAction
argument_list|)
expr_stmt|;
name|Component
index|[]
name|comps
init|=
name|toolBar
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
name|toolBar
return|;
block|}
DECL|method|createPreviewPane ()
specifier|private
name|void
name|createPreviewPane
parameter_list|()
block|{
name|previewPane
operator|=
operator|new
name|JEditorPane
argument_list|()
block|{
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
expr_stmt|;
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
name|JabRefDesktop
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not open external viewer"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
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
DECL|method|updateLayout (String layoutFormat)
specifier|public
name|void
name|updateLayout
parameter_list|(
name|String
name|layoutFormat
parameter_list|)
block|{
name|layoutFile
operator|=
name|layoutFormat
expr_stmt|;
name|updateLayout
argument_list|()
expr_stmt|;
block|}
DECL|method|updateLayout ()
specifier|private
name|void
name|updateLayout
parameter_list|()
block|{
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|layoutFile
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
try|try
block|{
name|layout
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|LayoutHelper
argument_list|(
name|sr
argument_list|)
operator|.
name|getLayoutFromText
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|layout
operator|=
name|Optional
operator|.
name|empty
argument_list|()
expr_stmt|;
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"no layout could be set"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
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
name|Optional
operator|.
name|of
argument_list|(
name|layout
argument_list|)
expr_stmt|;
block|}
DECL|method|setEntry (BibEntry newEntry)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibEntry
name|newEntry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|.
name|isPresent
argument_list|()
operator|&&
operator|(
name|entry
operator|.
name|get
argument_list|()
operator|!=
name|newEntry
operator|)
condition|)
block|{
name|entry
operator|.
name|ifPresent
argument_list|(
name|e
lambda|->
name|e
operator|.
name|removePropertyChangeListener
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
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
name|Optional
operator|.
name|ofNullable
argument_list|(
name|newEntry
argument_list|)
expr_stmt|;
name|updateLayout
argument_list|()
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getEntry ()
specifier|public
name|BibEntry
name|getEntry
parameter_list|()
block|{
return|return
name|this
operator|.
name|entry
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
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
name|entry
operator|.
name|ifPresent
argument_list|(
name|entry
lambda|->
name|layout
operator|.
name|ifPresent
argument_list|(
name|layout
lambda|->
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
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|highlightPattern
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|newValue
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
name|previewPane
operator|.
name|setText
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|revalidate
argument_list|()
expr_stmt|;
comment|// Scroll to top:
name|scrollToTop
argument_list|()
expr_stmt|;
block|}
DECL|method|scrollToTop ()
specifier|private
name|void
name|scrollToTop
parameter_list|()
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|scrollPane
operator|.
name|getVerticalScrollBar
argument_list|()
operator|.
name|setValue
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * The PreviewPanel has registered itself as an event listener with the      * currently displayed BibEntry. If the entry changes, an event is      * received here, and we can update the preview immediately.      */
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
DECL|method|highlightPattern (Optional<Pattern> highlightPattern)
specifier|public
name|void
name|highlightPattern
parameter_list|(
name|Optional
argument_list|<
name|Pattern
argument_list|>
name|highlightPattern
parameter_list|)
block|{
name|this
operator|.
name|highlightPattern
operator|=
name|highlightPattern
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
DECL|class|PrintAction
class|class
name|PrintAction
extends|extends
name|AbstractAction
block|{
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
literal|"Print entry preview"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PRINTED
operator|.
name|getIcon
argument_list|()
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
literal|"Print entry preview"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|PRINT_ENTRY_PREVIEW
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|map
argument_list|(
name|BibEntry
operator|::
name|getCiteKey
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"NO ENTRY"
argument_list|)
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
literal|"Print entry preview"
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
DECL|class|CloseAction
class|class
name|CloseAction
extends|extends
name|AbstractAction
block|{
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
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|CLOSE
operator|.
name|getSmallIcon
argument_list|()
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
name|ifPresent
argument_list|(
name|BasePanel
operator|::
name|hideBottomComponent
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|CopyPreviewAction
class|class
name|CopyPreviewAction
extends|extends
name|AbstractAction
block|{
DECL|method|CopyPreviewAction ()
specifier|public
name|CopyPreviewAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy preview"
argument_list|)
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|COPY
operator|.
name|getSmallIcon
argument_list|()
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
literal|"Copy preview"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|COPY_PREVIEW
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
name|previewPane
operator|.
name|selectAll
argument_list|()
expr_stmt|;
name|previewPane
operator|.
name|copy
argument_list|()
expr_stmt|;
name|previewPane
operator|.
name|select
argument_list|(
literal|0
argument_list|,
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

