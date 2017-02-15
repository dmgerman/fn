begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.cli
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|cli
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|CountDownLatch
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
name|ExecutorService
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
name|Executors
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
name|atomic
operator|.
name|AtomicInteger
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
name|logic
operator|.
name|importer
operator|.
name|ParserResult
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
name|fetcher
operator|.
name|CrossRef
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
name|fileformat
operator|.
name|BibtexParser
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
name|util
operator|.
name|DOI
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
name|database
operator|.
name|BibDatabase
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * Useful for checking out new algorithm improvements and thresholds. Not used inside the JabRef code itself.  */
end_comment

begin_class
DECL|class|CrossrefFetcherEvaluator
specifier|public
class|class
name|CrossrefFetcherEvaluator
block|{
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
throws|throws
name|IOException
throws|,
name|InterruptedException
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
try|try
init|(
name|FileReader
name|reader
init|=
operator|new
name|FileReader
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
init|)
block|{
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|parser
operator|.
name|parse
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|db
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|AtomicInteger
name|dois
init|=
operator|new
name|AtomicInteger
argument_list|()
decl_stmt|;
name|AtomicInteger
name|doiFound
init|=
operator|new
name|AtomicInteger
argument_list|()
decl_stmt|;
name|AtomicInteger
name|doiNew
init|=
operator|new
name|AtomicInteger
argument_list|()
decl_stmt|;
name|AtomicInteger
name|doiIdentical
init|=
operator|new
name|AtomicInteger
argument_list|()
decl_stmt|;
name|int
name|total
init|=
name|entries
operator|.
name|size
argument_list|()
decl_stmt|;
name|CountDownLatch
name|countDownLatch
init|=
operator|new
name|CountDownLatch
argument_list|(
name|total
argument_list|)
decl_stmt|;
name|ExecutorService
name|executorService
init|=
name|Executors
operator|.
name|newFixedThreadPool
argument_list|(
literal|5
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
name|executorService
operator|.
name|execute
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|origDOI
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|DOI
argument_list|)
operator|.
name|flatMap
argument_list|(
name|DOI
operator|::
name|build
argument_list|)
decl_stmt|;
if|if
condition|(
name|origDOI
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|dois
operator|.
name|incrementAndGet
argument_list|()
expr_stmt|;
name|Optional
argument_list|<
name|DOI
argument_list|>
name|crossrefDOI
init|=
name|CrossRef
operator|.
name|findDOI
argument_list|(
name|entry
argument_list|)
decl_stmt|;
if|if
condition|(
name|crossrefDOI
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|doiFound
operator|.
name|incrementAndGet
argument_list|()
expr_stmt|;
if|if
condition|(
name|origDOI
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|crossrefDOI
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
argument_list|)
condition|)
block|{
name|doiIdentical
operator|.
name|incrementAndGet
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"DOI not identical for : "
operator|+
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"DOI not found for: "
operator|+
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|crossrefDOI
init|=
name|CrossRef
operator|.
name|findDOI
argument_list|(
name|entry
argument_list|)
decl_stmt|;
if|if
condition|(
name|crossrefDOI
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"New DOI found for: "
operator|+
name|entry
argument_list|)
expr_stmt|;
name|doiNew
operator|.
name|incrementAndGet
argument_list|()
expr_stmt|;
block|}
block|}
name|countDownLatch
operator|.
name|countDown
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|countDownLatch
operator|.
name|await
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"---------------------------------"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Total DB size: "
operator|+
name|total
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Total DOIs: "
operator|+
name|dois
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"DOIs found: "
operator|+
name|doiFound
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"DOIs identical: "
operator|+
name|doiIdentical
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"New DOIs found: "
operator|+
name|doiNew
argument_list|)
expr_stmt|;
name|executorService
operator|.
name|shutdown
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
