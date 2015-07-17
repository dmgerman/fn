begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|assertEquals
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
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|prefs
operator|.
name|resetToDefaultPreferences
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPreferencesExport ()
specifier|public
name|void
name|testPreferencesExport
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|tmpFile
init|=
literal|"src/test/resources/net/sf/jabref/preferencesTest.pref"
decl_stmt|;
name|prefs
operator|.
name|exportPreferences
argument_list|(
name|tmpFile
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|actual
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|tmpFile
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|expected
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/net/sf/jabref/defaultPreferences.pref"
argument_list|)
argument_list|)
decl_stmt|;
name|Files
operator|.
name|delete
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|tmpFile
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

