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
name|ArrayList
import|;
end_import

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
name|Comparator
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
name|EntryBasedFetcher
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
name|IdBasedFetcher
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
name|ImportFormatPreferences
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
name|WebFetcher
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
name|fetcher
operator|.
name|ArXiv
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
name|fetcher
operator|.
name|AstrophysicsDataSystem
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
name|fetcher
operator|.
name|DBLPFetcher
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
name|fetcher
operator|.
name|DiVA
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
name|fetcher
operator|.
name|DoiFetcher
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
name|fetcher
operator|.
name|GoogleScholar
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
name|fetcher
operator|.
name|GvkFetcher
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
name|fetcher
operator|.
name|IsbnFetcher
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
name|fetcher
operator|.
name|MathSciNet
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
name|fetcher
operator|.
name|MedlineFetcher
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
name|fetcher
operator|.
name|TitleFetcher
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
name|fetcher
operator|.
name|zbMATH
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
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|CiteSeerXFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|DBLPFetcher
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|IEEEXploreFetcher
argument_list|(
name|abbreviationLoader
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|INSPIREFetcher
argument_list|()
argument_list|)
expr_stmt|;
comment|// entryFetchers.add(new OAI2Fetcher()); - new arXiv fetcher in place, see below
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|ACMPortalFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|DOAJFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SpringerFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|ArXiv
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|GvkFetcher
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|MedlineFetcher
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|MathSciNet
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|zbMATH
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entryFetchers
operator|.
name|add
argument_list|(
operator|new
name|SearchBasedEntryFetcher
argument_list|(
operator|new
name|GoogleScholar
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getIdFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|IdBasedFetcher
argument_list|>
name|getIdFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|IdBasedFetcher
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|ArXiv
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|IsbnFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DiVA
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DoiFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MedlineFetcher
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|TitleFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MathSciNet
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|sort
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|WebFetcher
operator|::
name|getName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|list
return|;
block|}
DECL|method|getEntryBasedFetchers (ImportFormatPreferences importFormatPreferences)
specifier|public
specifier|static
name|List
argument_list|<
name|EntryBasedFetcher
argument_list|>
name|getEntryBasedFetchers
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|EntryBasedFetcher
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|AstrophysicsDataSystem
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|DoiFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|add
argument_list|(
operator|new
name|MathSciNet
argument_list|(
name|importFormatPreferences
argument_list|)
argument_list|)
expr_stmt|;
name|list
operator|.
name|sort
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|WebFetcher
operator|::
name|getName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|list
return|;
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

