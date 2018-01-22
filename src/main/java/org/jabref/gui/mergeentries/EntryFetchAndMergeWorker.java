begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.mergeentries
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|mergeentries
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
name|java
operator|.
name|util
operator|.
name|Objects
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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingWorker
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
name|FetcherException
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
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|EntryFetchAndMergeWorker
specifier|public
class|class
name|EntryFetchAndMergeWorker
extends|extends
name|SwingWorker
argument_list|<
name|Optional
argument_list|<
name|BibEntry
argument_list|>
argument_list|,
name|Void
argument_list|>
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|EntryFetchAndMergeWorker
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|fetcher
specifier|private
specifier|final
name|EntryBasedFetcher
name|fetcher
decl_stmt|;
DECL|method|EntryFetchAndMergeWorker (BasePanel panel, BibEntry entry, EntryBasedFetcher fetcher)
specifier|public
name|EntryFetchAndMergeWorker
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|EntryBasedFetcher
name|fetcher
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|this
operator|.
name|fetcher
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fetcher
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|doInBackground ()
specifier|protected
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
name|entry
argument_list|)
decl_stmt|;
return|return
name|fetchedEntries
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|FetcherException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Info cannot be found"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|done ()
specifier|protected
name|void
name|done
parameter_list|()
block|{
if|if
condition|(
name|isCancelled
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|fetchedEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|MergeFetchedEntryDialog
name|dialog
init|=
operator|new
name|MergeFetchedEntryDialog
argument_list|(
name|panel
argument_list|,
name|entry
argument_list|,
name|fetchedEntry
operator|.
name|get
argument_list|()
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not find any bibliographic information."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while fetching Entry"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

