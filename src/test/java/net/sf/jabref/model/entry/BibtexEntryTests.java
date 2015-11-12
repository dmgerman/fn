begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
import|;
end_import

begin_class
DECL|class|BibtexEntryTests
specifier|public
class|class
name|BibtexEntryTests
block|{
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setup ()
specifier|public
name|void
name|setup
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
block|}
annotation|@
name|Test
DECL|method|testDefaultConstructor ()
specifier|public
name|void
name|testDefaultConstructor
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibtexEntry
argument_list|()
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
literal|"2003-02"
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = #FEB# }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-03"
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}, month = 3 }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003"
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {2003}}"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, month = 3 }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|null
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, author={bla}}"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2003-12"
argument_list|,
operator|(
name|BibtexParser
operator|.
name|singleFromString
argument_list|(
literal|"@ARTICLE{HipKro03, year = {03}, month = #DEC# }"
argument_list|)
operator|)
operator|.
name|getPublicationDate
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

