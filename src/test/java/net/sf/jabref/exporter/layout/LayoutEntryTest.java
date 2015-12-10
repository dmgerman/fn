begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Ignore
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
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

begin_comment
comment|/**  * The test class LayoutEntryTest test the net.sf.jabref.export.layout.LayoutEntry.  * Indirectly the net.sf.jabref.export.layout.Layout is testet too.  *<p/>  * The LayoutEntry creates a human readable String assinged with html formaters.  * To test the Highlighting Feature, an instance of LayoutEntry will be instatiated via Layout and LayoutHelper.  * With these instance the doLayout() Method is called several times for each test case.  * To simulate a search, a BibtexEntry will be created, wich will be used by LayoutEntry.  * The definiton of the search is set by  *<p/>  * LayoutEntry.setWordsToHighlight(words); and  * Globals.prefs.putBoolean("caseSensitiveSearch", false);  *<p/>  * There are five test cases:  * - The shown result text has no words which should be highlighted.  * - There is one word which will be highlighted ignoring case sensitivity.  * - There are two words which will be highlighted ignoring case sensitivity.  * - There is one word which will be highlighted case sensitivity.  * - There are more words which will be highlighted case sensitivity.  *  * @author Arne  */
end_comment

begin_class
DECL|class|LayoutEntryTest
specifier|public
class|class
name|LayoutEntryTest
block|{
DECL|field|mBTE
specifier|private
name|BibEntry
name|mBTE
decl_stmt|;
comment|/**      * Initialize Preferences.      */
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"highLightWords"
argument_list|,
name|Boolean
operator|.
name|TRUE
argument_list|)
expr_stmt|;
block|}
comment|// create Bibtext Entry
name|mBTE
operator|=
operator|new
name|BibEntry
argument_list|(
literal|"testid"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"In this paper, we initiate a formal study of security on Android: Google's new open-source platform for mobile devices. Tags: Paper android google Open-Source Devices"
argument_list|)
expr_stmt|;
comment|//  Specifically, we present a core typed language to describe Android applications, and to reason about their data-flow security properties. Our operational semantics and type system provide some necessary foundations to help both users and developers of Android applications deal with their security concerns.
name|mBTE
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"android, mobile devices, security"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"posted-at"
argument_list|,
literal|"2010-08-11 15:00:49"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"location"
argument_list|,
literal|"Dublin, Ireland"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"chaudhuri-plas09"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"1--7"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
literal|"PLAS '09: Proceedings of the ACM SIGPLAN Fourth Workshop on Programming Languages and Analysis for Security"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"citeulike-article-id"
argument_list|,
literal|"7615801"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"citeulike-linkout-1"
argument_list|,
literal|"http://dx.doi.org/10.1145/1554339.1554341"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://dx.doi.org/10.1145/1554339.1554341"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"ACM"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"timestamp"
argument_list|,
literal|"2010.11.11"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Chaudhuri, Avik"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Language-based security on Android"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
literal|"New York, NY, USA"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"priority"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
literal|"978-1-60558-645-8"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"owner"
argument_list|,
literal|"Arne"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2009"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"citeulike-linkout-0"
argument_list|,
literal|"http://portal.acm.org/citation.cfm?id=1554339.1554341"
argument_list|)
expr_stmt|;
name|mBTE
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1145/1554339.1554341"
argument_list|)
expr_stmt|;
block|}
comment|// helper Methods
DECL|method|layout (String layoutFile, BibEntry entry, ArrayList<String> wordsToHighlight)
specifier|public
name|String
name|layout
parameter_list|(
name|String
name|layoutFile
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|ArrayList
argument_list|<
name|String
argument_list|>
name|wordsToHighlight
parameter_list|)
throws|throws
name|Exception
block|{
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|layoutFile
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|Layout
name|layout
init|=
operator|new
name|LayoutHelper
argument_list|(
name|sr
argument_list|)
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|FORMATTER_PACKAGE
argument_list|)
decl_stmt|;
return|return
name|layout
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|,
name|wordsToHighlight
argument_list|)
return|;
block|}
comment|/*************************/
comment|/****** tests Cases ******/
comment|/*************************/
comment|/**      * @throws Exception      */
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testNoHighlighting ()
specifier|public
name|void
name|testNoHighlighting
parameter_list|()
throws|throws
name|Exception
block|{
comment|// say that this bibtex object was found
name|mBTE
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// define the highlighting settings
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|String
name|result
init|=
name|this
operator|.
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|mBTE
argument_list|,
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|expecting
init|=
literal|"<font face=\"arial\"><BR><BR><b>Abstract:</b> In this paper, we initiate a formal study of security on Android: Google's new open-source platform for mobile devices. Tags: Paper android google Open-Source Devices</font>"
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expecting
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
comment|/**      * @throws Exception      */
annotation|@
name|Test
DECL|method|testHighlightingOneWordCaseInsesitive ()
specifier|public
name|void
name|testHighlightingOneWordCaseInsesitive
parameter_list|()
throws|throws
name|Exception
block|{
comment|// say that this bibtex object was found
name|mBTE
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// define the serach words
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"google"
argument_list|)
expr_stmt|;
comment|// define the highlighting settings
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|String
name|result
init|=
name|this
operator|.
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|mBTE
argument_list|,
name|words
argument_list|)
decl_stmt|;
name|String
name|containing
init|=
literal|"<span style=\"background-color:#3399FF;\">Google</span>"
decl_stmt|;
comment|// check
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"Actual message: "
operator|+
name|result
argument_list|,
name|result
operator|.
name|contains
argument_list|(
name|containing
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @throws Exception      */
annotation|@
name|Test
DECL|method|testHighlightingTwoWordsCaseInsesitive ()
specifier|public
name|void
name|testHighlightingTwoWordsCaseInsesitive
parameter_list|()
throws|throws
name|Exception
block|{
comment|// say that this bibtex object was found
name|mBTE
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// define the serach words
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"Android"
argument_list|)
expr_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"study"
argument_list|)
expr_stmt|;
comment|// define the highlighting settings
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|String
name|result
init|=
name|this
operator|.
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|mBTE
argument_list|,
name|words
argument_list|)
decl_stmt|;
name|String
name|containing
init|=
literal|"<span style=\"background-color:#3399FF;\">Android</span>"
decl_stmt|;
name|String
name|containing2
init|=
literal|"<span style=\"background-color:#3399FF;\">study</span>"
decl_stmt|;
comment|// check
name|Assert
operator|.
name|assertTrue
argument_list|(
name|result
operator|.
name|contains
argument_list|(
name|containing
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|result
operator|.
name|contains
argument_list|(
name|containing2
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * @throws Exception      */
annotation|@
name|Test
DECL|method|testHighlightingOneWordCaseSesitive ()
specifier|public
name|void
name|testHighlightingOneWordCaseSesitive
parameter_list|()
throws|throws
name|Exception
block|{
comment|// say that this bibtex object was found
name|mBTE
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// define the search words
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"google"
argument_list|)
expr_stmt|;
comment|// define the highlighting settings
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|String
name|result
init|=
name|this
operator|.
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|mBTE
argument_list|,
name|words
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"<font face=\"arial\"><BR><BR><b>Abstract:</b> In this paper, we initiate a formal study of security on Android: Google's new open-source platform for mobile devices. Tags: Paper android<span style=\"background-color:#3399FF;\">google</span> Open-Source Devices</font>"
decl_stmt|;
comment|// check
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
comment|/**      * @throws Exception      */
annotation|@
name|Test
DECL|method|testHighlightingMoreWordsCaseSesitive ()
specifier|public
name|void
name|testHighlightingMoreWordsCaseSesitive
parameter_list|()
throws|throws
name|Exception
block|{
comment|// say that this bibtex object was found
name|mBTE
operator|.
name|setSearchHit
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// define the serach words
name|ArrayList
argument_list|<
name|String
argument_list|>
name|words
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"Android"
argument_list|)
expr_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"study"
argument_list|)
expr_stmt|;
name|words
operator|.
name|add
argument_list|(
literal|"Open"
argument_list|)
expr_stmt|;
comment|// define the highlighting settings
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|String
name|highlightColor
init|=
literal|"#3399FF;"
decl_stmt|;
name|String
name|result
init|=
name|this
operator|.
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|mBTE
argument_list|,
name|words
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"<font face=\"arial\"><BR><BR><b>Abstract:</b> In this paper, we initiate a formal<span style=\"background-color:"
operator|+
name|highlightColor
operator|+
literal|"\">study</span> of security on<span style=\"background-color:"
operator|+
name|highlightColor
operator|+
literal|"\">Android</span>: Google's new<span style=\"background-color:"
operator|+
name|highlightColor
operator|+
literal|"\">open</span>-source platform for mobile devices. Tags: Paper<span style=\"background-color:"
operator|+
name|highlightColor
operator|+
literal|"\">android</span> google<span style=\"background-color:"
operator|+
name|highlightColor
operator|+
literal|"\">Open</span>-Source Devices</font>"
decl_stmt|;
comment|// check
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

