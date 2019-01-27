begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.migrations
package|package
name|org
operator|.
name|jabref
operator|.
name|migrations
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|prefs
operator|.
name|Preferences
import|;
end_import

begin_import
import|import
name|org
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
name|never
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
name|verify
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
DECL|class|PreferencesMigrationsTest
class|class
name|PreferencesMigrationsTest
block|{
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|mainPrefsNode
specifier|private
name|Preferences
name|mainPrefsNode
decl_stmt|;
DECL|field|oldStylePatterns
specifier|private
specifier|final
name|String
index|[]
name|oldStylePatterns
init|=
operator|new
name|String
index|[]
block|{
literal|"\\bibtexkey"
block|,
literal|"\\bibtexkey\\begin{title} - \\format[RemoveBrackets]{\\title}\\end{title}"
block|}
decl_stmt|;
DECL|field|newStylePatterns
specifier|private
specifier|final
name|String
index|[]
name|newStylePatterns
init|=
operator|new
name|String
index|[]
block|{
literal|"[bibtexkey]"
block|,
literal|"[bibtexkey] - [fulltitle]"
block|}
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
block|{
name|prefs
operator|=
name|mock
argument_list|(
name|JabRefPreferences
operator|.
name|class
argument_list|)
expr_stmt|;
name|mainPrefsNode
operator|=
name|mock
argument_list|(
name|Preferences
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOldStyleBibtexkeyPattern0 ()
name|void
name|testOldStyleBibtexkeyPattern0
parameter_list|()
block|{
name|when
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|oldStylePatterns
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|mainPrefsNode
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
literal|null
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|oldStylePatterns
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|prefs
operator|.
name|hasKey
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeImportFileAndDirePatterns
argument_list|(
name|prefs
argument_list|,
name|mainPrefsNode
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|prefs
argument_list|)
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
name|newStylePatterns
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|mainPrefsNode
argument_list|)
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
name|newStylePatterns
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOldStyleBibtexkeyPattern1 ()
name|void
name|testOldStyleBibtexkeyPattern1
parameter_list|()
block|{
name|when
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|oldStylePatterns
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|mainPrefsNode
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
literal|null
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|oldStylePatterns
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|prefs
operator|.
name|hasKey
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeImportFileAndDirePatterns
argument_list|(
name|prefs
argument_list|,
name|mainPrefsNode
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|prefs
argument_list|)
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
name|newStylePatterns
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|mainPrefsNode
argument_list|)
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
name|newStylePatterns
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testArbitraryBibtexkeyPattern ()
name|void
name|testArbitraryBibtexkeyPattern
parameter_list|()
block|{
name|String
name|arbitraryPattern
init|=
literal|"[anyUserPrividedString]"
decl_stmt|;
name|when
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|arbitraryPattern
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|mainPrefsNode
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
literal|null
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|arbitraryPattern
argument_list|)
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeImportFileAndDirePatterns
argument_list|(
name|prefs
argument_list|,
name|mainPrefsNode
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|prefs
argument_list|,
name|never
argument_list|()
argument_list|)
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
name|arbitraryPattern
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|mainPrefsNode
argument_list|,
name|never
argument_list|()
argument_list|)
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_FILENAMEPATTERN
argument_list|,
name|arbitraryPattern
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPreviewStyle ()
name|void
name|testPreviewStyle
parameter_list|()
block|{
name|String
name|oldPreviewStyle
init|=
literal|"<font face=\"sans-serif\">"
operator|+
literal|"<b><i>\\bibtextype</i><a name=\"\\bibtexkey\">\\begin{bibtexkey} (\\bibtexkey)</a>"
operator|+
literal|"\\end{bibtexkey}</b><br>__NEWLINE__"
operator|+
literal|"\\begin{author} \\format[Authors(LastFirst,Initials,Semicolon,Amp),HTMLChars]{\\author}<BR>\\end{author}__NEWLINE__"
operator|+
literal|"\\begin{editor} \\format[Authors(LastFirst,Initials,Semicolon,Amp),HTMLChars]{\\editor} "
operator|+
literal|"<i>(\\format[IfPlural(Eds.,Ed.)]{\\editor})</i><BR>\\end{editor}__NEWLINE__"
operator|+
literal|"\\begin{title} \\format[HTMLChars]{\\title} \\end{title}<BR>__NEWLINE__"
operator|+
literal|"\\begin{chapter} \\format[HTMLChars]{\\chapter}<BR>\\end{chapter}__NEWLINE__"
operator|+
literal|"\\begin{journal}<em>\\format[HTMLChars]{\\journal},</em>\\end{journal}__NEWLINE__"
comment|// Include the booktitle field for @inproceedings, @proceedings, etc.
operator|+
literal|"\\begin{booktitle}<em>\\format[HTMLChars]{\\booktitle},</em>\\end{booktitle}__NEWLINE__"
operator|+
literal|"\\begin{school}<em>\\format[HTMLChars]{\\school},</em>\\end{school}__NEWLINE__"
operator|+
literal|"\\begin{institution}<em>\\format[HTMLChars]{\\institution},</em>\\end{institution}__NEWLINE__"
operator|+
literal|"\\begin{publisher}<em>\\format[HTMLChars]{\\publisher},</em>\\end{publisher}__NEWLINE__"
operator|+
literal|"\\begin{year}<b>\\year</b>\\end{year}\\begin{volume}<i>, \\volume</i>\\end{volume}"
operator|+
literal|"\\begin{pages}, \\format[FormatPagesForHTML]{\\pages} \\end{pages}__NEWLINE__"
operator|+
literal|"\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract} \\end{abstract}__NEWLINE__"
operator|+
literal|"\\begin{review}<BR><BR><b>Review:</b> \\format[HTMLChars]{\\review} \\end{review}"
operator|+
literal|"</dd>__NEWLINE__<p></p></font>"
decl_stmt|;
name|String
name|newPreviewStyle
init|=
literal|"<font face=\"sans-serif\">"
operator|+
literal|"<b><i>\\bibtextype</i><a name=\"\\bibtexkey\">\\begin{bibtexkey} (\\bibtexkey)</a>"
operator|+
literal|"\\end{bibtexkey}</b><br>__NEWLINE__"
operator|+
literal|"\\begin{author} \\format[Authors(LastFirst,Initials,Semicolon,Amp),HTMLChars]{\\author}<BR>\\end{author}__NEWLINE__"
operator|+
literal|"\\begin{editor} \\format[Authors(LastFirst,Initials,Semicolon,Amp),HTMLChars]{\\editor} "
operator|+
literal|"<i>(\\format[IfPlural(Eds.,Ed.)]{\\editor})</i><BR>\\end{editor}__NEWLINE__"
operator|+
literal|"\\begin{title} \\format[HTMLChars]{\\title} \\end{title}<BR>__NEWLINE__"
operator|+
literal|"\\begin{chapter} \\format[HTMLChars]{\\chapter}<BR>\\end{chapter}__NEWLINE__"
operator|+
literal|"\\begin{journal}<em>\\format[HTMLChars]{\\journal},</em>\\end{journal}__NEWLINE__"
comment|// Include the booktitle field for @inproceedings, @proceedings, etc.
operator|+
literal|"\\begin{booktitle}<em>\\format[HTMLChars]{\\booktitle},</em>\\end{booktitle}__NEWLINE__"
operator|+
literal|"\\begin{school}<em>\\format[HTMLChars]{\\school},</em>\\end{school}__NEWLINE__"
operator|+
literal|"\\begin{institution}<em>\\format[HTMLChars]{\\institution},</em>\\end{institution}__NEWLINE__"
operator|+
literal|"\\begin{publisher}<em>\\format[HTMLChars]{\\publisher},</em>\\end{publisher}__NEWLINE__"
operator|+
literal|"\\begin{year}<b>\\year</b>\\end{year}\\begin{volume}<i>, \\volume</i>\\end{volume}"
operator|+
literal|"\\begin{pages}, \\format[FormatPagesForHTML]{\\pages} \\end{pages}__NEWLINE__"
operator|+
literal|"\\begin{abstract}<BR><BR><b>Abstract:</b> \\format[HTMLChars]{\\abstract} \\end{abstract}__NEWLINE__"
operator|+
literal|"\\begin{comment}<BR><BR><b>Comment:</b> \\format[HTMLChars]{\\comment} \\end{comment}"
operator|+
literal|"</dd>__NEWLINE__<p></p></font>"
decl_stmt|;
name|prefs
operator|.
name|setPreviewStyle
argument_list|(
name|oldPreviewStyle
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|prefs
operator|.
name|getPreviewStyle
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|oldPreviewStyle
argument_list|)
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradePreviewStyleFromReviewToComment
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|prefs
argument_list|)
operator|.
name|setPreviewStyle
argument_list|(
name|newPreviewStyle
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

