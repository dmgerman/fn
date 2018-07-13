begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.bibtexkeypattern
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|bibtexkeypattern
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|actions
operator|.
name|BaseAction
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
name|FieldChange
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
name|BibDatabase
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

begin_comment
comment|/**  * Function for resolving duplicate BibTeX keys.  */
end_comment

begin_class
DECL|class|SearchFixDuplicateLabels
specifier|public
class|class
name|SearchFixDuplicateLabels
implements|implements
name|BaseAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|method|SearchFixDuplicateLabels (BasePanel panel)
specifier|public
name|SearchFixDuplicateLabels
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
throws|throws
name|Exception
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resolving duplicate BibTeX keys..."
argument_list|)
argument_list|)
expr_stmt|;
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|findDuplicates
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|dupes
lambda|->
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|resolveDuplicates
argument_list|(
name|dupes
argument_list|)
argument_list|)
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
DECL|method|resolveDuplicates (Map<String, List<BibEntry>> dupes)
specifier|private
name|void
name|resolveDuplicates
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|dupes
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|toGenerateFor
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|dupeEntry
range|:
name|dupes
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|ResolveDuplicateLabelDialog
name|rdld
init|=
operator|new
name|ResolveDuplicateLabelDialog
argument_list|(
name|panel
argument_list|,
name|dupeEntry
operator|.
name|getKey
argument_list|()
argument_list|,
name|dupeEntry
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|rdld
operator|.
name|show
argument_list|()
expr_stmt|;
if|if
condition|(
name|rdld
operator|.
name|isOkPressed
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|JCheckBox
argument_list|>
name|cbs
init|=
name|rdld
operator|.
name|getCheckBoxes
argument_list|()
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
name|cbs
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|cbs
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// The checkbox for entry i has been selected, so we should generate a new key for it:
name|toGenerateFor
operator|.
name|add
argument_list|(
name|dupeEntry
operator|.
name|getValue
argument_list|()
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|rdld
operator|.
name|isCancelPressed
argument_list|()
condition|)
block|{
break|break;
block|}
block|}
comment|// Do the actual generation:
if|if
condition|(
operator|!
name|toGenerateFor
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Resolve duplicate BibTeX keys"
argument_list|)
argument_list|)
decl_stmt|;
name|BibtexKeyGenerator
name|keyGenerator
init|=
operator|new
name|BibtexKeyGenerator
argument_list|(
name|panel
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
name|toGenerateFor
control|)
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|keyGenerator
operator|.
name|generateAndSetKey
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|change
operator|.
name|ifPresent
argument_list|(
name|fieldChange
lambda|->
name|ce
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
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Finished resolving duplicate BibTeX keys. %0 entries modified."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|toGenerateFor
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|findDuplicates ()
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|findDuplicates
parameter_list|()
block|{
comment|// Find all multiple occurrences of BibTeX keys.
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|dupes
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|BibEntry
argument_list|>
name|foundKeys
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|BibDatabase
name|db
init|=
name|panel
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|filter
argument_list|(
name|key
lambda|->
operator|!
name|key
operator|.
name|isEmpty
argument_list|()
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|key
lambda|->
block|{
comment|// See whether this entry's key is already known:
lambda|if (foundKeys.containsKey(key
argument_list|)
block|)
block|{
comment|// Already known, so we have found a dupe. See if it was already found as a dupe:
if|if
condition|(
name|dupes
operator|.
name|containsKey
argument_list|(
name|key
argument_list|)
condition|)
block|{
comment|// Already in the dupe map. Add this entry as well:
name|dupes
operator|.
name|get
argument_list|(
name|key
argument_list|)
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Construct a list of entries for this key:
name|List
argument_list|<
name|BibEntry
argument_list|>
name|al
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Add both the first one we found, and the one we found just now:
name|al
operator|.
name|add
argument_list|(
name|foundKeys
operator|.
name|get
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
name|al
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Add the list to the dupe map:
name|dupes
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|al
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Not already known. Add key and entry to map:
name|foundKeys
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|)
class|;
end_class

begin_expr_stmt
unit|}         return
name|dupes
expr_stmt|;
end_expr_stmt

unit|} }
end_unit

