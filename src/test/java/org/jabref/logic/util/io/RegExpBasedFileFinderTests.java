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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|database
operator|.
name|BibDatabase
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
name|BibtexEntryTypes
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

begin_class
DECL|class|RegExpBasedFileFinderTests
specifier|public
class|class
name|RegExpBasedFileFinderTests
block|{
DECL|field|filesDirectory
specifier|private
specifier|static
specifier|final
name|String
name|filesDirectory
init|=
literal|"src/test/resources/org/jabref/logic/importer/unlinkedFilesTestFolder"
decl_stmt|;
DECL|field|database
specifier|private
name|BibDatabase
name|database
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"HipKro03"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Eric von Hippel and Georg von Krogh"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Organization Science"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2003"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"14"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"209--223"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
literal|"Institute for Operations Research and the Management Sciences (INFORMS), Linthicum, Maryland, USA"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"http://dx.doi.org/10.1287/orsc.14.2.209.14992"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"1526-5455"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"INFORMS"
argument_list|)
expr_stmt|;
name|database
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFindFiles ()
specifier|public
name|void
name|testFindFiles
parameter_list|()
block|{
comment|//given
name|BibEntry
name|localEntry
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|localEntry
operator|.
name|setCiteKey
argument_list|(
literal|"pdfInDatabase"
argument_list|)
expr_stmt|;
name|localEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|extensions
init|=
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"pdf"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|dirs
init|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|filesDirectory
argument_list|)
argument_list|)
decl_stmt|;
name|RegExpBasedFileFinder
name|fileFinder
init|=
operator|new
name|RegExpBasedFileFinder
argument_list|(
literal|"**/[bibtexkey].*\\\\.[extension]"
argument_list|,
literal|','
argument_list|)
decl_stmt|;
comment|//when
name|List
argument_list|<
name|Path
argument_list|>
name|result
init|=
name|fileFinder
operator|.
name|findAssociatedFiles
argument_list|(
name|localEntry
argument_list|,
name|dirs
argument_list|,
name|extensions
argument_list|)
decl_stmt|;
comment|//then
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/org/jabref/logic/importer/unlinkedFilesTestFolder/pdfInDatabase.pdf"
argument_list|)
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFieldAndFormat ()
specifier|public
name|void
name|testFieldAndFormat
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[author]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"author"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[unknownkey]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[:]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[:lower]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"eric von hippel and georg von krogh"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[author:lower]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"HipKro03"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[bibtexkey]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"HipKro03"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|getFieldAndFormat
argument_list|(
literal|"[bibtexkey:]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testExpandBrackets ()
specifier|public
name|void
name|testExpandBrackets
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|expandBrackets
argument_list|(
literal|""
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"dropped"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|expandBrackets
argument_list|(
literal|"drop[unknownkey]ped"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh"
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|expandBrackets
argument_list|(
literal|"[author]"
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh are two famous authors."
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|expandBrackets
argument_list|(
literal|"[author] are two famous authors."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh are two famous authors."
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|expandBrackets
argument_list|(
literal|"[author] are two famous authors."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Eric von Hippel and Georg von Krogh have published Open Source Software and the \"Private-Collective\" Innovation Model: Issues for Organization Science in Organization Science."
argument_list|,
name|RegExpBasedFileFinder
operator|.
name|expandBrackets
argument_list|(
literal|"[author] have published [title] in [journal]."
argument_list|,
name|entry
argument_list|,
name|database
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
