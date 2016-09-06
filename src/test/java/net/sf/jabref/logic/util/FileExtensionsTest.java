begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util
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
package|;
end_package

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
name|Assert
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
DECL|class|FileExtensionsTest
specifier|public
class|class
name|FileExtensionsTest
block|{
annotation|@
name|Test
DECL|method|testSingleFileExtensionDescription ()
specifier|public
name|void
name|testSingleFileExtensionDescription
parameter_list|()
block|{
name|String
name|singleDescription
init|=
literal|"INSPEC file (*.txt)"
decl_stmt|;
name|assertEquals
argument_list|(
name|singleDescription
argument_list|,
name|FileExtensions
operator|.
name|INSPEC
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultiFileExtensionsDescription ()
specifier|public
name|void
name|testMultiFileExtensionsDescription
parameter_list|()
block|{
name|String
name|multiDescription
init|=
literal|"MedlinePlain file (*.nbib, *.txt)"
decl_stmt|;
name|assertEquals
argument_list|(
name|multiDescription
argument_list|,
name|FileExtensions
operator|.
name|MEDLINE_PLAIN
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testExtensions ()
specifier|public
name|void
name|testExtensions
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|extensions
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"nbib"
argument_list|,
literal|"txt"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertArrayEquals
argument_list|(
name|extensions
operator|.
name|toArray
argument_list|()
argument_list|,
name|FileExtensions
operator|.
name|MEDLINE_PLAIN
operator|.
name|getExtensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFirstExtensionWithDot ()
specifier|public
name|void
name|testFirstExtensionWithDot
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|".nbib"
argument_list|,
name|FileExtensions
operator|.
name|MEDLINE_PLAIN
operator|.
name|getFirstExtensionWithDot
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

