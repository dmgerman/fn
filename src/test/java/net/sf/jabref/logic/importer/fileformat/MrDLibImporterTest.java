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
name|logic
operator|.
name|importer
operator|.
name|ParserResult
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
name|logic
operator|.
name|util
operator|.
name|FileExtensions
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|model
operator|.
name|entry
operator|.
name|FieldName
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
DECL|field|inputMin
specifier|private
name|BufferedReader
name|inputMin
decl_stmt|;
DECL|field|inputMax
specifier|private
name|BufferedReader
name|inputMax
decl_stmt|;
annotation|@
name|Before
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
name|testMin
init|=
literal|"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><mr-dlib></mr-dlib>"
decl_stmt|;
name|String
name|testMax
init|=
literal|"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><mr-dlib><related_articles set_id=\"28184\" suggested_label=\"Related Articles\"><related_article document_id=\"2250539\" original_document_id=\"gesis-solis-00538797\" recommendation_id=\"204944\"><authors/><click_url>https://api-dev.mr-dlib.org/v1/recommendations/204944/original_url?access_key=99ab2fc64f3228ab839e9e3525ac37f8&format=direct_url_forward</click_url><debug_details><bibDocId>0</bibDocId><bibScore>2.0</bibScore><finalScore>2.0</finalScore><rankAfterAlgorithm>3</rankAfterAlgorithm><rankAfterReRanking>3</rankAfterReRanking><rankAfterShuffling>2</rankAfterShuffling><rankDelivered>2</rankDelivered><relevanceScoreFromAlgorithm>1.0</relevanceScoreFromAlgorithm></debug_details><fallback_url>http://sowiport.gesis.org/search/id/gesis-solis-00538797</fallback_url><published_in>Fachhochschulverl.</published_in><snippet format=\"html_plain\"><![CDATA[<a href='https://api-dev.mr-dlib.org/v1/recommendations/204944/original_url?access_key=99ab2fc64f3228ab839e9e3525ac37f8&format=direct_url_forward'>Gesundheit von Arbeitslosen fÃ¶rdern!: ein Handbuch fÃ¼r Wissenschaft und Praxis</a>. . Fachhochschulverl.. 2009.]]></snippet><snippet format=\"html_fully_formatted\"><![CDATA[<a href='https://api-dev.mr-dlib.org/v1/recommendations/204944/original_url?access_key=99ab2fc64f3228ab839e9e3525ac37f8&format=direct_url_forward'><font color='#000000' size='5' face='Arial, Helvetica, sans-serif'>Gesundheit von Arbeitslosen fÃ¶rdern!: ein Handbuch fÃ¼r Wissenschaft und Praxis.</font></a><font color='#000000' size='5' face='Arial, Helvetica, sans-serif'>.<i>Fachhochschulverl.</i>. 2009.</font>]]></snippet><snippet format=\"html_and_css\"><![CDATA[<span class='mdl-title'>Gesundheit von Arbeitslosen fÃ¶rdern!: ein Handbuch fÃ¼r Wissenschaft und Praxis</span>.<span class='mdl-authors'></span>.<span class='mdl-journal'>Fachhochschulverl.</span>.<span class='mdl-year'>2009</span>]]></snippet><suggested_rank>2</suggested_rank><title>Gesundheit von Arbeitslosen fÃ¶rdern!: ein Handbuch fÃ¼r Wissenschaft und Praxis</title><year>2009</year></related_article></related_articles></mr-dlib>"
decl_stmt|;
name|testMax
operator|=
name|testMax
operator|.
name|replaceAll
argument_list|(
literal|"&"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|inputMin
operator|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|testMin
argument_list|)
argument_list|)
expr_stmt|;
name|inputMax
operator|=
operator|new
name|BufferedReader
argument_list|(
operator|new
name|StringReader
argument_list|(
name|testMax
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
literal|"Takes valid xml documents. Parses from MrDLib API a BibEntry"
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
name|FileExtensions
operator|.
name|XML
argument_list|,
name|importer
operator|.
name|getExtensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportDatabaseIsHtmlSetCorrectly ()
specifier|public
name|void
name|testImportDatabaseIsHtmlSetCorrectly
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
name|inputMax
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
literal|"<a href='https://api-dev.mr-dlib.org/v1/recommendations/204944/original_url?access_key=99ab2fc64f3228ab839e9e3525ac37f8format=direct_url_forward'><font color='#000000' size='5' face='Arial, Helvetica, sans-serif'>Gesundheit von Arbeitslosen fÃ¶rdern!: ein Handbuch fÃ¼r Wissenschaft und Praxis.</font></a><font color='#000000' size='5' face='Arial, Helvetica, sans-serif'>.<i>Fachhochschulverl.</i>. 2009.</font>"
argument_list|,
name|resultList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getField
argument_list|(
literal|"html_representation"
argument_list|)
operator|.
name|get
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
name|inputMax
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
literal|"2009"
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
name|FieldName
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
name|inputMax
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
literal|"Gesundheit von Arbeitslosen fÃ¶rdern!: ein Handbuch fÃ¼r Wissenschaft und Praxis"
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
name|FieldName
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
name|inputMin
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
literal|0
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

