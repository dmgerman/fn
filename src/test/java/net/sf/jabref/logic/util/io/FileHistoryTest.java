begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.io
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|io
package|;
end_package

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
name|assertArrayEquals
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
DECL|class|FileHistoryTest
specifier|public
class|class
name|FileHistoryTest
block|{
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|oldFileNames
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|oldFileNames
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
name|oldFileNames
operator|=
name|prefs
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|RECENT_FILES
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|restore ()
specifier|public
name|void
name|restore
parameter_list|()
block|{
name|prefs
operator|.
name|putStringList
argument_list|(
name|JabRefPreferences
operator|.
name|RECENT_FILES
argument_list|,
name|oldFileNames
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFileHistory ()
specifier|public
name|void
name|testFileHistory
parameter_list|()
block|{
name|FileHistory
name|fh
init|=
operator|new
name|FileHistory
argument_list|(
name|prefs
argument_list|)
decl_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"aa"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aa"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"bb"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"bb"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"aa"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aa"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"cc"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"cc"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aa"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"bb"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"dd"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"dd"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"cc"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aa"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"ee"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"ff"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"gg"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"hh"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"bb"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|7
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|fh
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|fh
operator|.
name|newFile
argument_list|(
literal|"ii"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aa"
argument_list|,
name|fh
operator|.
name|getFileName
argument_list|(
literal|7
argument_list|)
argument_list|)
expr_stmt|;
name|fh
operator|.
name|removeItem
argument_list|(
literal|"ff"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|7
argument_list|,
name|fh
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|fh
operator|.
name|removeItem
argument_list|(
literal|"ee"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|removeItem
argument_list|(
literal|"dd"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|removeItem
argument_list|(
literal|"cc"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|removeItem
argument_list|(
literal|"cc"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|removeItem
argument_list|(
literal|"aa"
argument_list|)
expr_stmt|;
name|fh
operator|.
name|storeHistory
argument_list|()
expr_stmt|;
name|assertArrayEquals
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"ii"
block|,
literal|"hh"
block|,
literal|"gg"
block|}
argument_list|,
name|prefs
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|RECENT_FILES
argument_list|)
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

