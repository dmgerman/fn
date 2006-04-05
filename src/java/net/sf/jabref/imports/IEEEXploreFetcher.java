begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|ImportInspectionDialog
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
name|MalformedURLException
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
name|List
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
name|awt
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Mar 25, 2006  * Time: 1:09:32 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|IEEEXploreFetcher
specifier|public
class|class
name|IEEEXploreFetcher
implements|implements
name|Runnable
implements|,
name|EntryFetcher
block|{
DECL|field|dialog
name|ImportInspectionDialog
name|dialog
init|=
literal|null
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
init|=
literal|null
decl_stmt|;
DECL|field|htmlConverter
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
literal|"http://ieeexplore.ieee.org"
decl_stmt|;
DECL|field|searchUrlPart
name|String
name|searchUrlPart
init|=
literal|"/search/freesearchresult.jsp?queryText="
decl_stmt|;
DECL|field|endUrl
name|String
name|endUrl
init|=
literal|"+%3Cin%3E+metadata&ResultCount=25&ResultStart="
decl_stmt|;
DECL|field|perPage
DECL|field|hits
DECL|field|unparseable
DECL|field|parsed
specifier|private
name|int
name|perPage
init|=
literal|25
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
DECL|field|fetchAstracts
specifier|private
name|JCheckBox
name|fetchAstracts
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
DECL|field|fetchingAbstracts
specifier|private
name|boolean
name|fetchingAbstracts
init|=
literal|false
decl_stmt|;
DECL|field|MAX_ABSTRACT_FETCH
specifier|private
specifier|static
specifier|final
name|int
name|MAX_ABSTRACT_FETCH
init|=
literal|5
decl_stmt|;
DECL|method|IEEEXploreFetcher ()
specifier|public
name|IEEEXploreFetcher
parameter_list|()
block|{     }
comment|//Pattern hitsPattern = Pattern.compile("Your search matched<strong>(\\d+)</strong>");
DECL|field|hitsPattern
name|Pattern
name|hitsPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*Your search matched<strong>(\\d+)</strong>.*"
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
literal|".*A maximum of<strong>(\\d+)</strong>.*"
argument_list|)
decl_stmt|;
DECL|field|entryPattern1
name|Pattern
name|entryPattern1
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*<strong>(.+)</strong><br>\\s+(.+)<br>"
operator|+
literal|"\\s+<A href='(.+)'>(.+)</A><br>\\s+Volume (.+),&nbsp;\\s*"
operator|+
literal|"(.+)? (\\d\\d\\d\\d)\\s+Page\\(s\\):.*"
argument_list|)
decl_stmt|;
DECL|field|entryPattern2
name|Pattern
name|entryPattern2
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*<strong>(.+)</strong><br>\\s+(.+)<br>"
operator|+
literal|"\\s+<A href='(.+)'>(.+)</A><br>\\s+Volume (.+),&nbsp;\\s+Issue (\\d+),&nbsp;\\s*"
operator|+
literal|"(.+)? (\\d\\d\\d\\d)\\s+Page\\(s\\):.*"
argument_list|)
decl_stmt|;
DECL|field|entryPattern3
name|Pattern
name|entryPattern3
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*<strong>(.+)</strong><br>\\s+(.+)<br>"
operator|+
literal|"\\s+<A href='(.+)'>(.+)</A><br>\\s+Volume (.+),&nbsp;\\s+Issue (\\d+),&nbsp;"
operator|+
literal|"\\s+Part (\\d+),&nbsp;\\s*"
comment|//"[\\s-\\d]+"
operator|+
literal|"(.+)? (\\d\\d\\d\\d)\\s+Page\\(s\\):.*"
argument_list|)
decl_stmt|;
DECL|field|entryPattern4
name|Pattern
name|entryPattern4
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|".*<strong>(.+)</strong><br>\\s+(.+)<br>"
operator|+
literal|"\\s+<A href='(.+)'>(.+)</A><br>\\s*"
comment|//[\\s-\\da-z]+"
operator|+
literal|"(.+)? (\\d\\d\\d\\d)\\s+Page\\(s\\):.*"
argument_list|)
decl_stmt|;
DECL|field|abstractLinkPattern
name|Pattern
name|abstractLinkPattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"<a href=\"(.+)\" class=\"bodyCopySpaced\">Abstract</a>"
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
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|fetchAstracts
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
return|return
name|pan
return|;
block|}
DECL|method|processQuery (String query, ImportInspectionDialog dialog, JabRefFrame frame)
specifier|public
name|void
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspectionDialog
name|dialog
parameter_list|,
name|JabRefFrame
name|frame
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
name|frame
operator|=
name|frame
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
operator|(
operator|new
name|Thread
argument_list|(
name|this
argument_list|)
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
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
name|lang
argument_list|(
literal|"Search IEEEXplore"
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
name|wwwIcon
return|;
block|}
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
block|{
return|return
literal|null
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
comment|// wanted entries, and clicked Ok. The callback object can update status
comment|// line etc.
DECL|method|done (int entriesImported)
specifier|public
name|void
name|done
parameter_list|(
name|int
name|entriesImported
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Number of entries parsed: "
operator|+
name|parsed
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Parsing failed for "
operator|+
name|unparseable
operator|+
literal|" entries"
argument_list|)
expr_stmt|;
block|}
comment|// This method is called by the dialog when the user has cancelled or
comment|// signalled a stop. It is expected that any long-running fetch operations
comment|// will stop after this method is called.
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
comment|/**      * The code that runs the actual search and fetch operation.      */
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|frame
operator|.
name|block
argument_list|()
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
comment|// Fetch the search page and put the contents in a String:
comment|//String page = getResultsFromFile(new File("/home/alver/div/temp.txt"));
comment|//URLDownload ud = new URLDownload(new JPanel(), url, new File("/home/alver/div/temp.txt"));
comment|//ud.download();
comment|//dialog.setVisible(true);
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
literal|"Your search matched"
argument_list|,
name|hitsPattern
argument_list|)
expr_stmt|;
name|frame
operator|.
name|unblock
argument_list|()
expr_stmt|;
if|if
condition|(
name|hits
operator|==
literal|0
condition|)
block|{
name|dialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
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
literal|"Search IEEEXplore"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
else|else
block|{
name|fetchingAbstracts
operator|=
name|fetchAstracts
operator|.
name|isSelected
argument_list|()
expr_stmt|;
if|if
condition|(
name|fetchingAbstracts
operator|&&
operator|(
name|hits
operator|>
name|MAX_ABSTRACT_FETCH
operator|)
condition|)
block|{
name|fetchingAbstracts
operator|=
literal|false
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 entries found. To reduce server load, abstracts "
operator|+
literal|"will only be downloaded for searches returning %1 hits or less."
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
name|MAX_ABSTRACT_FETCH
argument_list|)
block|}
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search IEEEXplore"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|int
name|maxHits
init|=
name|getNumberOfHits
argument_list|(
name|page
argument_list|,
literal|"A maximum of"
argument_list|,
name|maxHitsPattern
argument_list|)
decl_stmt|;
comment|//String page = getResultsFromFile(new File("/home/alver/div/temp50.txt"));
comment|//List entries = new ArrayList();
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Number of hits: "
operator|+
name|hits
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Maximum returned: "
operator|+
name|maxHits
argument_list|)
expr_stmt|;
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Fetching from: "
operator|+
name|firstEntry
argument_list|)
expr_stmt|;
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
name|dialog
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
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
literal|"+"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|endUrl
argument_list|)
expr_stmt|;
name|sb
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
DECL|method|parse (ImportInspectionDialog dialog, String text, int startIndex, int firstEntryNumber)
specifier|private
name|void
name|parse
parameter_list|(
name|ImportInspectionDialog
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
name|List
name|entries
init|=
operator|new
name|ArrayList
argument_list|()
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
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|addEntries
argument_list|(
name|entries
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
name|entries
operator|.
name|clear
argument_list|()
expr_stmt|;
name|parsed
operator|++
expr_stmt|;
block|}
name|entryNumber
operator|++
expr_stmt|;
comment|//break;
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
literal|"<div align=\"left\"><strong>"
argument_list|)
operator|.
name|append
argument_list|(
name|entryNumber
argument_list|)
operator|.
name|append
argument_list|(
literal|".</strong></div>"
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
if|if
condition|(
name|endIndex
operator|<
literal|0
condition|)
name|endIndex
operator|=
name|allText
operator|.
name|length
argument_list|()
expr_stmt|;
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
name|BibtexEntryType
name|type
decl_stmt|;
name|String
name|sourceField
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|indexOf
argument_list|(
literal|"IEEE JNL"
argument_list|)
operator|>=
literal|0
condition|)
block|{
name|type
operator|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|sourceField
operator|=
literal|"journal"
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
literal|"inproceedings"
argument_list|)
expr_stmt|;
name|sourceField
operator|=
literal|"booktitle"
expr_stmt|;
block|}
name|index
operator|=
literal|0
expr_stmt|;
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
argument_list|,
name|type
argument_list|)
decl_stmt|;
comment|//System.out.println(text);
name|Matcher
name|m1
init|=
name|entryPattern1
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|Matcher
name|m2
init|=
name|entryPattern2
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|Matcher
name|m3
init|=
name|entryPattern3
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|Matcher
name|m4
init|=
name|entryPattern4
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
name|Matcher
name|m
decl_stmt|;
name|String
name|tmp
decl_stmt|;
name|String
name|rest
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|m1
operator|.
name|find
argument_list|()
condition|)
block|{
name|m
operator|=
name|m1
expr_stmt|;
comment|// Title:
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Author:
name|tmp
operator|=
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|tmp
operator|.
name|charAt
argument_list|(
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|';'
condition|)
name|tmp
operator|=
name|tmp
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|tmp
operator|.
name|replaceAll
argument_list|(
literal|"; "
argument_list|,
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
comment|// Publication:
name|tmp
operator|=
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|sourceField
argument_list|,
name|convertHTMLChars
argument_list|(
name|tmp
argument_list|)
argument_list|)
expr_stmt|;
comment|// Volume:
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Month:
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|6
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Year
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
literal|7
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|m2
operator|.
name|find
argument_list|()
condition|)
block|{
name|m
operator|=
name|m2
expr_stmt|;
comment|// Title:
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Author:
name|tmp
operator|=
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|tmp
operator|.
name|charAt
argument_list|(
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|';'
condition|)
name|tmp
operator|=
name|tmp
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|tmp
operator|.
name|replaceAll
argument_list|(
literal|"; "
argument_list|,
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
comment|// Publication:
name|tmp
operator|=
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|sourceField
argument_list|,
name|convertHTMLChars
argument_list|(
name|tmp
argument_list|)
argument_list|)
expr_stmt|;
comment|// Volume:
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Number:
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|6
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Month:
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|7
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Year:
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
literal|8
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|m3
operator|.
name|find
argument_list|()
condition|)
block|{
name|m
operator|=
name|m3
expr_stmt|;
comment|// Title:
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Author:
name|tmp
operator|=
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|tmp
operator|.
name|charAt
argument_list|(
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|';'
condition|)
name|tmp
operator|=
name|tmp
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|tmp
operator|.
name|replaceAll
argument_list|(
literal|"; "
argument_list|,
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
comment|// Publication:
name|tmp
operator|=
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|sourceField
argument_list|,
name|convertHTMLChars
argument_list|(
name|tmp
argument_list|)
argument_list|)
expr_stmt|;
comment|// Volume:
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Number:
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|6
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Month:
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|8
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Year
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
literal|9
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|m4
operator|.
name|find
argument_list|()
condition|)
block|{
name|m
operator|=
name|m4
expr_stmt|;
comment|// Title:
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Author:
name|tmp
operator|=
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|tmp
operator|.
name|charAt
argument_list|(
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|==
literal|';'
condition|)
name|tmp
operator|=
name|tmp
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|tmp
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
name|tmp
operator|.
name|replaceAll
argument_list|(
literal|"; "
argument_list|,
literal|" and "
argument_list|)
argument_list|)
expr_stmt|;
comment|// Publication:
name|tmp
operator|=
name|m
operator|.
name|group
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|sourceField
argument_list|,
name|convertHTMLChars
argument_list|(
name|tmp
argument_list|)
argument_list|)
expr_stmt|;
comment|// Month:
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
name|convertHTMLChars
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|5
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Year
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
literal|6
argument_list|)
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
literal|"---no structure match---"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|unparseable
operator|++
expr_stmt|;
block|}
name|int
name|pgInd
init|=
name|text
operator|.
name|indexOf
argument_list|(
literal|"Page(s):"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pgInd
operator|>=
literal|0
condition|)
block|{
comment|// Try to set pages:
name|rest
operator|=
name|text
operator|.
name|substring
argument_list|(
name|pgInd
operator|+
literal|8
argument_list|)
expr_stmt|;
name|pgInd
operator|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|pgInd
operator|>=
literal|0
condition|)
block|{
name|tmp
operator|=
name|rest
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|pgInd
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
name|tmp
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|""
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"-"
argument_list|,
literal|"--"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Try to set doi:
name|pgInd
operator|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|"Digital Object Identifier "
argument_list|,
name|pgInd
argument_list|)
expr_stmt|;
if|if
condition|(
name|pgInd
operator|>=
literal|0
condition|)
block|{
name|int
name|fieldEnd
init|=
name|rest
operator|.
name|indexOf
argument_list|(
literal|"<br>"
argument_list|,
name|pgInd
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldEnd
operator|>=
literal|0
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
name|rest
operator|.
name|substring
argument_list|(
name|pgInd
operator|+
literal|26
argument_list|,
name|fieldEnd
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Fetching abstracts takes time, and causes a lot of requests, so this should
comment|// be optional or disabled:
if|if
condition|(
name|fetchingAbstracts
condition|)
block|{
name|Matcher
name|abstractLink
init|=
name|abstractLinkPattern
operator|.
name|matcher
argument_list|(
name|text
argument_list|)
decl_stmt|;
if|if
condition|(
name|abstractLink
operator|.
name|find
argument_list|()
condition|)
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
name|abstractLink
operator|.
name|group
argument_list|(
literal|1
argument_list|)
argument_list|)
decl_stmt|;
comment|//System.out.println(sb.toString());
try|try
block|{
name|String
name|abstractText
init|=
name|fetchAbstract
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|abstractText
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|abstractText
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
operator|&&
operator|!
name|abstractText
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"not available"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
name|convertHTMLChars
argument_list|(
name|abstractText
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
block|}
block|}
return|return
name|entry
return|;
block|}
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
name|m
operator|.
name|find
argument_list|()
expr_stmt|;
if|if
condition|(
name|m
operator|.
name|groupCount
argument_list|()
operator|>=
literal|1
condition|)
block|{
try|try
block|{
return|return
name|Integer
operator|.
name|parseInt
argument_list|(
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
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
comment|/**      * Download and parse the web page containing an entry's Abstract:      * @param link      * @return      * @throws IOException      */
DECL|method|fetchAbstract (String link)
specifier|public
name|String
name|fetchAbstract
parameter_list|(
name|String
name|link
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
name|link
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|link
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Fetched abstract page."
argument_list|)
expr_stmt|;
name|String
name|marker
init|=
literal|"Abstract</span><br>"
decl_stmt|;
name|int
name|index
init|=
name|page
operator|.
name|indexOf
argument_list|(
name|marker
argument_list|)
decl_stmt|;
name|int
name|endIndex
init|=
name|page
operator|.
name|indexOf
argument_list|(
literal|"</td>"
argument_list|,
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|endIndex
operator|>
name|index
operator|)
condition|)
block|{
return|return
operator|new
name|String
argument_list|(
name|page
operator|.
name|substring
argument_list|(
name|index
operator|+
name|marker
operator|.
name|length
argument_list|()
argument_list|,
name|endIndex
argument_list|)
operator|.
name|trim
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

