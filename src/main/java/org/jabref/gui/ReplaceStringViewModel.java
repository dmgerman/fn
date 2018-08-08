begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|BooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleBooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleStringProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|StringProperty
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_class
DECL|class|ReplaceStringViewModel
specifier|public
class|class
name|ReplaceStringViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|allFieldReplace
specifier|private
name|boolean
name|allFieldReplace
decl_stmt|;
DECL|field|findString
specifier|private
name|String
name|findString
decl_stmt|;
DECL|field|replaceString
specifier|private
name|String
name|replaceString
decl_stmt|;
DECL|field|fieldStrings
specifier|private
name|String
index|[]
name|fieldStrings
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|findStringProperty
specifier|private
name|StringProperty
name|findStringProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|replaceStringProperty
specifier|private
name|StringProperty
name|replaceStringProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|fieldStringProperty
specifier|private
name|StringProperty
name|fieldStringProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|allFieldReplaceProperty
specifier|private
name|BooleanProperty
name|allFieldReplaceProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|selectOnlyProperty
specifier|private
name|BooleanProperty
name|selectOnlyProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|method|ReplaceStringViewModel (BasePanel basePanel)
specifier|public
name|ReplaceStringViewModel
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|basePanel
argument_list|)
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|basePanel
expr_stmt|;
block|}
DECL|method|replace ()
specifier|public
name|int
name|replace
parameter_list|()
block|{
name|findString
operator|=
name|findStringProperty
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|replaceString
operator|=
name|replaceStringProperty
operator|.
name|getValue
argument_list|()
expr_stmt|;
name|fieldStrings
operator|=
name|fieldStringProperty
operator|.
name|getValue
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
name|boolean
name|selOnly
init|=
name|selectOnlyProperty
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|allFieldReplace
operator|=
name|allFieldReplaceProperty
operator|.
name|getValue
argument_list|()
expr_stmt|;
specifier|final
name|NamedCompound
name|compound
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace string"
argument_list|)
argument_list|)
decl_stmt|;
name|int
name|counter
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|selOnly
condition|)
block|{
for|for
control|(
name|BibEntry
name|bibEntry
range|:
name|this
operator|.
name|panel
operator|.
name|getSelectedEntries
argument_list|()
control|)
name|counter
operator|+=
name|replaceItem
argument_list|(
name|bibEntry
argument_list|,
name|compound
argument_list|)
expr_stmt|;
block|}
else|else
block|{
for|for
control|(
name|BibEntry
name|bibEntry
range|:
name|this
operator|.
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
name|counter
operator|+=
name|replaceItem
argument_list|(
name|bibEntry
argument_list|,
name|compound
argument_list|)
expr_stmt|;
block|}
return|return
name|counter
return|;
block|}
comment|/**      * Does the actual operation on a Bibtex entry based on the      * settings specified in this same dialog. Returns the number of      * occurences replaced.      * Copied and Adapted from org.jabref.gui.ReplaceStringDialog.java      */
DECL|method|replaceItem (BibEntry entry, NamedCompound compound)
specifier|private
name|int
name|replaceItem
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|NamedCompound
name|compound
parameter_list|)
block|{
name|int
name|counter
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|this
operator|.
name|allFieldReplace
condition|)
block|{
for|for
control|(
name|String
name|fieldName
range|:
name|entry
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|counter
operator|+=
name|replaceField
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|,
name|compound
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|String
name|espFieldName
range|:
name|fieldStrings
control|)
block|{
name|counter
operator|+=
name|replaceField
argument_list|(
name|entry
argument_list|,
name|espFieldName
argument_list|,
name|compound
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|counter
return|;
block|}
DECL|method|replaceField (BibEntry entry, String fieldName, NamedCompound compound)
specifier|private
name|int
name|replaceField
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|NamedCompound
name|compound
parameter_list|)
block|{
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
return|return
literal|0
return|;
block|}
name|String
name|txt
init|=
name|entry
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|ind
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|int
name|counter
init|=
literal|0
decl_stmt|;
name|int
name|len1
init|=
name|this
operator|.
name|findString
operator|.
name|length
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|ind
operator|=
name|txt
operator|.
name|indexOf
argument_list|(
name|this
operator|.
name|findString
argument_list|,
name|piv
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
name|counter
operator|++
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
name|txt
argument_list|,
name|piv
argument_list|,
name|ind
argument_list|)
expr_stmt|;
comment|// Text leading up to s1
name|stringBuilder
operator|.
name|append
argument_list|(
name|this
operator|.
name|replaceString
argument_list|)
expr_stmt|;
comment|// Insert s2
name|piv
operator|=
name|ind
operator|+
name|len1
expr_stmt|;
block|}
name|stringBuilder
operator|.
name|append
argument_list|(
name|txt
operator|.
name|substring
argument_list|(
name|piv
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|newStr
init|=
name|stringBuilder
operator|.
name|toString
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|newStr
argument_list|)
expr_stmt|;
name|compound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|fieldName
argument_list|,
name|txt
argument_list|,
name|newStr
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|counter
return|;
block|}
DECL|method|allFieldReplaceProperty ()
specifier|public
name|BooleanProperty
name|allFieldReplaceProperty
parameter_list|()
block|{
return|return
name|allFieldReplaceProperty
return|;
block|}
DECL|method|selectOnlyProperty ()
specifier|public
name|BooleanProperty
name|selectOnlyProperty
parameter_list|()
block|{
return|return
name|selectOnlyProperty
return|;
block|}
DECL|method|fieldStringProperty ()
specifier|public
name|StringProperty
name|fieldStringProperty
parameter_list|()
block|{
return|return
name|fieldStringProperty
return|;
block|}
DECL|method|findStringProperty ()
specifier|public
name|StringProperty
name|findStringProperty
parameter_list|()
block|{
return|return
name|findStringProperty
return|;
block|}
DECL|method|replaceStringProperty ()
specifier|public
name|StringProperty
name|replaceStringProperty
parameter_list|()
block|{
return|return
name|replaceStringProperty
return|;
block|}
block|}
end_class

end_unit

