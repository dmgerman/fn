begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * This enum contains all kind of file extensions for open and save dialogs.  *  * Important: Enter the extension without a dot! The dot is added implicitly.  */
end_comment

begin_enum
DECL|enum|FileExtensions
specifier|public
enum|enum
name|FileExtensions
block|{
comment|//important: No dot before the extension!
DECL|enumConstant|BIBTEX_DB
DECL|enumConstant|String.format
name|BIBTEX_DB
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%1s %2s"
argument_list|,
literal|"BibTex"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Database"
argument_list|)
argument_list|)
argument_list|,
literal|"bib"
argument_list|)
block|,
DECL|enumConstant|BIBTEXML
DECL|enumConstant|Localization.lang
name|BIBTEXML
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"BibTeXML"
argument_list|)
argument_list|,
literal|"bibx"
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|BILBIOSCAPE
DECL|enumConstant|Localization.lang
name|BILBIOSCAPE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Biblioscape"
argument_list|)
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|COPAC
DECL|enumConstant|Localization.lang
name|COPAC
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Copac"
argument_list|)
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|ENDNOTE
DECL|enumConstant|Localization.lang
name|ENDNOTE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Endnote/Refer"
argument_list|)
argument_list|,
literal|"ref"
argument_list|,
literal|"enw"
argument_list|)
block|,
DECL|enumConstant|FREECITE
DECL|enumConstant|Localization.lang
name|FREECITE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"FreeCite"
argument_list|)
argument_list|,
literal|"txt"
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|INSPEC
DECL|enumConstant|Localization.lang
name|INSPEC
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"INSPEC"
argument_list|)
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|ISI
DECL|enumConstant|Localization.lang
name|ISI
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"ISI"
argument_list|)
argument_list|,
literal|"isi"
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|MEDLINE
DECL|enumConstant|Localization.lang
name|MEDLINE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Medline"
argument_list|)
argument_list|,
literal|"nbib"
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|MEDLINE_PLAIN
DECL|enumConstant|Localization.lang
name|MEDLINE_PLAIN
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"MedlinePlain"
argument_list|)
argument_list|,
literal|"nbib"
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|MODS
DECL|enumConstant|Localization.lang
name|MODS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"MODS"
argument_list|)
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|MSBIB
DECL|enumConstant|Localization.lang
name|MSBIB
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"MSBib"
argument_list|)
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|OVID
DECL|enumConstant|Localization.lang
name|OVID
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Ovid"
argument_list|)
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|PDF_CONTENT
DECL|enumConstant|Localization.lang
name|PDF_CONTENT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"PDF content "
argument_list|)
argument_list|,
literal|"pdf"
argument_list|)
block|,
DECL|enumConstant|PUBMED
DECL|enumConstant|Localization.lang
name|PUBMED
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"PubMed"
argument_list|)
argument_list|,
literal|"fcgi"
argument_list|)
block|,
DECL|enumConstant|REPEC
name|REPEC
argument_list|(
literal|"REPEC New Economic Papers (NEP)"
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|RIS
DECL|enumConstant|Localization.lang
name|RIS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"RIS"
argument_list|)
argument_list|,
literal|"ris"
argument_list|)
block|,
DECL|enumConstant|SILVER_PLATTER
DECL|enumConstant|Localization.lang
name|SILVER_PLATTER
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"SilverPlatter"
argument_list|)
argument_list|,
literal|"dat"
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|XMP
DECL|enumConstant|Localization.lang
name|XMP
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"XMP-annotated PDF"
argument_list|)
argument_list|,
literal|"pdf"
argument_list|)
block|,
DECL|enumConstant|AUX
DECL|enumConstant|Localization.lang
name|AUX
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"AUX"
argument_list|)
argument_list|,
literal|"aux"
argument_list|)
block|,
DECL|enumConstant|JSTYLE
DECL|enumConstant|Localization.lang
name|JSTYLE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Style file"
argument_list|)
argument_list|,
literal|"jstyle"
argument_list|)
block|,
DECL|enumConstant|LAYOUT
DECL|enumConstant|Localization.lang
name|LAYOUT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Custom layout file"
argument_list|)
argument_list|,
literal|"layout"
argument_list|)
block|,
DECL|enumConstant|TERMS
DECL|enumConstant|Localization.lang
name|TERMS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Protected terms file"
argument_list|)
argument_list|,
literal|"terms"
argument_list|)
block|,
DECL|enumConstant|TXT
DECL|enumConstant|Localization.lang
name|TXT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Plain text"
argument_list|)
argument_list|)
argument_list|,
literal|"txt"
argument_list|)
block|,
DECL|enumConstant|CLASS
DECL|enumConstant|Localization.lang
name|CLASS
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"CLASS"
argument_list|)
argument_list|,
literal|"class"
argument_list|)
block|,
DECL|enumConstant|JAR
DECL|enumConstant|Localization.lang
name|JAR
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"JAR"
argument_list|)
argument_list|,
literal|"jar"
argument_list|)
block|,
DECL|enumConstant|XML
DECL|enumConstant|Localization.lang
name|XML
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"XML"
argument_list|)
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|ZIP
DECL|enumConstant|Localization.lang
name|ZIP
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"ZIP"
argument_list|)
argument_list|,
literal|"zip"
argument_list|)
block|;
DECL|field|extension
specifier|private
specifier|final
name|String
index|[]
name|extension
decl_stmt|;
DECL|field|description
specifier|private
specifier|final
name|String
name|description
decl_stmt|;
DECL|method|FileExtensions (String description, String... extension)
specifier|private
name|FileExtensions
parameter_list|(
name|String
name|description
parameter_list|,
name|String
modifier|...
name|extension
parameter_list|)
block|{
name|this
operator|.
name|extension
operator|=
name|extension
expr_stmt|;
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
block|}
comment|//Array because a) is varags and b) gets passed as varags parameter to FileExtensionNameFilter
DECL|method|getExtensions ()
specifier|public
name|String
index|[]
name|getExtensions
parameter_list|()
block|{
return|return
name|extension
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
name|StringJoiner
name|sj
init|=
operator|new
name|StringJoiner
argument_list|(
literal|", "
argument_list|,
name|description
operator|+
literal|" ("
argument_list|,
literal|")"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|ext
range|:
name|extension
control|)
block|{
name|sj
operator|.
name|add
argument_list|(
literal|"*."
operator|+
name|ext
argument_list|)
expr_stmt|;
block|}
return|return
name|sj
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getFirstExtensionWithDot ()
specifier|public
name|String
name|getFirstExtensionWithDot
parameter_list|()
block|{
return|return
literal|"."
operator|+
name|extension
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
return|;
block|}
block|}
end_enum

end_unit

