begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout
package|package
name|net
operator|.
name|sf
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|layout
operator|.
name|Layout
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
name|layout
operator|.
name|LayoutHelper
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
name|Ignore
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
name|StringReader
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

begin_class
DECL|class|LayoutTest
specifier|public
class|class
name|LayoutTest
block|{
comment|/**      * Initialize Preferences.      */
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
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
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
block|}
name|Globals
operator|.
name|journalAbbreviationLoader
operator|=
name|mock
argument_list|(
name|JournalAbbreviationLoader
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
comment|/**      * Return Test data.      */
DECL|method|t1BibtexString ()
specifier|public
name|String
name|t1BibtexString
parameter_list|()
block|{
return|return
literal|"@article{canh05,\n"
operator|+
literal|"  author = {This\nis\na\ntext},\n"
operator|+
literal|"  title = {Effective work practices for floss development: A model and propositions},\n"
operator|+
literal|"  booktitle = {Hawaii International Conference On System Sciences (HICSS)},\n"
operator|+
literal|"  year = {2005},\n"
operator|+
literal|"  owner = {oezbek},\n"
operator|+
literal|"  timestamp = {2006.05.29},\n"
operator|+
literal|"  url = {http://james.howison.name/publications.html},\n"
operator|+
literal|"  abstract = {\\~{n}\n"
operator|+
literal|"\\~n\n"
operator|+
literal|"\\'i\n"
operator|+
literal|"\\i\n"
operator|+
literal|"\\i}\n"
operator|+
literal|"}\n"
return|;
block|}
DECL|method|bibtexString2BibtexEntry (String s)
specifier|public
specifier|static
name|BibEntry
name|bibtexString2BibtexEntry
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|StringReader
argument_list|(
name|s
argument_list|)
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|c
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|c
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|c
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
return|;
block|}
DECL|method|layout (String layoutFile, String entry)
specifier|public
name|String
name|layout
parameter_list|(
name|String
name|layoutFile
parameter_list|,
name|String
name|entry
parameter_list|)
throws|throws
name|Exception
block|{
name|BibEntry
name|be
init|=
name|LayoutTest
operator|.
name|bibtexString2BibtexEntry
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|StringReader
name|sr
init|=
operator|new
name|StringReader
argument_list|(
name|layoutFile
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
decl_stmt|;
name|Layout
name|layout
init|=
operator|new
name|LayoutHelper
argument_list|(
name|sr
argument_list|)
operator|.
name|getLayoutFromText
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|getRepository
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|layout
operator|.
name|doLayout
argument_list|(
name|be
argument_list|,
literal|null
argument_list|)
return|;
block|}
annotation|@
name|Test
DECL|method|testLayoutBibtextype ()
specifier|public
name|void
name|testLayoutBibtextype
parameter_list|()
throws|throws
name|Exception
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Unknown"
argument_list|,
name|layout
argument_list|(
literal|"\\bibtextype"
argument_list|,
literal|"@unknown{bla, author={This\nis\na\ntext}}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Article"
argument_list|,
name|layout
argument_list|(
literal|"\\bibtextype"
argument_list|,
literal|"@article{bla, author={This\nis\na\ntext}}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Misc"
argument_list|,
name|layout
argument_list|(
literal|"\\bibtextype"
argument_list|,
literal|"@misc{bla, author={This\nis\na\ntext}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testHTMLChar ()
specifier|public
name|void
name|testHTMLChar
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[HTMLChars]{\\author}\\end{author} "
argument_list|,
literal|"@other{bla, author={This\nis\na\ntext}}"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"This is a text "
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
name|layoutText
operator|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[HTMLChars]{\\author}\\end{author}"
argument_list|,
literal|"@other{bla, author={This\nis\na\ntext}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"This is a text"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
name|layoutText
operator|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[HTMLChars]{\\author}\\end{author} "
argument_list|,
literal|"@other{bla, author={This\nis\na\n\ntext}}"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"This is a<br>text "
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPluginLoading ()
specifier|public
name|void
name|testPluginLoading
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"\\begin{author}\\format[NameFormatter]{\\author}\\end{author}"
argument_list|,
literal|"@other{bla, author={Joe Doe and Jane, Moon}}"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Joe Doe, Moon Jane"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
comment|/**      * [ 1495181 ] Dotless i and tilde not handled in preview      *      * @throws Exception      */
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testLayout ()
specifier|public
name|void
name|testLayout
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|layoutText
init|=
name|layout
argument_list|(
literal|"<font face=\"arial\">\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract}\\end{abstract}</font>"
argument_list|,
name|t1BibtexString
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<font face=\"arial\"><BR><BR><b>Abstract:</b>&ntilde;&ntilde;&iacute;&#305;&#305;</font>"
argument_list|,
name|layoutText
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

