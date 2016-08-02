begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|importer
operator|.
name|fileformat
operator|.
name|BibTeXMLImporter
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
name|CopacImporter
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
name|OvidImporter
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertNotEquals
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
DECL|class|CustomImporterTest
specifier|public
class|class
name|CustomImporterTest
block|{
DECL|field|importer
specifier|private
name|CustomImporter
name|importer
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
name|importer
operator|=
operator|new
name|CustomImporter
argument_list|(
operator|new
name|CopacImporter
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetName ()
specifier|public
name|void
name|testGetName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Copac"
argument_list|,
name|importer
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCliId ()
specifier|public
name|void
name|testGetCliId
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"cpc"
argument_list|,
name|importer
operator|.
name|getClidId
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetClassName ()
specifier|public
name|void
name|testGetClassName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"net.sf.jabref.importer.fileformat.CopacImporter"
argument_list|,
name|importer
operator|.
name|getClassName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetBasePath ()
specifier|public
name|void
name|testGetBasePath
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"src/main/java/net/sf/jabref/importer/fileformat/CopacImporter.java"
argument_list|,
name|importer
operator|.
name|getBasePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetInstance ()
specifier|public
name|void
name|testGetInstance
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|CopacImporter
argument_list|()
argument_list|,
name|importer
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileFromBasePath ()
specifier|public
name|void
name|testGetFileFromBasePath
parameter_list|()
block|{
name|assertEquals
argument_list|(
operator|new
name|File
argument_list|(
literal|"src/main/java/net/sf/jabref/importer/fileformat/CopacImporter.java"
argument_list|)
argument_list|,
name|importer
operator|.
name|getFileFromBasePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetBasePathUrl ()
specifier|public
name|void
name|testGetBasePathUrl
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|File
argument_list|(
literal|"src/main/java/net/sf/jabref/importer/fileformat/CopacImporter.java"
argument_list|)
operator|.
name|toURI
argument_list|()
operator|.
name|toURL
argument_list|()
argument_list|,
name|importer
operator|.
name|getBasePathUrl
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetAsStringList ()
specifier|public
name|void
name|testGetAsStringList
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Copac"
argument_list|,
name|importer
operator|.
name|getAsStringList
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"cpc"
argument_list|,
name|importer
operator|.
name|getAsStringList
argument_list|()
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"net.sf.jabref.importer.fileformat.CopacImporter"
argument_list|,
name|importer
operator|.
name|getAsStringList
argument_list|()
operator|.
name|get
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"src/main/java/net/sf/jabref/importer/fileformat/CopacImporter.java"
argument_list|,
name|importer
operator|.
name|getAsStringList
argument_list|()
operator|.
name|get
argument_list|(
literal|3
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEqualsTrue ()
specifier|public
name|void
name|testEqualsTrue
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|importer
argument_list|,
name|importer
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEqualsFalse ()
specifier|public
name|void
name|testEqualsFalse
parameter_list|()
block|{
name|assertNotEquals
argument_list|(
operator|new
name|CopacImporter
argument_list|()
argument_list|,
name|importer
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCompareToSmaller ()
specifier|public
name|void
name|testCompareToSmaller
parameter_list|()
block|{
name|CustomImporter
name|ovidImporter
init|=
operator|new
name|CustomImporter
argument_list|(
operator|new
name|OvidImporter
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|importer
operator|.
name|compareTo
argument_list|(
name|ovidImporter
argument_list|)
operator|<
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCompareToGreater ()
specifier|public
name|void
name|testCompareToGreater
parameter_list|()
block|{
name|CustomImporter
name|bibtexmlImporter
init|=
operator|new
name|CustomImporter
argument_list|(
operator|new
name|BibTeXMLImporter
argument_list|()
argument_list|)
decl_stmt|;
name|CustomImporter
name|ovidImporter
init|=
operator|new
name|CustomImporter
argument_list|(
operator|new
name|OvidImporter
argument_list|()
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|ovidImporter
operator|.
name|compareTo
argument_list|(
name|bibtexmlImporter
argument_list|)
operator|>
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCompareToEven ()
specifier|public
name|void
name|testCompareToEven
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|importer
operator|.
name|compareTo
argument_list|(
operator|new
name|CustomImporter
argument_list|(
operator|new
name|CopacImporter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testToString ()
specifier|public
name|void
name|testToString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Copac"
argument_list|,
name|importer
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testClassicConstructor ()
specifier|public
name|void
name|testClassicConstructor
parameter_list|()
block|{
name|CustomImporter
name|customImporter
init|=
operator|new
name|CustomImporter
argument_list|(
literal|"Copac"
argument_list|,
literal|"cpc"
argument_list|,
literal|"net.sf.jabref.importer.fileformat.CopacImporter"
argument_list|,
literal|"src/main/java/net/sf/jabref/importer/fileformat/CopacImporter.java"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|importer
argument_list|,
name|customImporter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testListConstructor ()
specifier|public
name|void
name|testListConstructor
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|dataTest
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"Ovid"
argument_list|,
literal|"ovid"
argument_list|,
literal|"net.sf.jabref.importer.fileformat.OvidImporter"
argument_list|,
literal|"src/main/java/net/sf/jabref/importer/fileformat/OvidImporter.java"
argument_list|)
decl_stmt|;
name|CustomImporter
name|customImporter
init|=
operator|new
name|CustomImporter
argument_list|(
name|dataTest
argument_list|)
decl_stmt|;
name|CustomImporter
name|customOvidImporter
init|=
operator|new
name|CustomImporter
argument_list|(
operator|new
name|OvidImporter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|customImporter
argument_list|,
name|customOvidImporter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEmptyConstructor ()
specifier|public
name|void
name|testEmptyConstructor
parameter_list|()
block|{
name|CustomImporter
name|customImporter
init|=
operator|new
name|CustomImporter
argument_list|()
decl_stmt|;
name|customImporter
operator|.
name|setName
argument_list|(
literal|"Ovid"
argument_list|)
expr_stmt|;
name|customImporter
operator|.
name|setCliId
argument_list|(
literal|"ovid"
argument_list|)
expr_stmt|;
name|customImporter
operator|.
name|setClassName
argument_list|(
literal|"net.sf.jabref.importer.fileformat.OvidImporter"
argument_list|)
expr_stmt|;
name|customImporter
operator|.
name|setBasePath
argument_list|(
literal|"src/main/java/net/sf/jabref/importer/fileformat/OvidImporter.java"
argument_list|)
expr_stmt|;
name|CustomImporter
name|customOvidImporter
init|=
operator|new
name|CustomImporter
argument_list|(
operator|new
name|OvidImporter
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|customImporter
argument_list|,
name|customOvidImporter
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
