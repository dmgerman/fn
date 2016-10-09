begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.util
package|package
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
name|util
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
name|ImportFormatPreferences
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_class
DECL|class|DBLPHelper
specifier|public
class|class
name|DBLPHelper
block|{
DECL|field|cleaner
specifier|private
specifier|final
name|DBLPQueryCleaner
name|cleaner
init|=
operator|new
name|DBLPQueryCleaner
argument_list|()
decl_stmt|;
DECL|field|START_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|START_PATTERN
init|=
literal|"<pre class=\"verbatim select-on-click\">"
decl_stmt|;
DECL|field|END_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|END_PATTERN
init|=
literal|"</pre>"
decl_stmt|;
DECL|field|importFormatPreferences
specifier|private
specifier|final
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
comment|/*      * This is a small helper class that cleans the user submitted query. Right      * now, we cannot search for ":" on dblp.org. So, we remove colons from the      * user submitted search string. Also, the search is case sensitive if we      * use capitals. So, we better change the text to lower case.      */
DECL|class|DBLPQueryCleaner
specifier|static
class|class
name|DBLPQueryCleaner
block|{
DECL|method|cleanQuery (final String query)
specifier|public
name|String
name|cleanQuery
parameter_list|(
specifier|final
name|String
name|query
parameter_list|)
block|{
name|String
name|cleaned
init|=
name|query
decl_stmt|;
name|cleaned
operator|=
name|cleaned
operator|.
name|replace
argument_list|(
literal|"-"
argument_list|,
literal|" "
argument_list|)
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"%20"
argument_list|)
operator|.
name|replace
argument_list|(
literal|":"
argument_list|,
literal|""
argument_list|)
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
return|return
name|cleaned
return|;
block|}
block|}
DECL|method|DBLPHelper (ImportFormatPreferences importFormatPreferences)
specifier|public
name|DBLPHelper
parameter_list|(
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|)
block|{
name|this
operator|.
name|importFormatPreferences
operator|=
name|importFormatPreferences
expr_stmt|;
block|}
comment|/**      *      * @param query      *            string with the user query      * @return a string with the user query, but compatible with dblp.org      */
DECL|method|cleanDBLPQuery (String query)
specifier|public
name|String
name|cleanDBLPQuery
parameter_list|(
name|String
name|query
parameter_list|)
block|{
return|return
name|cleaner
operator|.
name|cleanQuery
argument_list|(
name|query
argument_list|)
return|;
block|}
comment|/**      * Takes an HTML file (as String) as input and extracts the bibtex      * information. After that, it will convert it into a BibEntry and return      * it (them).      *      * @param page      *            page as String      * @return list of BibEntry      */
DECL|method|getBibTexFromPage (final String page)
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getBibTexFromPage
parameter_list|(
specifier|final
name|String
name|page
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibtexList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|String
name|tmpStr
init|=
name|page
decl_stmt|;
name|int
name|startIdx
init|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|START_PATTERN
argument_list|)
decl_stmt|;
name|int
name|endIdx
init|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|END_PATTERN
argument_list|)
decl_stmt|;
comment|// this entry exists for sure
name|String
name|entry1
init|=
name|tmpStr
operator|.
name|substring
argument_list|(
name|startIdx
operator|+
name|START_PATTERN
operator|.
name|length
argument_list|()
argument_list|,
name|endIdx
argument_list|)
decl_stmt|;
name|entry1
operator|=
name|cleanEntry
argument_list|(
name|entry1
argument_list|)
expr_stmt|;
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|entry1
argument_list|,
name|importFormatPreferences
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|bibtexList
operator|::
name|add
argument_list|)
expr_stmt|;
comment|// let's see whether there is another entry (crossref)
name|tmpStr
operator|=
name|tmpStr
operator|.
name|substring
argument_list|(
name|endIdx
operator|+
name|END_PATTERN
operator|.
name|length
argument_list|()
argument_list|,
name|tmpStr
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|startIdx
operator|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|START_PATTERN
argument_list|)
expr_stmt|;
if|if
condition|(
name|startIdx
operator|!=
operator|-
literal|1
condition|)
block|{
name|endIdx
operator|=
name|tmpStr
operator|.
name|indexOf
argument_list|(
name|END_PATTERN
argument_list|)
expr_stmt|;
comment|// this entry exists for sure
name|String
name|entry2
init|=
name|tmpStr
operator|.
name|substring
argument_list|(
name|startIdx
operator|+
name|START_PATTERN
operator|.
name|length
argument_list|()
argument_list|,
name|endIdx
argument_list|)
decl_stmt|;
name|entry2
operator|=
name|cleanEntry
argument_list|(
name|entry2
argument_list|)
expr_stmt|;
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
name|entry2
argument_list|,
name|importFormatPreferences
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|bibtexList
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
return|return
name|bibtexList
return|;
block|}
DECL|method|cleanEntry (final String bibEntry)
specifier|private
name|String
name|cleanEntry
parameter_list|(
specifier|final
name|String
name|bibEntry
parameter_list|)
block|{
return|return
name|bibEntry
operator|.
name|replaceFirst
argument_list|(
literal|"<a href=\".*\">DBLP</a>"
argument_list|,
literal|"DBLP"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

