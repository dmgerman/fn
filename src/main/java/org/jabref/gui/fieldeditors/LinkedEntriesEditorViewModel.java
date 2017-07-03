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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ListProperty
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
name|SimpleListProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|util
operator|.
name|StringConverter
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
name|BindingsHelper
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
name|BibDatabaseContext
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
name|EntryLinkList
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
name|ParsedEntryLink
import|;
end_import

begin_class
DECL|class|LinkedEntriesEditorViewModel
specifier|public
class|class
name|LinkedEntriesEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|linkedEntries
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|ParsedEntryLink
argument_list|>
name|linkedEntries
decl_stmt|;
DECL|method|LinkedEntriesEditorViewModel (BibDatabaseContext databaseContext)
specifier|public
name|LinkedEntriesEditorViewModel
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|linkedEntries
operator|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
expr_stmt|;
name|BindingsHelper
operator|.
name|bindContentBidirectional
argument_list|(
name|linkedEntries
argument_list|,
name|text
argument_list|,
name|EntryLinkList
operator|::
name|serialize
argument_list|,
name|newText
lambda|->
name|EntryLinkList
operator|.
name|parse
argument_list|(
name|newText
argument_list|,
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|linkedEntriesProperty ()
specifier|public
name|ListProperty
argument_list|<
name|ParsedEntryLink
argument_list|>
name|linkedEntriesProperty
parameter_list|()
block|{
return|return
name|linkedEntries
return|;
block|}
DECL|method|getStringConverter ()
specifier|public
name|StringConverter
argument_list|<
name|ParsedEntryLink
argument_list|>
name|getStringConverter
parameter_list|()
block|{
return|return
operator|new
name|StringConverter
argument_list|<
name|ParsedEntryLink
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
name|ParsedEntryLink
name|linkedEntry
parameter_list|)
block|{
if|if
condition|(
name|linkedEntry
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
return|return
name|linkedEntry
operator|.
name|getKey
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|ParsedEntryLink
name|fromString
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryByKey
argument_list|(
name|key
argument_list|)
operator|.
name|map
argument_list|(
name|ParsedEntryLink
operator|::
operator|new
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
block|}
return|;
block|}
DECL|method|jumpToEntry (ParsedEntryLink parsedEntryLink)
specifier|public
name|void
name|jumpToEntry
parameter_list|(
name|ParsedEntryLink
name|parsedEntryLink
parameter_list|)
block|{
comment|// TODO: Implement jump to entry
comment|// TODO: Add toolitp for tag: Localization.lang("Jump to entry")
comment|// This feature was removed while converting the linked entries editor to JavaFX
comment|// Right now there is no nice way to re-implement it as we have no good interface to control the focus of the main table
comment|// (except directly using the JabRefFrame class as below)
comment|//parsedEntryLink.getLinkedEntry().ifPresent(
comment|//        e -> frame.getCurrentBasePanel().highlightEntry(e)
comment|//);
block|}
block|}
end_class

end_unit
