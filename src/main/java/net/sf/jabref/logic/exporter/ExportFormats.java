begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
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
name|Collections
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
name|TreeMap
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

begin_class
DECL|class|ExportFormats
specifier|public
class|class
name|ExportFormats
block|{
DECL|field|EXPORT_FORMATS
specifier|private
specifier|static
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
name|EXPORT_FORMATS
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Global variable that is used for counting output entries when exporting:
DECL|field|entryNumber
specifier|public
specifier|static
name|int
name|entryNumber
decl_stmt|;
DECL|method|initAllExports (Map<String, ExportFormat> customFormats)
specifier|public
specifier|static
name|void
name|initAllExports
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|ExportFormat
argument_list|>
name|customFormats
parameter_list|)
block|{
name|ExportFormats
operator|.
name|EXPORT_FORMATS
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Initialize Build-In Export Formats
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"HTML"
argument_list|,
literal|"html"
argument_list|,
literal|"html"
argument_list|,
literal|null
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Simple HTML"
argument_list|)
argument_list|,
literal|"simplehtml"
argument_list|,
literal|"simplehtml"
argument_list|,
literal|null
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"DocBook 4.4"
argument_list|,
literal|"docbook"
argument_list|,
literal|"docbook"
argument_list|,
literal|null
argument_list|,
literal|".xml"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"DIN 1505"
argument_list|,
literal|"din1505"
argument_list|,
literal|"din1505winword"
argument_list|,
literal|"din1505"
argument_list|,
literal|".rtf"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"BibTeXML"
argument_list|,
literal|"bibtexml"
argument_list|,
literal|"bibtexml"
argument_list|,
literal|null
argument_list|,
literal|".xml"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"BibO RDF"
argument_list|,
literal|"bibordf"
argument_list|,
literal|"bibordf"
argument_list|,
literal|null
argument_list|,
literal|".rdf"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ModsExportFormat
argument_list|()
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"HTML table"
argument_list|)
argument_list|,
literal|"tablerefs"
argument_list|,
literal|"tablerefs"
argument_list|,
literal|"tablerefs"
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"HTML list"
argument_list|)
argument_list|,
literal|"listrefs"
argument_list|,
literal|"listrefs"
argument_list|,
literal|"listrefs"
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"HTML table (with Abstract& BibTeX)"
argument_list|)
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"Harvard RTF"
argument_list|,
literal|"harvard"
argument_list|,
literal|"harvard"
argument_list|,
literal|"harvard"
argument_list|,
literal|".rtf"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"ISO 690"
argument_list|,
literal|"iso690rtf"
argument_list|,
literal|"iso690RTF"
argument_list|,
literal|"iso690rtf"
argument_list|,
literal|".rtf"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"ISO 690"
argument_list|,
literal|"iso690txt"
argument_list|,
literal|"iso690"
argument_list|,
literal|"iso690txt"
argument_list|,
literal|".txt"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"Endnote"
argument_list|,
literal|"endnote"
argument_list|,
literal|"EndNote"
argument_list|,
literal|"endnote"
argument_list|,
literal|".txt"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"OpenOffice/LibreOffice CSV"
argument_list|,
literal|"oocsv"
argument_list|,
literal|"openoffice-csv"
argument_list|,
literal|"openoffice"
argument_list|,
literal|".csv"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormat
name|ef
init|=
operator|new
name|ExportFormat
argument_list|(
literal|"RIS"
argument_list|,
literal|"ris"
argument_list|,
literal|"ris"
argument_list|,
literal|"ris"
argument_list|,
literal|".ris"
argument_list|)
decl_stmt|;
name|ef
operator|.
name|setEncoding
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
name|ef
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
literal|"MIS Quarterly"
argument_list|,
literal|"misq"
argument_list|,
literal|"misq"
argument_list|,
literal|"misq"
argument_list|,
literal|".rtf"
argument_list|)
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|OpenOfficeDocumentCreator
argument_list|()
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|OpenDocumentSpreadsheetCreator
argument_list|()
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|MSBibExportFormat
argument_list|()
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|putFormat
argument_list|(
operator|new
name|ModsExportFormat
argument_list|()
argument_list|)
expr_stmt|;
comment|// Now add custom export formats
for|for
control|(
name|IExportFormat
name|format
range|:
name|customFormats
operator|.
name|values
argument_list|()
control|)
block|{
name|ExportFormats
operator|.
name|putFormat
argument_list|(
name|format
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Build a string listing of all available export formats.      *      * @param maxLineLength      *            The max line length before a line break must be added.      * @param linePrefix      *            If a line break is added, this prefix will be inserted at the      *            beginning of the next line.      * @return The string describing available formats.      */
DECL|method|getConsoleExportList (int maxLineLength, int firstLineSubtr, String linePrefix)
specifier|public
specifier|static
name|String
name|getConsoleExportList
parameter_list|(
name|int
name|maxLineLength
parameter_list|,
name|int
name|firstLineSubtr
parameter_list|,
name|String
name|linePrefix
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|lastBreak
init|=
operator|-
name|firstLineSubtr
decl_stmt|;
for|for
control|(
name|String
name|name
range|:
name|ExportFormats
operator|.
name|EXPORT_FORMATS
operator|.
name|keySet
argument_list|()
control|)
block|{
if|if
condition|(
operator|(
operator|(
name|sb
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
name|sb
operator|.
name|append
argument_list|(
literal|",\n"
argument_list|)
expr_stmt|;
name|lastBreak
operator|=
name|sb
operator|.
name|length
argument_list|()
expr_stmt|;
name|sb
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
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Get a Map of all export formats.      * @return A Map containing all export formats, mapped to their console names.      */
DECL|method|getExportFormats ()
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
name|getExportFormats
parameter_list|()
block|{
comment|// It is perhaps overly paranoid to make a defensive copy in this case:
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|ExportFormats
operator|.
name|EXPORT_FORMATS
argument_list|)
return|;
block|}
comment|/**      * Look up the named export format.      *      * @param consoleName      *            The export name given in the JabRef console help information.      * @return The ExportFormat, or null if no exportformat with that name is      *         registered.      */
DECL|method|getExportFormat (String consoleName)
specifier|public
specifier|static
name|IExportFormat
name|getExportFormat
parameter_list|(
name|String
name|consoleName
parameter_list|)
block|{
return|return
name|ExportFormats
operator|.
name|EXPORT_FORMATS
operator|.
name|get
argument_list|(
name|consoleName
argument_list|)
return|;
block|}
DECL|method|putFormat (IExportFormat format)
specifier|private
specifier|static
name|void
name|putFormat
parameter_list|(
name|IExportFormat
name|format
parameter_list|)
block|{
name|ExportFormats
operator|.
name|EXPORT_FORMATS
operator|.
name|put
argument_list|(
name|format
operator|.
name|getConsoleName
argument_list|()
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

