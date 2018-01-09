begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|FileType
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

begin_class
DECL|class|ExporterFactory
specifier|public
class|class
name|ExporterFactory
block|{
comment|/**      * Global variable that is used for counting output entries when exporting:      *      * @deprecated find a better way to do this      */
DECL|field|entryNumber
annotation|@
name|Deprecated
specifier|public
specifier|static
name|int
name|entryNumber
decl_stmt|;
DECL|field|exporters
specifier|private
specifier|final
name|List
argument_list|<
name|Exporter
argument_list|>
name|exporters
decl_stmt|;
DECL|method|ExporterFactory (List<Exporter> exporters)
specifier|private
name|ExporterFactory
parameter_list|(
name|List
argument_list|<
name|Exporter
argument_list|>
name|exporters
parameter_list|)
block|{
name|this
operator|.
name|exporters
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|exporters
argument_list|)
expr_stmt|;
block|}
DECL|method|create (Map<String, TemplateExporter> customFormats, LayoutFormatterPreferences layoutPreferences, SavePreferences savePreferences)
specifier|public
specifier|static
name|ExporterFactory
name|create
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|TemplateExporter
argument_list|>
name|customFormats
parameter_list|,
name|LayoutFormatterPreferences
name|layoutPreferences
parameter_list|,
name|SavePreferences
name|savePreferences
parameter_list|)
block|{
name|List
argument_list|<
name|Exporter
argument_list|>
name|exporters
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Initialize build-in exporters
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"html"
argument_list|,
literal|"html"
argument_list|,
literal|null
argument_list|,
name|FileType
operator|.
name|HTML
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"simplehtml"
argument_list|,
literal|"simplehtml"
argument_list|,
literal|null
argument_list|,
name|FileType
operator|.
name|SIMPLE_HTML
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"docbook"
argument_list|,
literal|"docbook"
argument_list|,
literal|null
argument_list|,
name|FileType
operator|.
name|DOCBOOK
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"din1505"
argument_list|,
literal|"din1505winword"
argument_list|,
literal|"din1505"
argument_list|,
name|FileType
operator|.
name|DIN_1505
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"bibordf"
argument_list|,
literal|"bibordf"
argument_list|,
literal|null
argument_list|,
name|FileType
operator|.
name|BIBORDF
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"tablerefs"
argument_list|,
literal|"tablerefs"
argument_list|,
literal|"tablerefs"
argument_list|,
name|FileType
operator|.
name|HTML_TABLE
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"listrefs"
argument_list|,
literal|"listrefs"
argument_list|,
literal|"listrefs"
argument_list|,
name|FileType
operator|.
name|HTML_LIST
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"tablerefsabsbib"
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
name|FileType
operator|.
name|HTML_TABLE_WITH_ABSTRACT
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"harvard"
argument_list|,
literal|"harvard"
argument_list|,
literal|"harvard"
argument_list|,
name|FileType
operator|.
name|HARVARD_RTF
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"iso690rtf"
argument_list|,
literal|"iso690RTF"
argument_list|,
literal|"iso690rtf"
argument_list|,
name|FileType
operator|.
name|ISO_690_RTF
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"iso690txt"
argument_list|,
literal|"iso690"
argument_list|,
literal|"iso690txt"
argument_list|,
name|FileType
operator|.
name|ISO_690_TXT
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"endnote"
argument_list|,
literal|"EndNote"
argument_list|,
literal|"endnote"
argument_list|,
name|FileType
operator|.
name|ENDNOTE_TXT
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"oocsv"
argument_list|,
literal|"openoffice-csv"
argument_list|,
literal|"openoffice"
argument_list|,
name|FileType
operator|.
name|OO_LO
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"ris"
argument_list|,
literal|"ris"
argument_list|,
literal|"ris"
argument_list|,
name|FileType
operator|.
name|RIS
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
operator|.
name|withEncoding
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|TemplateExporter
argument_list|(
literal|"misq"
argument_list|,
literal|"misq"
argument_list|,
literal|"misq"
argument_list|,
name|FileType
operator|.
name|MIS_QUARTERLY
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|BibTeXMLExporter
argument_list|()
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|OpenOfficeDocumentCreator
argument_list|()
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|OpenDocumentSpreadsheetCreator
argument_list|()
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|MSBibExporter
argument_list|()
argument_list|)
expr_stmt|;
name|exporters
operator|.
name|add
argument_list|(
operator|new
name|ModsExporter
argument_list|()
argument_list|)
expr_stmt|;
comment|// Now add custom export formats
name|exporters
operator|.
name|addAll
argument_list|(
name|customFormats
operator|.
name|values
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|ExporterFactory
argument_list|(
name|exporters
argument_list|)
return|;
block|}
DECL|method|create (JabRefPreferences preferences, JournalAbbreviationLoader abbreviationLoader)
specifier|public
specifier|static
name|ExporterFactory
name|create
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|,
name|JournalAbbreviationLoader
name|abbreviationLoader
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|TemplateExporter
argument_list|>
name|customFormats
init|=
name|preferences
operator|.
name|customExports
operator|.
name|getCustomExportFormats
argument_list|(
name|preferences
argument_list|,
name|abbreviationLoader
argument_list|)
decl_stmt|;
name|LayoutFormatterPreferences
name|layoutPreferences
init|=
name|preferences
operator|.
name|getLayoutFormatterPreferences
argument_list|(
name|abbreviationLoader
argument_list|)
decl_stmt|;
name|SavePreferences
name|savePreferences
init|=
name|SavePreferences
operator|.
name|loadForExportFromPreferences
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
return|return
name|create
argument_list|(
name|customFormats
argument_list|,
name|layoutPreferences
argument_list|,
name|savePreferences
argument_list|)
return|;
block|}
comment|/**      * Build a string listing of all available exporters.      *      * @param maxLineLength The max line length before a line break must be added.      * @param linePrefix    If a line break is added, this prefix will be inserted at the beginning of the next line.      * @return The string describing available exporters.      */
DECL|method|getExportersAsString (int maxLineLength, int firstLineSubtraction, String linePrefix)
specifier|public
name|String
name|getExportersAsString
parameter_list|(
name|int
name|maxLineLength
parameter_list|,
name|int
name|firstLineSubtraction
parameter_list|,
name|String
name|linePrefix
parameter_list|)
block|{
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|lastBreak
init|=
operator|-
name|firstLineSubtraction
decl_stmt|;
for|for
control|(
name|Exporter
name|exporter
range|:
name|exporters
control|)
block|{
name|String
name|name
init|=
name|exporter
operator|.
name|getId
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|builder
operator|.
name|length
argument_list|()
operator|+
literal|2
operator|+
name|name
operator|.
name|length
argument_list|()
operator|)
operator|-
name|lastBreak
operator|)
operator|>
name|maxLineLength
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|",\n"
argument_list|)
expr_stmt|;
name|lastBreak
operator|=
name|builder
operator|.
name|length
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|linePrefix
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|builder
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
return|return
name|builder
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Get a list of all exporters.      *      * @return A list containing all exporters      */
DECL|method|getExporters ()
specifier|public
name|List
argument_list|<
name|Exporter
argument_list|>
name|getExporters
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|exporters
argument_list|)
return|;
block|}
comment|/**      * Look up the named exporter (case-insensitive).      *      * @param consoleName The export name given in the JabRef console help information.      * @return The exporter, or an empty option if no exporter with that name is registered.      */
DECL|method|getExporterByName (String consoleName)
specifier|public
name|Optional
argument_list|<
name|Exporter
argument_list|>
name|getExporterByName
parameter_list|(
name|String
name|consoleName
parameter_list|)
block|{
return|return
name|exporters
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|exporter
lambda|->
name|exporter
operator|.
name|getId
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|consoleName
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
block|}
end_class

end_unit

