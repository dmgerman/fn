begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|BufferedReader
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
name|io
operator|.
name|StringReader
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
name|Collections
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
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

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|BibTeXMLImporterTestTypes
specifier|public
class|class
name|BibTeXMLImporterTestTypes
block|{
DECL|field|bibteXMLImporter
specifier|private
name|BibTeXMLImporter
name|bibteXMLImporter
decl_stmt|;
annotation|@
name|Parameter
argument_list|(
name|value
operator|=
literal|0
argument_list|)
DECL|field|bibteXMLType
specifier|public
name|String
name|bibteXMLType
decl_stmt|;
annotation|@
name|Parameter
argument_list|(
name|value
operator|=
literal|1
argument_list|)
DECL|field|expectedBibType
specifier|public
name|String
name|expectedBibType
decl_stmt|;
annotation|@
name|Parameters
DECL|method|types ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
index|[]
argument_list|>
name|types
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
index|[]
block|{
block|{
literal|"journal"
block|,
literal|"article"
block|}
block|,
block|{
literal|"book section"
block|,
literal|"inbook"
block|}
block|,
block|{
literal|"book"
block|,
literal|"book"
block|}
block|,
block|{
literal|"conference"
block|,
literal|"inproceedings"
block|}
block|,
block|{
literal|"proceedings"
block|,
literal|"inproceedings"
block|}
block|,
block|{
literal|"report"
block|,
literal|"techreport"
block|}
block|,
block|{
literal|"master thesis"
block|,
literal|"mastersthesis"
block|}
block|,
block|{
literal|"thesis"
block|,
literal|"phdthesis"
block|}
block|,
block|{
literal|"master"
block|,
literal|"misc"
block|}
block|}
argument_list|)
return|;
block|}
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|bibteXMLImporter
operator|=
operator|new
name|BibTeXMLImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|importConvertsToCorrectBibType ()
specifier|public
name|void
name|importConvertsToCorrectBibType
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|bibteXMLInput
init|=
literal|"<?xml version=\"1.0\" ?>\n"
operator|+
literal|"<bibtex:file xmlns:bibtex=\"http://bibtexml.sf.net/\">\n"
operator|+
literal|"<bibtex:entry>\n"
operator|+
literal|"<bibtex:"
operator|+
name|expectedBibType
operator|+
literal|">\n"
operator|+
literal|"<bibtex:author>Max Mustermann</bibtex:author>\n"
operator|+
literal|"<bibtex:keywords>java</bibtex:keywords>\n"
operator|+
literal|"<bibtex:title>Java tricks</bibtex:title>\n"
operator|+
literal|"<bibtex:year>2016</bibtex:year>\n"
operator|+
literal|"</bibtex:"
operator|+
name|expectedBibType
operator|+
literal|">\n"
operator|+
literal|"</bibtex:entry>\n"
operator|+
literal|"</bibtex:file>"
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
init|=
name|bibteXMLImporter
operator|.
name|importDatabase
argument_list|(
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|bibteXMLInput
argument_list|)
argument_list|)
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Max Mustermann"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"java"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Java tricks"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|expectedBibType
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|bibEntries
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

