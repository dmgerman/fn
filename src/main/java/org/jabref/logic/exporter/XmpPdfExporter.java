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
name|Charset
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
name|Path
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
name|Objects
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
name|StandardFileType
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
name|xmp
operator|.
name|XmpPreferences
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
name|xmp
operator|.
name|XmpUtilWriter
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
name|BibDatabaseContext
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

begin_class
DECL|class|XmpPdfExporter
specifier|public
class|class
name|XmpPdfExporter
extends|extends
name|Exporter
block|{
DECL|field|xmpPreferences
specifier|private
specifier|final
name|XmpPreferences
name|xmpPreferences
decl_stmt|;
DECL|method|XmpPdfExporter (XmpPreferences xmpPreferences)
specifier|public
name|XmpPdfExporter
parameter_list|(
name|XmpPreferences
name|xmpPreferences
parameter_list|)
block|{
name|super
argument_list|(
literal|"pdf"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"XMP-annotated PDF"
argument_list|)
argument_list|,
name|StandardFileType
operator|.
name|PDF
argument_list|)
expr_stmt|;
name|this
operator|.
name|xmpPreferences
operator|=
name|xmpPreferences
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|export (BibDatabaseContext databaseContext, Path pdfFile, Charset encoding, List<BibEntry> entries)
specifier|public
name|void
name|export
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|Path
name|pdfFile
parameter_list|,
name|Charset
name|encoding
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
throws|throws
name|Exception
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|pdfFile
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entries
argument_list|)
expr_stmt|;
if|if
condition|(
name|pdfFile
operator|.
name|toString
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
condition|)
block|{
name|XmpUtilWriter
operator|.
name|writeXmp
argument_list|(
name|pdfFile
argument_list|,
name|entries
argument_list|,
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|xmpPreferences
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

