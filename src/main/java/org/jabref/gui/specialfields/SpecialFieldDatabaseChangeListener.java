begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.specialfields
package|package
name|org
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

begin_enum
DECL|enum|SpecialFieldDatabaseChangeListener
specifier|public
enum|enum
name|SpecialFieldDatabaseChangeListener
block|{
DECL|enumConstant|INSTANCE
name|INSTANCE
block|;
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
comment|// TODO
block|}
comment|/*     if (!Globals.prefs.isKeywordSyncEnabled()) {         return;     }      final BibEntry entry = event.getBibEntry();     // NamedCompount code similar to SpecialFieldUpdateListener     NamedCompound nc = new NamedCompound(Localization.lang("Synchronized special fields based on keywords"));     List<FieldChange> changes = SpecialFieldsUtils.syncSpecialFieldsFromKeywords(entry, Globals.prefs.getKeywordDelimiter());     for (FieldChange change: changes) {         nc.addEdit(new UndoableFieldChange(change));     }     // Don't insert the compound into the undoManager,     // it would be added before the component which undoes the insertion of the entry and creates heavy problems     // (which prohibits the undo the deleting multiple entries)     */
block|}
end_enum

end_unit

