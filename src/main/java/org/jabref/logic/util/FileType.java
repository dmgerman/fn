begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|EnumSet
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
name|java
operator|.
name|util
operator|.
name|StringJoiner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * This enum contains a list of file types used in open and save dialogs.  *  * @implNote Enter the extensions without a dot! The dot is added implicitly.  */
end_comment

begin_enum
DECL|enum|FileType
specifier|public
enum|enum
name|FileType
block|{
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
literal|"Library"
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
DECL|enumConstant|BIBTEXML_XML_ONLY
DECL|enumConstant|Localization.lang
name|BIBTEXML_XML_ONLY
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
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|BIBORDF
DECL|enumConstant|Localization.lang
name|BIBORDF
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Bib 0"
argument_list|)
argument_list|,
literal|"rdf"
argument_list|)
block|,
DECL|enumConstant|BIBLIOSCAPE
DECL|enumConstant|Localization.lang
name|BIBLIOSCAPE
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
DECL|enumConstant|CITATION_STYLE
DECL|enumConstant|Localization.lang
name|CITATION_STYLE
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"CSL"
argument_list|)
argument_list|,
literal|"csl"
argument_list|)
block|,
DECL|enumConstant|DOCBOOK
DECL|enumConstant|Localization.lang
name|DOCBOOK
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Docbook 4.4"
argument_list|)
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|DIN_1505
DECL|enumConstant|Localization.lang
name|DIN_1505
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"DIN 1505"
argument_list|)
argument_list|,
literal|"rtf"
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
DECL|enumConstant|ENDNOTE_TXT
DECL|enumConstant|Localization.lang
name|ENDNOTE_TXT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Endnote"
argument_list|)
argument_list|,
literal|"txt"
argument_list|)
block|,
comment|//for export
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
DECL|enumConstant|HARVARD_RTF
DECL|enumConstant|Localization.lang
name|HARVARD_RTF
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"Harvard"
argument_list|)
argument_list|,
literal|"rtf"
argument_list|)
block|,
DECL|enumConstant|HTML_LIST
DECL|enumConstant|Localization.lang
name|HTML_LIST
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
literal|"HTML list"
argument_list|)
argument_list|)
argument_list|,
literal|"html"
argument_list|)
block|,
DECL|enumConstant|HTML_TABLE
DECL|enumConstant|Localization.lang
name|HTML_TABLE
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
literal|"HTML table"
argument_list|)
argument_list|)
argument_list|,
literal|"html"
argument_list|)
block|,
DECL|enumConstant|HTML_TABLE_WITH_ABSTRACT
DECL|enumConstant|Localization.lang
name|HTML_TABLE_WITH_ABSTRACT
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
literal|"HTML table (with Abstract& BibTeX)"
argument_list|)
argument_list|)
argument_list|,
literal|"html"
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
DECL|enumConstant|ISO_690_RTF
DECL|enumConstant|Localization.lang
name|ISO_690_RTF
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"ISO 690"
argument_list|)
argument_list|,
literal|"rtf"
argument_list|)
block|,
DECL|enumConstant|ISO_690_TXT
DECL|enumConstant|Localization.lang
name|ISO_690_TXT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"ISO 690"
argument_list|)
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
DECL|enumConstant|MIS_QUARTERLY
DECL|enumConstant|Localization.lang
name|MIS_QUARTERLY
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"MIS Quarterly"
argument_list|)
argument_list|,
literal|"rtf"
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
literal|"MS Office 2007 bib"
argument_list|)
argument_list|,
literal|"xml"
argument_list|)
block|,
DECL|enumConstant|OO_LO
DECL|enumConstant|Localization.lang
name|OO_LO
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"OpenOffice/LibreOffice"
argument_list|)
argument_list|,
literal|"csv"
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
DECL|enumConstant|SIMPLE_HTML
DECL|enumConstant|Localization.lang
name|SIMPLE_HTML
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
literal|"Simple HTML"
argument_list|)
argument_list|)
argument_list|,
literal|"html"
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
block|,
DECL|enumConstant|ODS
DECL|enumConstant|Localization.lang
name|ODS
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
literal|"OpenDocument spreadsheet"
argument_list|)
argument_list|)
argument_list|,
literal|"ods"
argument_list|)
block|,
DECL|enumConstant|SXC
DECL|enumConstant|Localization.lang
name|SXC
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"OpenOffice/LibreOffice Calc"
argument_list|)
argument_list|,
literal|"sxc"
argument_list|)
block|,
DECL|enumConstant|HTML
DECL|enumConstant|Localization.lang
name|HTML
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"HTML"
argument_list|)
argument_list|,
literal|"html"
argument_list|)
block|,
DECL|enumConstant|RTF
DECL|enumConstant|Localization.lang
name|RTF
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"RTF"
argument_list|)
argument_list|,
literal|"rtf"
argument_list|)
block|,
DECL|enumConstant|RDF
DECL|enumConstant|Localization.lang
name|RDF
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"RDF"
argument_list|)
argument_list|,
literal|"rdf"
argument_list|)
block|,
DECL|enumConstant|CSV
DECL|enumConstant|Localization.lang
name|CSV
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"CSV"
argument_list|)
argument_list|,
literal|"csv"
argument_list|)
block|,
DECL|enumConstant|DEFAULT
DECL|enumConstant|Localization.lang
name|DEFAULT
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 file"
argument_list|,
literal|"DEFAULT"
argument_list|)
argument_list|,
literal|"default"
argument_list|)
block|;
DECL|field|ALL_FILE_TYPES
specifier|private
specifier|static
specifier|final
name|EnumSet
argument_list|<
name|FileType
argument_list|>
name|ALL_FILE_TYPES
init|=
name|EnumSet
operator|.
name|allOf
argument_list|(
name|FileType
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|extensions
specifier|private
specifier|final
name|String
index|[]
name|extensions
decl_stmt|;
DECL|field|description
specifier|private
specifier|final
name|String
name|description
decl_stmt|;
DECL|method|FileType (String description, String... extensions)
name|FileType
parameter_list|(
name|String
name|description
parameter_list|,
name|String
modifier|...
name|extensions
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|description
expr_stmt|;
name|this
operator|.
name|extensions
operator|=
name|extensions
expr_stmt|;
block|}
DECL|method|getExtensions ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExtensions
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|extensions
argument_list|)
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
name|extensions
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
name|extensions
index|[
literal|0
index|]
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|getExtensionsWithDot ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getExtensionsWithDot
parameter_list|()
block|{
return|return
name|getExtensions
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|extension
lambda|->
literal|"."
operator|+
name|extension
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|parse (String fileExtension)
specifier|public
specifier|static
name|FileType
name|parse
parameter_list|(
name|String
name|fileExtension
parameter_list|)
block|{
name|Optional
argument_list|<
name|FileType
argument_list|>
name|fileType
init|=
name|ALL_FILE_TYPES
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|f
lambda|->
name|f
operator|.
name|getExtensionsWithDot
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|anyMatch
argument_list|(
name|fileExtension
operator|::
name|equals
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
decl_stmt|;
return|return
name|fileType
operator|.
name|orElse
argument_list|(
name|FileType
operator|.
name|DEFAULT
argument_list|)
return|;
block|}
block|}
end_enum

end_unit

