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
name|javax
operator|.
name|swing
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

begin_class
DECL|class|ACMPortalFetcher
specifier|public
class|class
name|ACMPortalFetcher
implements|implements
name|PreviewEntryFetcher
block|{
DECL|field|dialog
specifier|private
name|ImportInspector
name|dialog
init|=
literal|null
decl_stmt|;
DECL|field|status
specifier|private
name|OutputPrinter
name|status
decl_stmt|;
DECL|field|htmlConverter
specifier|private
specifier|final
name|HTMLConverter
name|htmlConverter
init|=
operator|new
name|HTMLConverter
argument_list|()
decl_stmt|;
DECL|field|caseKeeper
specifier|final
name|CaseKeeper
name|caseKeeper
init|=
operator|new
name|CaseKeeper
argument_list|()
decl_stmt|;
DECL|field|unitFormatter
specifier|final
name|UnitFormatter
name|unitFormatter
init|=
operator|new
name|UnitFormatter
argument_list|()
decl_stmt|;
DECL|field|terms
specifier|private
name|String
name|terms
decl_stmt|;
DECL|field|startUrl
specifier|private
specifier|static
specifier|final
name|String
name|startUrl
init|=
literal|"http://portal.acm.org/"
decl_stmt|;
DECL|field|searchUrlPart
specifier|private
specifier|static
specifier|final
name|String
name|searchUrlPart
init|=
literal|"results.cfm?query="
decl_stmt|;
DECL|field|searchUrlPartII
specifier|private
specifier|static
specifier|final
name|String
name|searchUrlPartII
init|=
literal|"&dl="
decl_stmt|;
DECL|field|endUrl
specifier|private
specifier|static
specifier|final
name|String
name|endUrl
init|=
literal|"&coll=Portal&short=0"
decl_stmt|;
comment|//&start=";
DECL|field|bibtexUrl
specifier|private
specifier|static
specifier|final
name|String
name|bibtexUrl
init|=
literal|"exportformats.cfm?id="
decl_stmt|;
DECL|field|bibtexUrlEnd
specifier|private
specifier|static
specifier|final
name|String
name|bibtexUrlEnd
init|=
literal|"&expformat=bibtex"
decl_stmt|;
DECL|field|abstractUrl
specifier|private
specifier|static
specifier|final
name|String
name|abstractUrl
init|=
literal|"tab_abstract.cfm?id="
decl_stmt|;
DECL|field|acmButton
specifier|private
specifier|final
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
specifier|final
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
specifier|final
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
DECL|field|perPage
specifier|private
specifier|static
specifier|final
name|int
name|perPage
init|=
literal|20
decl_stmt|;
DECL|field|MAX_FETCH
specifier|private
specifier|static
specifier|final
name|int
name|MAX_FETCH
init|=
name|perPage
decl_stmt|;
comment|// only one page. Otherwise, the user will get blocked by ACM. 100 has been the old setting. See Bug 3532752 - https://sourceforge.net/tracker/index.php?func=detail&aid=3532752&group_id=92314&atid=600306
DECL|field|WAIT_TIME
specifier|private
specifier|static
specifier|final
name|int
name|WAIT_TIME
init|=
literal|200
decl_stmt|;
DECL|field|hits
DECL|field|unparseable
DECL|field|parsed
specifier|private
name|int
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
comment|// user settings
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
specifier|private
specifier|static
specifier|final
name|Pattern
name|hitsPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*Found<b>(\\d+,*\\d*)</b>.*"
argument_list|)
decl_stmt|;
DECL|field|maxHitsPattern
specifier|private
specifier|static
specifier|final
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
comment|//private static final Pattern bibPattern = Pattern.compile(".*'(exportformats.cfm\\?id=\\d+&expformat=bibtex)'.*");
DECL|field|fullCitationPattern
specifier|private
specifier|static
specifier|final
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
literal|"citation.cfm\\?id=\\d*\\.?(\\d+)&.*"
argument_list|)
decl_stmt|;
comment|// Patterns used to extract information for the preview:
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
literal|"<A HREF=.*?\">([^<]*)</A>"
argument_list|)
decl_stmt|;
DECL|field|monthYearPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|monthYearPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([A-Za-z]+ [0-9]{4})"
argument_list|)
decl_stmt|;
DECL|field|absPattern
specifier|private
specifier|static
specifier|final
name|Pattern
name|absPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<div .*?>(.*?)</div>"
argument_list|)
decl_stmt|;
DECL|field|preview
specifier|private
name|FetcherPreviewDialog
name|preview
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
name|this
operator|.
name|preview
operator|=
name|preview
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
name|fetchAbstract
operator|=
name|absCheckBox
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|int
name|firstEntry
init|=
literal|1
decl_stmt|;
name|String
name|address
init|=
name|makeUrl
argument_list|(
name|firstEntry
argument_list|)
decl_stmt|;
name|LinkedHashMap
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|previews
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
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|address
argument_list|)
decl_stmt|;
name|String
name|page
init|=
name|getResults
argument_list|(
name|url
argument_list|)
decl_stmt|;
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
block|{
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
block|}
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
name|hits
operator|=
name|getNumberOfHits
argument_list|(
name|page
argument_list|,
literal|"Results"
argument_list|,
name|maxHitsPattern
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|hits
condition|;
name|i
operator|++
control|)
block|{
name|parse
argument_list|(
name|page
argument_list|,
literal|0
argument_list|,
name|firstEntry
argument_list|,
name|previews
argument_list|)
expr_stmt|;
comment|//address = makeUrl(firstEntry);
name|firstEntry
operator|+=
name|perPage
expr_stmt|;
block|}
for|for
control|(
name|String
name|s
range|:
name|previews
operator|.
name|keySet
argument_list|()
control|)
block|{
name|preview
operator|.
name|addEntry
argument_list|(
name|s
argument_list|,
name|previews
operator|.
name|get
argument_list|(
name|s
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
for|for
control|(
name|String
name|id
range|:
name|selection
operator|.
name|keySet
argument_list|()
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
name|boolean
name|sel
init|=
name|selection
operator|.
name|get
argument_list|(
name|id
argument_list|)
decl_stmt|;
if|if
condition|(
name|sel
condition|)
block|{
name|BibtexEntry
name|entry
init|=
name|downloadEntryBibTeX
argument_list|(
name|id
argument_list|,
name|fetchAbstract
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
comment|// Convert from HTML and optionally add curly brackets around key words to keep the case
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
name|title
operator|=
name|title
operator|.
name|replaceAll
argument_list|(
literal|"\\\\&"
argument_list|,
literal|"&"
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\\\#"
argument_list|,
literal|"#"
argument_list|)
expr_stmt|;
name|title
operator|=
name|convertHTMLChars
argument_list|(
name|title
argument_list|)
expr_stmt|;
comment|// Unit formatting
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useUnitFormatterOnSearch"
argument_list|)
condition|)
block|{
name|title
operator|=
name|unitFormatter
operator|.
name|format
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
comment|// Case keeping
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useCaseKeeperOnSearch"
argument_list|)
condition|)
block|{
name|title
operator|=
name|caseKeeper
operator|.
name|format
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|title
argument_list|)
expr_stmt|;
block|}
name|String
name|abstr
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"abstract"
argument_list|)
decl_stmt|;
if|if
condition|(
name|abstr
operator|!=
literal|null
condition|)
block|{
name|abstr
operator|=
name|convertHTMLChars
argument_list|(
name|abstr
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|abstr
argument_list|)
expr_stmt|;
block|}
name|inspector
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
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
DECL|method|getPreferredPreviewHeight ()
specifier|public
name|int
name|getPreferredPreviewHeight
parameter_list|()
block|{
return|return
literal|75
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
literal|"&start="
argument_list|)
operator|.
name|append
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|startIndex
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
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"ACM"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"GUIDE"
argument_list|)
expr_stmt|;
block|}
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
specifier|private
name|int
name|piv
init|=
literal|0
decl_stmt|;
DECL|method|parse (String text, int startIndex, int firstEntryNumber, Map<String,JLabel> entries)
specifier|private
name|void
name|parse
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|startIndex
parameter_list|,
name|int
name|firstEntryNumber
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|entries
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
while|while
condition|(
name|getNextEntryURL
argument_list|(
name|text
argument_list|,
name|piv
argument_list|,
name|entryNumber
argument_list|,
name|entries
argument_list|)
condition|)
block|{
name|entryNumber
operator|++
expr_stmt|;
block|}
block|}
DECL|method|getEntryBibTeXURL (String fullCitation, boolean abs)
specifier|private
name|String
name|getEntryBibTeXURL
parameter_list|(
name|String
name|fullCitation
parameter_list|,
name|boolean
name|abs
parameter_list|)
block|{
comment|// Get ID
name|Matcher
name|idMatcher
init|=
name|idPattern
operator|.
name|matcher
argument_list|(
name|fullCitation
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
return|return
name|idMatcher
operator|.
name|group
argument_list|(
literal|1
argument_list|)
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
literal|"Did not find ID in: "
operator|+
name|fullCitation
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|getNextEntryURL (String allText, int startIndex, int entryNumber, Map<String,JLabel> entries)
specifier|private
name|boolean
name|getNextEntryURL
parameter_list|(
name|String
name|allText
parameter_list|,
name|int
name|startIndex
parameter_list|,
name|int
name|entryNumber
parameter_list|,
name|Map
argument_list|<
name|String
argument_list|,
name|JLabel
argument_list|>
name|entries
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
literal|"</strong><br>"
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
name|length
argument_list|()
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
name|String
name|link
init|=
name|getEntryBibTeXURL
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
decl_stmt|;
name|String
name|part
decl_stmt|;
name|int
name|endOfRecord
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|"<div class=\"abstract2\">"
argument_list|)
decl_stmt|;
if|if
condition|(
name|endOfRecord
operator|>
literal|0
condition|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|part
operator|=
name|text
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|endOfRecord
argument_list|)
expr_stmt|;
try|try
block|{
name|save
argument_list|(
literal|"part"
operator|+
name|entryNumber
operator|+
literal|".html"
argument_list|,
name|part
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
comment|//To change body of catch statement use File | Settings | File Templates.
block|}
comment|// Find authors:
name|String
name|authMarker
init|=
literal|"<div class=\"authors\">"
decl_stmt|;
name|int
name|authStart
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|authMarker
argument_list|)
decl_stmt|;
if|if
condition|(
name|authStart
operator|>=
literal|0
condition|)
block|{
name|int
name|authEnd
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|"</div>"
argument_list|,
name|authStart
operator|+
name|authMarker
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|authEnd
operator|>=
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<p>"
argument_list|)
operator|.
name|append
argument_list|(
name|text
operator|.
name|substring
argument_list|(
name|authStart
argument_list|,
name|authEnd
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</p>"
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Find title:
name|Matcher
name|titM
init|=
name|titlePattern
operator|.
name|matcher
argument_list|(
name|part
argument_list|)
decl_stmt|;
if|if
condition|(
name|titM
operator|.
name|find
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<p>"
argument_list|)
operator|.
name|append
argument_list|(
name|titM
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</p>"
argument_list|)
expr_stmt|;
block|}
comment|// Find month and year:
name|Matcher
name|mY
init|=
name|monthYearPattern
operator|.
name|matcher
argument_list|(
name|part
argument_list|)
decl_stmt|;
if|if
condition|(
name|mY
operator|.
name|find
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<p>"
argument_list|)
operator|.
name|append
argument_list|(
name|mY
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</p>"
argument_list|)
expr_stmt|;
block|}
name|part
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|/*.replaceAll("</tr>", "<br>");                     part = part.replaceAll("</td>", "");                     part = part.replaceAll("<tr valign=\"[A-Za-z]*\">", "");                     part = part.replaceAll("<table style=\"padding: 5px; 5px; 5px; 5px;\" border=\"0\">", "");*/
block|}
else|else
block|{
name|part
operator|=
name|link
expr_stmt|;
block|}
name|JLabel
name|preview
init|=
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|part
operator|+
literal|"</html>"
argument_list|)
decl_stmt|;
name|preview
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|750
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|entries
operator|.
name|put
argument_list|(
name|link
argument_list|,
name|preview
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
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
return|return
literal|false
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|downloadEntryBibTeX (String ID, boolean abs)
specifier|private
name|BibtexEntry
name|downloadEntryBibTeX
parameter_list|(
name|String
name|ID
parameter_list|,
name|boolean
name|abs
parameter_list|)
block|{
try|try
block|{
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|startUrl
operator|+
name|bibtexUrl
operator|+
name|ID
operator|+
name|bibtexUrlEnd
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
name|url
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
if|if
condition|(
name|item
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
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
name|Thread
operator|.
name|sleep
argument_list|(
name|WAIT_TIME
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
comment|// get abstract
if|if
condition|(
name|abs
condition|)
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
name|startUrl
operator|+
name|abstractUrl
operator|+
name|ID
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
name|Matcher
name|absM
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
name|absM
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
name|absM
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Thread
operator|.
name|sleep
argument_list|(
name|WAIT_TIME
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
block|}
return|return
name|entry
return|;
block|}
catch|catch
parameter_list|(
name|NoSuchElementException
name|e
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Bad Bibtex record read at: "
operator|+
name|bibtexUrl
operator|+
name|ID
operator|+
name|bibtexUrlEnd
argument_list|)
expr_stmt|;
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
block|{
break|break;
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
name|bytesRead
condition|;
name|i
operator|++
control|)
block|{
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
block|{
break|break;
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
name|bytesRead
condition|;
name|i
operator|++
control|)
block|{
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
literal|"ACM Portal"
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
literal|"ACM Portal"
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
block|{  	}
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
block|}
end_class

end_unit

