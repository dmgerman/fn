begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.importer.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fetcher
package|;
end_package

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
name|ImportInspector
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
name|OutputPrinter
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
name|id
operator|.
name|IdGenerator
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
name|net
operator|.
name|URLDownload
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
name|strings
operator|.
name|NameListNormalizer
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
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
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
name|io
operator|.
name|UnsupportedEncodingException
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
name|net
operator|.
name|URLEncoder
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

begin_class
DECL|class|CiteSeerXFetcher
specifier|public
class|class
name|CiteSeerXFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|MAX_PAGES_TO_LOAD
specifier|private
specifier|static
specifier|final
name|int
name|MAX_PAGES_TO_LOAD
init|=
literal|8
decl_stmt|;
DECL|field|QUERY_MARKER
specifier|private
specifier|static
specifier|final
name|String
name|QUERY_MARKER
init|=
literal|"___QUERY___"
decl_stmt|;
DECL|field|URL_START
specifier|private
specifier|static
specifier|final
name|String
name|URL_START
init|=
literal|"http://citeseer.ist.psu.edu"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
name|CiteSeerXFetcher
operator|.
name|URL_START
operator|+
literal|"/search?q="
operator|+
name|CiteSeerXFetcher
operator|.
name|QUERY_MARKER
operator|+
literal|"&submit=Search&sort=rlv&t=doc"
decl_stmt|;
DECL|field|CITE_LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|CITE_LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a class=\"remove doc_details\" href=\"(.*)\">"
argument_list|)
decl_stmt|;
DECL|field|stopFetching
specifier|private
name|boolean
name|stopFetching
decl_stmt|;
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector inspector, OutputPrinter status)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|inspector
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|stopFetching
operator|=
literal|false
expr_stmt|;
try|try
block|{
name|List
argument_list|<
name|String
argument_list|>
name|citations
init|=
name|getCitations
argument_list|(
name|query
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|citation
range|:
name|citations
control|)
block|{
if|if
condition|(
name|stopFetching
condition|)
block|{
break|break;
block|}
name|BibtexEntry
name|entry
init|=
name|getSingleCitation
argument_list|(
name|citation
argument_list|)
decl_stmt|;
comment|//BibtexEntry entry = BibsonomyScraper.getEntry(citation);
comment|//dialog.setProgress(++i, citations.size());
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|inspector
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
literal|"CiteSeerX"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"CiteSeerHelp.html"
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
name|stopFetching
operator|=
literal|true
expr_stmt|;
block|}
comment|/**      *      * @param query      *            The search term to query JStor for.      * @return a list of IDs      * @throws java.io.IOException      */
DECL|method|getCitations (String query)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getCitations
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|urlQuery
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|ids
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|urlQuery
operator|=
name|CiteSeerXFetcher
operator|.
name|SEARCH_URL
operator|.
name|replace
argument_list|(
name|CiteSeerXFetcher
operator|.
name|QUERY_MARKER
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|count
init|=
literal|1
decl_stmt|;
name|String
name|nextPage
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|nextPage
operator|=
name|getCitationsFromUrl
argument_list|(
name|urlQuery
argument_list|,
name|ids
argument_list|)
operator|)
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|count
operator|<
name|CiteSeerXFetcher
operator|.
name|MAX_PAGES_TO_LOAD
operator|)
condition|)
block|{
name|urlQuery
operator|=
name|nextPage
expr_stmt|;
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|stopFetching
condition|)
block|{
break|break;
block|}
block|}
return|return
name|ids
return|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
DECL|method|getCitationsFromUrl (String urlQuery, List<String> ids)
specifier|private
specifier|static
name|String
name|getCitationsFromUrl
parameter_list|(
name|String
name|urlQuery
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|ids
parameter_list|)
throws|throws
name|IOException
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|urlQuery
argument_list|)
decl_stmt|;
name|String
name|cont
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
operator|.
name|downloadToString
argument_list|()
decl_stmt|;
comment|//System.out.println(cont);
name|Matcher
name|m
init|=
name|CiteSeerXFetcher
operator|.
name|CITE_LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|ids
operator|.
name|add
argument_list|(
name|CiteSeerXFetcher
operator|.
name|URL_START
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
DECL|field|basePattern
specifier|private
specifier|static
specifier|final
name|String
name|basePattern
init|=
literal|"<meta name=\""
operator|+
name|CiteSeerXFetcher
operator|.
name|QUERY_MARKER
operator|+
literal|"\" content=\"(.*)\" />"
decl_stmt|;
DECL|field|titlePattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|titlePattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|CiteSeerXFetcher
operator|.
name|basePattern
operator|.
name|replace
argument_list|(
name|CiteSeerXFetcher
operator|.
name|QUERY_MARKER
argument_list|,
literal|"citation_title"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|authorPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|authorPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|CiteSeerXFetcher
operator|.
name|basePattern
operator|.
name|replace
argument_list|(
name|CiteSeerXFetcher
operator|.
name|QUERY_MARKER
argument_list|,
literal|"citation_authors"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|yearPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|yearPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|CiteSeerXFetcher
operator|.
name|basePattern
operator|.
name|replace
argument_list|(
name|CiteSeerXFetcher
operator|.
name|QUERY_MARKER
argument_list|,
literal|"citation_year"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|abstractPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|abstractPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<h3>Abstract</h3>\\s*<p>(.*)</p>"
argument_list|)
decl_stmt|;
DECL|method|getSingleCitation (String urlString)
specifier|private
specifier|static
name|BibtexEntry
name|getSingleCitation
parameter_list|(
name|String
name|urlString
parameter_list|)
throws|throws
name|IOException
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|urlString
argument_list|)
decl_stmt|;
name|String
name|cont
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
operator|.
name|downloadToString
argument_list|(
literal|"UTF8"
argument_list|)
decl_stmt|;
comment|// Find title, and create entry if we do. Otherwise assume we didn't get an entry:
name|Matcher
name|m
init|=
name|CiteSeerXFetcher
operator|.
name|titlePattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
decl_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|BibtexEntry
name|entry
init|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|// Find authors:
name|m
operator|=
name|CiteSeerXFetcher
operator|.
name|authorPattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|authors
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
name|authors
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Find year:
name|m
operator|=
name|CiteSeerXFetcher
operator|.
name|yearPattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Find abstract:
name|m
operator|=
name|CiteSeerXFetcher
operator|.
name|abstractPattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|entry
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
block|}
end_class

end_unit

