begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|BufferedReader
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
name|URL
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

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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
name|GUIGlobals
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
name|fileformat
operator|.
name|MedlineImporter
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
name|model
operator|.
name|entry
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * Fetch or search from Pubmed http://www.ncbi.nlm.nih.gov/sites/entrez/  *  */
end_comment

begin_class
DECL|class|MedlineFetcher
specifier|public
class|class
name|MedlineFetcher
implements|implements
name|EntryFetcher
block|{
DECL|class|SearchResult
class|class
name|SearchResult
block|{
DECL|field|count
specifier|public
name|int
name|count
decl_stmt|;
DECL|field|retmax
specifier|public
name|int
name|retmax
decl_stmt|;
DECL|field|retstart
specifier|public
name|int
name|retstart
decl_stmt|;
DECL|field|ids
specifier|public
name|String
name|ids
init|=
literal|""
decl_stmt|;
DECL|method|addID (String id)
specifier|public
name|void
name|addID
parameter_list|(
name|String
name|id
parameter_list|)
block|{
if|if
condition|(
name|ids
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|ids
operator|=
name|id
expr_stmt|;
block|}
else|else
block|{
name|ids
operator|+=
literal|","
operator|+
name|id
expr_stmt|;
block|}
block|}
block|}
comment|/**      * How many entries to query in one request      */
DECL|field|PACING
specifier|private
specifier|static
specifier|final
name|int
name|PACING
init|=
literal|20
decl_stmt|;
DECL|field|shouldContinue
specifier|private
name|boolean
name|shouldContinue
decl_stmt|;
DECL|field|frame
name|OutputPrinter
name|frame
decl_stmt|;
DECL|field|dialog
name|ImportInspector
name|dialog
decl_stmt|;
DECL|method|toSearchTerm (String in)
specifier|private
specifier|static
name|String
name|toSearchTerm
parameter_list|(
name|String
name|in
parameter_list|)
block|{
name|Pattern
name|part1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|", "
argument_list|)
decl_stmt|;
name|Pattern
name|part2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|","
argument_list|)
decl_stmt|;
name|Pattern
name|part3
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
name|Matcher
name|matcher
decl_stmt|;
name|matcher
operator|=
name|part1
operator|.
name|matcher
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"\\+AND\\+"
argument_list|)
expr_stmt|;
name|matcher
operator|=
name|part2
operator|.
name|matcher
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"\\+AND\\+"
argument_list|)
expr_stmt|;
name|matcher
operator|=
name|part3
operator|.
name|matcher
argument_list|(
name|in
argument_list|)
expr_stmt|;
name|in
operator|=
name|matcher
operator|.
name|replaceAll
argument_list|(
literal|"+"
argument_list|)
expr_stmt|;
return|return
name|in
return|;
block|}
comment|/**      * Gets the initial list of ids      */
DECL|method|getIds (String term, int start, int pacing)
specifier|private
name|SearchResult
name|getIds
parameter_list|(
name|String
name|term
parameter_list|,
name|int
name|start
parameter_list|,
name|int
name|pacing
parameter_list|)
block|{
name|String
name|baseUrl
init|=
literal|"http://eutils.ncbi.nlm.nih.gov/entrez/eutils"
decl_stmt|;
name|String
name|medlineUrl
init|=
name|baseUrl
operator|+
literal|"/esearch.fcgi?db=pubmed&retmax="
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|pacing
argument_list|)
operator|+
literal|"&retstart="
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|start
argument_list|)
operator|+
literal|"&term="
decl_stmt|;
name|Pattern
name|idPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<Id>(\\d+)</Id>"
argument_list|)
decl_stmt|;
name|Pattern
name|countPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<Count>(\\d+)<\\/Count>"
argument_list|)
decl_stmt|;
name|Pattern
name|retMaxPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<RetMax>(\\d+)<\\/RetMax>"
argument_list|)
decl_stmt|;
name|Pattern
name|retStartPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<RetStart>(\\d+)<\\/RetStart>"
argument_list|)
decl_stmt|;
name|boolean
name|doCount
init|=
literal|true
decl_stmt|;
name|SearchResult
name|result
init|=
operator|new
name|SearchResult
argument_list|()
decl_stmt|;
try|try
block|{
name|URL
name|ncbi
init|=
operator|new
name|URL
argument_list|(
name|medlineUrl
operator|+
name|term
argument_list|)
decl_stmt|;
comment|// get the ids
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|ncbi
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|inLine
decl_stmt|;
while|while
condition|(
operator|(
name|inLine
operator|=
name|in
operator|.
name|readLine
argument_list|()
operator|)
operator|!=
literal|null
condition|)
block|{
comment|// get the count
name|Matcher
name|idMatcher
init|=
name|idPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
decl_stmt|;
if|if
condition|(
name|idMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|addID
argument_list|(
name|idMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Matcher
name|retMaxMatcher
init|=
name|retMaxPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
decl_stmt|;
if|if
condition|(
name|retMaxMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|retmax
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|retMaxMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Matcher
name|retStartMatcher
init|=
name|retStartPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
decl_stmt|;
if|if
condition|(
name|retStartMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|retstart
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|retStartMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Matcher
name|countMatcher
init|=
name|countPattern
operator|.
name|matcher
argument_list|(
name|inLine
argument_list|)
decl_stmt|;
if|if
condition|(
name|doCount
operator|&&
name|countMatcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|result
operator|.
name|count
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|countMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|doCount
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
comment|// new URL() failed
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"bad url"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// openConnection() failed
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"connection failed"
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
name|result
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
name|shouldContinue
operator|=
literal|false
expr_stmt|;
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
name|GUIGlobals
operator|.
name|medlineHelp
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
comment|// No Option Panel
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
literal|"Medline"
return|;
block|}
annotation|@
name|Override
DECL|method|processQuery (String query, ImportInspector iIDialog, OutputPrinter frameOP)
specifier|public
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|iIDialog
parameter_list|,
name|OutputPrinter
name|frameOP
parameter_list|)
block|{
name|shouldContinue
operator|=
literal|true
expr_stmt|;
name|query
operator|=
name|query
operator|.
name|trim
argument_list|()
operator|.
name|replace
argument_list|(
literal|';'
argument_list|,
literal|','
argument_list|)
expr_stmt|;
if|if
condition|(
name|query
operator|.
name|matches
argument_list|(
literal|"\\d+[,\\d+]*"
argument_list|)
condition|)
block|{
name|frameOP
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Fetching Medline by id..."
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|bibs
init|=
name|MedlineImporter
operator|.
name|fetchMedline
argument_list|(
name|query
argument_list|,
name|frameOP
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|frameOP
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No references found"
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|bibs
control|)
block|{
name|iIDialog
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
if|if
condition|(
operator|!
name|query
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|frameOP
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Fetching Medline by term..."
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|searchTerm
init|=
name|toSearchTerm
argument_list|(
name|query
argument_list|)
decl_stmt|;
comment|// get the ids from entrez
name|SearchResult
name|result
init|=
name|getIds
argument_list|(
name|searchTerm
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|count
operator|==
literal|0
condition|)
block|{
name|frameOP
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No references found"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|int
name|numberToFetch
init|=
name|result
operator|.
name|count
decl_stmt|;
if|if
condition|(
name|numberToFetch
operator|>
name|MedlineFetcher
operator|.
name|PACING
condition|)
block|{
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"References found"
argument_list|)
operator|+
literal|": "
operator|+
name|numberToFetch
operator|+
literal|"  "
operator|+
name|Localization
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
name|numberToFetch
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
name|frameOP
operator|.
name|setStatus
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Medline import canceled"
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
try|try
block|{
name|numberToFetch
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|strCount
operator|.
name|trim
argument_list|()
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
name|frameOP
operator|.
name|showMessage
argument_list|(
name|Localization
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
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|numberToFetch
condition|;
name|i
operator|+=
name|MedlineFetcher
operator|.
name|PACING
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
name|int
name|noToFetch
init|=
name|Math
operator|.
name|min
argument_list|(
name|MedlineFetcher
operator|.
name|PACING
argument_list|,
name|numberToFetch
operator|-
name|i
argument_list|)
decl_stmt|;
comment|// get the ids from entrez
name|result
operator|=
name|getIds
argument_list|(
name|searchTerm
argument_list|,
name|i
argument_list|,
name|noToFetch
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|bibs
init|=
name|MedlineImporter
operator|.
name|fetchMedline
argument_list|(
name|result
operator|.
name|ids
argument_list|,
name|frameOP
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|bibs
control|)
block|{
name|iIDialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
name|iIDialog
operator|.
name|setProgress
argument_list|(
name|i
operator|+
name|noToFetch
argument_list|,
name|numberToFetch
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
name|frameOP
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please enter a comma separated list of Medline IDs (numbers) or search terms."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Input error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
end_class

end_unit

