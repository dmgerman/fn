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
name|JabRefPreferences
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
name|OutputPrinterToNull
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
name|InputStream
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
name|assertTrue
import|;
end_import

begin_comment
comment|/**  * This class tests the BibtexImporter.  * That importer is only used for --importToOpen, which is currently untested  *<p>  * TODO:  * 1. Add test for --importToOpen  * 2. Move these tests to the code opening a bibtex file  */
end_comment

begin_class
DECL|class|BibtexImporterTest
specifier|public
class|class
name|BibtexImporterTest
block|{
DECL|field|importer
specifier|private
name|BibtexImporter
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
name|BibtexImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormat ()
specifier|public
name|void
name|testIsRecognizedFormat
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|BibtexImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"BibtexImporter.examples.bib"
argument_list|)
init|)
block|{
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testImportEntries ()
specifier|public
name|void
name|testImportEntries
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|BibtexImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"BibtexImporter.examples.bib"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bibEntries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|stream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|4
argument_list|,
name|bibEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|bibEntries
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"aksin"
argument_list|)
condition|)
block|{
name|assertEquals
argument_list|(
literal|"Aks{\\i}n, {\\\"O}zge and T{\\\"u}rkmen, Hayati and Artok, Levent and {\\c{C}}etinkaya, "
operator|+
literal|"Bekir and Ni, Chaoying and B{\\\"u}y{\\\"u}kg{\\\"u}ng{\\\"o}r, Orhan and {\\\"O}zkal, Erhan"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"aksin"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"bibtexkey"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"2006"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"date"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Effect of immobilization on catalytic characteristics"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"indextitle"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#jomch#"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"journaltitle"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"13"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"number"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"3027-3036"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Effect of immobilization on catalytic characteristics of saturated {Pd-N}-heterocyclic "
operator|+
literal|"carbenes in {Mizoroki-Heck} reactions"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"691"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"stdmodel"
argument_list|)
condition|)
block|{
name|assertEquals
argument_list|(
literal|"A \\texttt{set} with three members discussing the standard model of particle physics. "
operator|+
literal|"The \\texttt{crossref} field in the \\texttt{@set} entry and the \\texttt{entryset} field in "
operator|+
literal|"each set member entry is needed only when using BibTeX as the backend"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"annotation"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"stdmodel"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"bibtexkey"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"glashow,weinberg,salam"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"entryset"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"set"
argument_list|)
condition|)
block|{
name|assertEquals
argument_list|(
literal|"A \\texttt{set} with three members. The \\texttt{crossref} field in the \\texttt{@set} "
operator|+
literal|"entry and the \\texttt{entryset} field in each set member entry is needed only when using "
operator|+
literal|"BibTeX as the backend"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"annotation"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"set"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"bibtexkey"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"herrmann,aksin,yoon"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"entryset"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|.
name|equals
argument_list|(
literal|"Preissel2016"
argument_list|)
condition|)
block|{
name|assertEquals
argument_list|(
literal|"Heidelberg"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"PreiÃel, RenÃ©"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Preissel2016"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"bibtexkey"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"3., aktualisierte und erweiterte Auflage"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"edition"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"978-3-86490-311-3"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"isbn"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Versionsverwaltung"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"keywords"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"XX, 327 Seiten"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"dpunkt.verlag"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Git: dezentrale Versionsverwaltung im Team : Grundlagen und Workflows"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"http://d-nb.info/107601965X"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"url"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"2016"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testGetFormatName ()
specifier|public
name|void
name|testGetFormatName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"BibTeX"
argument_list|,
name|importer
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetExtensions ()
specifier|public
name|void
name|testGetExtensions
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"bib"
argument_list|,
name|importer
operator|.
name|getExtensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

