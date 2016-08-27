begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.integrity
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
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
name|java
operator|.
name|util
operator|.
name|Optional
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
name|Defaults
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
name|database
operator|.
name|BibDatabase
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
name|database
operator|.
name|BibDatabaseContext
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
name|database
operator|.
name|BibDatabaseMode
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
name|InternalBibtexFields
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
name|metadata
operator|.
name|MetaData
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
name|Rule
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
name|rules
operator|.
name|TemporaryFolder
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
name|mockito
operator|.
name|Matchers
operator|.
name|any
import|;
end_import

begin_class
DECL|class|IntegrityCheckTest
specifier|public
class|class
name|IntegrityCheckTest
block|{
annotation|@
name|Rule
DECL|field|testFolder
specifier|public
name|TemporaryFolder
name|testFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|testUrlChecks ()
specifier|public
name|void
name|testUrlChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"http://www.google.com"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"file://c:/asdf/asdf"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"http://scikit-learn.org/stable/modules/ensemble.html#random-forests"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"www.google.com"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"google.com"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"c:/asdf/asdf"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testYearChecks ()
specifier|public
name|void
name|testYearChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"2014"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"around 1986"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"(around 1986)"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986,"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986}%"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986(){},.;!?<>%&$"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"abc"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"86"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"204"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"(1986a)"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986a,"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986}a%"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"year"
argument_list|,
literal|"1986a(){},.;!?<>%&$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEditionChecks ()
specifier|public
name|void
name|testEditionChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"Second"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"Third"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"second"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"2"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"2nd"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"2"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"10"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"Third, revised and expanded edition"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"Edition 2000"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"edition"
argument_list|,
literal|"2nd"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNoteChecks ()
specifier|public
name|void
name|testNoteChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"note"
argument_list|,
literal|"Lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"note"
argument_list|,
literal|"Lorem ipsum? 10"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"note"
argument_list|,
literal|"lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"note"
argument_list|,
literal|"Lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"note"
argument_list|,
literal|"lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHowpublishedChecks ()
specifier|public
name|void
name|testHowpublishedChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"howpublished"
argument_list|,
literal|"Lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"howpublished"
argument_list|,
literal|"Lorem ipsum? 10"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"howpublished"
argument_list|,
literal|"lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"howpublished"
argument_list|,
literal|"Lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"howpublished"
argument_list|,
literal|"lorem ipsum"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBracketChecks ()
specifier|public
name|void
name|testBracketChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"x"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{x}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{x}x{}x{{}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{x}x{}}x{{}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAuthorNameChecks ()
specifier|public
name|void
name|testAuthorNameChecks
parameter_list|()
block|{
for|for
control|(
name|String
name|field
range|:
name|InternalBibtexFields
operator|.
name|getPersonNameFields
argument_list|()
control|)
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Knuth"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"   Knuth, Donald E. "
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Knuth, Donald E. and Kurt Cobain and A. Einstein"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Donald E. Knuth and Kurt Cobain and A. Einstein"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|", and Kurt Cobain and A. Einstein"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Donald E. Knuth and Kurt Cobain and ,"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"and Kurt Cobain and A. Einstein"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Donald E. Knuth and Kurt Cobain and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testTitleChecks ()
specifier|public
name|void
name|testTitleChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a title"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a Title"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a {T}itle"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{This is a Title}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a {Title}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{C}urrent {C}hronicle"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{A Model-Driven Approach for Monitoring {ebBP} BusinessTransactions}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a title"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a Title"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a {T}itle"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{This is a Title}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"This is a {Title}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{C}urrent {C}hronicle"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"{A Model-Driven Approach for Monitoring {ebBP} BusinessTransactions}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAbbreviationChecks ()
specifier|public
name|void
name|testAbbreviationChecks
parameter_list|()
block|{
for|for
control|(
name|String
name|field
range|:
name|Arrays
operator|.
name|asList
argument_list|(
literal|"booktitle"
argument_list|,
literal|"journal"
argument_list|)
control|)
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Proceedings of the"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
name|field
argument_list|,
literal|"Proc. of the"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testFileChecks ()
specifier|public
name|void
name|testFileChecks
parameter_list|()
block|{
name|MetaData
name|metaData
init|=
name|Mockito
operator|.
name|mock
argument_list|(
name|MetaData
operator|.
name|class
argument_list|)
decl_stmt|;
name|Mockito
operator|.
name|when
argument_list|(
name|metaData
operator|.
name|getDefaultFileDirectory
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"."
argument_list|)
argument_list|)
expr_stmt|;
name|Mockito
operator|.
name|when
argument_list|(
name|metaData
operator|.
name|getUserFileDirectory
argument_list|(
name|any
argument_list|(
name|String
operator|.
name|class
argument_list|)
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
comment|// FIXME: must be set as checkBibtexDatabase only activates title checker based on database mode
name|Mockito
operator|.
name|when
argument_list|(
name|metaData
operator|.
name|getMode
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"file"
argument_list|,
literal|":build.gradle:gradle"
argument_list|,
name|metaData
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"file"
argument_list|,
literal|"description:build.gradle:gradle"
argument_list|,
name|metaData
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"file"
argument_list|,
literal|":asflakjfwofja:PDF"
argument_list|,
name|metaData
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|fileCheckFindsFilesRelativeToBibFile ()
specifier|public
name|void
name|fileCheckFindsFilesRelativeToBibFile
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|bibFile
init|=
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"lit.bib"
argument_list|)
decl_stmt|;
name|testFolder
operator|.
name|newFile
argument_list|(
literal|"file.pdf"
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|databaseContext
init|=
name|createContext
argument_list|(
literal|"file"
argument_list|,
literal|":file.pdf:PDF"
argument_list|)
decl_stmt|;
name|databaseContext
operator|.
name|setDatabaseFile
argument_list|(
name|bibFile
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTypeChecks ()
specifier|public
name|void
name|testTypeChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"11--15"
argument_list|,
literal|"inproceedings"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"11--15"
argument_list|,
literal|"proceedings"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBooktitleChecks ()
specifier|public
name|void
name|testBooktitleChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"booktitle"
argument_list|,
literal|"2014 Fourth International Conference on Digital Information and Communication Technology and it's Applications (DICTAP)"
argument_list|,
literal|"proceedings"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"booktitle"
argument_list|,
literal|"Digital Information and Communication Technology and it's Applications (DICTAP), 2014 Fourth International Conference on"
argument_list|,
literal|"proceedings"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPageNumbersChecks ()
specifier|public
name|void
name|testPageNumbersChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1--2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"12"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1-2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1,2,3"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"43+"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1 2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"{1}-{2}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7,41,73--97"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7,41--42,73"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7--11,41--43,73"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7+,41--43,73"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBiblatexPageNumbersChecks ()
specifier|public
name|void
name|testBiblatexPageNumbersChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1--2"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"12"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1-2"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
comment|// only diff to bibtex
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1,2,3"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"43+"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"1 2"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"{1}-{2}"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7,41,73--97"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7,41--42,73"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7--11,41--43,73"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|withMode
argument_list|(
name|createContext
argument_list|(
literal|"pages"
argument_list|,
literal|"7+,41--43,73"
argument_list|)
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBibStringChecks ()
specifier|public
name|void
name|testBibStringChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"Not a single hash mark"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"month"
argument_list|,
literal|"#jan#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"author"
argument_list|,
literal|"#einstein# and #newton#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"month"
argument_list|,
literal|"#jan"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"author"
argument_list|,
literal|"#einstein# #amp; #newton#"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testHTMLCharacterChecks ()
specifier|public
name|void
name|testHTMLCharacterChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"Not a single {HTML} character"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"month"
argument_list|,
literal|"#jan#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"author"
argument_list|,
literal|"A. Einstein and I. Newton"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"url"
argument_list|,
literal|"http://www.thinkmind.org/index.php?view=article&amp;articleid=cloud_computing_2013_1_20_20130"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"author"
argument_list|,
literal|"Lenhard, J&ouml;rg"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"author"
argument_list|,
literal|"Lenhard, J&#227;rg"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"journal"
argument_list|,
literal|"&Auml;rling Str&ouml;m for&#8211;&#x2031;"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testISSNChecks ()
specifier|public
name|void
name|testISSNChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"issn"
argument_list|,
literal|"0020-7217"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"issn"
argument_list|,
literal|"1687-6180"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"issn"
argument_list|,
literal|"2434-561x"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"issn"
argument_list|,
literal|"Some other stuff"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"issn"
argument_list|,
literal|"0020-7218"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testISBNChecks ()
specifier|public
name|void
name|testISBNChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"isbn"
argument_list|,
literal|"0-201-53082-1"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"isbn"
argument_list|,
literal|"0-9752298-0-X"
argument_list|)
argument_list|)
expr_stmt|;
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"isbn"
argument_list|,
literal|"978-0-306-40615-7"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"isbn"
argument_list|,
literal|"Some other stuff"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"isbn"
argument_list|,
literal|"0-201-53082-2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"isbn"
argument_list|,
literal|"978-0-306-40615-8"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testASCIIChecks ()
specifier|public
name|void
name|testASCIIChecks
parameter_list|()
block|{
name|assertCorrect
argument_list|(
name|createContext
argument_list|(
literal|"title"
argument_list|,
literal|"Only ascii characters!'@12"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"month"
argument_list|,
literal|"Umlauts are nÃ¶t Ã¤llowed"
argument_list|)
argument_list|)
expr_stmt|;
name|assertWrong
argument_list|(
name|createContext
argument_list|(
literal|"author"
argument_list|,
literal|"Some unicode â"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|createContext (String field, String value, String type)
specifier|private
name|BibDatabaseContext
name|createContext
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|,
name|String
name|type
parameter_list|)
block|{
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
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|BibDatabase
name|bibDatabase
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|bibDatabase
operator|.
name|insertEntryWithDuplicationCheck
argument_list|(
name|entry
argument_list|)
expr_stmt|;
return|return
operator|new
name|BibDatabaseContext
argument_list|(
name|bibDatabase
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
return|;
block|}
DECL|method|createContext (String field, String value, MetaData metaData)
specifier|private
name|BibDatabaseContext
name|createContext
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
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
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
name|BibDatabase
name|bibDatabase
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|bibDatabase
operator|.
name|insertEntryWithDuplicationCheck
argument_list|(
name|entry
argument_list|)
expr_stmt|;
return|return
operator|new
name|BibDatabaseContext
argument_list|(
name|bibDatabase
argument_list|,
name|metaData
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
return|;
block|}
DECL|method|createContext (String field, String value)
specifier|private
name|BibDatabaseContext
name|createContext
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|)
block|{
return|return
name|createContext
argument_list|(
name|field
argument_list|,
name|value
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|)
return|;
block|}
DECL|method|assertWrong (BibDatabaseContext context)
specifier|private
name|void
name|assertWrong
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
operator|new
name|IntegrityCheck
argument_list|(
name|context
argument_list|,
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
operator|.
name|checkBibtexDatabase
argument_list|()
decl_stmt|;
name|assertFalse
argument_list|(
name|messages
operator|.
name|toString
argument_list|()
argument_list|,
name|messages
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|assertCorrect (BibDatabaseContext context)
specifier|private
name|void
name|assertCorrect
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|)
block|{
name|List
argument_list|<
name|IntegrityMessage
argument_list|>
name|messages
init|=
operator|new
name|IntegrityCheck
argument_list|(
name|context
argument_list|,
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
operator|.
name|checkBibtexDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|messages
argument_list|)
expr_stmt|;
block|}
DECL|method|withMode (BibDatabaseContext context, BibDatabaseMode mode)
specifier|private
name|BibDatabaseContext
name|withMode
parameter_list|(
name|BibDatabaseContext
name|context
parameter_list|,
name|BibDatabaseMode
name|mode
parameter_list|)
block|{
name|context
operator|.
name|setMode
argument_list|(
name|mode
argument_list|)
expr_stmt|;
return|return
name|context
return|;
block|}
block|}
end_class

end_unit

