begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
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
name|util
operator|.
name|List
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
name|BasePanel
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
name|UndoableKeyChange
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
name|BackgroundTask
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
name|entry
operator|.
name|BibEntry
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
DECL|class|GenerateBibtexKeyAction
specifier|public
class|class
name|GenerateBibtexKeyAction
implements|implements
name|BaseAction
block|{
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|basePanel
specifier|private
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|entries
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|isCanceled
specifier|private
name|boolean
name|isCanceled
decl_stmt|;
DECL|method|GenerateBibtexKeyAction (BasePanel basePanel, DialogService dialogService)
specifier|public
name|GenerateBibtexKeyAction
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
block|}
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|entries
operator|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autogenerate BibTeX keys"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"First select the entries you want keys to be generated for."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|basePanel
operator|.
name|output
argument_list|(
name|formatOutputMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generating BibTeX key for"
argument_list|)
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|confirmOverwriteKeys (DialogService dialogService)
specifier|public
specifier|static
name|boolean
name|confirmOverwriteKeys
parameter_list|(
name|DialogService
name|dialogService
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
name|WARN_BEFORE_OVERWRITING_KEY
argument_list|)
condition|)
block|{
return|return
name|dialogService
operator|.
name|showConfirmationDialogWithOptOutAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite keys"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"One or more keys will be overwritten. Continue?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite keys"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Disable this confirmation dialog"
argument_list|)
argument_list|,
name|optOut
lambda|->
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|WARN_BEFORE_OVERWRITING_KEY
argument_list|,
operator|!
name|optOut
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// Always overwrite keys by default
return|return
literal|true
return|;
block|}
block|}
DECL|method|generateKeys ()
specifier|private
name|void
name|generateKeys
parameter_list|()
block|{
comment|// We don't want to generate keys for entries which already have one thus remove the entries
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
name|AVOID_OVERWRITING_KEY
argument_list|)
condition|)
block|{
name|entries
operator|.
name|removeIf
argument_list|(
name|BibEntry
operator|::
name|hasCiteKey
argument_list|)
expr_stmt|;
comment|// if we're going to override some cite keys warn the user about it
block|}
elseif|else
if|if
condition|(
name|entries
operator|.
name|parallelStream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|BibEntry
operator|::
name|hasCiteKey
argument_list|)
condition|)
block|{
name|boolean
name|overwriteKeys
init|=
name|confirmOverwriteKeys
argument_list|(
name|dialogService
argument_list|)
decl_stmt|;
comment|// The user doesn't want to override cite keys
if|if
condition|(
operator|!
name|overwriteKeys
condition|)
block|{
name|isCanceled
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
comment|// generate the new cite keys for each entry
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
literal|"Autogenerate BibTeX keys"
argument_list|)
argument_list|)
decl_stmt|;
name|BibtexKeyGenerator
name|keyGenerator
init|=
operator|new
name|BibtexKeyGenerator
argument_list|(
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBibtexKeyPatternPreferences
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|keyGenerator
operator|.
name|generateAndSetKey
argument_list|(
name|entry
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldChange
lambda|->
name|compound
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableKeyChange
argument_list|(
name|fieldChange
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|compound
operator|.
name|end
argument_list|()
expr_stmt|;
comment|// register the undo event only if new cite keys were generated
if|if
condition|(
name|compound
operator|.
name|hasEdits
argument_list|()
condition|)
block|{
name|basePanel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|compound
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|isCanceled
condition|)
block|{
return|return;
block|}
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|output
argument_list|(
name|formatOutputMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generated BibTeX key for"
argument_list|)
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|formatOutputMessage (String start, int count)
specifier|private
name|String
name|formatOutputMessage
parameter_list|(
name|String
name|start
parameter_list|,
name|int
name|count
parameter_list|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s %d %s."
argument_list|,
name|start
argument_list|,
name|count
argument_list|,
operator|(
name|count
operator|>
literal|1
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"entries"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"entry"
argument_list|)
operator|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
name|init
argument_list|()
expr_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|generateKeys
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
