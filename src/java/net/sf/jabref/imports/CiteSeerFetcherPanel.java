begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|BibtexEntry
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
name|BibtexEntryType
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
name|ImportInspectionDialog
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
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  *  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|CiteSeerFetcherPanel
specifier|public
class|class
name|CiteSeerFetcherPanel
extends|extends
name|SidePaneComponent
implements|implements
name|ActionListener
block|{
DECL|field|idList
name|String
name|idList
decl_stmt|;
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
DECL|field|citeSeerFetcher
name|CiteSeerFetcher
name|citeSeerFetcher
decl_stmt|;
DECL|field|authorDialog
name|AuthorDialog
name|authorDialog
decl_stmt|;
DECL|field|jFrame
name|JFrame
name|jFrame
decl_stmt|;
comment|// invisible dialog holder
DECL|field|go
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
DECL|field|helpBut
name|helpBut
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|help
name|HelpAction
name|help
decl_stmt|;
DECL|field|ths
name|CiteSeerFetcherPanel
name|ths
init|=
name|this
decl_stmt|;
DECL|method|CiteSeerFetcherPanel (SidePaneManager p0, final CiteSeerFetcher fetcher)
specifier|public
name|CiteSeerFetcherPanel
parameter_list|(
name|SidePaneManager
name|p0
parameter_list|,
specifier|final
name|CiteSeerFetcher
name|fetcher
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
literal|"citeseer"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetch CiteSeer"
argument_list|)
argument_list|)
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
name|GUIGlobals
operator|.
name|citeSeerHelp
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|this
operator|.
name|citeSeerFetcher
operator|=
name|fetcher
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
comment|//tf.setMinimumSize(new Dimension(1,1));
comment|//add(hd, BorderLayout.NORTH);
comment|//ok.setToolTipText(Globals.lang("Fetch Medline"));
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
comment|//gbl.setConstraints(header, con);
comment|//add(header);
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
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
comment|//    pan.setLayout(gbl);
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
name|citeSeerFetcher
operator|.
name|activateImportFetcher
argument_list|()
condition|)
block|{
operator|(
operator|new
name|Thread
argument_list|()
block|{
name|BibtexEntry
name|entry
decl_stmt|;
class|class
name|Update
implements|implements
name|ImportInspectionDialog
operator|.
name|CallBack
block|{
comment|// This method is called by the dialog when the user has selected the
comment|// wanted entries, and clicked Ok. The callback object can update status
comment|// line etc.
specifier|public
name|void
name|done
parameter_list|(
name|int
name|entriesImported
parameter_list|)
block|{
if|if
condition|(
name|entriesImported
operator|>
literal|0
condition|)
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Completed import from CiteSeer."
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries imported."
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|cancelled
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 import cancelled."
argument_list|,
literal|"CiteSeer"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{                     }
comment|/*public void run() {                         citeSeerFetcher.endImportCiteSeerProgress();                         if (addedEntries != null)                             panel.markBaseChanged();                         panel.refreshTable(); // Select the entries that were added:                         if (addedEntries.size()> 0) {                             BibtexEntry[] toSelect = new BibtexEntry[addedEntries.size()];                             toSelect = (BibtexEntry[]) addedEntries.toArray(toSelect);                              panel.selectEntries(toSelect, 0);                             if (toSelect.length == 1)                                 panel.showEntry(toSelect[0]); //else //    panel.updateViewToSelected();                         }                         panel.output(Globals.lang("Completed Import Fields from CiteSeer."));                     } */
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// Open the inspection dialog... we will fill it as the entries come in:
name|ImportInspectionDialog
name|diag
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useImportInspectionDialog"
argument_list|)
condition|)
block|{
name|diag
operator|=
operator|new
name|ImportInspectionDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|panel
argument_list|,
name|BibtexFields
operator|.
name|DEFAULT_INSPECTION_FIELDS
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetch CiteSeer"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|diag
operator|.
name|addCallBack
argument_list|(
operator|new
name|Update
argument_list|()
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|diag
argument_list|,
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setProgress
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
name|NamedCompound
name|undoEdit
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"CiteSeer import entries"
argument_list|)
argument_list|)
decl_stmt|,
comment|// Use a dummy UndoEdit to avoid storing the information on
comment|// every field change, since we are importing new entries:
name|dummyCompound
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
name|BooleanAssign
name|overwriteAll
init|=
operator|new
name|BooleanAssign
argument_list|(
literal|true
argument_list|)
decl_stmt|,
name|overwriteNone
init|=
operator|new
name|BooleanAssign
argument_list|(
literal|false
argument_list|)
decl_stmt|,
name|newValue
init|=
operator|new
name|BooleanAssign
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|Hashtable
name|rejectedEntries
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|String
name|text
init|=
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|","
argument_list|,
literal|";"
argument_list|)
decl_stmt|;
name|String
index|[]
name|ids
init|=
name|text
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|BibtexEntry
index|[]
name|entries
init|=
operator|new
name|BibtexEntry
index|[
name|ids
operator|.
name|length
index|]
decl_stmt|;
name|citeSeerFetcher
operator|.
name|activateImportFetcher
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|// Create the entry:
name|entries
index|[
name|i
index|]
operator|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|,
name|BibtexEntryType
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set its citeseerurl field:
name|entries
index|[
name|i
index|]
operator|.
name|setField
argument_list|(
literal|"citeseerurl"
argument_list|,
name|ids
index|[
name|i
index|]
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
comment|// Try to import based on the id:
name|boolean
name|newValues
init|=
name|citeSeerFetcher
operator|.
name|importCiteSeerEntry
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|,
name|dummyCompound
argument_list|,
name|overwriteAll
argument_list|,
name|overwriteNone
argument_list|,
name|newValue
argument_list|,
name|rejectedEntries
argument_list|)
decl_stmt|;
comment|// What we do with the entry depends on whether we are using the inspection window:
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
name|diag
operator|.
name|addEntry
argument_list|(
name|entries
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setProgress
argument_list|(
name|i
operator|+
literal|1
argument_list|,
name|entries
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Do nothing. We will add the entries afterwards (?)
block|}
block|}
name|citeSeerFetcher
operator|.
name|deactivateImportFetcher
argument_list|()
expr_stmt|;
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
comment|// Signal that we are done. The ImportInspectionDialog will add the entries for us.
name|diag
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|addBibEntries
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|entries
argument_list|)
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
expr_stmt|;
operator|(
operator|new
name|Update
argument_list|()
operator|)
operator|.
name|done
argument_list|(
name|entries
operator|.
name|length
argument_list|)
expr_stmt|;
block|}
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"A CiteSeer import operation is currently in progress."
argument_list|)
operator|+
literal|"  "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please wait until it has finished."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"CiteSeer Import Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

