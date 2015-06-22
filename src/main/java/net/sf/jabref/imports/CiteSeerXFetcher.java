begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|util
operator|.
name|NameListNormalizer
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
specifier|protected
specifier|static
specifier|final
name|int
name|MAX_PAGES_TO_LOAD
init|=
literal|8
decl_stmt|;
DECL|field|QUERY_MARKER
specifier|final
specifier|static
name|String
name|QUERY_MARKER
init|=
literal|"___QUERY___"
decl_stmt|;
DECL|field|URL_START
specifier|final
specifier|static
name|String
name|URL_START
init|=
literal|"http://citeseer.ist.psu.edu"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|final
specifier|static
name|String
name|SEARCH_URL
init|=
name|URL_START
operator|+
literal|"/search?q="
operator|+
name|QUERY_MARKER
operator|+
literal|"&submit=Search&sort=rlv&t=doc"
decl_stmt|;
DECL|field|CITE_LINK_PATTERN
specifier|final
specifier|static
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
specifier|protected
name|boolean
name|stopFetching
init|=
literal|false
decl_stmt|;
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
break|break;
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
name|inspector
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"CiteSeerX"
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|URL
name|getIcon
parameter_list|()
block|{
return|return
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"www"
argument_list|)
return|;
block|}
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
specifier|protected
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
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
try|try
block|{
name|urlQuery
operator|=
name|SEARCH_URL
operator|.
name|replace
argument_list|(
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
init|=
literal|null
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
break|break;
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
specifier|protected
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
specifier|final
specifier|static
name|String
name|basePattern
init|=
literal|"<meta name=\""
operator|+
name|QUERY_MARKER
operator|+
literal|"\" content=\"(.*)\" />"
decl_stmt|;
DECL|field|titlePattern
specifier|final
specifier|static
name|Pattern
name|titlePattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|basePattern
operator|.
name|replace
argument_list|(
name|QUERY_MARKER
argument_list|,
literal|"citation_title"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|authorPattern
specifier|final
specifier|static
name|Pattern
name|authorPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|basePattern
operator|.
name|replace
argument_list|(
name|QUERY_MARKER
argument_list|,
literal|"citation_authors"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|yearPattern
specifier|final
specifier|static
name|Pattern
name|yearPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|basePattern
operator|.
name|replace
argument_list|(
name|QUERY_MARKER
argument_list|,
literal|"citation_year"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|abstractPattern
specifier|final
specifier|static
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
specifier|protected
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
name|Util
operator|.
name|createNeutralId
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
comment|// Find abstract:
name|m
operator|=
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
return|return
name|entry
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

