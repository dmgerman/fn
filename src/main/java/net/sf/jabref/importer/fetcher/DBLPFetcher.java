begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015-2016 JabRef contributors.     Copyright (C) 2011 Sascha Hunold.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Collection
import|;
end_import

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
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|help
operator|.
name|HelpFiles
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
name|DuplicateCheck
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
DECL|class|DBLPFetcher
specifier|public
class|class
name|DBLPFetcher
implements|implements
name|EntryFetcher
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
name|DBLPFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|URL_START
specifier|private
specifier|static
specifier|final
name|String
name|URL_START
init|=
literal|"http://www.dblp.org/search/api/"
decl_stmt|;
DECL|field|URL_PART1
specifier|private
specifier|static
specifier|final
name|String
name|URL_PART1
init|=
literal|"?q="
decl_stmt|;
DECL|field|URL_END
specifier|private
specifier|static
specifier|final
name|String
name|URL_END
init|=
literal|"&h=1000&c=4&f=0&format=json"
decl_stmt|;
DECL|field|shouldContinue
specifier|private
specifier|volatile
name|boolean
name|shouldContinue
decl_stmt|;
DECL|field|query
specifier|private
name|String
name|query
decl_stmt|;
DECL|field|helper
specifier|private
specifier|final
name|DBLPHelper
name|helper
init|=
operator|new
name|DBLPHelper
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|stopFetching ()
specifier|public
name|void
name|stopFetching
parameter_list|()
block|{
name|shouldContinue
operator|=
literal|false
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String newQuery, ImportInspector inspector, OutputPrinter status)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|newQuery
parameter_list|,
name|ImportInspector
name|inspector
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|Boolean
argument_list|>
name|bibentryKnown
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|res
init|=
literal|false
decl_stmt|;
name|this
operator|.
name|query
operator|=
name|newQuery
expr_stmt|;
name|shouldContinue
operator|=
literal|true
expr_stmt|;
comment|// we save the duplicate check threshold
comment|// we need to overcome the "smart" approach of this heuristic
comment|// and we will set it back afterwards, so maybe someone is happy again
name|double
name|saveThreshold
init|=
name|DuplicateCheck
operator|.
name|duplicateThreshold
decl_stmt|;
try|try
block|{
name|String
name|address
init|=
name|makeSearchURL
argument_list|()
decl_stmt|;
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|address
argument_list|)
decl_stmt|;
name|URLDownload
name|dl
init|=
operator|new
name|URLDownload
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|String
name|page
init|=
name|dl
operator|.
name|downloadToString
argument_list|()
decl_stmt|;
name|String
index|[]
name|lines
init|=
name|page
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|bibtexUrlList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|line
range|:
name|lines
control|)
block|{
if|if
condition|(
name|line
operator|.
name|startsWith
argument_list|(
literal|"\"url\""
argument_list|)
condition|)
block|{
name|String
name|addr
init|=
name|line
operator|.
name|replace
argument_list|(
literal|"\"url\":\""
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|addr
operator|=
name|addr
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|addr
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|)
expr_stmt|;
name|bibtexUrlList
operator|.
name|add
argument_list|(
name|addr
argument_list|)
expr_stmt|;
block|}
block|}
name|DuplicateCheck
operator|.
name|duplicateThreshold
operator|=
name|Double
operator|.
name|MAX_VALUE
expr_stmt|;
comment|// 2014-11-08
comment|// DBLP now shows the BibTeX entry using ugly HTML entities
comment|// but they also offer the download of a bib file
comment|// we find this in the page which we get from "url"
comment|// and this bib file is then in "biburl"
name|int
name|count
init|=
literal|1
decl_stmt|;
for|for
control|(
name|String
name|urlStr
range|:
name|bibtexUrlList
control|)
block|{
if|if
condition|(
operator|!
name|shouldContinue
condition|)
block|{
break|break;
block|}
specifier|final
name|URL
name|bibUrl
init|=
operator|new
name|URL
argument_list|(
name|urlStr
argument_list|)
decl_stmt|;
specifier|final
name|String
name|bibtexHTMLPage
init|=
operator|new
name|URLDownload
argument_list|(
name|bibUrl
argument_list|)
operator|.
name|downloadToString
argument_list|()
decl_stmt|;
specifier|final
name|String
index|[]
name|htmlLines
init|=
name|bibtexHTMLPage
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|line
range|:
name|htmlLines
control|)
block|{
if|if
condition|(
name|line
operator|.
name|contains
argument_list|(
literal|"biburl"
argument_list|)
condition|)
block|{
name|int
name|sidx
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|'{'
argument_list|)
decl_stmt|;
name|int
name|eidx
init|=
name|line
operator|.
name|indexOf
argument_list|(
literal|'}'
argument_list|)
decl_stmt|;
comment|// now we take everything within the curly braces
name|String
name|bibtexUrl
init|=
name|line
operator|.
name|substring
argument_list|(
name|sidx
operator|+
literal|1
argument_list|,
name|eidx
argument_list|)
decl_stmt|;
comment|// we do not access dblp.uni-trier.de as they will complain
name|bibtexUrl
operator|=
name|bibtexUrl
operator|.
name|replace
argument_list|(
literal|"dblp.uni-trier.de"
argument_list|,
literal|"www.dblp.org"
argument_list|)
expr_stmt|;
specifier|final
name|URL
name|bibFileURL
init|=
operator|new
name|URL
argument_list|(
name|bibtexUrl
argument_list|)
decl_stmt|;
specifier|final
name|String
name|bibtexPage
init|=
operator|new
name|URLDownload
argument_list|(
name|bibFileURL
argument_list|)
operator|.
name|downloadToString
argument_list|()
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|bibtexEntries
init|=
name|BibtexParser
operator|.
name|fromString
argument_list|(
name|bibtexPage
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|be
range|:
name|bibtexEntries
control|)
block|{
if|if
condition|(
operator|!
name|bibentryKnown
operator|.
name|containsKey
argument_list|(
name|be
operator|.
name|getCiteKey
argument_list|()
argument_list|)
condition|)
block|{
name|inspector
operator|.
name|addEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|bibentryKnown
operator|.
name|put
argument_list|(
name|be
operator|.
name|getCiteKey
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
name|inspector
operator|.
name|setProgress
argument_list|(
name|count
argument_list|,
name|bibtexUrlList
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
comment|// everything went smooth
name|res
operator|=
literal|true
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
literal|"Communcation problems"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|status
operator|.
name|showMessage
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|// Restore the threshold
name|DuplicateCheck
operator|.
name|duplicateThreshold
operator|=
name|saveThreshold
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
DECL|method|makeSearchURL ()
specifier|private
name|String
name|makeSearchURL
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|DBLPFetcher
operator|.
name|URL_START
argument_list|)
operator|.
name|append
argument_list|(
name|DBLPFetcher
operator|.
name|URL_PART1
argument_list|)
decl_stmt|;
name|String
name|cleanedQuery
init|=
name|helper
operator|.
name|cleanDBLPQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|cleanedQuery
argument_list|)
operator|.
name|append
argument_list|(
name|DBLPFetcher
operator|.
name|URL_END
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
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
literal|"DBLP"
return|;
block|}
annotation|@
name|Override
DECL|method|getHelpPage ()
specifier|public
name|HelpFiles
name|getHelpPage
parameter_list|()
block|{
return|return
name|HelpFiles
operator|.
name|FETCHER_DBLP
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
block|}
end_class

end_unit

