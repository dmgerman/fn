begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
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
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Mockito
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|ImportFormatTest
specifier|public
class|class
name|ImportFormatTest
block|{
annotation|@
name|Parameter
DECL|field|format
specifier|public
name|ImportFormat
name|format
decl_stmt|;
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|isRecognizedFormatWithNullThrowsException ()
specifier|public
name|void
name|isRecognizedFormatWithNullThrowsException
parameter_list|()
throws|throws
name|IOException
block|{
name|format
operator|.
name|isRecognizedFormat
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|importDatabaseWithNullThrowsException ()
specifier|public
name|void
name|importDatabaseWithNullThrowsException
parameter_list|()
throws|throws
name|IOException
block|{
name|format
operator|.
name|importDatabase
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFormatterNameDoesNotReturnNull ()
specifier|public
name|void
name|getFormatterNameDoesNotReturnNull
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|format
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getExtensionsDoesNotReturnNull ()
specifier|public
name|void
name|getExtensionsDoesNotReturnNull
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|format
operator|.
name|getExtensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getIdDoesNotReturnNull ()
specifier|public
name|void
name|getIdDoesNotReturnNull
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|format
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getIdDoesNotContainWhitespace ()
specifier|public
name|void
name|getIdDoesNotContainWhitespace
parameter_list|()
block|{
name|Pattern
name|whitespacePattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\s"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|whitespacePattern
operator|.
name|matcher
argument_list|(
name|format
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|find
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getIdStripsSpecialCharactersAndConvertsToLowercase ()
specifier|public
name|void
name|getIdStripsSpecialCharactersAndConvertsToLowercase
parameter_list|()
block|{
name|ImportFormat
name|importFormat
init|=
name|Mockito
operator|.
name|mock
argument_list|(
name|ImportFormat
operator|.
name|class
argument_list|,
name|Mockito
operator|.
name|CALLS_REAL_METHODS
argument_list|)
decl_stmt|;
name|when
argument_list|(
name|importFormat
operator|.
name|getFormatName
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|"*Test-Importer"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"testimporter"
argument_list|,
name|importFormat
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getDescriptionDoesNotReturnNull ()
specifier|public
name|void
name|getDescriptionDoesNotReturnNull
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|format
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: {0}"
argument_list|)
DECL|method|instancesToTest ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|instancesToTest
parameter_list|()
block|{
comment|// all classes implementing {@link ImportFormat}
comment|// sorted alphabetically
comment|// @formatter:off
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|Object
index|[]
block|{
operator|new
name|BiblioscapeImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|BibtexImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|BibTeXMLImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|CopacImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|EndnoteImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|FreeCiteImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|InspecImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|IsiImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|MedlineImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|MedlinePlainImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|MsBibImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|OvidImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|PdfContentImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|PdfXmpImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|RepecNepImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|RisImporter
argument_list|()
block|}
argument_list|,
operator|new
name|Object
index|[]
block|{
operator|new
name|SilverPlatterImporter
argument_list|()
block|}
argument_list|)
return|;
comment|// @formatter:on
block|}
block|}
end_class

end_unit

