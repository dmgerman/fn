begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|ISSNTest
specifier|public
class|class
name|ISSNTest
block|{
annotation|@
name|Test
DECL|method|testIsCanBeCleaned ()
specifier|public
name|void
name|testIsCanBeCleaned
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"00279633"
argument_list|)
operator|.
name|isCanBeCleaned
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsCanBeCleanedIncorrectRubbish ()
specifier|public
name|void
name|testIsCanBeCleanedIncorrectRubbish
parameter_list|()
block|{
name|assertFalse
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"A brown fox"
argument_list|)
operator|.
name|isCanBeCleaned
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsCanBeCleanedDashAlreadyThere ()
specifier|public
name|void
name|testIsCanBeCleanedDashAlreadyThere
parameter_list|()
block|{
name|assertFalse
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"0027-9633"
argument_list|)
operator|.
name|isCanBeCleaned
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCleanedISSN ()
specifier|public
name|void
name|testGetCleanedISSN
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0027-9633"
argument_list|,
operator|new
name|ISSN
argument_list|(
literal|"00279633"
argument_list|)
operator|.
name|getCleanedISSN
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCleanedISSNDashAlreadyThere ()
specifier|public
name|void
name|testGetCleanedISSNDashAlreadyThere
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0027-9633"
argument_list|,
operator|new
name|ISSN
argument_list|(
literal|"0027-9633"
argument_list|)
operator|.
name|getCleanedISSN
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCleanedISSNDashRubbish ()
specifier|public
name|void
name|testGetCleanedISSNDashRubbish
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"A brown fox"
argument_list|,
operator|new
name|ISSN
argument_list|(
literal|"A brown fox"
argument_list|)
operator|.
name|getCleanedISSN
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidChecksumCorrect ()
specifier|public
name|void
name|testIsValidChecksumCorrect
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"0027-9633"
argument_list|)
operator|.
name|isValidChecksum
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"2434-561X"
argument_list|)
operator|.
name|isValidChecksum
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"2434-561x"
argument_list|)
operator|.
name|isValidChecksum
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidChecksumIncorrect ()
specifier|public
name|void
name|testIsValidChecksumIncorrect
parameter_list|()
block|{
name|assertFalse
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"0027-9634"
argument_list|)
operator|.
name|isValidChecksum
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidFormatCorrect ()
specifier|public
name|void
name|testIsValidFormatCorrect
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"0027-963X"
argument_list|)
operator|.
name|isValidFormat
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidFormatIncorrect ()
specifier|public
name|void
name|testIsValidFormatIncorrect
parameter_list|()
block|{
name|assertFalse
argument_list|(
operator|new
name|ISSN
argument_list|(
literal|"00279634"
argument_list|)
operator|.
name|isValidFormat
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

