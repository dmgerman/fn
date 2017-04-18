begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|gui
operator|.
name|util
operator|.
name|TaskExecutor
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|journals
operator|.
name|JournalAbbreviationPreferences
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
name|FieldProperty
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
name|InternalBibtexFields
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

begin_class
DECL|class|FieldEditors
specifier|public
class|class
name|FieldEditors
block|{
DECL|method|getForField (String fieldName, TaskExecutor taskExecutor, DialogService dialogService, JournalAbbreviationLoader journalAbbreviationLoader, JournalAbbreviationPreferences journalAbbreviationPreferences)
specifier|public
specifier|static
name|FieldEditorFX
name|getForField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
parameter_list|,
name|JournalAbbreviationPreferences
name|journalAbbreviationPreferences
parameter_list|)
block|{
specifier|final
name|Set
argument_list|<
name|FieldProperty
argument_list|>
name|fieldExtras
init|=
name|InternalBibtexFields
operator|.
name|getFieldProperties
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
comment|// TODO: Implement this
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|TIME_STAMP_FIELD
argument_list|)
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
operator|||
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|DATE
argument_list|)
condition|)
block|{
comment|// timestamp or a other field with datepicker command
comment|// double click AND datefield => insert the current date (today)
comment|//return FieldExtraComponents.getDateTimeExtraComponent(editor,
comment|//        fieldExtras.contains(FieldProperty.DATE), fieldExtras.contains(FieldProperty.ISO_DATE));
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|EXTERNAL
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getExternalExtraComponent(panel, editor);
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|JOURNAL_NAME
argument_list|)
condition|)
block|{
return|return
operator|new
name|JournalEditor
argument_list|(
name|fieldName
argument_list|,
name|journalAbbreviationLoader
argument_list|,
name|journalAbbreviationPreferences
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|DOI
argument_list|)
operator|||
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|EPRINT
argument_list|)
operator|||
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|ISBN
argument_list|)
condition|)
block|{
return|return
operator|new
name|IdentifierEditor
argument_list|(
name|fieldName
argument_list|,
name|taskExecutor
argument_list|,
name|dialogService
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|OWNER
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getSetOwnerExtraComponent(editor, storeFieldAction);
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|YES_NO
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getYesNoExtraComponent(editor, this);
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|MONTH
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getMonthExtraComponent(editor, this, frame.getCurrentBasePanel().getBibDatabaseContext().getMode());
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|GENDER
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getGenderExtraComponent(editor, this);
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|EDITOR_TYPE
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getEditorTypeExtraComponent(editor, this);
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|PAGINATION
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getPaginationExtraComponent(editor, this);
block|}
elseif|else
if|if
condition|(
name|fieldExtras
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|TYPE
argument_list|)
condition|)
block|{
comment|//return FieldExtraComponents.getTypeExtraComponent(editor, this, "patent".equalsIgnoreCase(entry.getType()));
block|}
comment|// default
return|return
operator|new
name|SimpleEditor
argument_list|(
name|fieldName
argument_list|)
return|;
block|}
block|}
end_class

end_unit

