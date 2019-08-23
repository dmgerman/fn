begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
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
name|Collections
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
name|layout
operator|.
name|format
operator|.
name|FileLinkPreferences
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
name|layout
operator|.
name|format
operator|.
name|NameFormatterPreferences
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
name|LinkedFile
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|types
operator|.
name|StandardEntryType
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
name|types
operator|.
name|UnknownEntryType
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
name|mockito
operator|.
name|Answers
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
name|mockito
operator|.
name|Mockito
operator|.
name|mock
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
DECL|class|LayoutTest
class|class
name|LayoutTest
block|{
DECL|field|layoutFormatterPreferences
specifier|private
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
block|{
name|layoutFormatterPreferences
operator|=
name|mock
argument_list|(
name|LayoutFormatterPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
expr_stmt|;
block|}
DECL|method|layout (String layout, BibEntry entry)
specifier|private
name|String
name|layout
parameter_list|(
name|String
name|layout
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
block|{
name|StringReader
name|layoutStringReader
init|=
operator|new
name|StringReader
argument_list|(
name|layout
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|LayoutHelper
argument_list|(
name|layoutStringReader
argument_list|,
name|layoutFormatterPreferences
argument_list|)
operator|.
name|getLayoutFromText
argument_list|()
operator|.
name|doLayout
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Test
DECL|method|entryTypeForUnknown ()
name|void
name|entryTypeForUnknown
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
operator|new
name|UnknownEntryType
argument_list|(
literal|"unknown"
argument_list|)
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"test"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Unknown"
argument_list|,
name|layout
argument_list|(
literal|"\\bibtextype"
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|entryTypeForArticle ()
name|void
name|entryTypeForArticle
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"test"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Article"
argument_list|,
name|layout
argument_list|(
literal|"\\bibtextype"
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|entryTypeForMisc ()
name|void
name|entryTypeForMisc
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Misc
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"test"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Misc"
argument_list|,
name|layout
argument_list|(
literal|"\\bibtextype"
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|HTMLChar ()
name|void
name|HTMLChar
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"This\nis\na\ntext"
argument_list|)
decl_stmt|;
name|String
name|actual
init|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[HTMLChars]{\\author}\\end{author}"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"This<br>is<br>a<br>text"
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|HTMLCharWithDoubleLineBreak ()
name|void
name|HTMLCharWithDoubleLineBreak
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"This\nis\na\n\ntext"
argument_list|)
decl_stmt|;
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[HTMLChars]{\\author}\\end{author} "
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"This<br>is<br>a<p>text "
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|nameFormatter ()
name|void
name|nameFormatter
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Joe Doe and Jane, Moon"
argument_list|)
decl_stmt|;
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[NameFormatter]{\\author}\\end{author}"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Joe Doe, Moon Jane"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|HTMLCharsWithDotlessIAndTiled ()
name|void
name|HTMLCharsWithDotlessIAndTiled
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
literal|"\\~{n} \\~n \\'i \\i \\i"
argument_list|)
decl_stmt|;
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"<font face=\"arial\"><BR><BR><b>Abstract:</b>&ntilde;&ntilde;&iacute;&imath;&imath;</font>"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test for http://discourse.jabref.org/t/the-wrapfilelinks-formatter/172 (the example in the help files)      */
annotation|@
name|Test
DECL|method|wrapFileLinksExpandFile ()
name|void
name|wrapFileLinksExpandFile
parameter_list|()
throws|throws
name|IOException
block|{
name|when
argument_list|(
name|layoutFormatterPreferences
operator|.
name|getFileLinkPreferences
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
operator|new
name|FileLinkPreferences
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"src/test/resources/pdfs/"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|entry
operator|.
name|addFile
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|"Test file"
argument_list|,
literal|"encrypted.pdf"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\begin{file}\\format[WrapFileLinks(\\i. \\d (\\p))]{\\file}\\end{file}"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"1. Test file ("
operator|+
operator|new
name|File
argument_list|(
literal|"src/test/resources/pdfs/encrypted.pdf"
argument_list|)
operator|.
name|getCanonicalPath
argument_list|()
operator|+
literal|")"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|expandCommandIfTerminatedByMinus ()
name|void
name|expandCommandIfTerminatedByMinus
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|EDITION
argument_list|,
literal|"2"
argument_list|)
decl_stmt|;
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\edition-th ed.-"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"2-th ed.-"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|customNameFormatter ()
name|void
name|customNameFormatter
parameter_list|()
throws|throws
name|IOException
block|{
name|when
argument_list|(
name|layoutFormatterPreferences
operator|.
name|getNameFormatterPreferences
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
operator|new
name|NameFormatterPreferences
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"DCA"
argument_list|)
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"1@*@{ll}@@2@1..1@{ff}{ll}@2..2@ and {ff}{l}@@*@*@more"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
operator|.
name|withField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Joe Doe and Mary Jane"
argument_list|)
decl_stmt|;
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[DCA]{\\author}\\end{author}"
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"JoeDoe and MaryJ"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

