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

begin_class
DECL|class|GoogleScholarFetcher
specifier|public
class|class
name|GoogleScholarFetcher
implements|implements
name|PreviewEntryFetcher
block|{
DECL|field|hasRunConfig
specifier|private
name|boolean
name|hasRunConfig
init|=
literal|false
decl_stmt|;
DECL|field|clearKeys
specifier|private
name|boolean
name|clearKeys
init|=
literal|true
decl_stmt|;
comment|// Should we clear the keys so new ones can be generated?
DECL|field|MAX_ENTRIES_TO_LOAD
specifier|protected
specifier|static
name|int
name|MAX_ENTRIES_TO_LOAD
init|=
literal|50
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
literal|"http://scholar.google.com"
decl_stmt|;
DECL|field|URL_SETTING
specifier|final
specifier|static
name|String
name|URL_SETTING
init|=
literal|"http://scholar.google.com/scholar_settings"
decl_stmt|;
DECL|field|URL_SETPREFS
specifier|final
specifier|static
name|String
name|URL_SETPREFS
init|=
literal|"http://scholar.google.com/scholar_setprefs"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|final
specifier|static
name|String
name|SEARCH_URL
init|=
name|URL_START
operator|+
literal|"/scholar?q="
operator|+
name|QUERY_MARKER
operator|+
literal|"&amp;hl=en&amp;btnG=Search"
decl_stmt|;
DECL|field|BIBTEX_LINK_PATTERN
specifier|final
specifier|static
name|Pattern
name|BIBTEX_LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\"([^\"]*)\">[A-Za-z ]*BibTeX"
argument_list|)
decl_stmt|;
DECL|field|TITLE_START_PATTERN
specifier|final
specifier|static
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
DECL|field|TITLE_END_PATTERN
specifier|final
specifier|static
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
comment|//final static Pattern NEXT_PAGE_PATTERN = Pattern.compile(
comment|//        "<a href=\"([^\"]*)\"><span class=\"SPRITE_nav_next\"></span><br><span style=\".*\">Next</span></a>");
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
return|return
literal|false
return|;
block|}
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
name|String
name|link
range|:
name|citations
operator|.
name|keySet
argument_list|()
control|)
block|{
name|preview
operator|.
name|addEntry
argument_list|(
name|link
argument_list|,
name|citations
operator|.
name|get
argument_list|(
name|link
argument_list|)
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
name|Globals
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
decl_stmt|,
name|downloaded
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|link
range|:
name|selection
operator|.
name|keySet
argument_list|()
control|)
block|{
name|boolean
name|isSelected
init|=
name|selection
operator|.
name|get
argument_list|(
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
name|isSelected
condition|)
name|toDownload
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|toDownload
operator|==
literal|0
condition|)
return|return;
for|for
control|(
name|String
name|link
range|:
name|selection
operator|.
name|keySet
argument_list|()
control|)
block|{
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
name|selection
operator|.
name|get
argument_list|(
name|link
argument_list|)
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
name|BibtexEntry
name|entry
init|=
name|downloadEntry
argument_list|(
name|link
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
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
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
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"Google Scholar"
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
literal|"GoogleScholarHelp.html"
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
DECL|method|save (String filename, String content)
specifier|private
name|void
name|save
parameter_list|(
name|String
name|filename
parameter_list|,
name|String
name|content
parameter_list|)
throws|throws
name|IOException
block|{
name|BufferedWriter
name|out
init|=
operator|new
name|BufferedWriter
argument_list|(
operator|new
name|FileWriter
argument_list|(
name|filename
argument_list|)
argument_list|)
decl_stmt|;
name|out
operator|.
name|write
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
DECL|method|runConfig ()
specifier|protected
name|void
name|runConfig
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|urlQuery
decl_stmt|;
try|try
block|{
name|URL
name|url
decl_stmt|;
name|URLDownload
name|ud
decl_stmt|;
name|url
operator|=
operator|new
name|URL
argument_list|(
literal|"http://scholar.google.com"
argument_list|)
expr_stmt|;
name|ud
operator|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
name|url
operator|=
operator|new
name|URL
argument_list|(
name|URL_SETTING
argument_list|)
expr_stmt|;
name|ud
operator|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
comment|//save("setting.html", ud.getStringContent());
name|String
name|settingsPage
init|=
name|ud
operator|.
name|getStringContent
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
name|MAX_ENTRIES_TO_LOAD
argument_list|)
argument_list|)
expr_stmt|;
name|StringBuilder
name|ub
init|=
operator|new
name|StringBuilder
argument_list|(
name|URL_SETPREFS
operator|+
literal|"?"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|formItems
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|name
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|ub
operator|.
name|append
argument_list|(
name|name
argument_list|)
operator|.
name|append
argument_list|(
literal|"="
argument_list|)
operator|.
name|append
argument_list|(
name|formItems
operator|.
name|get
argument_list|(
name|name
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
name|ub
operator|.
name|append
argument_list|(
literal|"&"
argument_list|)
expr_stmt|;
block|}
name|ub
operator|.
name|append
argument_list|(
literal|"&submit="
argument_list|)
expr_stmt|;
comment|// Download the URL to set preferences:
name|URL
name|url_setprefs
init|=
operator|new
name|URL
argument_list|(
name|ub
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|ud
operator|=
operator|new
name|URLDownload
argument_list|(
name|url_setprefs
argument_list|)
expr_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedEncodingException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      *      * @param query      *            The search term to query JStor for.      * @return a list of IDs      * @throws java.io.IOException      */
DECL|method|getCitations (String query)
specifier|protected
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
argument_list|<
name|String
argument_list|,
name|JLabel
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
break|break;
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
specifier|protected
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
name|URLDownload
name|ud
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
name|String
name|cont
init|=
name|ud
operator|.
name|getStringContent
argument_list|()
decl_stmt|;
name|Matcher
name|m
init|=
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
init|=
literal|null
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
name|pText
operator|=
name|part
expr_stmt|;
block|}
else|else
name|pText
operator|=
name|link
expr_stmt|;
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
specifier|protected
name|BibtexEntry
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
name|URL_START
operator|+
name|link
argument_list|)
decl_stmt|;
name|URLDownload
name|ud
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|ud
operator|.
name|download
argument_list|()
expr_stmt|;
name|String
name|s
init|=
name|ud
operator|.
name|getStringContent
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
name|BibtexEntry
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
name|BibtexEntry
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
if|if
condition|(
name|clearKeys
condition|)
name|entry
operator|.
name|setField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
literal|null
argument_list|)
expr_stmt|;
return|return
name|entry
return|;
block|}
elseif|else
if|if
condition|(
name|entries
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
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
name|System
operator|.
name|out
operator|.
name|println
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
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
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
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|field|inputPattern
specifier|static
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
specifier|public
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
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
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

