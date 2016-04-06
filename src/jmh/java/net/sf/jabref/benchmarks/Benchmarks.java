begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.benchmarks
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|benchmarks
package|;
end_package

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
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Random
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
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
name|exporter
operator|.
name|BibDatabaseWriter
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
name|exporter
operator|.
name|SaveException
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
name|exporter
operator|.
name|SavePreferences
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
name|search
operator|.
name|SearchQuery
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
name|database
operator|.
name|BibDatabaseMode
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
name|BibDatabaseModeDetection
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
name|org
operator|.
name|openjdk
operator|.
name|jmh
operator|.
name|Main
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openjdk
operator|.
name|jmh
operator|.
name|annotations
operator|.
name|*
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openjdk
operator|.
name|jmh
operator|.
name|runner
operator|.
name|RunnerException
import|;
end_import

begin_class
annotation|@
name|State
argument_list|(
name|Scope
operator|.
name|Thread
argument_list|)
DECL|class|Benchmarks
specifier|public
class|class
name|Benchmarks
block|{
DECL|field|bibtexString
name|String
name|bibtexString
decl_stmt|;
DECL|field|database
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
annotation|@
name|Setup
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|IOException
throws|,
name|SaveException
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
name|Random
name|randomizer
init|=
operator|new
name|Random
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
literal|100000
condition|;
name|i
operator|++
control|)
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"id"
operator|+
name|i
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"This is my title "
operator|+
name|i
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Firstname Lastname and FirstnameA LastnameA and FirstnameB LastnameB"
operator|+
name|i
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Journal Title "
operator|+
name|i
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1"
operator|+
name|i
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"rnd"
argument_list|,
literal|"2"
operator|+
name|randomizer
operator|.
name|nextInt
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
name|BibDatabaseWriter
name|databaseWriter
init|=
operator|new
name|BibDatabaseWriter
argument_list|()
decl_stmt|;
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|databaseWriter
operator|.
name|writePartOfDatabase
argument_list|(
name|stringWriter
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|(
name|database
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
argument_list|,
name|database
operator|.
name|getEntries
argument_list|()
argument_list|,
operator|new
name|SavePreferences
argument_list|()
argument_list|)
expr_stmt|;
name|bibtexString
operator|=
name|stringWriter
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Benchmark
DECL|method|parse ()
specifier|public
name|ParserResult
name|parse
parameter_list|()
throws|throws
name|IOException
block|{
name|StringReader
name|bibtexStringReader
init|=
operator|new
name|StringReader
argument_list|(
name|bibtexString
argument_list|)
decl_stmt|;
name|BibtexParser
name|parser
init|=
operator|new
name|BibtexParser
argument_list|(
name|bibtexStringReader
argument_list|)
decl_stmt|;
return|return
name|parser
operator|.
name|parse
argument_list|()
return|;
block|}
annotation|@
name|Benchmark
DECL|method|write ()
specifier|public
name|String
name|write
parameter_list|()
throws|throws
name|IOException
block|{
name|StringWriter
name|stringWriter
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|BibDatabaseWriter
name|databaseWriter
init|=
operator|new
name|BibDatabaseWriter
argument_list|()
decl_stmt|;
name|databaseWriter
operator|.
name|writePartOfDatabase
argument_list|(
name|stringWriter
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|(
name|database
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
argument_list|,
name|database
operator|.
name|getEntries
argument_list|()
argument_list|,
operator|new
name|SavePreferences
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|stringWriter
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Benchmark
DECL|method|search ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|search
parameter_list|()
block|{
comment|// FIXME: Reuse SearchWorker here
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"Journal Title 500"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|matchedEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|matchedEntries
operator|.
name|addAll
argument_list|(
name|database
operator|.
name|getEntries
argument_list|()
operator|.
name|stream
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
return|return
name|matchedEntries
return|;
block|}
annotation|@
name|Benchmark
DECL|method|inferBibDatabaseMode ()
specifier|public
name|BibDatabaseMode
name|inferBibDatabaseMode
parameter_list|()
block|{
return|return
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|database
argument_list|)
return|;
block|}
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
name|RunnerException
block|{
name|Main
operator|.
name|main
argument_list|(
name|args
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

