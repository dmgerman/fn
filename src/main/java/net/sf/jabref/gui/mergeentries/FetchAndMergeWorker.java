begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.mergeentries
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|BasePanel
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
name|importer
operator|.
name|FetcherException
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
name|importer
operator|.
name|IdBasedFetcher
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
name|importer
operator|.
name|WebFetchers
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|FieldName
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

begin_class
DECL|class|FetchAndMergeWorker
specifier|public
class|class
name|FetchAndMergeWorker
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
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|FetchAndMergeWorker
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
DECL|field|field
specifier|private
specifier|final
name|String
name|field
decl_stmt|;
DECL|field|fieldContent
specifier|private
specifier|final
name|Optional
argument_list|<
name|String
argument_list|>
name|fieldContent
decl_stmt|;
DECL|method|FetchAndMergeWorker (BasePanel panel, BibEntry entry, String field)
specifier|public
name|FetchAndMergeWorker
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|String
name|field
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
name|field
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|this
operator|.
name|fieldContent
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|field
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
name|Optional
argument_list|<
name|IdBasedFetcher
argument_list|>
name|fetcher
init|=
name|WebFetchers
operator|.
name|getIdBasedFetcherForField
argument_list|(
name|field
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|fieldContentValue
init|=
name|fieldContent
decl_stmt|;
if|if
condition|(
name|fieldContentValue
operator|.
name|isPresent
argument_list|()
operator|&&
name|fetcher
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|fetcher
operator|.
name|get
argument_list|()
operator|.
name|performSearchById
argument_list|(
name|fieldContentValue
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
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
name|String
name|type
init|=
name|FieldName
operator|.
name|getDisplayName
argument_list|(
name|field
argument_list|)
decl_stmt|;
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
name|type
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
literal|"Cannot get info based on given %0: %1"
argument_list|,
name|type
argument_list|,
name|fieldContent
operator|.
name|get
argument_list|()
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
