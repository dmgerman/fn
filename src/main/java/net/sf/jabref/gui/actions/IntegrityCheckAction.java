begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
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
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTable
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
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
name|gui
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
name|logic
operator|.
name|integrity
operator|.
name|IntegrityCheck
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
name|integrity
operator|.
name|IntegrityMessage
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

begin_class
DECL|class|IntegrityCheckAction
specifier|public
class|class
name|IntegrityCheckAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|ELLIPSES
specifier|private
specifier|static
specifier|final
name|String
name|ELLIPSES
init|=
literal|"..."
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|IntegrityCheckAction (JabRefFrame frame)
specifier|public
name|IntegrityCheckAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Check integrity"
argument_list|)
operator|+
name|ELLIPSES
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
name|IntegrityCheck
name|check
init|=
operator|new
name|IntegrityCheck
argument_list|(
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
name|check
operator|.
name|checkBibtexDatabase
argument_list|()
decl_stmt|;
if|if
condition|(
name|messages
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No problems found."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// prepare data model
name|Object
index|[]
index|[]
name|model
init|=
operator|new
name|Object
index|[
name|messages
operator|.
name|size
argument_list|()
index|]
index|[
literal|3
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|IntegrityMessage
name|message
range|:
name|messages
control|)
block|{
name|model
index|[
name|i
index|]
index|[
literal|0
index|]
operator|=
name|message
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
name|model
index|[
name|i
index|]
index|[
literal|1
index|]
operator|=
name|message
operator|.
name|getFieldName
argument_list|()
expr_stmt|;
name|model
index|[
name|i
index|]
index|[
literal|2
index|]
operator|=
name|message
operator|.
name|getMessage
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
comment|// construct view
name|JTable
name|table
init|=
operator|new
name|JTable
argument_list|(
name|model
argument_list|,
operator|new
name|Object
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"BibTeX key"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Message"
argument_list|)
block|}
argument_list|)
decl_stmt|;
name|table
operator|.
name|setAutoCreateRowSorter
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|table
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|table
operator|.
name|setDefaultEditor
argument_list|(
name|Object
operator|.
name|class
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|ListSelectionModel
name|selectionModel
init|=
name|table
operator|.
name|getSelectionModel
argument_list|()
decl_stmt|;
name|selectionModel
operator|.
name|addListSelectionListener
argument_list|(
name|event
lambda|->
block|{
if|if
condition|(
operator|!
name|event
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
name|String
name|citeKey
init|=
operator|(
name|String
operator|)
name|model
index|[
name|table
operator|.
name|convertRowIndexToModel
argument_list|(
name|table
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
index|]
index|[
literal|0
index|]
decl_stmt|;
name|String
name|fieldName
init|=
operator|(
name|String
operator|)
name|model
index|[
name|table
operator|.
name|convertRowIndexToModel
argument_list|(
name|table
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
index|]
index|[
literal|1
index|]
decl_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|editEntryByKeyAndFocusField
argument_list|(
name|citeKey
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|table
operator|.
name|setRowHeight
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|MENU_FONT_SIZE
argument_list|)
operator|+
literal|2
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|80
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|250
argument_list|)
expr_stmt|;
name|table
operator|.
name|setAutoResizeMode
argument_list|(
name|JTable
operator|.
name|AUTO_RESIZE_LAST_COLUMN
argument_list|)
expr_stmt|;
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
decl_stmt|;
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 problem(s) found"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|messages
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|JDialog
name|dialog
init|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|title
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|add
argument_list|(
name|scrollPane
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setSize
argument_list|(
literal|600
argument_list|,
literal|500
argument_list|)
expr_stmt|;
comment|// show view
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
