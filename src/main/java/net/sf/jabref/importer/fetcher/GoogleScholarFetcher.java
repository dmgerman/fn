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
name|gui
operator|.
name|FetcherPreviewDialog
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
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

begin_class
DECL|class|GoogleScholarFetcher
specifier|public
class|class
name|GoogleScholarFetcher
implements|implements
name|PreviewEntryFetcher
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
name|GoogleScholarFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|hasRunConfig
specifier|private
name|boolean
name|hasRunConfig
decl_stmt|;
DECL|field|MAX_ENTRIES_TO_LOAD
specifier|private
specifier|static
specifier|final
name|int
name|MAX_ENTRIES_TO_LOAD
init|=
literal|50
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
literal|"http://scholar.google.com"
decl_stmt|;
DECL|field|URL_SETTING
specifier|private
specifier|static
specifier|final
name|String
name|URL_SETTING
init|=
literal|"http://scholar.google.com/scholar_settings"
decl_stmt|;
DECL|field|URL_SETPREFS
specifier|private
specifier|static
specifier|final
name|String
name|URL_SETPREFS
init|=
literal|"http://scholar.google.com/scholar_setprefs"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
name|GoogleScholarFetcher
operator|.
name|URL_START
operator|+
literal|"/scholar?q="
operator|+
name|GoogleScholarFetcher
operator|.
name|QUERY_MARKER
operator|+
literal|"&amp;hl=en&amp;btnG=Search"
decl_stmt|;
DECL|field|BIBTEX_LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|BIBTEX_LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\"([^\"]*)\"[^>]*>[A-Za-z ]*BibTeX"
argument_list|)
decl_stmt|;
DECL|field|TITLE_START_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|TITLE_START_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<div class=\"gs_ri\">"
argument_list|)
decl_stmt|;
DECL|field|LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<h3 class=\"gs_rt\"><a href=\"([^\"]*)\">"
argument_list|)
decl_stmt|;
DECL|field|TITLE_END_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|TITLE_END_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<div class=\"gs_fl\">"
argument_list|)
decl_stmt|;
DECL|field|entryLinks
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entryLinks
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|//final static Pattern NEXT_PAGE_PATTERN = Pattern.compile(
comment|//        "<a href=\"([^\"]*)\"><span class=\"SPRITE_nav_next\"></span><br><span style=\".*\">Next</span></a>");
DECL|field|stopFetching
specifier|private
name|boolean
name|stopFetching
decl_stmt|;
annotation|@
name|Override
DECL|method|getWarningLimit ()
specifier|public
name|int
name|getWarningLimit
parameter_list|()
block|{
return|return
literal|10
return|;
block|}
annotation|@
name|Override
DECL|method|getPreferredPreviewHeight ()
specifier|public
name|int
name|getPreferredPreviewHeight
parameter_list|()
block|{
return|return
literal|100
return|;
block|}
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
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|processQueryGetPreview (String query, FetcherPreviewDialog preview, OutputPrinter status)
specifier|public
name|boolean
name|processQueryGetPreview
parameter_list|(
name|String
name|query
parameter_list|,
name|FetcherPreviewDialog
name|preview
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|entryLinks
operator|.
name|clear
argument_list|()
expr_stmt|;
name|stopFetching
operator|=
literal|false
expr_stmt|;
try|try
block|{
if|if
condition|(
operator|!
name|hasRunConfig
condition|)
block|{
name|runConfig
argument_list|()
expr_stmt|;
name|hasRunConfig
operator|=
literal|true
expr_stmt|;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
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
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|linkEntry
range|:
name|citations
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|preview
operator|.
name|addEntry
argument_list|(
name|linkEntry
operator|.
name|getKey
argument_list|()
argument_list|,
name|linkEntry
operator|.
name|getValue
argument_list|()
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
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error fetching from Google Scholar"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getEntries (Map<String, Boolean> selection, ImportInspector inspector)
specifier|public
name|void
name|getEntries
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|selection
parameter_list|,
name|ImportInspector
name|inspector
parameter_list|)
block|{
name|int
name|toDownload
init|=
literal|0
decl_stmt|;
name|int
name|downloaded
init|=
literal|0
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|selEntry
range|:
name|selection
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|boolean
name|isSelected
init|=
name|selEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|isSelected
condition|)
block|{
name|toDownload
operator|++
expr_stmt|;
block|}
block|}
if|if
condition|(
name|toDownload
operator|==
literal|0
condition|)
block|{
return|return;
block|}
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|selEntry
range|:
name|selection
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|stopFetching
condition|)
block|{
break|break;
block|}
name|inspector
operator|.
name|setProgress
argument_list|(
name|downloaded
argument_list|,
name|toDownload
argument_list|)
expr_stmt|;
name|boolean
name|isSelected
init|=
name|selEntry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|isSelected
condition|)
block|{
name|downloaded
operator|++
expr_stmt|;
try|try
block|{
name|BibEntry
name|entry
init|=
name|downloadEntry
argument_list|(
name|selEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
name|inspector
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot download entry from Google scholar"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
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
literal|"Google Scholar"
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
literal|"GoogleScholarHelp.html"
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
comment|/*  Used for debugging */
comment|/*    private static void save(String filename, String content) throws IOException {         try (BufferedWriter out = new BufferedWriter(new FileWriter(filename))) {             out.write(content);         }     }     */
DECL|method|runConfig ()
specifier|private
specifier|static
name|void
name|runConfig
parameter_list|()
throws|throws
name|IOException
block|{
try|try
block|{
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://scholar.google.com"
argument_list|)
argument_list|)
operator|.
name|downloadToString
argument_list|()
expr_stmt|;
comment|//save("setting.html", ud.getStringContent());
name|String
name|settingsPage
init|=
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
name|GoogleScholarFetcher
operator|.
name|URL_SETTING
argument_list|)
argument_list|)
operator|.
name|downloadToString
argument_list|()
decl_stmt|;
comment|// Get the form items and their values from the page:
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|formItems
init|=
name|GoogleScholarFetcher
operator|.
name|getFormElements
argument_list|(
name|settingsPage
argument_list|)
decl_stmt|;
comment|// Override the important ones:
name|formItems
operator|.
name|put
argument_list|(
literal|"scis"
argument_list|,
literal|"yes"
argument_list|)
expr_stmt|;
name|formItems
operator|.
name|put
argument_list|(
literal|"scisf"
argument_list|,
literal|"4"
argument_list|)
expr_stmt|;
name|formItems
operator|.
name|put
argument_list|(
literal|"num"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|GoogleScholarFetcher
operator|.
name|MAX_ENTRIES_TO_LOAD
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|request
init|=
name|formItems
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Object
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|(
literal|"&"
argument_list|,
name|GoogleScholarFetcher
operator|.
name|URL_SETPREFS
operator|+
literal|"?"
argument_list|,
literal|"&submit="
argument_list|)
argument_list|)
decl_stmt|;
comment|// Download the URL to set preferences:
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
name|request
argument_list|)
argument_list|)
operator|.
name|downloadToString
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Unsupported encoding."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * @param query The search term to query Google Scholar for.      * @return a list of IDs      * @throws java.io.IOException      */
DECL|method|getCitations (String query)
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
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
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|res
init|=
operator|new
name|LinkedHashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|urlQuery
operator|=
name|GoogleScholarFetcher
operator|.
name|SEARCH_URL
operator|.
name|replace
argument_list|(
name|GoogleScholarFetcher
operator|.
name|QUERY_MARKER
argument_list|,
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
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
name|res
argument_list|)
operator|)
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|count
operator|<
literal|2
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
name|res
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
DECL|method|getCitationsFromUrl (String urlQuery, Map<String, JLabel> ids)
specifier|private
name|String
name|getCitationsFromUrl
parameter_list|(
name|String
name|urlQuery
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
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
comment|//save("query.html", cont);
name|Matcher
name|m
init|=
name|GoogleScholarFetcher
operator|.
name|BIBTEX_LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
decl_stmt|;
name|int
name|lastRegionStart
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|link
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"&amp;"
argument_list|,
literal|"&"
argument_list|)
decl_stmt|;
name|String
name|pText
decl_stmt|;
comment|//System.out.println("regionStart: "+m.start());
name|String
name|part
init|=
name|cont
operator|.
name|substring
argument_list|(
name|lastRegionStart
argument_list|,
name|m
operator|.
name|start
argument_list|()
argument_list|)
decl_stmt|;
name|Matcher
name|titleS
init|=
name|GoogleScholarFetcher
operator|.
name|TITLE_START_PATTERN
operator|.
name|matcher
argument_list|(
name|part
argument_list|)
decl_stmt|;
name|Matcher
name|titleE
init|=
name|GoogleScholarFetcher
operator|.
name|TITLE_END_PATTERN
operator|.
name|matcher
argument_list|(
name|part
argument_list|)
decl_stmt|;
name|boolean
name|fS
init|=
name|titleS
operator|.
name|find
argument_list|()
decl_stmt|;
name|boolean
name|fE
init|=
name|titleE
operator|.
name|find
argument_list|()
decl_stmt|;
comment|//System.out.println("fs = "+fS+", fE = "+fE);
comment|//System.out.println(titleS.end()+" : "+titleE.start());
if|if
condition|(
name|fS
operator|&&
name|fE
condition|)
block|{
if|if
condition|(
name|titleS
operator|.
name|end
argument_list|()
operator|<
name|titleE
operator|.
name|start
argument_list|()
condition|)
block|{
name|pText
operator|=
name|part
operator|.
name|substring
argument_list|(
name|titleS
operator|.
name|end
argument_list|()
argument_list|,
name|titleE
operator|.
name|start
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|pText
operator|=
name|part
expr_stmt|;
block|}
block|}
else|else
block|{
name|pText
operator|=
name|link
expr_stmt|;
block|}
name|pText
operator|=
name|pText
operator|.
name|replaceAll
argument_list|(
literal|"\\[PDF\\]"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|JLabel
name|preview
init|=
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|pText
operator|+
literal|"</html>"
argument_list|)
decl_stmt|;
name|ids
operator|.
name|put
argument_list|(
name|link
argument_list|,
name|preview
argument_list|)
expr_stmt|;
comment|// See if we can extract the link Google Scholar puts on the entry's title.
comment|// That will be set as "url" for the entry if downloaded:
name|Matcher
name|linkMatcher
init|=
name|GoogleScholarFetcher
operator|.
name|LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|pText
argument_list|)
decl_stmt|;
if|if
condition|(
name|linkMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|entryLinks
operator|.
name|put
argument_list|(
name|link
argument_list|,
name|linkMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|lastRegionStart
operator|=
name|m
operator|.
name|end
argument_list|()
expr_stmt|;
block|}
comment|/*m = NEXT_PAGE_PATTERN.matcher(cont);         if (m.find()) {             System.out.println("NEXT: "+URL_START+m.group(1).replaceAll("&amp;", "&"));             return URL_START+m.group(1).replaceAll("&amp;", "&");         }         else*/
return|return
literal|null
return|;
block|}
DECL|method|downloadEntry (String link)
specifier|private
name|BibEntry
name|downloadEntry
parameter_list|(
name|String
name|link
parameter_list|)
throws|throws
name|IOException
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|GoogleScholarFetcher
operator|.
name|URL_START
operator|+
name|link
argument_list|)
decl_stmt|;
name|String
name|s
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
name|BibtexParser
name|bp
init|=
operator|new
name|BibtexParser
argument_list|(
operator|new
name|StringReader
argument_list|(
name|s
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|bp
operator|.
name|parse
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|pr
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
comment|// If the entry's url field is not set, and we have stored an url for this
comment|// entry, set it:
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
operator|==
literal|null
condition|)
block|{
name|String
name|storedUrl
init|=
name|entryLinks
operator|.
name|get
argument_list|(
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
name|storedUrl
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
name|storedUrl
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Clean up some remaining HTML code from Elsevier(?) papers
comment|// Search for: Poincare algebra
comment|// to see an example
name|String
name|title
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
decl_stmt|;
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|String
name|newtitle
init|=
name|title
operator|.
name|replaceAll
argument_list|(
literal|"<.?i>([^<]*)</i>"
argument_list|,
literal|"$1"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|newtitle
operator|.
name|equals
argument_list|(
name|title
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|newtitle
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|entry
return|;
block|}
elseif|else
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"No entry found! ("
operator|+
name|link
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
name|entries
operator|.
name|size
argument_list|()
operator|+
literal|" entries found! ("
operator|+
name|link
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Parser failed! ("
operator|+
name|link
operator|+
literal|")"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Malformed URL."
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|field|inputPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|inputPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<input type=([^ ]+) name=([^ ]+) value=([^> ]+)"
argument_list|)
decl_stmt|;
DECL|method|getFormElements (String page)
specifier|private
specifier|static
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getFormElements
parameter_list|(
name|String
name|page
parameter_list|)
block|{
name|Matcher
name|m
init|=
name|GoogleScholarFetcher
operator|.
name|inputPattern
operator|.
name|matcher
argument_list|(
name|page
argument_list|)
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|items
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|name
init|=
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|name
operator|.
name|length
argument_list|()
operator|>
literal|2
operator|)
operator|&&
operator|(
name|name
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'"'
operator|)
operator|&&
operator|(
name|name
operator|.
name|charAt
argument_list|(
name|name
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'"'
operator|)
condition|)
block|{
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|name
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|String
name|value
init|=
name|m
operator|.
name|group
argument_list|(
literal|3
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|value
operator|.
name|length
argument_list|()
operator|>
literal|2
operator|)
operator|&&
operator|(
name|value
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
literal|'"'
operator|)
operator|&&
operator|(
name|value
operator|.
name|charAt
argument_list|(
name|value
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|'"'
operator|)
condition|)
block|{
name|value
operator|=
name|value
operator|.
name|substring
argument_list|(
literal|1
argument_list|,
name|value
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|items
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
return|return
name|items
return|;
block|}
block|}
end_class

end_unit

