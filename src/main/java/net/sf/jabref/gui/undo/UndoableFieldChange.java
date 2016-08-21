begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.undo
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|FieldChange
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

begin_comment
comment|/**  * This class represents a change in any field value. The relevant  * information is the BibEntry, the field name, the old and the  * new value. Old/new values can be null.  */
end_comment

begin_class
DECL|class|UndoableFieldChange
specifier|public
class|class
name|UndoableFieldChange
extends|extends
name|AbstractUndoableJabRefEdit
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
name|UndoableFieldChange
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|field|oldValue
specifier|private
specifier|final
name|String
name|oldValue
decl_stmt|;
DECL|field|newValue
specifier|private
specifier|final
name|String
name|newValue
decl_stmt|;
DECL|method|UndoableFieldChange (BibEntry entry, String field, String oldValue, String newValue)
specifier|public
name|UndoableFieldChange
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|oldValue
parameter_list|,
name|String
name|newValue
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|field
operator|=
name|field
expr_stmt|;
name|this
operator|.
name|oldValue
operator|=
name|oldValue
expr_stmt|;
name|this
operator|.
name|newValue
operator|=
name|newValue
expr_stmt|;
block|}
DECL|method|UndoableFieldChange (FieldChange change)
specifier|public
name|UndoableFieldChange
parameter_list|(
name|FieldChange
name|change
parameter_list|)
block|{
name|this
argument_list|(
name|change
operator|.
name|getEntry
argument_list|()
argument_list|,
name|change
operator|.
name|getField
argument_list|()
argument_list|,
name|change
operator|.
name|getOldValue
argument_list|()
argument_list|,
name|change
operator|.
name|getNewValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getPresentationName ()
specifier|public
name|String
name|getPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"change field %0 of entry %1 from %2 to %3"
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
name|field
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
name|entry
operator|.
name|getCiteKey
argument_list|()
argument_list|,
literal|""
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
name|oldValue
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"undefined"
argument_list|)
argument_list|)
argument_list|,
name|StringUtil
operator|.
name|boldHTML
argument_list|(
name|newValue
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"undefined"
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
comment|// Revert the change.
try|try
block|{
if|if
condition|(
name|oldValue
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|oldValue
argument_list|)
expr_stmt|;
block|}
comment|// this is the only exception explicitly thrown here
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot perform undo"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
comment|// Redo the change.
try|try
block|{
if|if
condition|(
name|newValue
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot perform redo"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

