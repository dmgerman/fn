begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
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
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|StandardFileType
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
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
name|assertSame
import|;
end_import

begin_class
DECL|class|MrDLibImporterTest
specifier|public
class|class
name|MrDLibImporterTest
block|{
DECL|field|importer
specifier|private
name|MrDLibImporter
name|importer
decl_stmt|;
DECL|field|input
specifier|private
name|BufferedReader
name|input
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|importer
operator|=
operator|new
name|MrDLibImporter
argument_list|()
expr_stmt|;
name|String
name|testInput
init|=
literal|"{\"label\": {\"label-description\": \"The following articles are similar to the document have currently selected.\", \"label-language\": \"en\", \"label-text\": \"Related Articles\"},    \"recommendation_set_id\": \"1\",    \"recommendations\": {        \"74021358\": {            \"abstract\": \"abstract\",            \"authors\":\"Sajovic, Marija\",            \"published_year\": \"2006\",            \"item_id_original\": \"12088644\",            \"keywords\": [                \"visoko\\u0161olski program Geodezija - smer Prostorska informatika\"            ],            \"language_provided\": \"sl\",            \"recommendation_id\": \"1\",            \"title\": \"The protection of rural lands with the spatial development strategy on the case of Hrastnik commune\",            \"url\": \"http://drugg.fgg.uni-lj.si/701/1/GEV_0199_Sajovic.pdf\"        },        \"82005804\": {            \"abstract\": \"abstract\",            \"year_published\": null,            \"item_id_original\": \"30145702\",            \"language_provided\": null,            \"recommendation_id\": \"2\",            \"title\": \"Engagement of the volunteers in the solution to the accidents in the South-Moravia region\"        },        \"82149599\": {            \"abstract\": \"abstract\",            \"year_published\": null,            \"item_id_original\": \"97690763\",            \"language_provided\": null,            \"recommendation_id\": \"3\",            \"title\": \"\\\"The only Father's word\\\". The relationship of the Father and the Son in the documents of saint John of the Cross\",            \"url\": \"http://www.nusl.cz/ntk/nusl-285711\"        },        \"84863921\": {            \"abstract\": \"abstract\",            \"authors\":\"Kaffa, Elena\",            \"year_published\": null,            \"item_id_original\": \"19397104\",            \"keywords\": [                \"BX\",                \"D111\"            ],            \"language_provided\": \"en\",            \"recommendation_id\": \"4\",            \"title\": \"Greek Church of Cyprus, the Morea and Constantinople during the Frankish Era (1196-1303)\"        },        \"88950992\": {            \"abstract\": \"abstract\",            \"authors\":\"Yasui, Kono\",            \"year_published\": null,            \"item_id_original\": \"38763657\",            \"language_provided\": null,            \"recommendation_id\": \"5\",            \"title\": \"A Phylogenetic Consideration on the Vascular Plants, Cotyledonary Node Including Hypocotyl Being Taken as the Ancestral Form : A Preliminary Note\"        }    }}"
decl_stmt|;
name|input
operator|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|testInput
argument_list|)
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
literal|"Takes valid JSON documents from the Mr. DLib API and parses them into a BibEntry"
argument_list|,
name|importer
operator|.
name|getDescription
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
literal|"MrDLibImporter"
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
DECL|method|testGetFileExtention ()
specifier|public
name|void
name|testGetFileExtention
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|StandardFileType
operator|.
name|JSON
argument_list|,
name|importer
operator|.
name|getFileType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportDatabaseIsYearSetCorrectly ()
specifier|public
name|void
name|testImportDatabaseIsYearSetCorrectly
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|parserResult
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|resultList
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|resultList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getLatexFreeField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportDatabaseIsTitleSetCorrectly ()
specifier|public
name|void
name|testImportDatabaseIsTitleSetCorrectly
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|parserResult
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|resultList
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"The protection of rural lands with the spatial development strategy on the case of Hrastnik commune"
argument_list|,
name|resultList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getLatexFreeField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportDatabaseMin ()
specifier|public
name|void
name|testImportDatabaseMin
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|parserResult
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|input
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|resultList
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|assertSame
argument_list|(
literal|5
argument_list|,
name|resultList
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

