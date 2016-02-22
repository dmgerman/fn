begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|openoffice
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
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
name|File
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
name|io
operator|.
name|Reader
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
name|org
operator|.
name|junit
operator|.
name|After
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
name|Test
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
name|JabRef
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
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
import|;
end_import

begin_class
DECL|class|OOBibStyleTest
specifier|public
class|class
name|OOBibStyleTest
block|{
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
name|journalAbbreviationLoader
operator|=
operator|new
name|JournalAbbreviationLoader
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
throws|throws
name|Exception
block|{     }
annotation|@
name|Test
DECL|method|testAuthorYear ()
specifier|public
name|void
name|testAuthorYear
parameter_list|()
block|{
try|try
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_AUTHORYEAR_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBibtexKeyCiteMarkers
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBoldCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isFormatCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isItalicCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isNumberEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isSortByPosition
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testAuthorYearAsFile ()
specifier|public
name|void
name|testAuthorYearAsFile
parameter_list|()
block|{
try|try
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_AUTHORYEAR_STYLE_PATH
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
operator|new
name|File
argument_list|(
name|defPath
operator|.
name|getFile
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBibtexKeyCiteMarkers
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBoldCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isFormatCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isItalicCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isNumberEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isSortByPosition
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testNumerical ()
specifier|public
name|void
name|testNumerical
parameter_list|()
block|{
try|try
block|{
name|URL
name|defPath
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|OpenOfficePanel
operator|.
name|DEFAULT_NUMERICAL_STYLE_PATH
argument_list|)
decl_stmt|;
name|Reader
name|r
init|=
operator|new
name|InputStreamReader
argument_list|(
name|defPath
operator|.
name|openStream
argument_list|()
argument_list|)
decl_stmt|;
name|OOBibStyle
name|style
init|=
operator|new
name|OOBibStyle
argument_list|(
name|r
argument_list|,
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBibtexKeyCiteMarkers
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isBoldCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isFormatCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|style
operator|.
name|isItalicCitations
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isNumberEntries
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|style
operator|.
name|isSortByPosition
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
