begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexFields
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
name|FocusRequester
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
name|GUIGlobals
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
name|HelpAction
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
name|JabRefFrame
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
name|SidePaneComponent
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
name|SidePaneManager
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
name|Util
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
name|FetcherPreviewDialog
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
name|ImportInspectionDialog
import|;
end_import

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  *  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|GeneralFetcher
specifier|public
class|class
name|GeneralFetcher
extends|extends
name|SidePaneComponent
implements|implements
name|ActionListener
block|{
DECL|field|tf
name|JTextField
name|tf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|go
DECL|field|helpBut
name|JButton
name|go
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetch"
argument_list|)
argument_list|)
decl_stmt|,
name|helpBut
init|=
operator|new
name|JButton
argument_list|(
DECL|field|reset
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|,
name|reset
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Reset"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|fetcherChoice
name|JComboBox
name|fetcherChoice
decl_stmt|;
DECL|field|optionsCards
name|CardLayout
name|optionsCards
init|=
operator|new
name|CardLayout
argument_list|()
decl_stmt|;
DECL|field|optionsPanel
name|JPanel
name|optionsPanel
init|=
operator|new
name|JPanel
argument_list|(
name|optionsCards
argument_list|)
decl_stmt|;
DECL|field|optPanel
name|JPanel
name|optPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|help
name|HelpAction
name|help
decl_stmt|;
DECL|field|sidePaneManager
name|SidePaneManager
name|sidePaneManager
decl_stmt|;
DECL|field|action
name|Action
name|action
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|activeFetcher
name|EntryFetcher
name|activeFetcher
decl_stmt|;
DECL|field|fetcherArray
name|EntryFetcher
index|[]
name|fetcherArray
decl_stmt|;
DECL|method|GeneralFetcher (SidePaneManager p0, JabRefFrame frame, final List<EntryFetcher> fetchers)
specifier|public
name|GeneralFetcher
parameter_list|(
name|SidePaneManager
name|p0
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
specifier|final
name|List
argument_list|<
name|EntryFetcher
argument_list|>
name|fetchers
parameter_list|)
block|{
name|super
argument_list|(
name|p0
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"www"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Web search"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|sidePaneManager
operator|=
name|p0
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|fetcherArray
operator|=
name|fetchers
operator|.
name|toArray
argument_list|(
operator|new
name|EntryFetcher
index|[
name|fetchers
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
comment|//JLabel[] choices = new JLabel[fetchers.size()];
name|String
index|[]
name|choices
init|=
operator|new
name|String
index|[
name|fetchers
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|fetchers
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|choices
index|[
name|i
index|]
operator|=
name|fetchers
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getTitle
argument_list|()
expr_stmt|;
comment|//choices[i] = new JLabel(fetchers.get(i).getTitle(), new ImageIcon(fetchers.get(i).getIcon()),
comment|//        JLabel.HORIZONTAL);
comment|/*if (fetchers.get(i).getOptionsPanel() != null)                 optionsPanel.add(fetchers.get(i).getOptionsPanel(), String.valueOf(i));             else                 optionsPanel.add(new JPanel(), String.valueOf(i));*/
block|}
name|fetcherChoice
operator|=
operator|new
name|JComboBox
argument_list|(
name|choices
argument_list|)
expr_stmt|;
name|int
name|defaultFetcher
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
literal|"selectedFetcherIndex"
argument_list|)
decl_stmt|;
if|if
condition|(
name|defaultFetcher
operator|>=
name|fetcherArray
operator|.
name|length
condition|)
name|defaultFetcher
operator|=
literal|0
expr_stmt|;
name|this
operator|.
name|activeFetcher
operator|=
name|fetcherArray
index|[
name|defaultFetcher
index|]
expr_stmt|;
name|fetcherChoice
operator|.
name|setSelectedIndex
argument_list|(
name|defaultFetcher
argument_list|)
expr_stmt|;
if|if
condition|(
name|this
operator|.
name|activeFetcher
operator|.
name|getOptionsPanel
argument_list|()
operator|!=
literal|null
condition|)
name|optPanel
operator|.
name|add
argument_list|(
name|this
operator|.
name|activeFetcher
operator|.
name|getOptionsPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|setEnabled
argument_list|(
name|activeFetcher
operator|.
name|getHelpPage
argument_list|()
operator|!=
literal|null
argument_list|)
expr_stmt|;
comment|//optionsCards.show(optionsPanel, String.valueOf(defaultFetcher));
comment|/*fetcherChoice.setRenderer(new ListCellRenderer() {             JLabel label = new JLabel();             public Component getListCellRendererComponent(JList jList, Object o, int i, boolean isSelected,                 boolean cellHasFocus) {                 JLabel theLab = (JLabel)o;                 label.setIcon(theLab.getIcon());                 label.setText(theLab.getText());                 if (cellHasFocus) {                     label.setBackground(UIManager.getDefaults().getColor("ComboBox.selectionBackground").darker());                     label.setForeground(UIManager.getDefaults().getColor("ComboBox.foreground"));                 } else {                     label.setBackground(UIManager.getDefaults().getColor("ComboBox.background"));                     label.setForeground(UIManager.getDefaults().getColor("ComboBox.foreground"));                 }                 return label;             }         });*/
name|fetcherChoice
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|activeFetcher
operator|=
name|fetcherArray
index|[
name|fetcherChoice
operator|.
name|getSelectedIndex
argument_list|()
index|]
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
literal|"selectedFetcherIndex"
argument_list|,
name|fetcherChoice
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|activeFetcher
operator|.
name|getHelpPage
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|help
operator|.
name|setHelpFile
argument_list|(
name|activeFetcher
operator|.
name|getHelpPage
argument_list|()
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|helpBut
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|optionsCards
operator|.
name|show
argument_list|(
name|optionsPanel
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|fetcherChoice
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|optPanel
operator|.
name|removeAll
argument_list|()
expr_stmt|;
if|if
condition|(
name|activeFetcher
operator|.
name|getOptionsPanel
argument_list|()
operator|!=
literal|null
condition|)
name|optPanel
operator|.
name|add
argument_list|(
name|activeFetcher
operator|.
name|getOptionsPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|revalidate
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|action
operator|=
operator|new
name|FetcherAction
argument_list|()
expr_stmt|;
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|Globals
operator|.
name|helpDiag
argument_list|,
name|activeFetcher
operator|.
name|getHelpPage
argument_list|()
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|addActionListener
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|helpBut
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
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|1
argument_list|,
name|tf
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setName
argument_list|(
literal|"tf"
argument_list|)
expr_stmt|;
comment|// add action to reset-button. resets tf and requests focus
name|reset
operator|.
name|addActionListener
argument_list|(
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|tf
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|1
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fetcherChoice
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|fetcherChoice
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|tf
argument_list|)
expr_stmt|;
comment|// Go Button
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|go
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|go
argument_list|)
expr_stmt|;
comment|// Reset Button
name|reset
operator|.
name|setName
argument_list|(
literal|"reset"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|reset
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|reset
argument_list|)
expr_stmt|;
comment|// Help Button
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|helpBut
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|helpBut
argument_list|)
expr_stmt|;
if|if
condition|(
name|pan
operator|!=
literal|null
condition|)
block|{
name|gbl
operator|.
name|setConstraints
argument_list|(
name|optPanel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|optPanel
argument_list|)
expr_stmt|;
block|}
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|go
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|tf
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|setHelpResourceOwner (Class c)
specifier|public
name|void
name|setHelpResourceOwner
parameter_list|(
name|Class
name|c
parameter_list|)
block|{
name|help
operator|.
name|setResourceOwner
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|getTextField ()
specifier|public
name|JTextField
name|getTextField
parameter_list|()
block|{
return|return
name|tf
return|;
block|}
DECL|method|getAction ()
specifier|public
name|Action
name|getAction
parameter_list|()
block|{
return|return
name|action
return|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|// We have two categories of fetchers. One category can show previews first and ask the
comment|// user which ones to download:
if|if
condition|(
name|activeFetcher
operator|instanceof
name|PreviewEntryFetcher
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Searching..."
argument_list|)
argument_list|)
expr_stmt|;
name|frame
operator|.
name|setProgressBarIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|.
name|setProgressBarVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|PreviewEntryFetcher
name|pFetcher
init|=
operator|(
name|PreviewEntryFetcher
operator|)
name|activeFetcher
decl_stmt|;
specifier|final
name|FetcherPreviewDialog
name|dialog
init|=
operator|new
name|FetcherPreviewDialog
argument_list|(
name|frame
argument_list|,
name|pFetcher
operator|.
name|getWarningLimit
argument_list|()
argument_list|)
decl_stmt|;
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
specifier|final
name|boolean
name|result
init|=
name|pFetcher
operator|.
name|processQueryGetPreview
argument_list|(
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|dialog
argument_list|,
name|dialog
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|frame
operator|.
name|setProgressBarVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
literal|""
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|result
condition|)
return|return;
name|dialog
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|dialog
operator|.
name|isOkPressed
argument_list|()
condition|)
block|{
specifier|final
name|ImportInspectionDialog
name|d2
init|=
operator|new
name|ImportInspectionDialog
argument_list|(
name|frame
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|,
name|BibtexFields
operator|.
name|DEFAULT_INSPECTION_FIELDS
argument_list|,
name|activeFetcher
operator|.
name|getTitle
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|d2
operator|.
name|addCallBack
argument_list|(
name|activeFetcher
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|d2
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|d2
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|pFetcher
operator|.
name|getEntries
argument_list|(
name|dialog
operator|.
name|getSelection
argument_list|()
argument_list|,
name|d2
argument_list|)
expr_stmt|;
name|d2
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|// The other category downloads the entries first, then asks the user which ones to keep:
else|else
block|{
specifier|final
name|ImportInspectionDialog
name|dialog
init|=
operator|new
name|ImportInspectionDialog
argument_list|(
name|frame
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|,
name|BibtexFields
operator|.
name|DEFAULT_INSPECTION_FIELDS
argument_list|,
name|activeFetcher
operator|.
name|getTitle
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|addCallBack
argument_list|(
name|activeFetcher
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|dialog
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|activeFetcher
operator|.
name|processQuery
argument_list|(
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|dialog
argument_list|,
name|dialog
argument_list|)
condition|)
block|{
name|dialog
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|dialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|FetcherAction
class|class
name|FetcherAction
extends|extends
name|AbstractAction
block|{
DECL|method|FetcherAction ()
specifier|public
name|FetcherAction
parameter_list|()
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Web search"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"www"
argument_list|)
argument_list|)
expr_stmt|;
comment|//if ((activeFetcher.getKeyName() != null)&& (activeFetcher.getKeyName().length()> 0))
name|putValue
argument_list|(
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Fetch Medline"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|sidePaneManager
operator|.
name|hasComponent
argument_list|(
name|GeneralFetcher
operator|.
name|this
operator|.
name|getTitle
argument_list|()
argument_list|)
condition|)
block|{
name|sidePaneManager
operator|.
name|register
argument_list|(
name|GeneralFetcher
operator|.
name|this
operator|.
name|getTitle
argument_list|()
argument_list|,
name|GeneralFetcher
operator|.
name|this
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sidePaneManager
operator|.
name|toggle
argument_list|(
name|GeneralFetcher
operator|.
name|this
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|sidePaneManager
operator|.
name|isComponentVisible
argument_list|(
name|GeneralFetcher
operator|.
name|this
operator|.
name|getTitle
argument_list|()
argument_list|)
condition|)
block|{
operator|new
name|FocusRequester
argument_list|(
name|getTextField
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{
name|super
operator|.
name|componentClosing
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"webSearchVisible"
argument_list|,
name|Boolean
operator|.
name|FALSE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|componentOpening ()
specifier|public
name|void
name|componentOpening
parameter_list|()
block|{
name|super
operator|.
name|componentOpening
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"webSearchVisible"
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

