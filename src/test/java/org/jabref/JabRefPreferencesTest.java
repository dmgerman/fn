begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
package|;
end_package

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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|org
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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|JabRefPreferencesTest
specifier|public
class|class
name|JabRefPreferencesTest
block|{
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|backup
specifier|private
name|JabRefPreferences
name|backup
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|backup
operator|=
name|prefs
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPreferencesImport ()
specifier|public
name|void
name|testPreferencesImport
parameter_list|()
throws|throws
name|JabRefException
block|{
comment|// the primary sort field has been changed to "editor" in this case
name|File
name|importFile
init|=
operator|new
name|File
argument_list|(
literal|"src/test/resources/org/jabref/customPreferences.xml"
argument_list|)
decl_stmt|;
name|prefs
operator|.
name|importPreferences
argument_list|(
name|importFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
name|String
name|expected
init|=
literal|"dummyvalue"
decl_stmt|;
name|String
name|actual
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"dummykey"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getDefaultEncodingReturnsPreviouslyStoredEncoding ()
specifier|public
name|void
name|getDefaultEncodingReturnsPreviouslyStoredEncoding
parameter_list|()
block|{
name|prefs
operator|.
name|setDefaultEncoding
argument_list|(
name|StandardCharsets
operator|.
name|UTF_16BE
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|StandardCharsets
operator|.
name|UTF_16BE
argument_list|,
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
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
block|{
comment|//clean up preferences to default state
name|prefs
operator|.
name|overwritePreferences
argument_list|(
name|backup
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
