begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 Aaron Chen     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|java
operator|.
name|awt
operator|.
name|GridLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

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
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
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
name|InputStream
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
name|ConnectException
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
name|Collection
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
name|ButtonGroup
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
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
name|javax
operator|.
name|swing
operator|.
name|JRadioButton
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

begin_class
DECL|class|ACMPortalFetcher
specifier|public
class|class
name|ACMPortalFetcher
implements|implements
name|EntryFetcher
block|{
DECL|field|dialog
name|ImportInspector
name|dialog
init|=
literal|null
decl_stmt|;
DECL|field|status
name|OutputPrinter
name|status
decl_stmt|;
DECL|field|htmlConverter
specifier|final
name|HTMLConverter
name|htmlConverter
init|=
operator|new
name|HTMLConverter
argument_list|()
decl_stmt|;
DECL|field|terms
specifier|private
name|String
name|terms
decl_stmt|;
DECL|field|startUrl
name|String
name|startUrl
init|=
literal|"http://portal.acm.org/"
decl_stmt|;
DECL|field|searchUrlPart
name|String
name|searchUrlPart
init|=
literal|"results.cfm?query="
decl_stmt|;
DECL|field|searchUrlPartII
name|String
name|searchUrlPartII
init|=
literal|"&dl="
decl_stmt|;
DECL|field|endUrl
name|String
name|endUrl
init|=
literal|"&coll=Portal&short=0"
decl_stmt|;
comment|//&start=";
DECL|field|acmButton
specifier|private
name|JRadioButton
name|acmButton
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The ACM Digital Library"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|guideButton
specifier|private
name|JRadioButton
name|guideButton
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The Guide to Computing Literature"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|absCheckBox
specifier|private
name|JCheckBox
name|absCheckBox
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Include abstracts"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|MAX_FETCH
specifier|private
specifier|static
specifier|final
name|int
name|MAX_FETCH
init|=
literal|20
decl_stmt|;
comment|// 20 when short=0
DECL|field|perPage
DECL|field|hits
DECL|field|unparseable
DECL|field|parsed
specifier|private
name|int
name|perPage
init|=
name|MAX_FETCH
decl_stmt|,
name|hits
init|=
literal|0
decl_stmt|,
name|unparseable
init|=
literal|0
decl_stmt|,
name|parsed
init|=
literal|0
decl_stmt|;
DECL|field|shouldContinue
specifier|private
name|boolean
name|shouldContinue
init|=
literal|false
decl_stmt|;
DECL|field|fetchAbstract
specifier|private
name|boolean
name|fetchAbstract
init|=
literal|false
decl_stmt|;
DECL|field|acmOrGuide
specifier|private
name|boolean
name|acmOrGuide
init|=
literal|false
decl_stmt|;
DECL|field|hitsPattern
name|Pattern
name|hitsPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*Found<b>(\\d+,*\\d*)</b> of.*"
argument_list|)
decl_stmt|;
DECL|field|maxHitsPattern
name|Pattern
name|maxHitsPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*Results \\d+ - \\d+ of (\\d+,*\\d*).*"
argument_list|)
decl_stmt|;
DECL|field|bibPattern
name|Pattern
name|bibPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*(popBibTex.cfm.*)','BibTex'.*"
argument_list|)
decl_stmt|;
DECL|field|absPattern
name|Pattern
name|absPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*ABSTRACT</A></span>\\s+<p class=\"abstract\">\\s+(.*)"
argument_list|)
decl_stmt|;
DECL|field|fullCitationPattern
name|Pattern
name|fullCitationPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<A HREF=\"(citation.cfm.*)\" class.*"
argument_list|)
decl_stmt|;
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
block|{
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|guideButton
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ButtonGroup
name|group
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|add
argument_list|(
name|acmButton
argument_list|)
expr_stmt|;
name|group
operator|.
name|add
argument_list|(
name|guideButton
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|absCheckBox
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|acmButton
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|guideButton
argument_list|)
expr_stmt|;
return|return
name|pan
return|;
block|}
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
name|this
operator|.
name|dialog
operator|=
name|dialog
expr_stmt|;
name|this
operator|.
name|status
operator|=
name|status
expr_stmt|;
name|this
operator|.
name|terms
operator|=
name|query
expr_stmt|;
name|piv
operator|=
literal|0
expr_stmt|;
name|shouldContinue
operator|=
literal|true
expr_stmt|;
name|parsed
operator|=
literal|0
expr_stmt|;
name|unparseable
operator|=
literal|0
expr_stmt|;
name|acmOrGuide
operator|=
name|acmButton
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|String
name|address
init|=
name|makeUrl
argument_list|(
literal|0
argument_list|)
decl_stmt|;
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|address
argument_list|)
decl_stmt|;
comment|//dialog.setVisible(true);
name|String
name|page
init|=
name|getResults
argument_list|(
name|url
argument_list|)
decl_stmt|;
comment|//System.out.println(address);
name|hits
operator|=
name|getNumberOfHits
argument_list|(
name|page
argument_list|,
literal|"Found"
argument_list|,
name|hitsPattern
argument_list|)
expr_stmt|;
name|int
name|index
init|=
name|page
operator|.
name|indexOf
argument_list|(
literal|"Found"
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|page
operator|=
name|page
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|5
argument_list|)
expr_stmt|;
name|index
operator|=
name|page
operator|.
name|indexOf
argument_list|(
literal|"Found"
argument_list|)
expr_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
name|page
operator|=
name|page
operator|.
name|substring
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
comment|//System.out.println(page);
comment|//System.out.printf("Hit %d\n", hits);
if|if
condition|(
name|hits
operator|==
literal|0
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
name|terms
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search ACM Portal"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
name|int
name|maxHits
init|=
name|getNumberOfHits
argument_list|(
name|page
argument_list|,
literal|"Results"
argument_list|,
name|maxHitsPattern
argument_list|)
decl_stmt|;
comment|//System.out.printf("maxHit %d\n", maxHits);
comment|//String page = getResultsFromFile(new File("/home/alver/div/temp50.txt"));
comment|//List entries = new ArrayList();
comment|//System.out.println("Number of hits: "+hits);
comment|//System.out.println("Maximum returned: "+maxHits);
if|if
condition|(
name|hits
operator|>
name|maxHits
condition|)
name|hits
operator|=
name|maxHits
expr_stmt|;
if|if
condition|(
name|hits
operator|>
name|MAX_FETCH
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
literal|"%0 entries found. To reduce server load, "
operator|+
literal|"only %1 will be downloaded. It will be very slow, in order to make ACM happy."
argument_list|,
operator|new
name|String
index|[]
block|{
name|String
operator|.
name|valueOf
argument_list|(
name|hits
argument_list|)
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|MAX_FETCH
argument_list|)
block|}
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search ACM Portal"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|hits
operator|=
name|MAX_FETCH
expr_stmt|;
block|}
name|fetchAbstract
operator|=
name|absCheckBox
operator|.
name|isSelected
argument_list|()
expr_stmt|;
comment|//parse(dialog, page, 0, 51);
comment|//dialog.setProgress(perPage/2, hits);
name|parse
argument_list|(
name|dialog
argument_list|,
name|page
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
comment|//System.out.println(page);
name|int
name|firstEntry
init|=
name|perPage
decl_stmt|;
while|while
condition|(
name|shouldContinue
operator|&&
operator|(
name|firstEntry
operator|<
name|hits
operator|)
condition|)
block|{
comment|//System.out.println("Fetching from: "+firstEntry);
name|address
operator|=
name|makeUrl
argument_list|(
name|firstEntry
argument_list|)
expr_stmt|;
comment|//System.out.println(address);
name|page
operator|=
name|getResults
argument_list|(
operator|new
name|URL
argument_list|(
name|address
argument_list|)
argument_list|)
expr_stmt|;
comment|//dialog.setProgress(firstEntry+perPage/2, hits);
if|if
condition|(
operator|!
name|shouldContinue
condition|)
break|break;
name|parse
argument_list|(
name|dialog
argument_list|,
name|page
argument_list|,
literal|0
argument_list|,
literal|1
operator|+
name|firstEntry
argument_list|)
expr_stmt|;
name|firstEntry
operator|+=
name|perPage
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ConnectException
name|e
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
literal|"Connection to ACM Portal failed"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search ACM Portal"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
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
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search ACM Portal"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|makeUrl (int startIndex)
specifier|private
name|String
name|makeUrl
parameter_list|(
name|int
name|startIndex
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|(
name|startUrl
argument_list|)
operator|.
name|append
argument_list|(
name|searchUrlPart
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|terms
operator|.
name|replaceAll
argument_list|(
literal|" "
argument_list|,
literal|"%20"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|searchUrlPartII
argument_list|)
expr_stmt|;
if|if
condition|(
name|acmOrGuide
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"ACM"
argument_list|)
expr_stmt|;
else|else
name|sb
operator|.
name|append
argument_list|(
literal|"GUIDE"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|endUrl
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|field|piv
name|int
name|piv
init|=
literal|0
decl_stmt|;
DECL|method|parse (ImportInspector dialog, String text, int startIndex, int firstEntryNumber)
specifier|private
name|void
name|parse
parameter_list|(
name|ImportInspector
name|dialog
parameter_list|,
name|String
name|text
parameter_list|,
name|int
name|startIndex
parameter_list|,
name|int
name|firstEntryNumber
parameter_list|)
block|{
name|piv
operator|=
name|startIndex
expr_stmt|;
name|int
name|entryNumber
init|=
name|firstEntryNumber
decl_stmt|;
name|BibtexEntry
name|entry
decl_stmt|;
while|while
condition|(
operator|(
operator|(
name|entry
operator|=
name|parseNextEntry
argument_list|(
name|text
argument_list|,
name|piv
argument_list|,
name|entryNumber
argument_list|)
operator|)
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|shouldContinue
operator|)
condition|)
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
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
name|dialog
operator|.
name|setProgress
argument_list|(
name|parsed
operator|+
name|unparseable
argument_list|,
name|hits
argument_list|)
expr_stmt|;
name|parsed
operator|++
expr_stmt|;
block|}
name|entryNumber
operator|++
expr_stmt|;
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|e
operator|.
name|getStackTrace
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|parseEntryBibTeX (String fullCitation, boolean abs)
specifier|private
name|BibtexEntry
name|parseEntryBibTeX
parameter_list|(
name|String
name|fullCitation
parameter_list|,
name|boolean
name|abs
parameter_list|)
throws|throws
name|IOException
block|{
name|URL
name|url
decl_stmt|;
try|try
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
name|startUrl
operator|+
name|fullCitation
argument_list|)
expr_stmt|;
name|String
name|page
init|=
name|getResults
argument_list|(
name|url
argument_list|)
decl_stmt|;
name|Thread
operator|.
name|sleep
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
name|Matcher
name|bibtexAddr
init|=
name|bibPattern
operator|.
name|matcher
argument_list|(
name|page
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibtexAddr
operator|.
name|find
argument_list|()
condition|)
block|{
name|URL
name|bibtexUrl
init|=
operator|new
name|URL
argument_list|(
name|startUrl
operator|+
name|bibtexAddr
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|bibtexUrl
operator|.
name|openStream
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|in
argument_list|)
decl_stmt|;
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|item
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibtexEntry
name|entry
init|=
name|item
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|abs
operator|==
literal|true
condition|)
block|{
name|Matcher
name|absMatch
init|=
name|absPattern
operator|.
name|matcher
argument_list|(
name|page
argument_list|)
decl_stmt|;
if|if
condition|(
name|absMatch
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|absBlock
init|=
name|absMatch
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
literal|"abstract"
argument_list|,
name|convertHTMLChars
argument_list|(
name|absBlock
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"No abstract matched."
argument_list|)
expr_stmt|;
comment|//System.out.println(page);
block|}
block|}
name|Thread
operator|.
name|sleep
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
return|return
name|entry
return|;
block|}
else|else
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|ConnectException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
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
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|parseNextEntry (String allText, int startIndex, int entryNumber)
specifier|private
name|BibtexEntry
name|parseNextEntry
parameter_list|(
name|String
name|allText
parameter_list|,
name|int
name|startIndex
parameter_list|,
name|int
name|entryNumber
parameter_list|)
block|{
name|String
name|toFind
init|=
operator|new
name|StringBuffer
argument_list|()
operator|.
name|append
argument_list|(
literal|"<strong>"
argument_list|)
operator|.
name|append
argument_list|(
name|entryNumber
argument_list|)
operator|.
name|append
argument_list|(
literal|"</strong>"
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|int
name|index
init|=
name|allText
operator|.
name|indexOf
argument_list|(
name|toFind
argument_list|,
name|startIndex
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|allText
operator|.
name|indexOf
argument_list|(
literal|"</table>"
argument_list|,
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
comment|//if (endIndex< 0)
name|endIndex
operator|=
name|allText
operator|.
name|length
argument_list|()
expr_stmt|;
name|BibtexEntry
name|entry
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|piv
operator|=
name|index
operator|+
literal|1
expr_stmt|;
name|String
name|text
init|=
name|allText
operator|.
name|substring
argument_list|(
name|index
argument_list|,
name|endIndex
argument_list|)
decl_stmt|;
comment|// Always try RIS import first
name|Matcher
name|fullCitation
init|=
name|fullCitationPattern
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|fullCitation
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
name|Thread
operator|.
name|sleep
argument_list|(
literal|10000
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
name|entry
operator|=
name|parseEntryBibTeX
argument_list|(
name|fullCitation
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|,
name|fetchAbstract
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
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
else|else
block|{
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
literal|"Citation Unmatched %d\n"
argument_list|,
name|entryNumber
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|printf
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
comment|// fetch successful
return|return
name|entry
return|;
block|}
block|}
comment|//System.out.println(allText);
comment|//System.out.println(toFind);
comment|//System.out.println("Parse Failed");
return|return
literal|null
return|;
block|}
comment|/**      * This method must convert HTML style char sequences to normal characters.      * @param text The text to handle.      * @return The converted text.      */
DECL|method|convertHTMLChars (String text)
specifier|private
name|String
name|convertHTMLChars
parameter_list|(
name|String
name|text
parameter_list|)
block|{
return|return
name|htmlConverter
operator|.
name|format
argument_list|(
name|text
argument_list|)
return|;
block|}
comment|/**      * Find out how many hits were found.      * @param page      */
DECL|method|getNumberOfHits (String page, String marker, Pattern pattern)
specifier|private
name|int
name|getNumberOfHits
parameter_list|(
name|String
name|page
parameter_list|,
name|String
name|marker
parameter_list|,
name|Pattern
name|pattern
parameter_list|)
throws|throws
name|IOException
block|{
name|int
name|ind
init|=
name|page
operator|.
name|indexOf
argument_list|(
name|marker
argument_list|)
decl_stmt|;
if|if
condition|(
name|ind
operator|<
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|page
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not parse number of hits"
argument_list|)
argument_list|)
throw|;
block|}
name|String
name|substring
init|=
name|page
operator|.
name|substring
argument_list|(
name|ind
argument_list|,
name|Math
operator|.
name|min
argument_list|(
name|ind
operator|+
literal|42
argument_list|,
name|page
operator|.
name|length
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|Matcher
name|m
init|=
name|pattern
operator|.
name|matcher
argument_list|(
name|substring
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Unmatched!"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|substring
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
comment|// get rid of ,
name|String
name|number
init|=
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|//NumberFormat nf = NumberFormat.getInstance();
comment|//return nf.parse(number).intValue();
name|number
operator|=
name|number
operator|.
name|replaceAll
argument_list|(
literal|","
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|//System.out.println(number);
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|number
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not parse number of hits"
argument_list|)
argument_list|)
throw|;
block|}
catch|catch
parameter_list|(
name|IllegalStateException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not parse number of hits"
argument_list|)
argument_list|)
throw|;
block|}
block|}
throw|throw
operator|new
name|IOException
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not parse number of hits"
argument_list|)
argument_list|)
throw|;
block|}
comment|/**      * Download the URL and return contents as a String.      * @param source      * @return      * @throws IOException      */
DECL|method|getResults (URL source)
specifier|public
name|String
name|getResults
parameter_list|(
name|URL
name|source
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|in
init|=
name|source
operator|.
name|openStream
argument_list|()
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|256
index|]
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|bytesRead
init|=
name|in
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
decl_stmt|;
if|if
condition|(
name|bytesRead
operator|==
operator|-
literal|1
condition|)
break|break;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bytesRead
condition|;
name|i
operator|++
control|)
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|buffer
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Read results from a file instead of an URL. Just for faster debugging.      * @param f      * @return      * @throws IOException      */
DECL|method|getResultsFromFile (File f)
specifier|public
name|String
name|getResultsFromFile
parameter_list|(
name|File
name|f
parameter_list|)
throws|throws
name|IOException
block|{
name|InputStream
name|in
init|=
operator|new
name|BufferedInputStream
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|f
argument_list|)
argument_list|)
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|byte
index|[]
name|buffer
init|=
operator|new
name|byte
index|[
literal|256
index|]
decl_stmt|;
while|while
condition|(
literal|true
condition|)
block|{
name|int
name|bytesRead
init|=
name|in
operator|.
name|read
argument_list|(
name|buffer
argument_list|)
decl_stmt|;
if|if
condition|(
name|bytesRead
operator|==
operator|-
literal|1
condition|)
break|break;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|bytesRead
condition|;
name|i
operator|++
control|)
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|buffer
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Search ACM Portal"
argument_list|)
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
literal|"ACMPortalHelp.html"
return|;
block|}
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
block|{
return|return
literal|"Search ACM Portal"
return|;
block|}
comment|// This method is called by the dialog when the user has cancelled the import.
DECL|method|cancelled ()
specifier|public
name|void
name|cancelled
parameter_list|()
block|{
name|shouldContinue
operator|=
literal|false
expr_stmt|;
block|}
comment|// This method is called by the dialog when the user has selected the
comment|//wanted entries, and clicked Ok. The callback object can update status
comment|//line etc.
DECL|method|done (int entriesImported)
specifier|public
name|void
name|done
parameter_list|(
name|int
name|entriesImported
parameter_list|)
block|{
comment|//System.out.println("Number of entries parsed: "+parsed);
comment|//System.out.println("Parsing failed for "+unparseable+" entries");
block|}
comment|// This method is called by the dialog when the user has cancelled or
comment|//signalled a stop. It is expected that any long-running fetch operations
comment|//will stop after this method is called.
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
block|}
end_class

end_unit

