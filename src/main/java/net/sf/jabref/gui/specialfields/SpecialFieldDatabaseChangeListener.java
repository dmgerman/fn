begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|specialfields
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|logic
operator|.
name|specialfields
operator|.
name|SpecialFieldsUtils
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
name|event
operator|.
name|EntryAddedEvent
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_class
DECL|class|SpecialFieldDatabaseChangeListener
specifier|public
class|class
name|SpecialFieldDatabaseChangeListener
block|{
DECL|field|INSTANCE
specifier|private
specifier|static
name|SpecialFieldDatabaseChangeListener
name|INSTANCE
decl_stmt|;
DECL|method|getInstance ()
specifier|public
specifier|static
name|SpecialFieldDatabaseChangeListener
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|SpecialFieldDatabaseChangeListener
operator|.
name|INSTANCE
operator|==
literal|null
condition|)
block|{
name|SpecialFieldDatabaseChangeListener
operator|.
name|INSTANCE
operator|=
operator|new
name|SpecialFieldDatabaseChangeListener
argument_list|()
expr_stmt|;
block|}
return|return
name|SpecialFieldDatabaseChangeListener
operator|.
name|INSTANCE
return|;
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryAddedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryAddedEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|isKeywordSyncEnabled
argument_list|()
condition|)
block|{
specifier|final
name|BibEntry
name|entry
init|=
name|event
operator|.
name|getBibEntry
argument_list|()
decl_stmt|;
comment|// NamedCompount code similar to SpecialFieldUpdateListener
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Synchronized special fields based on keywords"
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|changes
init|=
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|FieldChange
name|change
range|:
name|changes
control|)
block|{
name|nc
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|change
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Don't insert the compound into the undoManager,
comment|// it would be added before the component which undoes the insertion of the entry and creates heavy problems
comment|// (which prohibits the undo the deleting multiple entries)
block|}
block|}
block|}
end_class

end_unit

