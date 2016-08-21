begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|logic
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
name|preferences
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
name|Test
import|;
end_import

begin_class
DECL|class|ParsedBibEntryTests
specifier|public
class|class
name|ParsedBibEntryTests
block|{
DECL|field|importFormatPreferences
specifier|private
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
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
name|importFormatPreferences
operator|=
name|ImportFormatPreferences
operator|.
name|fromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetPublicationDate ()
specifier|public
name|void
name|testGetPublicationDate
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003-02"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = #FEB# }"
argument_list|,
name|importFormatPreferences
argument_list|)
operator|)
operator|.
name|get
argument_list|()
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003-03"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = 3 }"
argument_list|,
name|importFormatPreferences
argument_list|)
operator|)
operator|.
name|get
argument_list|()
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}}"
argument_list|,
name|importFormatPreferences
argument_list|)
operator|)
operator|.
name|get
argument_list|()
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, month = 3 }"
argument_list|,
name|importFormatPreferences
argument_list|)
operator|)
operator|.
name|get
argument_list|()
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, author={bla}}"
argument_list|,
name|importFormatPreferences
argument_list|)
operator|)
operator|.
name|get
argument_list|()
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2003-12"
argument_list|)
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = #DEC# }"
argument_list|,
name|importFormatPreferences
argument_list|)
operator|)
operator|.
name|get
argument_list|()
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

