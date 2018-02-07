begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.io
package|package
name|org
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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"ii"
argument_list|,
literal|"hh"
argument_list|,
literal|"gg"
argument_list|)
argument_list|,
name|fh
operator|.
name|getHistory
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

