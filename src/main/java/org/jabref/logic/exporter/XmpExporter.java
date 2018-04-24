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
name|io
operator|.
name|BufferedWriter
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
name|Files
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
name|nio
operator|.
name|file
operator|.
name|Paths
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

begin_comment
comment|/**  * A custom exporter to write bib entries to a .xmp file for further processing  * in other scenarios and applications. The xmp metadata are written in dublin  * core format.  */
end_comment

begin_class
DECL|class|XmpExporter
specifier|public
class|class
name|XmpExporter
extends|extends
name|Exporter
block|{
DECL|field|XMP_SPLIT_PATTERN
specifier|private
specifier|static
specifier|final
name|String
name|XMP_SPLIT_PATTERN
init|=
literal|"split"
decl_stmt|;
DECL|field|xmpPreferences
specifier|private
specifier|final
name|XmpPreferences
name|xmpPreferences
decl_stmt|;
DECL|method|XmpExporter (XmpPreferences xmpPreferences)
specifier|public
name|XmpExporter
parameter_list|(
name|XmpPreferences
name|xmpPreferences
parameter_list|)
block|{
name|super
argument_list|(
literal|"xmp"
argument_list|,
name|FileType
operator|.
name|PLAIN_XMP
operator|.
name|getDescription
argument_list|()
argument_list|,
name|FileType
operator|.
name|PLAIN_XMP
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
DECL|method|export (BibDatabaseContext databaseContext, Path file, Charset encoding, List<BibEntry> entries)
specifier|public
name|void
name|export
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|Path
name|file
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
name|file
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
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// This is a distinction between writing all entries from the supplied list to a single .xmp file,
comment|// or write every entry to a separate file.
if|if
condition|(
name|file
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
name|XMP_SPLIT_PATTERN
argument_list|)
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
comment|// Avoid situations, where two cite keys are null
name|Path
name|entryFile
decl_stmt|;
name|String
name|suffix
init|=
name|entry
operator|.
name|getId
argument_list|()
operator|+
literal|"_"
operator|+
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|+
literal|".xmp"
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|getParent
argument_list|()
operator|==
literal|null
condition|)
block|{
name|entryFile
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|suffix
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entryFile
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|file
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
operator|+
literal|"/"
operator|+
name|suffix
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|writeBibToXmp
argument_list|(
name|entryFile
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|this
operator|.
name|writeBibToXmp
argument_list|(
name|file
argument_list|,
name|entries
argument_list|,
name|encoding
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|writeBibToXmp (Path file, List<BibEntry> entries, Charset encoding)
specifier|private
name|void
name|writeBibToXmp
parameter_list|(
name|Path
name|file
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|Charset
name|encoding
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|xmpContent
init|=
name|XmpUtilWriter
operator|.
name|generateXmpStringWithoutXmpDeclaration
argument_list|(
name|entries
argument_list|,
name|this
operator|.
name|xmpPreferences
argument_list|)
decl_stmt|;
try|try
init|(
name|BufferedWriter
name|writer
init|=
name|Files
operator|.
name|newBufferedWriter
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|)
init|)
block|{
name|writer
operator|.
name|write
argument_list|(
name|xmpContent
argument_list|)
expr_stmt|;
name|writer
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

