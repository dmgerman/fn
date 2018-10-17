begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.protectedterms
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|protectedterms
package|;
end_package

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
name|Path
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
name|BeforeEach
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
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|extension
operator|.
name|ExtendWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junitpioneer
operator|.
name|jupiter
operator|.
name|TempDirectory
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
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
annotation|@
name|ExtendWith
argument_list|(
name|TempDirectory
operator|.
name|class
argument_list|)
DECL|class|ProtectedTermsListTest
specifier|public
class|class
name|ProtectedTermsListTest
block|{
DECL|field|internalList
specifier|private
name|ProtectedTermsList
name|internalList
decl_stmt|;
DECL|field|externalList
specifier|private
name|ProtectedTermsList
name|externalList
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp (@empDirectory.TempDir Path temporaryFolder)
specifier|public
name|void
name|setUp
parameter_list|(
annotation|@
name|TempDirectory
operator|.
name|TempDir
name|Path
name|temporaryFolder
parameter_list|)
throws|throws
name|IOException
block|{
name|Path
name|path
init|=
name|temporaryFolder
operator|.
name|resolve
argument_list|(
literal|"ThisIsARandomlyNamedFile"
argument_list|)
decl_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|String
name|tempFileName
init|=
name|path
operator|.
name|toString
argument_list|()
decl_stmt|;
name|internalList
operator|=
operator|new
name|ProtectedTermsList
argument_list|(
literal|"Name"
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"AAA"
argument_list|,
literal|"BBB"
argument_list|)
argument_list|)
argument_list|,
literal|"location"
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|externalList
operator|=
operator|new
name|ProtectedTermsList
argument_list|(
literal|"Namely"
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"AAA"
argument_list|,
literal|"BBB"
argument_list|)
argument_list|)
argument_list|,
name|tempFileName
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testProtectedTermsListStringListOfStringStringBoolean ()
specifier|public
name|void
name|testProtectedTermsListStringListOfStringStringBoolean
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|internalList
operator|.
name|isInternalList
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testProtectedTermsListStringListOfStringString ()
specifier|public
name|void
name|testProtectedTermsListStringListOfStringString
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|externalList
operator|.
name|isInternalList
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDescription ()
specifier|public
name|void
name|testGetDescription
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Name"
argument_list|,
name|internalList
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetTermList ()
specifier|public
name|void
name|testGetTermList
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"AAA"
argument_list|,
literal|"BBB"
argument_list|)
argument_list|,
name|internalList
operator|.
name|getTermList
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLocation ()
specifier|public
name|void
name|testGetLocation
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"location"
argument_list|,
name|internalList
operator|.
name|getLocation
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetTermListing ()
specifier|public
name|void
name|testGetTermListing
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"AAA\nBBB"
argument_list|,
name|internalList
operator|.
name|getTermListing
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCompareTo ()
specifier|public
name|void
name|testCompareTo
parameter_list|()
block|{
name|assertEquals
argument_list|(
operator|-
literal|2
argument_list|,
name|internalList
operator|.
name|compareTo
argument_list|(
name|externalList
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetEnabledIsEnabled ()
specifier|public
name|void
name|testSetEnabledIsEnabled
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|internalList
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|internalList
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|internalList
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNotEnabledByDefault ()
specifier|public
name|void
name|testNotEnabledByDefault
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|internalList
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCanNotAddTermToInternalList ()
specifier|public
name|void
name|testCanNotAddTermToInternalList
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|internalList
operator|.
name|addProtectedTerm
argument_list|(
literal|"CCC"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTermNotAddedToInternalList ()
specifier|public
name|void
name|testTermNotAddedToInternalList
parameter_list|()
block|{
name|internalList
operator|.
name|addProtectedTerm
argument_list|(
literal|"CCC"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|internalList
operator|.
name|getTermList
argument_list|()
operator|.
name|contains
argument_list|(
literal|"CCC"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCanAddTermToExternalList ()
specifier|public
name|void
name|testCanAddTermToExternalList
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|externalList
operator|.
name|addProtectedTerm
argument_list|(
literal|"CCC"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTermAddedToExternalList ()
specifier|public
name|void
name|testTermAddedToExternalList
parameter_list|()
block|{
name|externalList
operator|.
name|addProtectedTerm
argument_list|(
literal|"CCC"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|externalList
operator|.
name|getTermList
argument_list|()
operator|.
name|contains
argument_list|(
literal|"CCC"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

