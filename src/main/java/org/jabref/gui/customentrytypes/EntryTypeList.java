begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customentrytypes
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customentrytypes
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|Locale
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
name|event
operator|.
name|ListSelectionEvent
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
name|ListSelectionListener
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DialogService
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtexkeypattern
operator|.
name|BibtexKeyGenerator
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|EntryTypes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseMode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|CustomEntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|EntryType
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * This class extends FieldSetComponent to provide some required functionality for the  * list of entry types in EntryCustomizationDialog.  * @author alver  */
end_comment

begin_class
DECL|class|EntryTypeList
specifier|public
class|class
name|EntryTypeList
extends|extends
name|FieldSetComponent
implements|implements
name|ListSelectionListener
block|{
DECL|field|def
specifier|private
specifier|final
name|JButton
name|def
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|mode
specifier|private
specifier|final
name|BibDatabaseMode
name|mode
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
comment|/** Creates a new instance of EntryTypeList      * @param frame */
DECL|method|EntryTypeList (DialogService dialogService, List<String> fields, BibDatabaseMode mode)
specifier|public
name|EntryTypeList
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry types"
argument_list|)
argument_list|,
name|fields
argument_list|,
name|fields
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|VERTICAL
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|def
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|def
argument_list|)
expr_stmt|;
name|list
operator|.
name|addListSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|def
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|addField (String str)
specifier|protected
name|void
name|addField
parameter_list|(
name|String
name|str
parameter_list|)
block|{
name|String
name|s
init|=
name|str
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|forceLowerCase
condition|)
block|{
name|s
operator|=
name|s
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|s
argument_list|)
operator|||
name|listModel
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
block|{
return|return;
block|}
name|String
name|testString
init|=
name|BibtexKeyGenerator
operator|.
name|cleanKey
argument_list|(
name|s
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|testString
operator|.
name|equals
argument_list|(
name|s
argument_list|)
operator|||
operator|(
name|s
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
comment|// Report error and exit.
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry type names are not allowed to contain white space or the following "
operator|+
literal|"characters"
argument_list|)
operator|+
literal|": # { } ~ , ^&"
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
literal|"comment"
operator|.
name|equalsIgnoreCase
argument_list|(
name|s
argument_list|)
condition|)
block|{
comment|// Report error and exit.
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The name 'comment' cannot be used as an entry type name."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|addFieldUncritically
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|removeSelected ()
specifier|protected
name|void
name|removeSelected
parameter_list|()
block|{
comment|//super.removeSelected();
name|int
index|[]
name|selected
init|=
name|list
operator|.
name|getSelectedIndices
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|changesMade
operator|=
literal|true
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selected
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|typeName
init|=
name|listModel
operator|.
name|get
argument_list|(
name|selected
index|[
name|selected
operator|.
name|length
operator|-
literal|1
operator|-
name|i
index|]
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|type
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|typeName
argument_list|,
name|this
operator|.
name|mode
argument_list|)
decl_stmt|;
comment|// If it is a custom entry type, we can remove it. If type == null, it means
comment|// the user must have added it and not yet applied it, so we can remove it
comment|// in this case as well. If it is a standard type it cannot be removed.
if|if
condition|(
name|type
operator|.
name|isPresent
argument_list|()
operator|&&
operator|(
name|type
operator|.
name|get
argument_list|()
operator|instanceof
name|CustomEntryType
operator|)
condition|)
block|{
name|listModel
operator|.
name|removeElementAt
argument_list|(
name|selected
index|[
name|selected
operator|.
name|length
operator|-
literal|1
operator|-
name|i
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// This shouldn't happen, since the Remove button should be disabled.
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove entry type"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This entry type cannot be removed."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
comment|// Do nothing
block|}
DECL|method|enable (String typeName, boolean isChanged)
specifier|public
name|void
name|enable
parameter_list|(
name|String
name|typeName
parameter_list|,
name|boolean
name|isChanged
parameter_list|)
block|{
comment|//String s = (String)list.getSelectedValue();
if|if
condition|(
name|EntryTypes
operator|.
name|getStandardType
argument_list|(
name|typeName
argument_list|,
name|mode
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|EntryType
argument_list|>
name|entryType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|typeName
argument_list|,
name|mode
argument_list|)
decl_stmt|;
if|if
condition|(
name|isChanged
operator|||
operator|(
name|entryType
operator|.
name|isPresent
argument_list|()
operator|&&
operator|(
name|entryType
operator|.
name|get
argument_list|()
operator|instanceof
name|CustomEntryType
operator|)
operator|)
condition|)
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|remove
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addDefaultActionListener (ActionListener l)
specifier|public
name|void
name|addDefaultActionListener
parameter_list|(
name|ActionListener
name|l
parameter_list|)
block|{
name|def
operator|.
name|addActionListener
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setEnabled (boolean en)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|en
parameter_list|)
block|{
name|super
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
name|def
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

