begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|io
operator|.
name|BufferedReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Matcher
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
operator|.
name|DoiCleanup
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
name|cleanup
operator|.
name|MoveFieldCleanup
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|EntryBasedParserFetcher
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
name|importer
operator|.
name|IdBasedParserFetcher
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
name|Parser
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
name|SearchBasedParserFetcher
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
name|fileformat
operator|.
name|BibtexParser
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
name|util
operator|.
name|OS
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URIBuilder
import|;
end_import

begin_comment
comment|/**  * Fetches data from the MathSciNet (http://www.ams.org/mathscinet)  */
end_comment

begin_class
DECL|class|MathSciNet
specifier|public
class|class
name|MathSciNet
implements|implements
name|SearchBasedParserFetcher
implements|,
name|EntryBasedParserFetcher
implements|,
name|IdBasedParserFetcher
block|{
DECL|field|preferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|preferences
decl_stmt|;
DECL|method|MathSciNet (ImportFormatPreferences preferences)
specifier|public
name|MathSciNet
parameter_list|(
name|ImportFormatPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"MathSciNet"
return|;
block|}
comment|/**      * We use MR Lookup (http://www.ams.org/mrlookup) instead of the usual search since this tool is also available      * without subscription and, moreover, is optimized for finding a publication based on partial information.      */
annotation|@
name|Override
DECL|method|getURLForEntry (BibEntry entry)
specifier|public
name|URL
name|getURLForEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"https://mathscinet.ams.org/mrlookup"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"format"
argument_list|,
literal|"bibtex"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|title
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"ti"
argument_list|,
name|title
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|author
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"au"
argument_list|,
name|author
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|journal
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"jrnl"
argument_list|,
name|journal
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getFieldOrAlias
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|year
lambda|->
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"year"
argument_list|,
name|year
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForQuery (String query)
specifier|public
name|URL
name|getURLForQuery
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"https://mathscinet.ams.org/mathscinet/search/publications.html"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"pg7"
argument_list|,
literal|"ALLF"
argument_list|)
expr_stmt|;
comment|// search all fields
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"s7"
argument_list|,
name|query
argument_list|)
expr_stmt|;
comment|// query
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"r"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
comment|// start index
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"extend"
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
comment|// should return up to 100 items (instead of default 10)
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"fmt"
argument_list|,
literal|"bibtex"
argument_list|)
expr_stmt|;
comment|// BibTeX format
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getURLForID (String identifier)
specifier|public
name|URL
name|getURLForID
parameter_list|(
name|String
name|identifier
parameter_list|)
throws|throws
name|URISyntaxException
throws|,
name|MalformedURLException
throws|,
name|FetcherException
block|{
name|URIBuilder
name|uriBuilder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"https://mathscinet.ams.org/mathscinet/search/publications.html"
argument_list|)
decl_stmt|;
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"pg1"
argument_list|,
literal|"MR"
argument_list|)
expr_stmt|;
comment|// search MR number
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"s1"
argument_list|,
name|identifier
argument_list|)
expr_stmt|;
comment|// identifier
name|uriBuilder
operator|.
name|addParameter
argument_list|(
literal|"fmt"
argument_list|,
literal|"bibtex"
argument_list|)
expr_stmt|;
comment|// BibTeX format
return|return
name|uriBuilder
operator|.
name|build
argument_list|()
operator|.
name|toURL
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getParser ()
specifier|public
name|Parser
name|getParser
parameter_list|()
block|{
comment|// MathSciNet returns the BibTeX result embedded in HTML
comment|// So we extract the BibTeX string from the<pre>bibtex</pre> tags and pass the content to the BibTeX parser
return|return
name|inputStream
lambda|->
block|{
name|String
name|response
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|inputStream
argument_list|)
argument_list|)
operator|.
name|lines
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
name|OS
operator|.
name|NEWLINE
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|BibtexParser
name|bibtexParser
init|=
operator|new
name|BibtexParser
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<pre>(?s)(.*)</pre>"
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|response
argument_list|)
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|bibtexEntryString
init|=
name|matcher
operator|.
name|group
argument_list|()
decl_stmt|;
name|entries
operator|.
name|addAll
argument_list|(
name|bibtexParser
operator|.
name|parseEntries
argument_list|(
name|bibtexEntryString
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|entries
return|;
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|doPostCleanup (BibEntry entry)
specifier|public
name|void
name|doPostCleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
operator|new
name|MoveFieldCleanup
argument_list|(
literal|"fjournal"
argument_list|,
name|FieldName
operator|.
name|JOURNAL
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|MoveFieldCleanup
argument_list|(
literal|"mrclass"
argument_list|,
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"mrreviewer"
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|DoiCleanup
argument_list|()
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
operator|new
name|FieldFormatterCleanup
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// Remove comments: MathSciNet prepends a<pre> html tag
name|entry
operator|.
name|setCommentsBeforeEntry
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

