begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|logic
operator|.
name|importer
operator|.
name|WebFetchers
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

begin_class
DECL|class|EntryFetchers
specifier|public
class|class
name|EntryFetchers
block|{
DECL|field|entryFetchers
specifier|private
specifier|final
name|List
argument_list|<
name|EntryFetcher
argument_list|>
name|entryFetchers
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|EntryFetchers (JournalAbbreviationLoader abbreviationLoader)
specifier|public
name|EntryFetchers
parameter_list|(
name|JournalAbbreviationLoader
name|abbreviationLoader
parameter_list|)
block|{
name|WebFetchers
operator|.
name|getSearchBasedFetchers
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|SearchBasedEntryFetcher
operator|::
operator|new
argument_list|)
operator|.
name|forEach
argument_list|(
name|entryFetchers
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
DECL|method|getEntryFetchers ()
specifier|public
name|List
argument_list|<
name|EntryFetcher
argument_list|>
name|getEntryFetchers
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|this
operator|.
name|entryFetchers
argument_list|)
return|;
block|}
block|}
end_class

end_unit

