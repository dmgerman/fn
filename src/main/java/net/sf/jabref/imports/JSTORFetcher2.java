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
name|BibtexEntry
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
name|net
operator|.
name|URLDownload
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
DECL|class|JSTORFetcher2
specifier|public
class|class
name|JSTORFetcher2
implements|implements
name|EntryFetcher
block|{
DECL|field|CANCELLED
specifier|private
specifier|static
specifier|final
name|String
name|CANCELLED
init|=
literal|"__CANCELLED__"
decl_stmt|;
DECL|field|MAX_PAGES_TO_LOAD
specifier|private
specifier|static
specifier|final
name|int
name|MAX_PAGES_TO_LOAD
init|=
literal|8
decl_stmt|;
DECL|field|MAX_REFS
specifier|protected
specifier|static
name|int
name|MAX_REFS
init|=
literal|7
operator|*
literal|25
decl_stmt|;
DECL|field|REFS_PER_PAGE
specifier|private
specifier|static
specifier|final
name|int
name|REFS_PER_PAGE
init|=
literal|25
decl_stmt|;
comment|// This is the current default of JSTOR;
DECL|field|JSTOR_URL
specifier|private
specifier|static
specifier|final
name|String
name|JSTOR_URL
init|=
literal|"http://www.jstor.org"
decl_stmt|;
DECL|field|SEARCH_URL
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL
init|=
name|JSTORFetcher2
operator|.
name|JSTOR_URL
operator|+
literal|"/action/doBasicSearch?Query="
decl_stmt|;
DECL|field|SEARCH_URL_END
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL_END
init|=
literal|"&x=0&y=0&wc=on"
decl_stmt|;
DECL|field|SINGLE_CIT_ENC
specifier|private
specifier|static
specifier|final
name|String
name|SINGLE_CIT_ENC
init|=
comment|//"http://www.jstor.org/action/exportSingleCitation?singleCitation=true&suffix=";
literal|"http://www.jstor.org/action/exportSingleCitation?singleCitation=true&doi=10.2307/"
decl_stmt|;
comment|// suffix doesn't work anymore (March 2013), changed to doi=10.2307/citations but only if it a doi
comment|// to be improved...
comment|//"http%3A%2F%2Fwww.jstor.org%2Faction%2FexportSingleCitation%3FsingleCitation"
comment|//+"%3Dtrue%26suffix%3D";
DECL|field|idPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|idPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a class=\"title\" href=\"/stable/(\\d+)\\?"
argument_list|)
decl_stmt|;
DECL|field|numberofhits
specifier|private
specifier|static
specifier|final
name|Pattern
name|numberofhits
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<span id=\"NumberOfHits\" name=\"(\\d+)\""
argument_list|)
decl_stmt|;
DECL|field|nextPagePattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|nextPagePattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\"(.*)\">Next&nbsp;&raquo;"
argument_list|)
decl_stmt|;
DECL|field|noAccessIndicator
specifier|private
specifier|static
specifier|final
name|String
name|noAccessIndicator
init|=
literal|"We do not recognize you as having access to JSTOR"
decl_stmt|;
DECL|field|stopFetching
specifier|private
name|boolean
name|stopFetching
init|=
literal|false
decl_stmt|;
DECL|field|noAccessFound
specifier|private
name|boolean
name|noAccessFound
init|=
literal|false
decl_stmt|;
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|"JSTOR.html"
return|;
block|}
annotation|@
name|Override
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"JSTOR"
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
comment|// No Options panel
return|return
literal|null
return|;
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
literal|"JSTOR"
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
name|noAccessFound
operator|=
literal|false
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector dialog, OutputPrinter status)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|dialog
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
argument_list|,
name|dialog
argument_list|,
name|status
argument_list|)
decl_stmt|;
comment|//System.out.println("JSTORFetcher2 processQuery within list");
if|if
condition|(
name|citations
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|//System.out.println("JSTORFetcher2 processQuery after false citations=" + citations);
if|if
condition|(
name|citations
operator|.
name|size
argument_list|()
operator|==
literal|0
condition|)
block|{
if|if
condition|(
operator|!
name|noAccessFound
condition|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries found for the search string '%0'"
argument_list|,
name|query
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search JSTOR"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries found. It looks like you do not have access to search JStor."
argument_list|,
name|query
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search JSTOR"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|cit
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
name|cit
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|dialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
name|dialog
operator|.
name|setProgress
argument_list|(
operator|++
name|i
argument_list|,
name|citations
operator|.
name|size
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error while fetching from JSTOR"
argument_list|)
operator|+
literal|": "
operator|+
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      *      * @param query      *            The search term to query JStor for.      * @return a list of IDs      * @throws java.io.IOException      */
DECL|method|getCitations (String query, ImportInspector dialog, OutputPrinter status)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getCitations
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|dialog
parameter_list|,
name|OutputPrinter
name|status
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
name|JSTORFetcher2
operator|.
name|SEARCH_URL
operator|+
name|URLEncoder
operator|.
name|encode
argument_list|(
name|query
argument_list|,
literal|"UTF-8"
argument_list|)
operator|+
name|JSTORFetcher2
operator|.
name|SEARCH_URL_END
expr_stmt|;
name|int
name|count
init|=
literal|1
decl_stmt|;
name|String
index|[]
name|numberOfRefs
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|int
name|refsRequested
decl_stmt|;
name|int
name|numberOfPagesRequested
init|=
name|JSTORFetcher2
operator|.
name|MAX_PAGES_TO_LOAD
decl_stmt|;
name|String
name|nextPage
decl_stmt|;
while|while
condition|(
operator|(
name|count
operator|<=
name|Math
operator|.
name|min
argument_list|(
name|JSTORFetcher2
operator|.
name|MAX_PAGES_TO_LOAD
argument_list|,
name|numberOfPagesRequested
argument_list|)
operator|)
operator|&&
operator|(
operator|(
name|nextPage
operator|=
name|getCitationsFromUrl
argument_list|(
name|urlQuery
argument_list|,
name|ids
argument_list|,
name|count
argument_list|,
name|numberOfRefs
argument_list|,
name|dialog
argument_list|,
name|status
argument_list|)
operator|)
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// If user has cancelled the import, return null to signal this:
if|if
condition|(
operator|(
name|count
operator|==
literal|1
operator|)
operator|&&
operator|(
name|nextPage
operator|.
name|equals
argument_list|(
name|JSTORFetcher2
operator|.
name|CANCELLED
argument_list|)
operator|)
condition|)
block|{
return|return
literal|null
return|;
block|}
comment|//System.out.println("JSTORFetcher2 getCitations numberofrefs=" + numberOfRefs[0]);
comment|//System.out.println("JSTORFetcher2 getCitations numberofrefs=" + " refsRequested=" + numberOfRefs[1]);
name|refsRequested
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|numberOfRefs
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
comment|//System.out.println("JSTORFetcher2 getCitations refsRequested=" + Integer.valueOf(refsRequested));
name|numberOfPagesRequested
operator|=
operator|(
operator|(
operator|(
name|refsRequested
operator|-
literal|1
operator|)
operator|-
operator|(
operator|(
name|refsRequested
operator|-
literal|1
operator|)
operator|%
name|JSTORFetcher2
operator|.
name|REFS_PER_PAGE
operator|)
operator|)
operator|/
name|JSTORFetcher2
operator|.
name|REFS_PER_PAGE
operator|)
operator|+
literal|1
expr_stmt|;
comment|//System.out.println("JSTORFetcher2 getCitations numberOfPagesRequested=" + Integer.valueOf(numberOfPagesRequested));
name|urlQuery
operator|=
name|nextPage
expr_stmt|;
comment|//System.out.println("JSTORFetcher2 getcitations count=" + Integer.valueOf(count) + " ids=" + ids);
name|count
operator|++
expr_stmt|;
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
DECL|method|getCitationsFromUrl (String urlQuery, List<String> ids, int count, String[] numberOfRefs, ImportInspector dialog, OutputPrinter status)
specifier|private
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
parameter_list|,
name|int
name|count
parameter_list|,
name|String
index|[]
name|numberOfRefs
parameter_list|,
name|ImportInspector
name|dialog
parameter_list|,
name|OutputPrinter
name|status
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
name|String
name|cont
init|=
name|ud
operator|.
name|downloadToString
argument_list|()
decl_stmt|;
name|String
name|entirePage
init|=
name|cont
decl_stmt|;
name|String
name|pageEntire
init|=
name|cont
decl_stmt|;
name|int
name|countOfRefs
init|=
literal|0
decl_stmt|;
name|int
name|refsRequested
decl_stmt|;
if|if
condition|(
name|count
operator|==
literal|1
condition|)
block|{
comment|//  Readin the numberofhits (only once)
name|Matcher
name|mn
init|=
name|JSTORFetcher2
operator|.
name|numberofhits
operator|.
name|matcher
argument_list|(
name|pageEntire
argument_list|)
decl_stmt|;
if|if
condition|(
name|mn
operator|.
name|find
argument_list|()
condition|)
block|{
comment|//System.out.println("JSTORFetcher2 getCitationsFromUrl numberofhits=" + mn.group(1));
name|numberOfRefs
index|[
literal|0
index|]
operator|=
name|mn
operator|.
name|group
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|countOfRefs
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|numberOfRefs
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|//System.out.println("JSTORFetcher2 getCitationsFromUrl numberofrefs[0]=" + Integer.valueOf(numberOfRefs[0]));
block|}
else|else
block|{
comment|//System.out.println("JSTORFetcher2 getCitationsFromUrl cant find numberofhits=");
name|numberOfRefs
index|[
literal|0
index|]
operator|=
literal|"0"
expr_stmt|;
block|}
while|while
condition|(
literal|true
condition|)
block|{
name|String
name|strCount
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"References found"
argument_list|)
operator|+
literal|": "
operator|+
name|countOfRefs
operator|+
literal|"  "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Number of references to fetch?"
argument_list|)
argument_list|,
name|Integer
operator|.
name|toString
argument_list|(
name|countOfRefs
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|strCount
operator|==
literal|null
condition|)
block|{
name|status
operator|.
name|setStatus
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"JSTOR import cancelled"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|JSTORFetcher2
operator|.
name|CANCELLED
return|;
block|}
try|try
block|{
name|numberOfRefs
index|[
literal|1
index|]
operator|=
name|strCount
operator|.
name|trim
argument_list|()
expr_stmt|;
name|refsRequested
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|numberOfRefs
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
break|break;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|ex
parameter_list|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please enter a valid number"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|countOfRefs
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|numberOfRefs
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|refsRequested
operator|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|numberOfRefs
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|Matcher
name|m
init|=
name|JSTORFetcher2
operator|.
name|idPattern
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
operator|&&
operator|(
operator|(
name|ids
operator|.
name|size
argument_list|()
operator|+
literal|1
operator|)
operator|<=
name|refsRequested
operator|)
condition|)
block|{
do|do
block|{
name|ids
operator|.
name|add
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|cont
operator|=
name|cont
operator|.
name|substring
argument_list|(
name|m
operator|.
name|end
argument_list|()
argument_list|)
expr_stmt|;
name|m
operator|=
name|JSTORFetcher2
operator|.
name|idPattern
operator|.
name|matcher
argument_list|(
name|cont
argument_list|)
expr_stmt|;
block|}
do|while
condition|(
name|m
operator|.
name|find
argument_list|()
operator|&&
operator|(
operator|(
name|ids
operator|.
name|size
argument_list|()
operator|+
literal|1
operator|)
operator|<=
name|refsRequested
operator|)
condition|)
do|;
block|}
elseif|else
if|if
condition|(
name|entirePage
operator|.
name|contains
argument_list|(
name|JSTORFetcher2
operator|.
name|noAccessIndicator
argument_list|)
condition|)
block|{
name|noAccessFound
operator|=
literal|true
expr_stmt|;
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
name|m
operator|=
name|JSTORFetcher2
operator|.
name|nextPagePattern
operator|.
name|matcher
argument_list|(
name|entirePage
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
return|return
name|JSTORFetcher2
operator|.
name|JSTOR_URL
operator|+
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
DECL|method|getSingleCitation (String cit)
specifier|private
name|BibtexEntry
name|getSingleCitation
parameter_list|(
name|String
name|cit
parameter_list|)
block|{
return|return
name|BibsonomyScraper
operator|.
name|getEntry
argument_list|(
name|JSTORFetcher2
operator|.
name|SINGLE_CIT_ENC
operator|+
name|cit
argument_list|)
return|;
block|}
block|}
end_class

end_unit

