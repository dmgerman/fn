begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
package|;
end_package

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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|gui
operator|.
name|JabRefFrame
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
name|logic
operator|.
name|search
operator|.
name|SearchQuery
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
DECL|class|GlobalSearchWorker
class|class
name|GlobalSearchWorker
extends|extends
name|SwingWorker
argument_list|<
name|Map
argument_list|<
name|BasePanel
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
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
name|GlobalSearchWorker
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|searchQuery
specifier|private
specifier|final
name|SearchQuery
name|searchQuery
decl_stmt|;
DECL|field|dialog
specifier|private
specifier|final
name|SearchResultFrame
name|dialog
decl_stmt|;
DECL|method|GlobalSearchWorker (JabRefFrame frame, SearchQuery query)
specifier|public
name|GlobalSearchWorker
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|SearchQuery
name|query
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchQuery
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|query
argument_list|)
expr_stmt|;
name|dialog
operator|=
operator|new
name|SearchResultFrame
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search results in all libraries for %0"
argument_list|,
name|this
operator|.
name|searchQuery
operator|.
name|localize
argument_list|()
argument_list|)
argument_list|,
name|searchQuery
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getGlobalSearchBar
argument_list|()
operator|.
name|setSearchResultFrame
argument_list|(
name|dialog
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|doInBackground ()
specifier|protected
name|Map
argument_list|<
name|BasePanel
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
name|Map
argument_list|<
name|BasePanel
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|matches
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BasePanel
name|basePanel
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
name|matches
operator|.
name|put
argument_list|(
name|basePanel
argument_list|,
name|basePanel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|parallelStream
argument_list|()
operator|.
name|filter
argument_list|(
name|searchQuery
operator|::
name|isMatch
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|matches
return|;
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
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|BasePanel
argument_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
argument_list|>
name|match
range|:
name|get
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|dialog
operator|.
name|addEntries
argument_list|(
name|match
operator|.
name|getValue
argument_list|()
argument_list|,
name|match
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|dialog
operator|.
name|selectFirstEntry
argument_list|()
expr_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
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
literal|"something went wrong during the search"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

