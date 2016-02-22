begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef Contributors     Copyright (C) 2003-2011 Aaron Chen     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|JabRefPreferences
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|HTMLToLatexFormatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|UnitFormatter
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
name|formatter
operator|.
name|casechanger
operator|.
name|CaseKeeper
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
name|net
operator|.
name|URLConnection
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
name|Charset
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
name|LinkedHashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|NoSuchElementException
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
DECL|class|ACMPortalFetcher
specifier|public
class|class
name|ACMPortalFetcher
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
name|ACMPortalFetcher
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|htmlConverter
specifier|private
specifier|final
name|HTMLToLatexFormatter
name|htmlConverter
init|=
operator|new
name|HTMLToLatexFormatter
argument_list|()
decl_stmt|;
DECL|field|caseKeeper
specifier|private
specifier|final
name|CaseKeeper
name|caseKeeper
init|=
operator|new
name|CaseKeeper
argument_list|()
decl_stmt|;
DECL|field|unitFormatter
specifier|private
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
DECL|field|START_URL
specifier|private
specifier|static
specifier|final
name|String
name|START_URL
init|=
literal|"http://portal.acm.org/"
decl_stmt|;
DECL|field|SEARCH_URL_PART
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL_PART
init|=
literal|"results.cfm?query="
decl_stmt|;
DECL|field|SEARCH_URL_PART_II
specifier|private
specifier|static
specifier|final
name|String
name|SEARCH_URL_PART_II
init|=
literal|"&dl="
decl_stmt|;
DECL|field|END_URL
specifier|private
specifier|static
specifier|final
name|String
name|END_URL
init|=
literal|"&coll=Portal&short=0"
decl_stmt|;
comment|//&start=";
DECL|field|BIBTEX_URL
specifier|private
specifier|static
specifier|final
name|String
name|BIBTEX_URL
init|=
literal|"exportformats.cfm?id="
decl_stmt|;
DECL|field|BIBTEX_URL_END
specifier|private
specifier|static
specifier|final
name|String
name|BIBTEX_URL_END
init|=
literal|"&expformat=bibtex"
decl_stmt|;
DECL|field|ABSTRACT_URL
specifier|private
specifier|static
specifier|final
name|String
name|ABSTRACT_URL
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
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Include abstracts"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|PER_PAGE
specifier|private
specifier|static
specifier|final
name|int
name|PER_PAGE
init|=
literal|20
decl_stmt|;
comment|// Fetch only one page. Otherwise, the user will get blocked by ACM. 100 has been the old setting. See Bug 3532752 - https://sourceforge.net/tracker/index.php?func=detail&aid=3532752&group_id=92314&atid=600306
DECL|field|WAIT_TIME
specifier|private
specifier|static
specifier|final
name|int
name|WAIT_TIME
init|=
literal|200
decl_stmt|;
DECL|field|shouldContinue
specifier|private
name|boolean
name|shouldContinue
decl_stmt|;
comment|// user settings
DECL|field|fetchAbstract
specifier|private
name|boolean
name|fetchAbstract
decl_stmt|;
DECL|field|acmOrGuide
specifier|private
name|boolean
name|acmOrGuide
decl_stmt|;
DECL|field|piv
specifier|private
name|int
name|piv
decl_stmt|;
DECL|field|HITS_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|HITS_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<strong>(\\d+,*\\d*)</strong> results found"
argument_list|)
decl_stmt|;
DECL|field|MAX_HITS_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|MAX_HITS_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"Result \\d+,*\\d*&ndash; \\d+,*\\d* of (\\d+,*\\d*)"
argument_list|)
decl_stmt|;
DECL|field|FULL_CITATION_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|FULL_CITATION_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\"(citation.cfm.*)\" target.*"
argument_list|)
decl_stmt|;
DECL|field|ID_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|ID_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"citation.cfm\\?id=(\\d+)&.*"
argument_list|)
decl_stmt|;
comment|// Patterns used to extract information for the preview:
DECL|field|TITLE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|TITLE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=.*?\">([^<]*)</a>"
argument_list|)
decl_stmt|;
DECL|field|ABSTRACT_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|ABSTRACT_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<div .*?>(.*?)</div>"
argument_list|)
decl_stmt|;
DECL|field|SOURCE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|SOURCE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<span style=\"padding-left:10px\">([^<]*)</span>"
argument_list|)
decl_stmt|;
annotation|@
name|Override
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
name|String
name|address
init|=
name|makeUrl
argument_list|()
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
argument_list|<>
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
name|resultsFound
init|=
literal|"<div id=\"resfound\">"
decl_stmt|;
name|int
name|hits
init|=
name|getNumberOfHits
argument_list|(
name|page
argument_list|,
name|resultsFound
argument_list|,
name|ACMPortalFetcher
operator|.
name|HITS_PATTERN
argument_list|)
decl_stmt|;
name|int
name|index
init|=
name|page
operator|.
name|indexOf
argument_list|(
name|resultsFound
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
name|resultsFound
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"No entries found for the search string '%0'"
argument_list|,
name|terms
argument_list|)
argument_list|,
name|Localization
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
elseif|else
if|if
condition|(
name|hits
operator|>
literal|20
condition|)
block|{
name|status
operator|.
name|showMessage
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 entries found. To reduce server load, only %1 will be downloaded."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|hits
argument_list|)
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|PER_PAGE
argument_list|)
argument_list|)
argument_list|,
name|Localization
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
block|}
name|hits
operator|=
name|getNumberOfHits
argument_list|(
name|page
argument_list|,
literal|"<div class=\"pagerange\">"
argument_list|,
name|ACMPortalFetcher
operator|.
name|MAX_HITS_PATTERN
argument_list|)
expr_stmt|;
name|parse
argument_list|(
name|page
argument_list|,
name|Math
operator|.
name|min
argument_list|(
name|hits
argument_list|,
name|PER_PAGE
argument_list|)
argument_list|,
name|previews
argument_list|)
expr_stmt|;
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
name|entry
range|:
name|previews
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|preview
operator|.
name|addEntry
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|,
name|entry
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
name|MalformedURLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem with ACM fetcher URL"
argument_list|,
name|e
argument_list|)
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection to ACM Portal failed"
argument_list|)
argument_list|,
name|Localization
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
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem with ACM Portal"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
literal|false
return|;
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
name|selentry
range|:
name|selection
operator|.
name|entrySet
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
name|selentry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|sel
condition|)
block|{
name|BibEntry
name|entry
init|=
name|downloadEntryBibTeX
argument_list|(
name|selentry
operator|.
name|getKey
argument_list|()
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
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"title"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|title
lambda|->
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
name|JabRefPreferences
operator|.
name|USE_UNIT_FORMATTER_ON_SEARCH
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
name|JabRefPreferences
operator|.
name|USE_CASE_KEEPER_ON_SEARCH
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
argument_list|)
expr_stmt|;
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"abstract"
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|abstr
lambda|->
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|convertHTMLChars
argument_list|(
name|abstr
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
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
literal|75
return|;
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
return|return
literal|false
return|;
block|}
DECL|method|makeUrl ()
specifier|private
name|String
name|makeUrl
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|ACMPortalFetcher
operator|.
name|START_URL
argument_list|)
operator|.
name|append
argument_list|(
name|ACMPortalFetcher
operator|.
name|SEARCH_URL_PART
argument_list|)
operator|.
name|append
argument_list|(
name|terms
operator|.
name|replace
argument_list|(
literal|" "
argument_list|,
literal|"%20"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
name|ACMPortalFetcher
operator|.
name|SEARCH_URL_PART_II
argument_list|)
decl_stmt|;
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
name|ACMPortalFetcher
operator|.
name|END_URL
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|parse (String text, int hits, Map<String, JLabel> entries)
specifier|private
name|void
name|parse
parameter_list|(
name|String
name|text
parameter_list|,
name|int
name|hits
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
name|int
name|entryNumber
init|=
literal|1
decl_stmt|;
while|while
condition|(
name|getNextEntryURL
argument_list|(
name|text
argument_list|,
name|entryNumber
argument_list|,
name|entries
argument_list|)
operator|&&
operator|(
name|entryNumber
operator|<=
name|hits
operator|)
condition|)
block|{
name|entryNumber
operator|++
expr_stmt|;
block|}
block|}
DECL|method|getEntryBibTeXURL (String fullCitation)
specifier|private
specifier|static
name|String
name|getEntryBibTeXURL
parameter_list|(
name|String
name|fullCitation
parameter_list|)
block|{
comment|// Get ID
name|Matcher
name|idMatcher
init|=
name|ACMPortalFetcher
operator|.
name|ID_PATTERN
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
name|LOGGER
operator|.
name|info
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
DECL|method|getNextEntryURL (String allText, int entryNumber, Map<String, JLabel> entries)
specifier|private
name|boolean
name|getNextEntryURL
parameter_list|(
name|String
name|allText
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
literal|"<div class=\"numbering\">"
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
name|piv
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|allText
operator|.
name|indexOf
argument_list|(
literal|"<br clear=\"all\" />"
argument_list|,
name|index
argument_list|)
decl_stmt|;
name|piv
operator|=
name|endIndex
expr_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
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
name|ACMPortalFetcher
operator|.
name|FULL_CITATION_PATTERN
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|String
name|item
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
argument_list|)
decl_stmt|;
if|if
condition|(
name|endIndex
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
name|ACMPortalFetcher
operator|.
name|TITLE_PATTERN
operator|.
name|matcher
argument_list|(
name|text
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
name|String
name|sourceMarker
init|=
literal|"<div class=\"source\">"
decl_stmt|;
name|int
name|sourceStart
init|=
name|text
operator|.
name|indexOf
argument_list|(
name|sourceMarker
argument_list|)
decl_stmt|;
if|if
condition|(
name|sourceStart
operator|>=
literal|0
condition|)
block|{
name|int
name|sourceEnd
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|"</div>"
argument_list|,
name|sourceStart
operator|+
name|sourceMarker
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sourceEnd
operator|>=
literal|0
condition|)
block|{
name|String
name|sourceText
init|=
name|text
operator|.
name|substring
argument_list|(
name|sourceStart
argument_list|,
name|sourceEnd
argument_list|)
decl_stmt|;
comment|// Find source:
name|Matcher
name|source
init|=
name|ACMPortalFetcher
operator|.
name|SOURCE_PATTERN
operator|.
name|matcher
argument_list|(
name|sourceText
argument_list|)
decl_stmt|;
if|if
condition|(
name|source
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
name|source
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
block|}
block|}
name|item
operator|=
name|sb
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|item
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
name|item
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Citation unmatched "
operator|+
name|Integer
operator|.
name|toString
argument_list|(
name|entryNumber
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|downloadEntryBibTeX (String id, boolean downloadAbstract)
specifier|private
specifier|static
name|BibEntry
name|downloadEntryBibTeX
parameter_list|(
name|String
name|id
parameter_list|,
name|boolean
name|downloadAbstract
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
name|ACMPortalFetcher
operator|.
name|START_URL
operator|+
name|ACMPortalFetcher
operator|.
name|BIBTEX_URL
operator|+
name|id
operator|+
name|ACMPortalFetcher
operator|.
name|BIBTEX_URL_END
argument_list|)
decl_stmt|;
name|URLConnection
name|connection
init|=
name|url
operator|.
name|openConnection
argument_list|()
decl_stmt|;
comment|// set user-agent to avoid being blocked as a crawler
name|connection
operator|.
name|addRequestProperty
argument_list|(
literal|"User-Agent"
argument_list|,
literal|"Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0"
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|items
init|=
literal|null
decl_stmt|;
try|try
init|(
name|BufferedReader
name|in
init|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|InputStreamReader
argument_list|(
name|connection
operator|.
name|getInputStream
argument_list|()
argument_list|,
name|Charset
operator|.
name|forName
argument_list|(
literal|"UTF-8"
argument_list|)
argument_list|)
argument_list|)
init|)
block|{
name|items
operator|=
name|BibtexParser
operator|.
name|parse
argument_list|(
name|in
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
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
name|info
argument_list|(
literal|"Download of BibTeX information from ACM Portal failed."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|(
name|items
operator|==
literal|null
operator|)
operator|||
name|items
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|BibEntry
name|entry
init|=
name|items
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
name|ACMPortalFetcher
operator|.
name|WAIT_TIME
argument_list|)
expr_stmt|;
comment|//wait between requests or you will be blocked by ACM
comment|// get abstract
if|if
condition|(
name|downloadAbstract
condition|)
block|{
name|url
operator|=
operator|new
name|URL
argument_list|(
name|ACMPortalFetcher
operator|.
name|START_URL
operator|+
name|ACMPortalFetcher
operator|.
name|ABSTRACT_URL
operator|+
name|id
argument_list|)
expr_stmt|;
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
name|Matcher
name|absM
init|=
name|ACMPortalFetcher
operator|.
name|ABSTRACT_PATTERN
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
name|ACMPortalFetcher
operator|.
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Bad BibTeX record read at: "
operator|+
name|ACMPortalFetcher
operator|.
name|BIBTEX_URL
operator|+
name|id
operator|+
name|ACMPortalFetcher
operator|.
name|BIBTEX_URL_END
argument_list|,
name|e
argument_list|)
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Malformed URL."
argument_list|,
name|e
argument_list|)
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot connect."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|ignored
parameter_list|)
block|{
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
specifier|static
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
operator|>=
literal|0
condition|)
block|{
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
literal|100
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
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
try|try
block|{
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
name|number
operator|=
name|number
operator|.
name|replace
argument_list|(
literal|","
argument_list|,
literal|""
argument_list|)
expr_stmt|;
comment|// Remove , as in 1,234
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
name|IllegalStateException
decl||
name|NumberFormatException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Cannot parse number of hits"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Unmatched! "
operator|+
name|substring
argument_list|)
expr_stmt|;
block|}
block|}
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Cannot parse number of hits"
argument_list|)
throw|;
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
literal|"ACM Portal"
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
literal|"ACMPortalHelp"
return|;
block|}
comment|// This method is called by the dialog when the user has cancelled or
comment|//signaled a stop. It is expected that any long-running fetch operations
comment|//will stop after this method is called.
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
block|}
end_class

end_unit

